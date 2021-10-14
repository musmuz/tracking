package za.co.moitrack.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import za.co.moitrack.data.model.Vehicle;
import za.co.moitrack.data.model.VehicleReport;
import za.co.moitrack.data.repository.VehicleReportRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class VehicleReportService {
    @Autowired
    TrackingService trackingService;
    @Autowired
    VehicleReportRepository vehicleReportRepository;
    private Log log = LogFactory.getLog(this.getClass().getName());

    public VehicleReport findByOne(String reportDate) {
        DateTime dateTime = DateTime.parse(reportDate, DateTimeFormat.forPattern("dd/MM/yyyy"));
        return vehicleReportRepository.findByReportDate(dateTime);
    }

    public void saveVehicleReport(int totalNumberOfVehicles, Collection<Vehicle> nonReportingVehicles, List<Vehicle> reportingVehicles) {
        VehicleReport vehicleReport = new VehicleReport();
        String[] vehiclesNotReporting = new String[nonReportingVehicles.size()];
        List<Vehicle> nonReportingVehiclesList = new ArrayList<>(nonReportingVehicles);
        IntStream.range(0, nonReportingVehicles.size()).forEach(i -> vehiclesNotReporting[i] = nonReportingVehiclesList.get(i).getRegistrationNumber());

        vehicleReport.setNonReportingVehicles(vehiclesNotReporting);
        vehicleReport.setReportDate(new DateTime());
        vehicleReport.setTotalNumberOfVehicles(totalNumberOfVehicles);
        vehicleReport.setReportingNumberOfVehicles(reportingVehicles.size());
        vehicleReport.setNonReportingNumberOfVehicles(nonReportingVehicles.size());

        vehicleReportRepository.save(vehicleReport);
    }

    public List<VehicleReport> findReportsByDate(String dateFrom, String dateTo) {
        Sort sort = new Sort(Sort.Direction.DESC, "reportDate");

        DateTime dateTimeFrom = DateTime.parse(dateFrom, DateTimeFormat.forPattern("dd/MM/yyyy"));
        DateTime dateTimeTo = DateTime.parse(dateTo, DateTimeFormat.forPattern("dd/MM/yyyy"));

        List<VehicleReport> vehicleReports = new ArrayList<>(vehicleReportRepository.findByDateBetween(dateTimeFrom, dateTimeTo, sort));
        return vehicleReports;
    }
}
