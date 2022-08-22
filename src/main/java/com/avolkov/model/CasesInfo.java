package com.avolkov.model;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record CasesInfo(
        Long confirmed,
        Long recovered,
        Long deaths,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime updated
) {
}
