package com.tensheet.hackathon.summer2019;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

@PlanningEntity
public class Client {

    private Integer id;

    private AccountingAssociate accountingAssociate;

    private Boolean isTechSavvy;

    private Boolean hasBookkeepingKnowledge;

    @PlanningVariable(valueRangeProviderRefs = {"accounting-associates"}, nullable = true)
    public AccountingAssociate getAccountingAssociate() {
        return accountingAssociate;
    }

    public void setAccountingAssociate(AccountingAssociate accountingAssociate) {
        this.accountingAssociate = accountingAssociate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getTechSavvy() {
        return isTechSavvy;
    }

    public void setTechSavvy(Boolean techSavvy) {
        isTechSavvy = techSavvy;
    }

    public Boolean getHasBookkeepingKnowledge() {
        return hasBookkeepingKnowledge;
    }

    public void setHasBookkeepingKnowledge(Boolean hasBookkeepingKnowledge) {
        this.hasBookkeepingKnowledge = hasBookkeepingKnowledge;
    }
}
