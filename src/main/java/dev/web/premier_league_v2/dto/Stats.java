package dev.web.premier_league_v2.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Stats {
    private Double  goals;
    private int appearances;


    public Double  getGoals() {
        return goals;
    }

    public void setGoals(Double  goals) {
        this.goals = goals;
    }

    public int getAppearances() {
        return appearances;
    }

    public void setAppearances(int appearances) {
        this.appearances = appearances;
    }
}
