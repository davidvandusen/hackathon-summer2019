package com.tensheet.hackathon.summer2019;

import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.api.solver.event.BestSolutionChangedEvent;
import org.optaplanner.core.api.solver.event.SolverEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientAssignmentSolverApp {

    private static class NewBestScoreListener implements SolverEventListener<ClientAssignmentSolution> {
        @Override
        public void bestSolutionChanged(BestSolutionChangedEvent<ClientAssignmentSolution> event) {
            ClientAssignmentSolution newBestSolution = event.getNewBestSolution();
            LOG.info("New best score: " + newBestSolution.getScore());
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
    }

}
