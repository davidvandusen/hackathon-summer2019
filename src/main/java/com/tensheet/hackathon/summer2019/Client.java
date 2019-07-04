package com.tensheet.hackathon.summer2019;

import org.optaplanner.core.api.domain.entity.PlanningEntity;

@PlanningEntity
public class Client {

    private Client id;

    public Client getId() {
        return id;
    }

    public void setId(Client id) {
        this.id = id;
    }
}
