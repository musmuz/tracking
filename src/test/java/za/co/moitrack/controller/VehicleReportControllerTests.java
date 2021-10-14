package za.co.moitrack.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import za.co.moitrack.service.VehicleReportService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(VehicleReportController.class)
public class VehicleReportControllerTests {
    @Autowired
    private MockMvc mvc;
    @MockBean
    VehicleReportService vehicleReportService;

    @Before
    public void setUp() {
    }

    @Test
    public void findVehilceReportByDate() throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String reportDate = LocalDate.now().format(formatter);
        this.mvc.perform(get(VehicleReportController.HOME + VehicleReportController.REPORT + VehicleReportController.HOME +
                             VehicleReportController.FINDONE + "?" + "reportdate=" + reportDate)
                 .contentType(MediaType.APPLICATION_JSON)
                 .accept(MediaType.APPLICATION_JSON_UTF8))
                 .andExpect(status().isOk())
                 .andDo(print());
    }

    @Test
    public void findVehicleReportsBetweenDates() throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dateFrom = LocalDate.now().format(formatter);
        String dateTo = LocalDate.now().plusDays(1).format(formatter);
        this.mvc.perform(get(VehicleReportController.HOME + VehicleReportController.REPORT + VehicleReportController.HOME +
                VehicleReportController.REPORTLIST + "?" + "dateFrom=" + dateFrom + "&" + "dateTo=" + dateTo)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
