package com.avolkov;

import com.avolkov.exceptions.CovidException;
import com.avolkov.model.CovidInfo;
import com.avolkov.service.CovidInfoService;
import com.avolkov.utils.FormatUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringSubstitutor;

import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Covid 19 API - Fetcher
 */
@Slf4j
public class App {
    private static final String DELIMITER = "---" + System.lineSeparator();

    public static void main(String[] args) {
        CovidInfoService service = new CovidInfoService();
        Scanner in = new Scanner(System.in);

        System.out.println("Enter country (e.g. France):");
        final String countryName = in.nextLine();

        if (StringUtils.isNotBlank(countryName)) {
            try {
                final Map<String, CovidInfo> info = service.getInfo(countryName);
                printInfo(info);
            } catch (CovidException ex) {
                log.error("Error: {}", ex.getMessage(), ex);
            }
        }
    }

    private static void printInfo(Map<String, CovidInfo> info) {
        final String result = info.entrySet()
                .stream()
                .map(App::formatRegion)
                .collect(Collectors.joining(DELIMITER));
        System.out.printf("""
                                
                ### Result: ###
                                
                %s
                %n""", result);
    }

    private static String formatRegion(Map.Entry<String, CovidInfo> item) {
        final String regionInfoTemplate = """
                [${region}]:
                ${info}""";
        final Map<String, String> values = Map.of(
                "region", item.getKey(),
                "info", FormatUtils.format(item.getValue())
        );
        return new StringSubstitutor(values).replace(regionInfoTemplate);
    }
}
