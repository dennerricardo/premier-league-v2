package dev.web.premier_league_v2.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PlayerEntry {

    private PlayerMetadata playerMetadata;
    private Stats stats;

    public PlayerMetadata getPlayerMetadata() {
        return playerMetadata;
    }

    public void setPlayerMetadata(PlayerMetadata playerMetadata) {
        this.playerMetadata = playerMetadata;
    }

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }
}
