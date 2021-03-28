package com.protel.berlin.clock.api.controller;

import com.protel.berlin.clock.api.enums.Constants;
import com.protel.berlin.clock.api.exception.model.InvalidTimeException;
import com.protel.berlin.clock.api.service.BerlinClockService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BerlinClockController.class)
class BerlinClockControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BerlinClockService berlinClockService;

    @Test
    void it_should_return_current_time_berlin_clock_equivalent() throws Exception {
        //Given
        String berlinClockOfCurrentTime = "Y\n" +
                "RRRR\n" +
                "0000\n" +
                "YY000000000\n" +
                "0000";

        when(berlinClockService.getBerlinClock(anyString())).thenReturn(berlinClockOfCurrentTime);

        //When
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/berlinClock")
                .contentType(MediaType.APPLICATION_JSON));

        //Then
        resultActions.andExpect(status().isOk());
    }

    @Test
    void it_should_return_given_time_berlin_clock_equivalent() throws Exception {
        //Given
        String berlinClockOfCurrentTime = "Y\n" +
                "RRRR\n" +
                "0000\n" +
                "YY000000000\n" +
                "0000";

        when(berlinClockService.getBerlinClock(anyString())).thenReturn(berlinClockOfCurrentTime);

        //When
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/berlinClock")
                .contentType(MediaType.APPLICATION_JSON)
                .param("time", "20:10:10"));

        //Then
        resultActions.andExpect(status().isOk());
        assertEquals(berlinClockOfCurrentTime, resultActions.andReturn().getResponse().getContentAsString());
    }

    @Test
    void it_should_throw_exception_if_time_param_not_valid() throws Exception {
        //Given
        String time = "20:10";
        when(berlinClockService.getBerlinClock(time)).thenThrow(new InvalidTimeException(Constants.INVALID_TIME_ERROR.getPattern()));

        //When
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/berlinClock")
                .contentType(MediaType.APPLICATION_JSON)
                .param("time", time));

        //Then
        resultActions.andExpect(status().isBadRequest());
        assertEquals(String.format(Constants.EXCEPTION_MESSAGE.getPattern(), Constants.INVALID_TIME_ERROR.getPattern()), resultActions.andReturn().getResponse().getContentAsString());
    }

    @Test
    void it_should_throw_exception_if_time_param_not_numeric() throws Exception {
        //Given
        String time = "20:10:asd";
        when(berlinClockService.getBerlinClock(time)).thenThrow(new InvalidTimeException(Constants.NUMERIC_TIME_ERROR.getPattern()));

        //When
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/berlinClock")
                .contentType(MediaType.APPLICATION_JSON)
                .param("time", time));

        //Then
        resultActions.andExpect(status().isBadRequest());
        assertEquals(String.format(Constants.EXCEPTION_MESSAGE.getPattern(), Constants.NUMERIC_TIME_ERROR.getPattern()), resultActions.andReturn().getResponse().getContentAsString());
    }

    @Test
    void it_should_throw_exception_if_hour_not_valid() throws Exception {
        //Given
        String time = "25:10:10";
        when(berlinClockService.getBerlinClock(time)).thenThrow(new IllegalArgumentException(Constants.HOUR_NOT_VALID_ERROR.getPattern()));

        //When
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/berlinClock")
                .contentType(MediaType.APPLICATION_JSON)
                .param("time", time));

        //Then
        resultActions.andExpect(status().isBadRequest());
        assertEquals(String.format(Constants.EXCEPTION_MESSAGE.getPattern(), Constants.HOUR_NOT_VALID_ERROR.getPattern()), resultActions.andReturn().getResponse().getContentAsString());
    }

    @Test
    void it_should_throw_exception_if_min_not_valid() throws Exception {
        //Given
        String time = "20:60:10";
        when(berlinClockService.getBerlinClock(time)).thenThrow(new IllegalArgumentException(Constants.MIN_NOT_VALID_ERROR.getPattern()));

        //When
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/berlinClock")
                .contentType(MediaType.APPLICATION_JSON)
                .param("time", time));

        //Then
        resultActions.andExpect(status().isBadRequest());
        assertEquals(String.format(Constants.EXCEPTION_MESSAGE.getPattern(), Constants.MIN_NOT_VALID_ERROR.getPattern()), resultActions.andReturn().getResponse().getContentAsString());
    }

    @Test
    void it_should_throw_exception_if_sec_not_valid() throws Exception {
        //Given
        String time = "20:10:60";
        when(berlinClockService.getBerlinClock(time)).thenThrow(new IllegalArgumentException(Constants.SEC_NOT_VALID_ERROR.getPattern()));

        //When
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/berlinClock")
                .contentType(MediaType.APPLICATION_JSON)
                .param("time", time));

        //Then
        resultActions.andExpect(status().isBadRequest());
        assertEquals(String.format(Constants.EXCEPTION_MESSAGE.getPattern(), Constants.SEC_NOT_VALID_ERROR.getPattern()), resultActions.andReturn().getResponse().getContentAsString());
    }
}
