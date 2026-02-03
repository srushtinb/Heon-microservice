package com.skyscanner.hoen;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skyscanner.hoen.api.SearchResource;
import com.skyscanner.hoen.core.SearchResult;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class SearchServiceApplication extends Application<SearchServiceConfiguration> {

    public static void main(String[] args) throws Exception {
        new SearchServiceApplication().run(args);
    }

   @Override
public void run(SearchServiceConfiguration config,
                Environment environment) throws Exception {

    ObjectMapper mapper = new ObjectMapper();
    List<SearchResult> searchResults = new ArrayList<>();

    // ---------------- LOAD HOTELS ----------------
    InputStream hotelsStream =
        getClass().getResourceAsStream("/hotels.json");

    List<SearchResult> hotels =
        mapper.readValue(
            hotelsStream,
            new TypeReference<List<SearchResult>>() {}
        );

    hotels.forEach(h ->
        searchResults.add(
            new SearchResult(
                h.getCity(),
                "hotel",
                h.getTitle()
            )
        )
    );

    // ---------------- LOAD RENTAL CARS ----------------
    InputStream carsStream =
        getClass().getResourceAsStream("/rental_cars.json");

    List<SearchResult> cars =
        mapper.readValue(
            carsStream,
            new TypeReference<List<SearchResult>>() {}
        );

    cars.forEach(c ->
        searchResults.add(
            new SearchResult(
                c.getCity(),
                "rental_car",
                c.getTitle()
            )
        )
    );

    // ---------------- REGISTER RESOURCE ----------------
    environment.jersey().register(
        new SearchResource(searchResults)
    );
}
}