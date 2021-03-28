package com.protel.berlin.clock.api.service;

import com.protel.berlin.clock.api.enums.Constants;
import com.protel.berlin.clock.api.exception.model.InvalidTimeException;
import com.protel.berlin.clock.api.exception.model.NumericTimeException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class BerlinClockServiceTest {

    @InjectMocks
    private BerlinClockService berlinClockService;

    @Test
    void it_should_return_berlin_clock_eauivalent_of_given_time() {
        //Given
        String berlinClockOfGivenTime = "Y\n" +
                "RRRR\n" +
                "0000\n" +
                "YY000000000\n" +
                "0000";
        //When, Then
        assertEquals(berlinClockOfGivenTime, berlinClockService.getBerlinClock("20:10:10"));
    }

    @Test
    void it_should_throw_invalid_time_exception_if_time_not_valid() {
        try {
            berlinClockService.getBerlinClock("20:10");
        } catch (InvalidTimeException ite) {
            assertEquals(Constants.INVALID_TIME_ERROR.getPattern(), ite.getMessage());
        }
    }

    @Test
    void it_should_throw_numeric_time_exception_if_time_is_numeric() {
        try {
            berlinClockService.getBerlinClock("20:10:asd");
        } catch (NumericTimeException nte) {
            assertEquals(Constants.NUMERIC_TIME_ERROR.getPattern(), nte.getMessage());
        }
    }

    @Test
    void it_should_throw_illegal_argument_exception_if_hour_is_valid() {
        try {
            berlinClockService.getBerlinClock("25:10:10");
        } catch (IllegalArgumentException iae) {
            assertEquals(Constants.HOUR_NOT_VALID_ERROR.getPattern(), iae.getMessage());
        }
    }

    @Test
    void it_should_throw_illegal_argument_exception_if_min_is_valid() {
        try {
            berlinClockService.getBerlinClock("20:60:10");
        } catch (IllegalArgumentException iae) {
            assertEquals(Constants.MIN_NOT_VALID_ERROR.getPattern(), iae.getMessage());
        }
    }

    @Test
    void it_should_throw_illegal_argument_exception_if_sex_is_valid() {
        try {
            berlinClockService.getBerlinClock("20:10:60");
        } catch (IllegalArgumentException iae) {
            assertEquals(Constants.SEC_NOT_VALID_ERROR.getPattern(), iae.getMessage());
        }
    }
}
