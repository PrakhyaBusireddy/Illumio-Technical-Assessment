# FlowTagging Java Project

## Description

This project processes flow log data and maps each row to a tag based on a lookup table. It generates two output files:

- `tag_counts.csv`: Contains the count of matches for each tag.

- `port_protocol_counts.csv`: Contains the count of matches for each port/protocol combination.


## Prerequisites

- Java 8 or later.

- Input files (`flowlogs.txt` and `lookup.csv`) should be placed in the `data/` folder.

 
## Project Structure

- `src/` - Contains the Java source code.

- `data/` - Contains input files and stores output files.

- `README.md` - Documentation.

- `.gitignore` - Git configuration.

 
## How to Run

1. Compile the code:

   ```

   javac src/FlowTagging.java src/TagLookup.java

   ```

2. Run the program:

   ```

   java -cp src FlowTagging

   ```

3. Output files (`tag_counts.csv` and `port_protocol_counts.csv`) will be generated in the `data/` folder.

## Input Files Format

- `flowlogs.txt`: Contains flow log entries with space-separated fields.

- `lookup.csv`: Contains mappings in the format `dstport,protocol,tag`.

## Output Files

- `tag_counts.csv`: Contains the count of matches for each tag.

- `port_protocol_counts.csv`: Contains the count of matches for each port/protocol combination.
