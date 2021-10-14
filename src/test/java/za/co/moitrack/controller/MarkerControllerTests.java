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
import za.co.moitrack.data.builder.VehicleBuilder;
import za.co.moitrack.data.model.Vehicle;
import za.co.moitrack.service.MarkerService;
import za.co.moitrack.service.TeltonikaTrackingService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(MarkerController.class)
public class MarkerControllerTests {
    @Autowired
    private MockMvc mvc;
    @MockBean
    MarkerService markerService;
    @MockBean
    TeltonikaTrackingService teltonikaTrackingService;

    @Before
    public void setUp() {

    }

    @Test
    public void findMarkerList() throws Exception {
        this.mvc.perform(get("/marker/list").contentType(MediaType.APPLICATION_JSON))
                 .andExpect(status().isOk())
                 .andDo(print());
    }

    @Test
    public void findMarkerByRegistrationTest() throws Exception {
        Vehicle vehicle = VehicleBuilder.buildAVehicle(1);
        this.mvc.perform(get("/marker/" + vehicle.getRegistrationNumber()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void findMarkerByImei() throws Exception {
        List<String> imeis = new ArrayList<>();
        List<Vehicle> vehicleList = VehicleBuilder.buildVehicles(10);
        for(Vehicle vehicle: vehicleList) {
            imeis.add(vehicle.getImei());
        }
        this.mvc.perform(get("/marker/findby/imei?imeis==" + Arrays.toString(imeis.toArray())).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void findTeltonikaMarkerList() throws Exception {
        this.mvc.perform(get("/marker/teltonika/list").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void findTeltonikaMarkerByImei() throws Exception {
        String imei = "354018115195087";
        this.mvc.perform(get("/marker/teltonika/findby/imei?imeis==" + imei).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

}

