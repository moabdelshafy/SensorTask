package com.sensor.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sensor.config.TestConfig;
import com.sensor.dto.LocationDTO;
import com.sensor.service.LocationService;
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


@WebMvcTest(LocationController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(TestConfig.class)
class LocationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private LocationService locationService;

    private LocationDTO locationDTO;

    @BeforeEach
    void setUp() {
        locationDTO = LocationDTO.builder().code("testLocation").build();
    }

    @Test
    void testAddLocationReturn_200_Ok() throws Exception {

        when(locationService.addOrUpdateLocation(Mockito.any(LocationDTO.class))).thenReturn(locationDTO);
        mockMvc.perform(post("/location/addLocation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(locationDTO)))
                .andExpect(jsonPath("$.code").isNotEmpty())
                .andExpect(jsonPath("$.code").value("testLocation"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}