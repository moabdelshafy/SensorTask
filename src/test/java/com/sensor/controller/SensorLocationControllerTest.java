package com.sensor.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sensor.config.TestConfig;
import com.sensor.dto.SensorLocationDTO;
import com.sensor.service.SensorLocationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(SensorLocationController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(TestConfig.class)
class SensorLocationControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private SensorLocationService locationSensorService;

    private SensorLocationDTO locationSensorDTO;

    @BeforeEach
    void setUp() {
        locationSensorDTO = SensorLocationDTO.builder().locationId(1L).sensorId(1L).max(2.5f).min(1.0f).build();
    }

    @Test
    void testAddSensorsToLocationReturn_200_Ok() throws Exception {
        when(locationSensorService.addSensorsToLocation(Mockito.any(SensorLocationDTO.class))).thenReturn(locationSensorDTO);
        mockMvc.perform(post("/sensorLocation/addSensorsToLocation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(locationSensorDTO)))
                        .andExpect(jsonPath("$.max").isNotEmpty())
                        .andExpect(status().isOk())
                        .andDo(print());
    }

    @Test
    void testAddSensorsToLocationReturn_400_BadRequest() throws Exception {
        locationSensorDTO.setLocationId(null);
        when(locationSensorService.addSensorsToLocation(Mockito.any(SensorLocationDTO.class))).thenReturn(locationSensorDTO);
        mockMvc.perform(post("/sensorLocation/addSensorsToLocation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(locationSensorDTO)))
                        .andExpect(status().isBadRequest())
                        .andDo(print());
    }

    @Test
    void testFindByLocationIdAndSensorId() throws Exception {
        when(locationSensorService.findByLocationIdAndSensorId(Mockito.any(SensorLocationDTO.class))).thenReturn(locationSensorDTO);
        mockMvc.perform(post("/sensorLocation/findByLocationIdAndSensorId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(locationSensorDTO)))
                        .andExpect(status().isOk())
                        .andDo(print());
    }
}