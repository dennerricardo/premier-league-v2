package dev.web.premier_league_v2.service;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.web.premier_league_v2.dto.LeaderboardResponse;
import dev.web.premier_league_v2.dto.PlayerEntry;
import dev.web.premier_league_v2.dto.PlayerMetadata;
import dev.web.premier_league_v2.dto.Stats;
import dev.web.premier_league_v2.model.GoalRanking;
import dev.web.premier_league_v2.repository.GoalRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
@Service
public class GoalRankingService {
    private static final String API_URL =
            "https://sdp-prem-prod.premier-league-prod.pulselive.com/api/v3/competitions/8/seasons/2025/players/stats/leaderboard?_sort=goals%3Adesc&country=&_limit=100";
    private final GoalRepository repository;
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    public GoalRankingService(GoalRepository repository) {
        this.repository = repository;
        this.objectMapper = new ObjectMapper();
        this.restTemplate = new RestTemplate();
    }

    public List<GoalRanking> fetchAndSaveGoalRankings() {
        try {

            List<GoalRanking> allRankings = new ArrayList<>();
            String url = API_URL;

            while(url != null){
                String json = restTemplate.getForObject(url, String.class);
                LeaderboardResponse response = objectMapper.readValue(json, LeaderboardResponse.class);

                for (PlayerEntry entry : response.getData()) {

                    if (entry.getStats() == null || entry.getStats().getGoals() == null)
                    continue;

                    GoalRanking ranking = mapToGoalRanking(entry);
                    allRankings.add(ranking);
                }

                String nextCursor = response.getPagination().get_next();
                url = (nextCursor != null) ? API_URL + "&_next=" + nextCursor : null;
            }
            allRankings.sort(Comparator.comparingInt(GoalRanking::getGoals).reversed()
                    .thenComparing(GoalRanking::getPlayerName));

            int rank = 1;
            for(int i = 0; i < allRankings.size();i++){
                if(i > 0 && allRankings.get(i).getGoals() == allRankings.get(i - 1).getGoals()){
                    allRankings.get(i).setRankPosition(allRankings.get(i - 1).getRankPosition());
                }else{
                    allRankings.get(i).setRankPosition(rank);
                }
                rank++;
            }

            saveToJsonFile(allRankings);
            repository.deleteAll();
            return repository.saveAll(allRankings);

        } catch (Exception e) {
            throw new RuntimeException("Error searching and saving goal rankings.", e);
        }
    }

    private GoalRanking mapToGoalRanking(PlayerEntry entry) {
        PlayerMetadata metadata = entry.getPlayerMetadata();
        Stats stats = entry.getStats();

        GoalRanking ranking = new GoalRanking();

        ranking.setPlayerName(metadata.getName());

        ranking.setTeam(metadata.getCurrentTeam().getName());
        ranking.setCountry(metadata.getCountry().getCountry());
        ranking.setPosition(metadata.getPosition());
        ranking.setGoals(stats.getGoals().intValue());

        return ranking;
    }
    private void saveToJsonFile(List<GoalRanking> rankings) {
        try { objectMapper.writerWithDefaultPrettyPrinter().writeValue(new java.io.File("goal_rankings.json"), rankings);
            System.out.println("Rankings of goals saved in goal_rankings.json"); }
        catch (Exception e) {
            throw new RuntimeException("Error saving goal rankings to JSON file", e);
        }
    }
    public List<GoalRanking> getAll() {
        return repository.findAllByOrderByGoalsDesc();
    }
}