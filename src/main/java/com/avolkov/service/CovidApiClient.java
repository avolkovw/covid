package com.avolkov.service;

import com.avolkov.exceptions.CovidException;
import com.avolkov.model.CaseStatus;
import com.avolkov.model.CasesInfo;
import com.avolkov.model.HistoryInfo;
import com.avolkov.model.VaccinesInfo;
import com.avolkov.utils.JsonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Map;

public class CovidApiClient {
    public static final String BASE_URL = "https://covid-api.mmediagroup.fr/v1";
    private static final String COUNTRY_PARAM = "country";

    /**
     * Return live cases data
     * @param country if the value is null then return information for all countries
     * @return
     * @throws CovidException
     */
    public Map<String, CasesInfo> fetchCases(String country) throws CovidException {
        final URI uri;
        try {
            uri = new URIBuilder(CovidApiClient.BASE_URL + "/cases")
                    .addParameter(COUNTRY_PARAM, country)
                    .build();
        } catch (URISyntaxException ex) {
            throw new CovidException(ex);
        }

        HttpGet request = new HttpGet(uri);

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(request)) {

            final HttpEntity entity = response.getEntity();
            if (entity != null) {
                return JsonUtils.getMapper().readValue(entity.getContent(), new TypeReference<>() {
                });
            }
        } catch (IOException ex) {
            throw new CovidException(ex);
        }
        return Collections.emptyMap();
    }

    /**
     * Return historical cases data
     * @param country if the value is null then return information for all countries
     * @return
     * @throws CovidException
     */
    public Map<String, VaccinesInfo> fetchVaccines(String country) throws CovidException {
        final URI uri;
        try {
            uri = new URIBuilder(CovidApiClient.BASE_URL + "/vaccines")
                    .addParameter(COUNTRY_PARAM, country)
                    .build();
        } catch (URISyntaxException ex) {
            throw new CovidException(ex);
        }
        HttpGet request = new HttpGet(uri);

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(request)) {

            final HttpEntity entity = response.getEntity();
            if (entity != null) {
                return JsonUtils.getMapper().readValue(entity.getContent(), new TypeReference<>() {
                });
            }
        } catch (Exception ex) {
            throw new CovidException(ex);
        }
        return Collections.emptyMap();
    }

    /**
     * Return vaccines data
     * @param country if the value is null then return information for all countries
     * @param status
     * @return
     * @throws CovidException
     */
    public Map<String, HistoryInfo> fetchHistory(String country, CaseStatus status) throws CovidException {
        final URI uri;
        try {
            uri = new URIBuilder(CovidApiClient.BASE_URL + "/history")
                    .addParameter(COUNTRY_PARAM, country)
                    .addParameter("status", status.name().toLowerCase())
                    .build();
        } catch (URISyntaxException ex) {
            throw new CovidException(ex);
        }
        HttpGet request = new HttpGet(uri);

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(request)) {

            final HttpEntity entity = response.getEntity();
            if (entity != null) {
                return JsonUtils.getMapper().readValue(entity.getContent(), new TypeReference<>() {
                });
            }
        } catch (Exception ex) {
            throw new CovidException(ex);
        }
        return Collections.emptyMap();
    }
}
