package dev.web.premier_league_v2.repository;

import dev.web.premier_league_v2.model.GoalRanking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoalRepository extends JpaRepository<GoalRanking, Long> {
    List<GoalRanking> findAllByOrderByGoalsDesc();
}
