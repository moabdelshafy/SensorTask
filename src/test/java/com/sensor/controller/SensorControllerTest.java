package com.sensor.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sensor.config.TestConfig;
import com.sensor.dto.SensorDTO;
import com.sensor.service.SensorService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(SensorController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(TestConfig.class)
class SensorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SensorService sensorService;

    private SensorDTO sensorDTO;


    @BeforeEach
    void setUp() {
        sensorDTO = SensorDTO.builder().name("testSensor").status(Boolean.TRUE).build();
    }

    @Test
    void testAddSensorReturn_200_Ok() throws Exception {
        when(sensorService.addSensor(Mockito.any(SensorDTO.class))).thenReturn(sensorDTO);
        mockMvc.perform(post("/sensor/addSensor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sensorDTO)))
                        .andExpect(jsonPath("$.name").isNotEmpty())
                        .andExpect(jsonPath("$.name").value("testSensor"))
                        .andExpect(jsonPath("$.status").value(Boolean.TRUE))
                        .andExpect(status().isOk())
                        .andDo(print());
    }

    @Test
    void testAddSensorReturn_400_BadRequest() throws Exception {
        sensorDTO.setName(" ");
        when(sensorService.addSensor(Mockito.any(SensorDTO.class))).thenReturn(sensorDTO);
        mockMvc.perform(post("/sensor/addSensor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sensorDTO)))
                        .andExpect(status().isBadRequest())
                        .andDo(print());
    }
}