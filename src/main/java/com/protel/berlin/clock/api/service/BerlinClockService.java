package com.protel.berlin.clock.api.service;

import com.protel.berlin.clock.api.enums.Constants;
import com.protel.berlin.clock.api.exception.model.InvalidTimeException;
import com.protel.berlin.clock.api.exception.model.NumericTimeException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

@Service
public class BerlinClockService {

    private static final int TIME_LENGTH = 3;
    private static final String NEW_LINE = System.getProperty("line.separator");

    public String getBerlinClock(String time) {
        if (StringUtils.hasText(time)) {
            return generateBerlinClock(time);
        }
        Format simpleDateFormat = new SimpleDateFormat(Constants.TIME_FORMAT.getPattern());
        return generateBerlinClock(simpleDateFormat.format(new Date()));
    }

    private String generateBerlinClock(String time) {
        String[] times = time.split(Constants.TIME_SPLITTER.getPattern(), TIME_LENGTH);

        if (times.length != TIME_LENGTH) {
            throw new InvalidTimeException(Constants.INVALID_TIME_ERROR.getPattern());
        }

        int hours;
        int minutes;
        int seconds;

        try {
            hours = Integer.parseInt(times[0]);
            minutes = Integer.parseInt(times[1]);
            seconds = Integer.parseInt(times[2]);
        } catch (NumberFormatException e) {
            throw new NumericTimeException(Constants.NUMERIC_TIME_ERROR.getPattern());
        }

        validateTimes(hours, minutes, seconds);

        return processTime(hours, minutes, seconds);
    }

    private void validateTimes(int hours, int minutes, int seconds) {
        if (hours < 0 || hours > 23) throw new IllegalArgumentException(Constants.HOUR_NOT_VALID_ERROR.getPattern());
        if (minutes < 0 || minutes > 59) throw new IllegalArgumentException(Constants.MIN_NOT_VALID_ERROR.getPattern());
        if (seconds < 0 || seconds > 59) throw new IllegalArgumentException(Constants.SEC_NOT_VALID_ERROR.getPattern());
    }

    private String processTime(int hours, int minutes, int seconds) {
        String line1 = (seconds % 2 == 0) ? Constants.YELLOW.getPattern() : Constants.ZERO.getPattern();
        String line2 = rowString(hours / 5, 4, Constants.RED.getPattern());
        String line3 = rowString(hours % 5, 4, Constants.RED.getPattern());
        String line4 = rowString(minutes / 5, 11, Constants.YELLOW.getPattern()).replace("YYY", "YYR");
        String line5 = rowString(minutes % 5, 4, Constants.YELLOW.getPattern());

        return String.join(NEW_LINE, Arrays.asList(line1, line2, line3, line4, line5));
    }

    private String rowString(int litLights, int lightsInRow, String lampType) {
        int unlitLights = lightsInRow - litLights;
        String lit = String.join("", Collections.nCopies(litLights, lampType));
        String unlit = String.join("", Collections.nCopies(unlitLights, Constants.ZERO.getPattern()));

        return lit + unlit;
    }
}
