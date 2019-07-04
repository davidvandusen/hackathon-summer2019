package com.tensheet.hackathon.summer2019;

import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.api.solver.event.BestSolutionChangedEvent;
import org.optaplanner.core.api.solver.event.SolverEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SeatingPlanSolverApp {

    private static class NewBestScoreListener implements SolverEventListener<SeatingPlanSolution> {
        @Override
        public void bestSolutionChanged(BestSolutionChangedEvent<SeatingPlanSolution> event) {
            SeatingPlanSolution newBestSolution = event.getNewBestSolution();
            System.out.println("New best score: " + newBestSolution.getScore());
        }
    }

    private static final Logger LOG = LoggerFactory.getLogger(SeatingPlanSolverApp.class);

    public static void main(String[] args) throws Exception {
        if (args.length < 1) throw new IllegalArgumentException("path to problem YAML file must be first arg");
        if (args.length < 2) throw new IllegalArgumentException("path to solver XML file must be second arg");
        LOG.info("Building seating plan problem from file (" + args[0] + ").");
        SeatingPlanSolution planningProblem = SeatingPlanSolutionFactory.createFromYamlResource(args[0]);
        LOG.info("Building solver from file (" + args[1] + ").");
        Solver solver = SolverFactory.createFromXmlResource(args[1]).buildSolver();
        NewBestScoreListener newBestScoreListener = new NewBestScoreListener();
        solver.addEventListener(newBestScoreListener);
        solver.solve(planningProblem);
        SeatingPlanSolution solution = (SeatingPlanSolution) solver.getBestSolution();
        SeatingPlanSolutionPresenter presenter = new SeatingPlanSolutionPresenter(solution);
        LOG.info("Solution stats: " + presenter.displaySolutionStats() + ".");
        LOG.info("Solution seat assignments:\n" + presenter.displaySeatAssignments());
    }

}
