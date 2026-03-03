package dev.web.premier_league_v2.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Country {

    private String country;

    public String getCountry() {
        return country;
    }

}
