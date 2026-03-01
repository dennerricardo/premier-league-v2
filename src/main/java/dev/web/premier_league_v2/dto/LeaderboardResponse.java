package dev.web.premier_league_v2.dto;

import java.util.List;

public class LeaderboardResponse {

    private Pagination pagination;
    private List<PlayerEntry> data;

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public List<PlayerEntry> getData() {
        return data;
    }

    public void setData(List<PlayerEntry> data) {
        this.data = data;
    }
}
