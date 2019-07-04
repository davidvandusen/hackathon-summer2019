package com.tensheet.hackathon.summer2019;

import org.optaplanner.core.api.score.Score;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.impl.score.director.easy.EasyScoreCalculator;

public class ClientAssignmentScoreCalculator implements EasyScoreCalculator<ClientAssignmentSolution> {

    @Override
    public Score calculateScore(ClientAssignmentSolution solution) {
        int hardScore = getHardScore(solution);
        int softScore = getSoftScore(solution);
        return HardSoftScore.valueOf(hardScore, softScore);
    }

    private int getSoftScore(ClientAssignmentSolution solution) {
        return 0
            - getAssociatesPerIndustry(solution)
            - getChurnyClientsWithChurnyAssociates(solution)
            - getAssociatesWithExcessNonTechSavvyClients(solution)
            - getAssociatesWithExcessNonBookkeepingKnowledgeableClients(solution);
    }

    public int getAssociatesWithExcessNonBookkeepingKnowledgeableClients(ClientAssignmentSolution solution) {
        return 0;
    }

    public int getAssociatesWithExcessNonTechSavvyClients(ClientAssignmentSolution solution) {
        return 0;
    }

    public int getChurnyClientsWithChurnyAssociates(ClientAssignmentSolution solution) {
        return 0;
    }

    public int getAssociatesPerIndustry(ClientAssignmentSolution solution) {
        return 0;
    }

    private int getHardScore(ClientAssignmentSolution solution) {
        int unassignedClients = (int) solution.getClients()
            .stream()
            .filter(client -> client.getAccountingAssociate() == null)
            .count();
        return 0 - unassignedClients;
    }

}
