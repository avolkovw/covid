package com.avolkov.service;

import com.avolkov.model.CaseStatus;
import com.avolkov.model.CasesInfo;
import com.avolkov.model.HistoryInfo;
import com.avolkov.model.VaccinesInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class CovidApiClientITTest {
    private static final String ALL_REGION = "All";
    private final CovidApiClient apiClient = new CovidApiClient();

    @Test
    void fetchCases() {
        try {
            final Map<String, CasesInfo> result = apiClient.fetchCases("France");
            result.forEach((region, info) -> log.debug("{} : {}", region, info));
            assertNotNull(result);
            assertTrue(result.get(ALL_REGION).confirmed() > 0);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            fail(ex.getMessage());
        }
    }

    @Test
    void fetchVaccines() {
        try {
            final Map<String, VaccinesInfo> result = apiClient.fetchVaccines("France");
            result.forEach((region, info) -> log.debug("{} : {}", region, info));
            assertNotNull(result);
            assertTrue(result.get(ALL_REGION).population() > 0);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            fail(ex.getMessage());
        }
    }

    @Test
    void fetchHistory() {
        try {
            final Map<String, HistoryInfo> result = apiClient.fetchHistory("Germany", CaseStatus.CONFIRMED);
            result.forEach((region, info) -> log.debug("{} : {}", region, info));
            assertNotNull(result);

            final HistoryInfo all = result.get(ALL_REGION);
            assertTrue(all.dates().size() > 0);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            fail(ex.getMessage());
        }
    }
}