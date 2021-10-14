package za.co.moitrack.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import za.co.moitrack.data.builder.OdometerBuilder;
import za.co.moitrack.data.model.Odometer;
import za.co.moitrack.data.repository.OdometerRepository;
import za.co.moitrack.service.OdometerService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(OdometerController.class)
public class OdometerControllerTests {
    @Autowired
    ObjectMapper objectMapper;
    private Log log = LogFactory.getLog(this.getClass().getName());
    @Autowired
    private MockMvc mvc;
    @MockBean
    private OdometerService odometerService;
    @MockBean
    private OdometerRepository odometerRepository;
    private Odometer odometer;
    private List<Odometer> odometers = new ArrayList<>();
    @Autowired
    private MessageSource messageSource;

    @Before
    public void setup() {
        odometer = OdometerBuilder.buildAnOdometer(1);
        odometers = OdometerBuilder.buildOdometers(10);
    }

    @Test
    public void testFindAll() throws Exception {
        given(this.odometerService.findAll()).willReturn(odometers);
        this.mvc.perform(get("/odometer/list").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testFindLatestOdos() throws Exception {
        given(this.odometerService.findLatestOdos()).willReturn(odometers);
        this.mvc.perform(get("/odometer/latestlist").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testFindOdosByImei() throws Exception {
        this.mvc.perform(get("/odometer/findby/imei?imei==" + odometer.getImei()))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testFindOne() throws Exception {
        //Sort sort = new Sort(Sort.Direction.DESC, "timeStamp");
        given(this.odometerService.findOne(odometer.getImei())).willReturn(odometer);
        this.mvc.perform(get("/odometer/" + odometer.getImei()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.imei").value(odometer.getImei()))
                .andDo(print());

    }

    @Test
    public void createOdometer_IsValid() throws Exception {
        given(odometerService.isValid(any(Odometer.class))).willReturn(true);
        this.mvc.perform(post(OdometerController.HOME + OdometerController.ODOMETER + OdometerController.HOME_CREATE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(odometer)))
                .andExpect(status().isCreated());
    }

    @Test
    public void createOdometer_IsNotValid() throws Exception {
        Odometer nullOdometer = null;
        given(odometerService.isValid(any(Odometer.class))).willReturn(false);
        this.mvc.perform(post(OdometerController.HOME + OdometerController.ODOMETER + OdometerController.HOME_CREATE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(nullOdometer)))
                .andExpect(status().isBadRequest());
    }

    /**
     * We want to test the deletion of an odometer record
     */
    @Test
    public void testOdoDelete() throws Exception {
        //Sort sort = new Sort(Sort.Direction.DESC, "timeStamp");
        given(odometerService.findOne(odometer.getImei())).willReturn(odometer);
        this.mvc.perform(delete("/odometer/" + odometer.getImei())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testOdoDeleteFailure() throws Exception {
        // Sort sort = new Sort(Sort.Direction.DESC, "timeStamp");
        given(odometerService.findOne(odometer.getImei())).willReturn(null);
        this.mvc.perform(delete("/odometer/" + odometer.getImei())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    /**
     * We want to test the distance travelled by a specific vehicle using it's IMEI by specifying dates
     *
     * @throws Exception
     */
    @Test
    public void getDistancePathForOdometer() throws Exception {
        given(this.odometerService.create(odometer)).willReturn(odometer);
        this.mvc.perform(get("/odometer/path?imei=213456362345&dateFrom=01/01/2019&dateTo=31/12/2019")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    /**
     * We want to test the distance travelled by a specific vehicle using it's IMEI without specifying dates
     */
    @Test
    public void getDistanceWithoutDatesTest() throws Exception {
        given(this.odometerService.create(odometer)).willReturn(odometer);
        this.mvc.perform(get("/odometer/path/213456362345")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
