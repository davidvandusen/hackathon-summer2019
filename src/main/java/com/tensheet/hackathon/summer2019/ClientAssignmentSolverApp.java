package com.tensheet.hackathon.summer2019;

import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.api.solver.event.BestSolutionChangedEvent;
import org.optaplanner.core.api.solver.event.SolverEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ClientAssignmentSolverApp {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_BLACK = "\u001B[30m";

    private static class NewBestScoreListener implements SolverEventListener<ClientAssignmentSolution> {
        @Override
        public void bestSolutionChanged(BestSolutionChangedEvent<ClientAssignmentSolution> event) {
            ClientAssignmentSolution newBestSolution = event.getNewBestSolution();
            ClientAssignmentScoreCalculator clientAssignmentScoreCalculator = new ClientAssignmentScoreCalculator();
            int nonBookkeepingKnowledgeableClients = clientAssignmentScoreCalculator.getAssociatesWithExcessNonBookkeepingKnowledgeableClients(newBestSolution);
            int nonTechSavvyClients = clientAssignmentScoreCalculator.getAssociatesWithExcessNonTechSavvyClients(newBestSolution);
            int personalityPortfolios = nonBookkeepingKnowledgeableClients + nonTechSavvyClients;
            System.out.println(
                "\n" +
                    "Workload Deviation:  " +
                    ANSI_RED_BACKGROUND + ANSI_BLACK + " " +
                    String.format("%.2f", getPortfolioSizeDeviation(newBestSolution)) +
                    " " + ANSI_RESET +
                    "    Avg. Industries:  " +
                    ANSI_BLUE_BACKGROUND + ANSI_BLACK + " " +
                    String.format("%.2f", getAverageIndustriesPerPortfolio(newBestSolution)) +
                    " " + ANSI_RESET +
                    "    Churn Risk Assignments:  " +
                    ANSI_CYAN_BACKGROUND + ANSI_BLACK + " " +
                    clientAssignmentScoreCalculator.getChurnyClientsWithChurnyAssociates(newBestSolution) +
                    " " + ANSI_RESET +
                    "    “Personality” Portfolios:  " +
                    ANSI_PURPLE_BACKGROUND + ANSI_BLACK + " " +
                    personalityPortfolios +
                    " " + ANSI_RESET +
                    "\n");
        }
    }

    private static final Logger LOG = LoggerFactory.getLogger(ClientAssignmentSolverApp.class);

    public static void main(String[] args) throws Exception {
        LOG.info("Building client assignment problem from files (client:" + args[0] + ", aa:" + args[1] + ").");
        ClientAssignmentSolution planningProblem = ClientAssignmentSolutionFactory.createFromCsv(args[0], args[1]);
        LOG.info("Building solver from file " + args[2]);
        Solver solver = SolverFactory.createFromXmlResource(args[2]).buildSolver();
        NewBestScoreListener newBestScoreListener = new NewBestScoreListener();
        solver.addEventListener(newBestScoreListener);
        solver.solve(planningProblem);
        ClientAssignmentSolution solution = (ClientAssignmentSolution) solver.getBestSolution();
        LOG.info("Final best score: " + solution.getScore());
        solution.getClients()
            .stream()
            .sorted(Comparator.comparing(client -> client.getCompany().toLowerCase()))
            .forEach(client -> System.out.println(client.getCompany() + " ⇒ " + client.getAccountingAssociate().getFullName()));
    }

    private static Double getAverageIndustriesPerPortfolio(ClientAssignmentSolution solution) {
        return solution.getClients()
            .stream()
            .collect(Collectors.groupingBy(Client::getAccountingAssociate))
            .values()
            .stream()
            .mapToDouble(clientList -> clientList.stream()
                .map(Client::getBenchVertical)
                .distinct()
                .count()
            )
            .average()
            .orElse(0.0);
    }

    private static Double getPortfolioSizeDeviation(ClientAssignmentSolution solution) {
        return solution.getClients()
            .stream()
            .collect(Collectors.groupingBy(Client::getAccountingAssociate))
            .values()
            .stream()
            .map(List::size)
            .map(Number::doubleValue)
            .collect(DoubleStatistics.collector())
            .getStandardDeviation();
    }

    // Used for calculating standard deviation
    // https://codereview.stackexchange.com/questions/125409/computing-the-standard-deviation-of-a-number-array-in-java
    static class DoubleStatistics extends DoubleSummaryStatistics {

        private double sumOfSquare = 0.0d;
        private double sumOfSquareCompensation; // Low order bits of sum
        private double simpleSumOfSquare; // Used to compute right sum for
        // non-finite inputs

        @Override
        public void accept(double value) {
            super.accept(value);
            double squareValue = value * value;
            simpleSumOfSquare += squareValue;
            sumOfSquareWithCompensation(squareValue);
        }

        public DoubleStatistics combine(DoubleStatistics other) {
            super.combine(other);
            simpleSumOfSquare += other.simpleSumOfSquare;
            sumOfSquareWithCompensation(other.sumOfSquare);
            sumOfSquareWithCompensation(other.sumOfSquareCompensation);
            return this;
        }

        private void sumOfSquareWithCompensation(double value) {
            double tmp = value - sumOfSquareCompensation;
            double velvel = sumOfSquare + tmp; // Little wolf of rounding error
            sumOfSquareCompensation = (velvel - sumOfSquare) - tmp;
            sumOfSquare = velvel;
        }

        public double getSumOfSquare() {
            double tmp = sumOfSquare + sumOfSquareCompensation;
            if (Double.isNaN(tmp) && Double.isInfinite(simpleSumOfSquare)) {
                return simpleSumOfSquare;
            }
            return tmp;
        }

        public final double getStandardDeviation() {
            long count = getCount();
            double sumOfSquare = getSumOfSquare();
            double average = getAverage();
            return count > 0 ? Math.sqrt((sumOfSquare - count * Math.pow(average, 2)) / (count - 1)) : 0.0d;
        }

        public static Collector<Double, ?, DoubleStatistics> collector() {
            return Collector.of(DoubleStatistics::new, DoubleStatistics::accept, DoubleStatistics::combine);
        }

    }
}
