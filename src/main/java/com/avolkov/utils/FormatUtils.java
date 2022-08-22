package com.avolkov.utils;

import com.avolkov.model.CovidInfo;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringSubstitutor;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@UtilityClass
public class FormatUtils {
    public static String format(CovidInfo info) {
        final String template = """
                Confirmed ${confirmed};
                Deaths: ${deaths};
                Recovered: ${recovered};
                New confirmed: ${newConfirmed};
                Vaccinated level: ${vaccinatedLevel};
                 """;

        Map<String, Object> values = new HashMap<>();
        values.put("confirmed", info.confirmed());
        values.put("deaths", info.deaths());
        values.put("recovered", info.recovered());
        values.put("newConfirmed", info.newConfirmed());
        values.put("vaccinatedLevel",
                Optional.ofNullable(info.vaccinatedLevel())
                        .map(vl -> String.format("%s%%", info.vaccinatedLevel()))
                        .orElse("- ")
        );
        return new StringSubstitutor(values).replace(template);
    }
}
