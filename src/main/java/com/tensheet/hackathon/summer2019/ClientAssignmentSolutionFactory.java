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
        Set<AccountingAssociate> accountingAssociates = buildAssociates(associateRecords);
        Set<Client> clients = buildClients(clientRecords, accountingAssociates);
        solution.setClients(clients);
        solution.setAccountingAssociates(accountingAssociates);
        return solution;
    }

    private static Set<Client> buildClients(Iterable<CSVRecord> clientRecords, Set<AccountingAssociate> accountingAssociates) {
        Set<Client> clients = new HashSet<>();
        for (CSVRecord clientRecord: clientRecords) {
            Client client = new Client();

            // Make sure that the assigned associate exists. If not, don't add the client because it's probably not valid
            // Why this is really done is because the construction heuristic was taking forever to just assign non-null
            // associates to the clients, so this speeds up the initial phase so we get to the part of the demo that looks
            // cool faster.
            String aaFullName = clientRecord.get("bk1_fullname");
            AccountingAssociate accountingAssociate = accountingAssociates.stream()
                .filter(aa -> aaFullName.equals(aa.getFullName()))
                .findAny()
                .orElse(null);
            if (accountingAssociate == null) {
                continue;
            }
            client.setAccountingAssociate(accountingAssociate);

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
            associate.setEmail(associateRecord.get("Company Email"));
            associate.setFullName(associateRecord.get("Full Name"));
            associate.setAverageRetentionPercent(Double.parseDouble(associateRecord.get("Average Retention Rate")));
            associate.setTenureDays(Integer.parseInt(associateRecord.get("Tenure (D)")));
            associates.add(associate);
        }
        return associates;
    }
}
