package dev.web.premier_league_v2.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Pagination {
    private int _limit;
    private String _prev;
    private String _next;

    public int get_limit() {
        return _limit;
    }

    public void set_limit(int _limit) {
        this._limit = _limit;
    }

    public String get_prev() {
        return _prev;
    }

    public void set_prev(String _prev) {
        this._prev = _prev;
    }

    public String get_next() {
        return _next;
    }

    public void set_next(String _next) {
        this._next = _next;
    }
}
