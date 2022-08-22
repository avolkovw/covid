package com.avolkov.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record VaccinesInfo(
        @JsonProperty("people_vaccinated")
        Long peopleVaccinated,
        Long population) {
}
