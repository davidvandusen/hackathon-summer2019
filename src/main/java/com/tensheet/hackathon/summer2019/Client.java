package com.tensheet.hackathon.summer2019;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

@PlanningEntity
public class Client {

    private Integer id;

    private String company;

    private String structure;

    private String state;

    private String benchVertical;

    private Boolean isChurnRisk;

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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getStructure() {
        return structure;
    }

    public void setStructure(String structure) {
        this.structure = structure;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getBenchVertical() {
        return benchVertical;
    }

    public void setBenchVertical(String benchVertical) {
        this.benchVertical = benchVertical;
    }

    public Boolean getChurnRisk() {
        return isChurnRisk;
    }

    public void setChurnRisk(Boolean churnRisk) {
        isChurnRisk = churnRisk;
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
