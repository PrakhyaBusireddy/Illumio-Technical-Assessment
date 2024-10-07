package com.rama;

import java.io.IOException;
import java.util.List;

public class App {
    public static void main(String[] args) {
        try {
            // File paths for input and output
            String flowLogFile = "data/flowlogs.txt";
            String lookupFile = "data/lookup.csv";
            String outputTagFile = "data/tag_counts.csv";
            String outputPortProtocolFile = "data/port_protocol_counts.csv";


            List<TagLookup> lookupTable = FlowTagging.readLookupTable(lookupFile);
            List<String[]> flowLogs = FlowTagging.readFlowLog(flowLogFile);


            FlowTagging.generateTagCounts(flowLogs, lookupTable, outputTagFile, outputPortProtocolFile);

            System.out.println("Tag counts and port/protocol counts have been generated successfully.");
            System.out.println("Check the 'data' folder for the output files: 'tag_counts.csv' and 'port_protocol_counts.csv'.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("An error occurred while processing the flow logs.");
        }
    }
}
