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
import za.co.moitrack.data.builder.TrackingBuilder;
import za.co.moitrack.data.builder.VehicleBuilder;
import za.co.moitrack.data.model.Tracking;
import za.co.moitrack.data.model.Vehicle;
import za.co.moitrack.service.TeltonikaTrackingService;
import za.co.moitrack.service.TrackingService;
import za.co.moitrack.service.VehicleService;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TrackingController.class)
public class TrackingControllerTests {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private TrackingService trackingService;
    @MockBean
    private VehicleService vehicleService;
    @MockBean
    TeltonikaTrackingService teltonikaTrackingService;
    private List<Tracking> trackings;
    private Tracking tracking;
    private Vehicle vehicle;
    @Autowired
    ObjectMapper objectMapper;

    @Before
    public void setup() {
        trackings = TrackingBuilder.buildTrackings(10);
        tracking = TrackingBuilder.BuildATracking(1);
        vehicle = VehicleBuilder.buildAVehicle(1);
        //trackingService.save(tracking);
        //vehicleService.save(vehicle);
    }

    @Test
    public void testCreateTrackingData() throws Exception {
        given(this.trackingService.create(tracking)).willReturn(tracking);
        this.mvc.perform(post("/tracking/create")
                 .contentType(MediaType.APPLICATION_JSON)
                 .content(objectMapper.writeValueAsBytes(tracking)))
                 .andExpect(status().isOk())
                 .andExpect(jsonPath("$.imei", is(tracking.getImei())))
                 .andDo(print());
    }


    @Test
    public void testFindAllTracking() throws Exception {

        given(this.trackingService.findAll()).willReturn(trackings);
        this.mvc.perform(get("/tracking/list")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testFindbyRegistrationNumber() throws Exception {

        given(this.trackingService.findByVehicleRegistration(vehicle.getRegistrationNumber()))
                .willReturn(tracking);
                this.mvc.perform(get("/tracking/findby/registration?registration=" + vehicle.getRegistrationNumber()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testFindbyImei() throws Exception {
        given(this.trackingService.findOne(tracking.getImei()))
                .willReturn(tracking);
        this.mvc.perform(get("/tracking/"+ tracking.getImei()).accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.imei").value(tracking.getImei()))
                .andDo(print());
    }
}

