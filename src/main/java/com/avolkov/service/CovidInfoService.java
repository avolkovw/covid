package com.avolkov.service;

import com.avolkov.exceptions.CovidException;
import com.avolkov.model.*;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class CovidInfoService {
    private final CovidApiClient apiClient = new CovidApiClient();

    public Map<String, CovidInfo> getInfo(String country) throws CovidException {
        final var casesInfo = apiClient.fetchCases(country);
        final var vaccinesInfo = apiClient.fetchVaccines(country);
        final var historyInfo = apiClient.fetchHistory(country, CaseStatus.CONFIRMED);

        final Set<String> regionNames = casesInfo.keySet();

        Map<String, CovidInfo> result = new HashMap<>();

        for (String regionName : regionNames) {
            log.debug("Processing region name: {}", regionName);

            final CasesInfo caseTotalInfo = casesInfo.get(regionName);
            final Optional<Float> vaccinatedLevel = getVaccinatedLevel(vaccinesInfo, regionName);
            final Optional<Long> newConfirmed = getNewConfirmed(historyInfo, regionName, caseTotalInfo);

            result.put(regionName,
                    new CovidInfo(caseTotalInfo.confirmed(),
                            caseTotalInfo.recovered(),
                            caseTotalInfo.deaths(),
                            vaccinatedLevel.orElse(null),
                            newConfirmed.orElse(null)
                    )
            );
        }
        return result;
    }

    private Optional<Long> getNewConfirmed(Map<String, HistoryInfo> historyInfo, String regionName, CasesInfo caseTotalInfo) {
        if (historyInfo.get(regionName).dates() == null) {
            return Optional.empty();
        }

        final Long previousConfirmed = historyInfo.get(regionName)
                .dates()
                .entrySet()
                .stream()
                .min(Map.Entry.comparingByKey(Comparator.reverseOrder()))
                .map(Map.Entry::getValue)
                .orElse(0L);

        final long newConfirmed = caseTotalInfo.confirmed() - previousConfirmed;
        return Optional.of(newConfirmed);
    }

    private Optional<Float> getVaccinatedLevel(Map<String, VaccinesInfo> vaccinesInfo, String regionName) {
        return Optional.ofNullable(vaccinesInfo.get(regionName))
                .flatMap(this::getVaccinatedLevel);
    }

    /**
     * Return the percent of all vaccinated
     *
     * @param vaccinesInfo
     * @return
     */
    private Optional<Float> getVaccinatedLevel(VaccinesInfo vaccinesInfo) {
        return Optional.ofNullable(vaccinesInfo.population())
                .map(population -> vaccinesInfo.peopleVaccinated().floatValue() / population * 100.0f);
    }
}
