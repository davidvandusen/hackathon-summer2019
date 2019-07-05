package com.tensheet.hackathon.summer2019;

import org.optaplanner.core.api.score.Score;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.impl.score.director.easy.EasyScoreCalculator;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class ClientAssignmentScoreCalculator implements EasyScoreCalculator<ClientAssignmentSolution> {

    @Override
    public Score calculateScore(ClientAssignmentSolution solution) {
        int hardScore = getHardScore(solution);
        int softScore = getSoftScore(solution);
        return HardSoftScore.valueOf(hardScore, softScore);
    }

    private int getSoftScore(ClientAssignmentSolution solution) {
        return 0
            - getSumOfPortfolioSizeDeviationFromAverage(solution)
            - getSumOfAssociatesPerIndustry(solution)
            - 2 * getChurnyClientsWithChurnyAssociates(solution)
            - getAssociatesWithExcessNonTechSavvyClients(solution)
            - getAssociatesWithExcessNonBookkeepingKnowledgeableClients(solution);
    }

    public int getSumOfPortfolioSizeDeviationFromAverage(ClientAssignmentSolution solution) {
        int avgClientsPerPortfolio = solution.getClients().size() / solution.getAccountingAssociates().size();
        return solution.getClients()
            .stream()
            .filter(client -> client.getAccountingAssociate() != null)
            .collect(Collectors.groupingBy(Client::getAccountingAssociate))
            .values()
            .stream()
            .mapToInt(portfolio -> Math.abs(portfolio.size() - avgClientsPerPortfolio))
            .sum();
    }

    public int getAssociatesWithExcessNonBookkeepingKnowledgeableClients(ClientAssignmentSolution solution) {
        int[] ints = solution.getClients()
            .stream()
            .filter(client -> client.getAccountingAssociate() != null)
            .collect(Collectors.groupingBy(Client::getAccountingAssociate))
            .values()
            .stream()
            .mapToInt(portfolio -> (int) portfolio.stream()
                .filter(client -> client.getHasBookkeepingKnowledge() == false)
                .count()
            )
            .toArray();
        double averageNonKnowledgeable = Arrays.stream(ints).average().orElse(0.0);
        return (int) Arrays.stream(ints).filter(count -> count > averageNonKnowledgeable).count();
    }

    public int getAssociatesWithExcessNonTechSavvyClients(ClientAssignmentSolution solution) {
        int[] ints = solution.getClients()
            .stream()
            .filter(client -> client.getAccountingAssociate() != null)
            .collect(Collectors.groupingBy(Client::getAccountingAssociate))
            .values()
            .stream()
            .mapToInt(portfolio -> (int) portfolio.stream()
                .filter(client -> client.getTechSavvy() == false)
                .count()
            )
            .toArray();
        double averageNonTechy = Arrays.stream(ints).average().orElse(0.0);
        return (int) Arrays.stream(ints).filter(count -> count > averageNonTechy).count();
    }

    public int getChurnyClientsWithChurnyAssociates(ClientAssignmentSolution solution) {
        int middleIndex = solution.getAccountingAssociates().size() / 2;
        double medianChurn = solution.getAccountingAssociates()
            .stream()
            .mapToDouble(AccountingAssociate::getAverageRetentionPercent)
            .sorted()
            .toArray()[middleIndex];
        return (int) solution.getClients()
            .stream()
            .filter(client ->
                client.getChurnRisk() != null &&
                    client.getAccountingAssociate() != null &&
                    client.getChurnRisk() &&
                    client.getAccountingAssociate().getAverageRetentionPercent() <= medianChurn
            )
            .count();
    }

    public int getSumOfAssociatesPerIndustry(ClientAssignmentSolution solution) {
        return solution.getClients()
            .stream()
            .collect(Collectors.groupingBy(Client::getBenchVertical))
            .values()
            .stream()
            .mapToInt(listOfClients ->
                (int) listOfClients.stream()
                    .map(Client::getAccountingAssociate)
                    .filter(Objects::nonNull)
                    .distinct()
                    .count()
            )
            .sum();
    }

    private int getHardScore(ClientAssignmentSolution solution) {
        int unassignedClients = (int) solution.getClients()
            .stream()
            .map(Client::getAccountingAssociate)
            .filter(Objects::isNull)
            .count();
        return 0 - unassignedClients;
    }

}
