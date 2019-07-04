package com.tensheet.hackathon.summer2019;

import org.optaplanner.core.api.score.Score;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.impl.score.director.easy.EasyScoreCalculator;

public class ClientAssignmentScoreCalculator implements EasyScoreCalculator<ClientAssignmentSolution> {

    @Override
    public Score calculateScore(ClientAssignmentSolution solution) {
        return HardSoftScore.valueOf(0, 0);
    }

}
