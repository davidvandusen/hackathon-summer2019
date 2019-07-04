package com.tensheet.hackathon.summer2019;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

@PlanningEntity
public class Client {

    private Client id;

    private AccountingAssociate accountingAssociate;

    @PlanningVariable(valueRangeProviderRefs = {"accounting-associates"}, nullable = true)
    public AccountingAssociate getAccountingAssociate() {
        return accountingAssociate;
    }

    public void setAccountingAssociate(AccountingAssociate accountingAssociate) {
        this.accountingAssociate = accountingAssociate;
    }

    public Client getId() {
        return id;
    }

    public void setId(Client id) {
        this.id = id;
    }
}
