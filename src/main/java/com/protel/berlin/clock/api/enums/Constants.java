package com.protel.berlin.clock.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Constants {
    EXCEPTION_MESSAGE("Format of the time parameter must be in this format: HH:MM:SS%nException: %s"),
    INVALID_TIME_ERROR("Invalid time provided."),
    NUMERIC_TIME_ERROR("Time values must be numeric."),
    HOUR_NOT_VALID_ERROR("Hours out of bounds."),
    MIN_NOT_VALID_ERROR("Minutes out of bounds."),
    SEC_NOT_VALID_ERROR("Seconds out of bounds."),
    TIME_FORMAT("hh:mm:ss"),
    TIME_SPLITTER(":"),
    YELLOW("Y"),
    RED("R"),
    ZERO("0");

    private final String pattern;
}
