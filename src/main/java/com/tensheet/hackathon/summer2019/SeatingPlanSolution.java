package com.tensheet.hackathon.summer2019;

import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.Solution;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

import java.util.Collection;
import java.util.Set;

@PlanningSolution
public class SeatingPlanSolution extends SeatingPlan implements Solution<HardSoftScore> {

    private HardSoftScore score;

    @Override
    public Collection<?> getProblemFacts() {
        return null;
    }

    @Override
    @ValueRangeProvider(id = "guests")
    public Set<Guest> getGuests() {
        return super.getGuests();
    }

    @Override
    @PlanningEntityCollectionProperty
    public Set<Seat> getSeats() {
        return super.getSeats();
    }

    @Override
    public HardSoftScore getScore() {
        return score;
    }

    @Override
    public void setScore(HardSoftScore score) {
        this.score = score;
    }

}
