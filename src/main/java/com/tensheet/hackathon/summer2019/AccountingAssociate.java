package com.tensheet.hackathon.summer2019;

import java.util.Set;

public class AccountingAssociate {

    private Integer id;

    private Set<Client> clients;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<Client> getClients() {
        return clients;
    }

    public void setClients(Set<Client> clients) {
        this.clients = clients;
    }
}
