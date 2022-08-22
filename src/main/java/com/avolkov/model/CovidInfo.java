package com.avolkov.model;

public record CovidInfo(
        Long confirmed,
        Long recovered,
        Long deaths,
        Float vaccinatedLevel,
        Long newConfirmed
) {
}
