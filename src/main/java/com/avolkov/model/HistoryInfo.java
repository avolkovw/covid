package com.avolkov.model;

import java.time.LocalDate;
import java.util.Map;

public record HistoryInfo(
        Map<LocalDate, Long> dates) {
}
