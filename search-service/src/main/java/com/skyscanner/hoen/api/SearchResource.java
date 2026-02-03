package com.skyscanner.hoen.api;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.skyscanner.hoen.core.Search;
import com.skyscanner.hoen.core.SearchResult;

@Path("/search")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SearchResource {

    private final List<SearchResult> searchResults;

    public SearchResource(List<SearchResult> searchResults) {
        this.searchResults = searchResults;
    }

    @POST
public List<SearchResult> search(Search search) {

    List<SearchResult> response = new ArrayList<>();

    if (search.getCity() == null) {
        return response;
    }

    for (SearchResult result : searchResults) {
        if (result.getCity() != null &&
            result.getCity().equalsIgnoreCase(search.getCity())) {
            response.add(result);
        }
    }

    return response;
}

}