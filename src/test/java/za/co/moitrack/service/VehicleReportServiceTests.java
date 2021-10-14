package za.co.moitrack.service;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.mapstruct.BeforeMapping;
import org.springframework.beans.factory.annotation.Autowired;
import za.co.moitrack.DemoApplicationTests;
import za.co.moitrack.data.builder.VehicleBuilder;
import za.co.moitrack.data.model.Vehicle;
import za.co.moitrack.data.model.VehicleReport;
import za.co.moitrack.data.repository.VehicleReportRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class VehicleReportServiceTests extends DemoApplicationTests {
    private Log log = LogFactory.getLog(this.getClass().getName());
    @Autowired
    VehicleReportRepository vehicleReportRepository;
    @Autowired
    VehicleReportService vehicleReportService;
    List<Vehicle> reportingVehicles;
    List<Vehicle> nonReportingVehicles;
    int totalNumberOfVehicles;

    @Before
    public void setup() {
        reportingVehicles = VehicleBuilder.buildVehicles(5);
        nonReportingVehicles = VehicleBuilder.buildVehicles(5);
        totalNumberOfVehicles = 10;
    }

    @Test
    public void getOneReport() {
        vehicleReportService.saveVehicleReport(totalNumberOfVehicles, nonReportingVehicles, reportingVehicles);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String reportDate = LocalDate.now().format(formatter);
        VehicleReport vehicleReport = vehicleReportService.findByOne(reportDate);

        log.debug("vehicle report:" + vehicleReport.toString());
    }

    @Test
    public void getReportListBetweenDates() {
        int count = 5;
        for(int i = 0; i < count; i++) {
            vehicleReportService.saveVehicleReport(totalNumberOfVehicles, nonReportingVehicles, reportingVehicles);
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String reportDateFrom = LocalDate.now().format(formatter);
        String reportDateTo = LocalDate.now().plusDays(1).format(formatter);

        List<VehicleReport> vehicleReports = vehicleReportService.findReportsByDate(reportDateFrom, reportDateTo);

        log.debug("vehicle report size is:" + vehicleReports.size());
        assertEquals(vehicleReports.size(), 5);
    }
}
