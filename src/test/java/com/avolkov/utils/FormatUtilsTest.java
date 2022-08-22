package com.avolkov.utils;

import com.avolkov.model.CovidInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class FormatUtilsTest {
    @Test
    void format() {
        String sample = """
                Confirmed 1;
                Deaths: 3;
                Recovered: 2;
                New confirmed: 6;
                Vaccinated level: 4.5%;
                """;
        final String result = FormatUtils.format(new CovidInfo(1L, 2L, 3L, 4.5f, 6L));
        log.debug("{}", result);

        assertEquals(sample, result);
    }

    @Test
    void formatWithNullValues() {
        String sample = """
                Confirmed 1;
                Deaths: 3;
                Recovered: 2;
                New confirmed: 6;
                Vaccinated level: - ;
                """;
        final String result = FormatUtils.format(new CovidInfo(1L, 2L, 3L, null, 6L));
        log.debug("{}", result);

        assertEquals(sample, result);
    }
}