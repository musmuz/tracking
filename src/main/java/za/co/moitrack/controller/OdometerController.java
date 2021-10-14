package za.co.moitrack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.moitrack.data.model.Odometer;
import za.co.moitrack.service.OdometerService;

import java.util.Collection;


/**
 * Created by nguni52 on 2/21/17.
 *
 * @Todo - Mosa to add documentation to each of the methods to describe what each of them does.
 */

@RestController
@RequestMapping(value = OdometerController.ODOMETER)
public class OdometerController extends MainController {
    public static final String ODOMETER = "odometer";
    public static final String LATEST_LIST = "latestlist";
    public static final String HOME_LATEST_LIST = HOME + LATEST_LIST;
    private static final String IMEI = "imei";
    private static final String HOME_IMEI = HOME + "{" + IMEI + "}";
    private static final String DATE_FROM = "dateFrom";
    private static final String DATE_TO = "dateTo";
    private static final String PATH = "path";
    public static final String HOME_PATH = HOME + PATH;
    private static final String HOME_PATH_IMEI = HOME + PATH + HOME + "{" + IMEI + "}";
    private static final String FINDBY = "findby";
    private static final String HOME_FINDBY_IMEI = HOME + FINDBY + HOME + IMEI;
    @Autowired
    private OdometerService odometerService;

    /**
     * This method exposes the search for an odometer document via an imei
     *
     * @param imei - The imei of the vehicle whose odometer we are interested in
     * @return - Odometer object for this imei
     */

    @GetMapping(HOME_IMEI)
    public Odometer findOne(@PathVariable(IMEI) String imei) {
        return odometerService.findOne(imei);
    }

    /**
     * This method exposes the search for all odometer documents
     *
     * @return - all odometer objects in odometer collection
     */

    @GetMapping(HOME_LIST)
    public Collection<Odometer> findAllOdometers() {
        return odometerService.findAll();
    }

    /**
     * This method returns a list of the latest odometer readings to be recorded
     */
    @GetMapping(HOME_LATEST_LIST)
    public Collection<Odometer> findlatestOdos() {
        return odometerService.findLatestOdos();
    }

    @GetMapping(HOME_FINDBY_IMEI)
    public Collection<Odometer> findOdosByImei(@RequestParam(IMEI) String imei) {
        return odometerService.findOdosByImei(imei);
    }


    /**
     * This method builds and saves an odometer object
     *
     * @param odometer
     * @return - the message that the object was created or not
     */
    @PostMapping(HOME_CREATE)
    public ResponseEntity<String> createOdometer(@RequestBody Odometer odometer) {
        try {
            odometerService.create(odometer);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Created", "OdometerController");
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping(HOME_PATH)
    public float getDistance(@RequestParam(IMEI) String imei, @RequestParam(DATE_FROM) String dateFrom, @RequestParam(DATE_TO) String dateTo) {
        return odometerService.getDistance(imei, dateFrom, dateTo);
    }

    @GetMapping(HOME_PATH_IMEI)
    public float getDistanceWithoutDates(@PathVariable(IMEI) String imei) {

        return odometerService.getDistanceWithoutDates(imei) / 1000f;
    }

    @DeleteMapping(HOME_IMEI)
    public ResponseEntity<String> deleteOdometer(@PathVariable(IMEI) String odometerImei) {
        if (odometerService.findOne(odometerImei) != null) {
            odometerService.delete(odometerImei);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Deleted", "OdometerController");
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
