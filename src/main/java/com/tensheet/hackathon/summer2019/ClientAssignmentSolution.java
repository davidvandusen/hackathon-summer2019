package com.tensheet.hackathon.summer2019;

import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.Solution;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

import java.util.Collection;
import java.util.Set;

@PlanningSolution
public class ClientAssignmentSolution extends ClientAssignment implements Solution<HardSoftScore> {

    private HardSoftScore score;

    @Override
    public Collection<?> getProblemFacts() {
        return null;
    }

    @Override
    @PlanningEntityCollectionProperty
    public Set<Client> getClients() {
        return super.getClients();
    }

    @Override
    @ValueRangeProvider(id = "accounting-associates")
    public Set<AccountingAssociate> getAccountingAssociates() {
        return super.getAccountingAssociates();
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
