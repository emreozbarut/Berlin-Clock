package com.protel.berlin.clock.api.controller;

import com.protel.berlin.clock.api.service.BerlinClockService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/berlinClock")
public class BerlinClockController {

    private final BerlinClockService berlinClockService;

    @GetMapping()
    public String getBerlinClock(@RequestParam(required = false, value = "time") String time) {
        return berlinClockService.getBerlinClock(time);
    }
}
