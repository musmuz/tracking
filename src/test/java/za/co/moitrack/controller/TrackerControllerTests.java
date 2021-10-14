package za.co.moitrack.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import za.co.moitrack.service.TeltonikaTrackerService;
import za.co.moitrack.service.TrackerService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by nguni52 on 2017/04/05.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(TrackerController.class)
public class TrackerControllerTests {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private TrackerService trackerService;
    @MockBean
    private TeltonikaTrackerService teltonikaTrackerService;
    @Autowired
    ObjectMapper objectMapper;


    @Before
    public void setUp() {

    }

    @Test
    public void testFindByImeiAndDate() throws Exception {
        this.mvc.perform(get("/tracker?imei=868324022096759&dateFrom=21/03/2018&dateTo=22/03/2018")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testFindAllByIMEIAndDate() throws Exception {
        this.mvc.perform(get("/tracker/path?imei=868324022096759&dateFrom=22/05/2017&dateTo=23/05/2017&limitOutput=true")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testFindAllTrackersByImeiAndDateRange() throws Exception {
        this.mvc.perform(get("/tracker/list?imei=868324022096759&dateFrom=22/05/2017&dateTo=23/05/2017")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andDo(print());
    }

    /**
     * We want to test the retrieval of tracker list segmented into distinct trips
     *
     * @throws Exception
     */


    @Test
    public void testFindTripListByImeiAndDate() throws Exception {
        this.mvc.perform(get("/tracker/triplist?imei=868324022096759&dateFrom=22/05/2017&dateTo=23/05/2017")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testfindDrivestatesByImeiAndDate() throws Exception {
        this.mvc.perform(get("/tracker/drivestate?imei=868324022096759&dateFrom=22/05/2017&dateTo=23/05/2017")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testFindTeltonikaTripListByImeiAndDate() throws Exception {
        this.mvc.perform(get("/tracker/teltonika/triplist?imei=868324022096759&dateFrom=22/05/2017&dateTo=23/05/2017")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
