import java.io.*;
import java.util.Arrays;

public class OS_PHASE1 {
    private char[][] Memory = new char[100][4]; // Memory
    private char[] R = new char[4]; // Register
    private char[] IR = new char[4]; // Instruction Register
    private int IC = 0; // Instruction counter
    private boolean C = false; // Toggle Value
    private int SI = 0; // Interrupt
    private char[] buffer = new char[200]; // Buffer
    private int jobNumber = 0; // Job Number Counter

    private BufferedReader inputFile;
    private BufferedWriter outputFile;

    public OS_PHASE1() {
        // Initialize memory and registers
        for (int i = 0; i < Memory.length; i++) {
            Arrays.fill(Memory[i], ' ');
        }
        Arrays.fill(R, ' ');
        Arrays.fill(IR, ' ');
    }

    private void INIT() {
        for (int i = 0; i < Memory.length; i++) {
            Arrays.fill(Memory[i], ' ');
        }
        Arrays.fill(R, ' ');
        Arrays.fill(IR, ' ');
        C = false;
        IC = 0;
    }

    private void LOAD() {
        System.out.println("Reading Data...");
        int x = 0;
        try {
            String line;
            while ((line = inputFile.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("$AMJ")) {
                    INIT();
                    jobNumber++;
                    System.out.println("Job " + jobNumber + " loading started..."); // Notify job loading
                } else if (line.startsWith("$DTA")) {
                    Start();
                } else if (line.startsWith("$END")) {
                    x = 0;
                    System.out.println("Job " + jobNumber + " Memory Allocation:");
                    printMemory(); // Print memory allocation to terminal for each job
                    outputFile.write("\n\n\n"); // Three-line spacing after each job output in the file
                    System.out.println("Job " + jobNumber + " loading completed.");
                } else {
                    int k = 0;
                    int limit = x + 10;
                    while (x < limit && k < line.length()) {
                        for (int j = 0; j < 4; j++) {
                            if (k < line.length()) {
                                Memory[x][j] = line.charAt(k++);
                            } else {
                                break;
                            }
                        }
                        x++;
                        if (k >= line.length()) {
                            break;
                        }
                    }
                }
            }
        } catch (IOException e) {
            logError("An error occurred: " + e.getMessage());
        }
    }

    private int OppAdd() {
        if (Character.isDigit(IR[2]) && Character.isDigit(IR[3])) {
            return (IR[2] - '0') * 10 + (IR[3] - '0');
        } else {
            return -1;
        }
    }

    private void Start() {
        IC = 0;
        EXECUTE();
    }

    private void EXECUTE() {
        while (true) {
            if (IC < 0 || IC >= 100) {
                logError("Instruction Counter out of bounds: " + IC);
                break;
            }

            IR = Memory[IC].clone();
            IC++;

            int loc = OppAdd();

            if (loc == -1 || loc >= 100 || loc < 0) {
                logError("Memory location out of bounds or invalid: " + loc);
                continue;
            }

            String instruction = new String(IR, 0, 2);
            switch (instruction) {
                case "GD":
                    SI = 1;
                    MOS();
                    break;
                case "PD":
                    SI = 2;
                    MOS();
                    break;
                case "H":
                    SI = 3;
                    MOS();
                    return;
                case "LR":
                    R = Memory[loc].clone();
                    break;
                case "SR":
                    Memory[loc] = R.clone();
                    break;
                case "CR":
                    C = Arrays.equals(Memory[loc], R);
                    break;
                case "BT":
                    if (C) {
                        IC = loc;
                        C = false;
                    }
                    break;
                default:
                    logError("Invalid instruction: " + new String(IR).trim());
            }
        }
    }

    private void MOS() {
        switch (SI) {
            case 1:
                READ();
                break;
            case 2:
                WRITE();
                break;
            case 3:
                HALT();
                break;
        }
    }

    private void READ() {
        try {
            String line = inputFile.readLine();
            if (line == null)
                return;
            buffer = line.toCharArray();
            int k = 0;
            int loc = OppAdd();

            for (int l = 0; l < 10; l++) {
                for (int j = 0; j < 4; j++) {
                    if (k < buffer.length) {
                        Memory[loc][j] = buffer[k++];
                    }
                }
                if (new String(Memory[loc]).startsWith("$END")) {
                    return;
                }
                loc++;
            }
        } catch (IOException e) {
            logError("An error occurred: " + e.getMessage());
        }
    }

    private void WRITE() {
        try {
            int loc = OppAdd();
            for (int l = 0; l < 10; l++) {
                outputFile.write(new String(Memory[loc]));
                loc++;
            }
            outputFile.write('\n'); // No extra spacing between lines
        } catch (IOException e) {
            logError("An error occurred: " + e.getMessage());
        }
    }

    private void HALT() {
        try {
            outputFile.write("\n\n\n"); // Three-line spacing after job completion
        } catch (IOException e) {
            logError("An error occurred: " + e.getMessage());
        }
    }

    private void printMemory() {
        for (int i = 0; i < 100; i++) {
            StringBuilder line = new StringBuilder(String.format("%02d |", i));
            for (int j = 0; j < 4; j++) {
                line.append(String.format(" %c |", Memory[i][j]));
            }
            System.out.println(line);
            if (i % 10 == 9) {
                System.out.println();
            }
        }
    }

    private void logError(String message) {
        try (BufferedWriter errorFile = new BufferedWriter(new FileWriter("error_log.txt", true))) {
            errorFile.write(message);
            errorFile.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        OS_PHASE1 os_phase1 = new OS_PHASE1();
        System.out.println("Phase 1 Implemented\n");
        System.out.println("Processing jobs...");

        try {
            String inputFilePath = "./input.txt";
            String outputFilePath = "./output.txt";

            os_phase1.inputFile = new BufferedReader(new FileReader(inputFilePath));
            os_phase1.outputFile = new BufferedWriter(new FileWriter(outputFilePath));

            System.out.println("File exists and is being processed.");
            os_phase1.LOAD();

            os_phase1.inputFile.close();
            os_phase1.outputFile.close();
        } catch (FileNotFoundException e) {
            os_phase1.logError("Error: File not found. Please check the file path.");
        } catch (IOException e) {
            os_phase1.logError("An error occurred: " + e.getMessage());
        }
    }
}