package com.tensheet.hackathon.summer2019;

import org.optaplanner.core.api.score.Score;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.impl.score.director.easy.EasyScoreCalculator;

public class ClientAssignmentScoreCalculator implements EasyScoreCalculator<ClientAssignmentSolution> {

    @Override
    public Score calculateScore(ClientAssignmentSolution solution) {
        int hardScore = getHardScore(solution);
        return HardSoftScore.valueOf(hardScore, 0);
    }

    private int getHardScore(ClientAssignmentSolution solution) {
        int unassignedClients = (int) solution.getClients()
            .stream()
            .filter(client -> client.getAccountingAssociate() == null)
            .count();
        return 0 - unassignedClients;
    }

}
