package com.tensheet.hackathon.summer2019;

import java.util.HashSet;

public class ClientAssignmentSolutionFactory {

    public static ClientAssignmentSolution createFromCsv(String clientCsvPath, String AccountAssociateCsvPath) {
        ClientAssignmentSolution clientAssignmentSolution = new ClientAssignmentSolution();
        clientAssignmentSolution.setAccountingAssociates(new HashSet<>());
        clientAssignmentSolution.setClients(new HashSet<>());
        return clientAssignmentSolution;
    }

}
