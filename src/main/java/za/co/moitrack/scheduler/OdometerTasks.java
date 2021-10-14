package za.co.moitrack.scheduler;

import net.javacrumbs.shedlock.core.SchedulerLock;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import za.co.moitrack.data.model.Odometer;
import za.co.moitrack.data.model.Tracking;
import za.co.moitrack.data.repository.TrackingRepository;
import za.co.moitrack.service.OdometerService;
import za.co.moitrack.service.VehicleService;
import net.javacrumbs.shedlock.core.SchedulerLock;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class OdometerTasks {
    @Autowired
    private TrackingRepository trackingRepository;
    @Autowired
    private OdometerService odometerService;
    private Log log = LogFactory.getLog(this.getClass().getName());

    /**
     * This methods reads the incoming odometer value from the tracking collection and compares this value to the
     *
     * @zeroOdometer value from the Odometer collection. @zeroOdometer value is the value of the odometer recorded since
     * the clearing of codes on the vehicle's OBD system. The difference is then added to the @realOdoReading which is the true value of the odometer reading as seen from the vehicle's dashboard.
     * <p>
     * If the value of the incoming odometer is zero, then the car must have gone in for service and hence the @zeroOdometer will be reset to zero.
     * <p>
     * If the value of the incoming odometer is less than the previous value, the vehicle must have gone in for service and had the codes cleared but the
     * CAN connector was not put back in before the vehicle was driven out. Therefore an alert will be sent out by setting @alertFlag value to true in order to notify the driver of the vehicle
     * to put the connector in.
     */

    @Scheduled(cron = "0 */3 * * * *")
    @SchedulerLock(name = "odometerTask", lockAtMostForString = "PT2M", lockAtLeastForString = "PT2M")
    public void calculateOdometers() {
        Collection<Tracking> trackingCollection = trackingRepository.findAll();
        double realOdoReading;
        double realOdometer;
        double zeroOdometer;

        for (Tracking tracking : trackingCollection) {
            try {
                String imeiValue = tracking.getImei();
                String regex = "\\d{15}+$";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(imeiValue);
                if (matcher.matches()) {
                    Odometer odometer = odometerService.findOne(tracking.getImei());
                    realOdometer = odometer.getActualOdometer();
                    zeroOdometer = odometer.getZeroOdometer();
                    String ignitionState = tracking.getPayload().getDriveStates().getIgnitionState();
                    Double currentOdometer = tracking.getPayload().getOdometerReading();
                    Double currentSpeed = Double.valueOf(tracking.getPayload().getGps().getSpeed());
                    DateTime timeStamp = tracking.getPayload().getTimeStamp();

                    if ((currentOdometer.compareTo(zeroOdometer) > 0) && ignitionState.equals("true") && (currentSpeed > 5)) {
                        realOdoReading = (currentOdometer - zeroOdometer) + realOdometer;
                        odometer.setTimeStamp(timeStamp);
                        odometer.setActualOdometer(realOdoReading);
                        odometer.setZeroOdometer(currentOdometer);
                        odometer.setAlertFlag("false");
                        Odometer latestOdo = this.buildOdometer(odometer);
                        odometerService.save(latestOdo);
                        log.debug(latestOdo.toString());
                    } else if ((currentOdometer != 0.0) && (currentOdometer.compareTo(zeroOdometer) < 0) && ignitionState.equals("true") && (currentSpeed > 5)) {
                        realOdometer = (currentOdometer + realOdometer);
                        odometer.setTimeStamp(timeStamp);
                        odometer.setActualOdometer(realOdometer);
                        odometer.setZeroOdometer(currentOdometer);
                        odometer.setAlertFlag("true");
                        Odometer latestOdo = this.buildOdometer(odometer);
                        odometerService.save(latestOdo);
                        log.debug(latestOdo.toString());
                    }
                }
            } catch (NullPointerException npe) {
                log.debug(npe.getLocalizedMessage());
                log.info("We caught an NPE while processing odometer readings");
            } catch (Exception ex) {
                log.info("Error while processing odometer readings: ", ex);
                log.debug("Exception: " + ex);
            }
        }
    }

    private Odometer buildOdometer(Odometer odometer) {
        Odometer latestOdo = new Odometer();
        latestOdo.setImei(odometer.getImei());
        latestOdo.setTimeStamp(odometer.getTimeStamp());
        latestOdo.setActualOdometer(odometer.getActualOdometer());
        latestOdo.setZeroOdometer(odometer.getZeroOdometer());
        latestOdo.setAlertFlag(odometer.getAlertFlag());

        return latestOdo;
    }
}
