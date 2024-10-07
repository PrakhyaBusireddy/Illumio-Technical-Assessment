package com.rama;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class FlowTagging {
    public static List<TagLookup> readLookupTable(String lookupFile) throws IOException {
        List<TagLookup> lookupTable = new ArrayList<>();
        List<String> lines = Files.readAllLines(Paths.get(lookupFile));

        // Skip the header line
        boolean isFirstLine = true;
        for (String line : lines) {
            if (isFirstLine) {
                isFirstLine = false; // Set to false after reading the header
                continue; // Skip the header line
            }

            String[] parts = line.split(",");
            if (parts.length == 3) {
                int port = Integer.parseInt(parts[0].trim());
                String protocol = parts[1].trim().toLowerCase();
                String tag = parts[2].trim();
                lookupTable.add(new TagLookup(port, protocol, tag));
            }
        }
        return lookupTable;
    }

    public static List<String[]> readFlowLog(String flowLogFile) throws IOException {
        List<String[]> flowLogs = new ArrayList<>();
        List<String> lines = Files.readAllLines(Paths.get(flowLogFile));
        for (String line : lines) {
            String[] parts = line.split(" ");
            if (parts.length > 6) {
                flowLogs.add(parts);
            }
        }
        return flowLogs;
    }

    public static String getTagForFlowLog(int port, String protocol, List<TagLookup> lookupTable) {
        for (TagLookup lookup : lookupTable) {
            if (lookup.getPort() == port && lookup.getProtocol().equalsIgnoreCase(protocol)) {
                return lookup.getTag();
            }
        }
        return "Untagged";
    }

    public static void generateTagCounts(List<String[]> flowLogs, List<TagLookup> lookupTable, String outputTagFile, String outputPortProtocolFile) throws IOException {
        Map<String, Integer> tagCounts = new HashMap<>();
        Map<String, Integer> portProtocolCounts = new HashMap<>();

        for (String[] log : flowLogs) {
            int dstPort = Integer.parseInt(log[5]);
            String protocol = "tcp";  // Assuming protocol from the logs is "tcp".
            String tag = getTagForFlowLog(dstPort, protocol, lookupTable);

            tagCounts.put(tag, tagCounts.getOrDefault(tag, 0) + 1);

            String portProtocolKey = dstPort + "," + protocol;
            portProtocolCounts.put(portProtocolKey, portProtocolCounts.getOrDefault(portProtocolKey, 0) + 1);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputTagFile))) {
            writer.write("Tag,Count\n");
            for (Map.Entry<String, Integer> entry : tagCounts.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue() + "\n");
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputPortProtocolFile))) {
            writer.write("Port,Protocol,Count\n");
            for (Map.Entry<String, Integer> entry : portProtocolCounts.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue() + "\n");
            }
        }
    }

    public static void main(String[] args) {
        try {
            String flowLogFile = "data/flowlogs.txt";
            String lookupFile = "data/lookup.csv";
            String outputTagFile = "data/tag_counts.csv";
            String outputPortProtocolFile = "data/port_protocol_counts.csv";

            List<TagLookup> lookupTable = readLookupTable(lookupFile);
            List<String[]> flowLogs = readFlowLog(flowLogFile);

            generateTagCounts(flowLogs, lookupTable, outputTagFile, outputPortProtocolFile);

            System.out.println("Tag counts and port/protocol counts generated successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

