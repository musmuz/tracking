package za.co.moitrack.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import za.co.moitrack.data.model.Vehicle;
import za.co.moitrack.data.builder.VehicleBuilder;
import za.co.moitrack.service.VehicleService;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by nguni52 on 2017/03/24.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(VehicleController.class)

public class VehicleControllerTests {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private VehicleService vehicleService;
    private Vehicle vehicle;
    private List<Vehicle> vehicles;
    @Autowired
    ObjectMapper objectMapper;


    @Before
    public void setUp() {
        vehicle = VehicleBuilder.buildAVehicle(1);
        vehicles = VehicleBuilder.buildVehicles(10);

    }

    @Test
    public void testFindAll() throws Exception {
       given(this.vehicleService.findAll()).willReturn(vehicles);
       this.mvc.perform(get("/vehicle/list").accept(MediaType.APPLICATION_JSON_UTF8))
               .andExpect(status().isOk())
               .andDo(print());


    }
@Test
    public void testFindOne() throws Exception {

        given(this.vehicleService.findOne(vehicle.getImei()))
                .willReturn(vehicle);
        this.mvc.perform(get("/vehicle/"+ vehicle.getImei()).accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.imei").value(vehicle.getImei()))
                .andDo(print());
    }

    @Test
    public void  createVehicle() throws Exception {
        given(this.vehicleService.create(vehicle)).willReturn(vehicle);
        this.mvc.perform(post("/vehicle/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(vehicle)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.imei", is(vehicle.getImei())))
                .andDo(print());
    }

   @Test
   public void testDelete() throws Exception {
       // given(this.vehicleService.delete(vehicle.getId)).willReturn(vehicle.getId);
       this.mvc.perform(delete("/vehicle/"+ vehicle.getId()).contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk());
        }

   @Test
    public void testSaveVehicle() throws Exception {
        given(this.vehicleService.save(vehicle)).willReturn(vehicle);
        this.mvc.perform(put("/vehicle/")
                 .contentType(MediaType.APPLICATION_JSON)
                  .content(objectMapper.writeValueAsBytes(vehicle)))
                 .andExpect(status().isOk())
                 .andDo(print());
   }

    /**
     * We want to test vehicle data retrieval of more than one vehicle by using their registration numbers
     * @throws Exception
     */

   @Test
    public void testGetGPSAndOdometer() throws Exception {
       List<String> imeis = new ArrayList<>();
       List<Vehicle> vehicleList = VehicleBuilder.buildVehicles(10);
       for(Vehicle vehicle: vehicleList) {
           imeis.add(vehicle.getImei());
       }
       this.mvc.perform(get("/vehicle/findby/registration?vehicles==" + Arrays.toString(vehicleList.toArray())).contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andDo(print());
   }
}

