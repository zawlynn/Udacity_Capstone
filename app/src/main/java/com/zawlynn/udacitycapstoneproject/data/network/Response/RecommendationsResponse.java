package com.zawlynn.udacitycapstoneproject.data.network.Response;

import com.zawlynn.udacitycapstoneproject.pojo.Episode;

import java.util.List;

public class RecommendationsResponse {
    private List<Episode> recommendations;

    public List<Episode> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(List<Episode> recommendations) {
        this.recommendations = recommendations;
    }
}
