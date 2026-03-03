package dev.web.premier_league_v2.controller;

import dev.web.premier_league_v2.model.GoalRanking;
import dev.web.premier_league_v2.service.GoalRankingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rankings")
public class GoalRankingController {

    private final GoalRankingService service;

    public GoalRankingController(GoalRankingService service) {
        this.service = service;
    }

    // ── POST /api/rankings/fetch ─────────────────────────────────
    // calls the PL API → saves JSON file → persists to H2
    @PostMapping("/fetch")
    public ResponseEntity<List<GoalRanking>> fetchAndPersist() {
//        try {
            List<GoalRanking> rankings = service.fetchAndSaveGoalRankings();
            return ResponseEntity.ok(rankings);
//        } catch (Exception e) {
//            return ResponseEntity.internalServerError().build();
//        }
    }

    // ── GET /api/rankings ────────────────────────────────────────
    // reads everything from H2
    @GetMapping
    public ResponseEntity<List<GoalRanking>> getAll() {
        List<GoalRanking> rankings = service.getAll();

        if (rankings.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(rankings);
    }
}