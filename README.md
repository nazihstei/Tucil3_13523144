# Tucil3_13523144: Rush Hour Puzzle Solver

This project is a solution for **Tugas Kecil 3 Strategi Algoritma** at ITB, implementing a solver for the Rush Hour puzzle game using various algorithmic approaches.

## Program Overview
This program solves the Rush Hour puzzle using the following algorithms:
- **Uniform Cost Search (UCS)**
- **Greedy Best-First Search (GBFS)**
- **A***
- **Branch and Bound (B&B)**

It also supports multiple heuristic options for guiding the search:
1. **Distance of the main piece to the exit.**
2. **Number of pieces blocking the exit.**
3. **Number of "locked" pieces blocking the exit.**
4. **Minimum steps required for all blocking piece to move out of the way** (assuming no other pieces block its path).

The program outputs the solution tree details and step-by-step moves to solve the puzzle. Additionally, it features an interactive **CLI animation** to visualize the solution.

## Requirements
- **OpenJDK 21**

## How to Compile and Run
### Windows
1. Open a terminal in the project directory.
2. Run the following command:
   ```bash
   ./run.cmd
   ```

### Linux
1. Open a terminal in the project directory.
2. Grant execution permissions to the script:
   ```bash
   chmod +x run.sh
   ```
3. Run the script:
   ```bash
   ./run.sh
   ```

#### Troubleshooting for Linux
If the script fails to execute, you may need to convert line endings using `dos2unix`:
```bash
sudo apt update
sudo apt install dos2unix
dos2unix run.sh
chmod +x run.sh
./run.sh
```

## Author
**Muhamad Nazih Najmudin** (13523144)  
GitHub: [nazihstei](https://github.com/nazihstei)