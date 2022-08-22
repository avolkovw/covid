package com.avolkov.service;

import com.avolkov.model.CovidInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class CovidInfoServiceITTest {

    private static final String ALL_REGION = "All";
    private final CovidInfoService service = new CovidInfoService();

    @Test
    void getInfo() {
        try {
            final Map<String, CovidInfo> info = service.getInfo("France");
            log.debug("France total info: {}", info);
            assertNotNull(info);
            assertTrue(info.get(ALL_REGION).confirmed() > 0);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            fail(ex.getMessage());
        }
    }
}