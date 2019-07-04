package com.tensheet.hackathon.summer2019;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class ClientAssignmentSolutionFactory {

    public static ClientAssignmentSolution createFromCsv(String clientCsvPath, String accountAssociateCsvPath) throws IOException {
        Reader clientReader = new InputStreamReader(ClientAssignmentSolutionFactory.class.getClassLoader().getResourceAsStream(clientCsvPath));
        Iterable<CSVRecord> clientRecords = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(clientReader);

        Reader associateReader = new InputStreamReader(ClientAssignmentSolutionFactory.class.getClassLoader().getResourceAsStream(accountAssociateCsvPath));
        Iterable<CSVRecord> associateRecords = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(associateReader);

        ClientAssignmentSolution solution = new ClientAssignmentSolution();
        solution.setClients(buildClients(clientRecords));
        solution.setAccountingAssociates(buildAssociates(associateRecords));
        return solution;
    }

    private static Set<Client> buildClients(Iterable<CSVRecord> clientRecords) {
        Set<Client> clients = new HashSet<>();
        for (CSVRecord clientRecord: clientRecords) {
            Client client = new Client();
            client.setId(Integer.parseInt(clientRecord.get("id")));
            client.setCompany(clientRecord.get("company"));
            client.setStructure(clientRecord.get("structure"));
            client.setState(clientRecord.get("state"));
            client.setBenchVertical(clientRecord.get("bench_vertical_name"));
            client.setChurnRisk(Boolean.parseBoolean(clientRecord.get("At risk?")));
            client.setHasBookkeepingKnowledge(Boolean.parseBoolean(clientRecord.get("Bookkeeping savvy?")));
            client.setTechSavvy(Boolean.parseBoolean(clientRecord.get("Tech savvy?")));
            clients.add(client);
        }
        return clients;
    }

    private static Set<AccountingAssociate> buildAssociates(Iterable<CSVRecord> associateRecords) {
        Set<AccountingAssociate> associates = new HashSet<>();
        for (CSVRecord associateRecord: associateRecords) {
            AccountingAssociate associate = new AccountingAssociate();
            associate.setId(Integer.parseInt(associateRecord.get("id")));
            associates.add(associate);
        }
        return associates;
    }
}
