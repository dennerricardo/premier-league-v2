package dev.web.premier_league_v2.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.web.premier_league_v2.dto.LeaderboardResponse;
import dev.web.premier_league_v2.dto.PlayerEntry;
import dev.web.premier_league_v2.model.GoalRanking;
import dev.web.premier_league_v2.repository.GoalRepository;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class GoalRankingService {

    private static final String API_URL = "https://sdp-prem-prod.premier-league-prod.pulselive.com/api/v3/competitions/8/seasons/2025/players/stats/leaderboard?_sort=goals%3Adesc&country=&_limit=100";
    private final GoalRepository repository;
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    public GoalRankingService(GoalRepository repository) {
        this.repository = repository;
        this.objectMapper = new ObjectMapper();
        this.restTemplate = new RestTemplate();
    }

    public List<GoalRanking> allRanking = new ArrayList<>();
    String url = API_URL;
    int rank = 1;

    while(url !=null){
        String json = restTemplate.getForObject(url, String.class);

        LeaderboardResponse response = objectMapper.readValue(json, LeaderboardResponse.class);

        for (PlayerEntry entry : response.getData()) {
            if (entry.getStats() == null || entry.getStats().getGoals() == null)
                continue;
        }

        GoalRanking ranking = mapToGoalRanking(entry, rank);
        allRanking.add(ranking);
        rank++;

        String nextCursor = response.getPagination().getnext();
        url = (nextCursor !=null) ? API_URL + "&_next=" + nextCursor : null;
    }

    saveToJsonFile(allRankings);

    repository.deleteAll();
    return repository.saveAll(allRankings)



}
