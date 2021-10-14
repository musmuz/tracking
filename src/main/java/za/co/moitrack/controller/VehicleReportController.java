package za.co.moitrack.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import za.co.moitrack.data.model.VehicleReport;
import za.co.moitrack.service.VehicleReportService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = VehicleReportController.REPORT)
public class VehicleReportController extends MainController {
    public static final String REPORT = "report";
    public static final String FINDONE = "findone";
    public static final String HOME_FINDONE = HOME + FINDONE;
    public static final String REPORTDATE = "reportdate";
    public static final String DATE_FROM = "dateFrom";
    public static final String DATE_TO = "dateTo";
    public static final String REPORTLIST = "reportlist";
    private Log log = LogFactory.getLog(this.getClass().getName());

    @Autowired
    private VehicleReportService vehicleReportService;

    @GetMapping(HOME_FINDONE)
    public VehicleReport getOneVehicleReport(@RequestParam(REPORTDATE) String reportdate) {
        try {
            return vehicleReportService.findByOne(reportdate);
        } catch (Exception ex) {
            log.debug("Exception finding one report by date");
            return new VehicleReport();
        }
    }

    @GetMapping(REPORTLIST)
    public List<VehicleReport> getReportsBetweenDates(@RequestParam(DATE_FROM) String dateFrom,
                                                      @RequestParam(DATE_TO) String dateTo) {
        try {
            return vehicleReportService.findReportsByDate(dateFrom, dateTo);
        } catch (Exception ex) {
            log.debug("Exception finding a list of reports between dates");
            return new ArrayList<>();
        }
    }
}
