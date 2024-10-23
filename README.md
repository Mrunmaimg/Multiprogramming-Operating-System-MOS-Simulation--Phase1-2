# Multiprogramming-Operating-System-MOS-Simulation--Phase1-2


This project simulates a **Multiprogramming Operating System (MOS)** in Java, implementing job handling, memory management, paging, and error handling mechanisms across two phases. The system demonstrates how an operating system can manage multiple programs with limited resources, providing a realistic simulation of job scheduling and memory paging.

## Features

### Phase 1: Basic Job Management
- **Single Program Execution**: The system loads and runs one program at a time.
- **Service Interrupts**: Handles service interrupts for:
  - **GD (Get Data)**: Reads input data into memory.
  - **PD (Put Data)**: Writes memory data to the output file.
  - **H (Halt)**: Ends the program execution.
- **Memory Management**: Jobs are loaded into a memory array of 100 blocks, each containing 4 characters.
- **Instruction Set**: The system supports basic instructions:
  - **LR**: Load register.
  - **SR**: Store register.
  - **CR**: Compare memory with the register.
  - **BT**: Branch on true.
  - **GD**: Input operation.
  - **PD**: Output operation.
  - **H**: Halt execution.
- **Error Logging**: Errors such as invalid memory access, out-of-bounds instruction counters, and invalid instructions are logged for debugging.

### Phase 2: Paging and Advanced Error Handling
- **Paging**: Implements a paging mechanism where the system dynamically allocates memory blocks using a random number generator. Programs are divided into pages stored in different memory locations.
- **Virtual to Real Address Mapping**: Implements a page table to map virtual addresses (VA) to real addresses (RA).
- **Interrupt Handling**: Introduces additional interrupts for time limit (TI) and program errors (PI).
- **Job Control**: Manages jobs using a **Process Control Block (PCB)** that tracks:
  - **JID**: Job ID.
  - **TTL**: Total Time Limit.
  - **TLL**: Total Line Limit.
- **Error Handling**: Comprehensive error handling for different scenarios:
  - **Out of Data**: The input stream reaches the end unexpectedly.
  - **Line Limit Exceeded**: More lines are written than allowed by the job's limit.
  - **Time Limit Exceeded**: The job execution exceeds the set time limit.
  - **Opcode Error**: An invalid instruction is encountered.
  - **Operand Error**: The operand in the instruction is incorrect.
  - **Invalid Page Fault**: A page fault occurs, but the page cannot be allocated.
  - **Time Limit + Operand Error**: Both the time limit is exceeded and an operand error occurs.
  - **Time Limit + Opcode Error**: The job runs out of time and encounters an invalid instruction.

## Project Structure

- **OS_PHASE1.java**: Implements the first phase of the operating system, including basic job handling, memory management, and instruction execution.
- **OS_PHASE2.java**: Extends the system with paging, interrupts, error handling, and advanced memory management.

## How to Run
1. Clone the repository:
    ```bash
    git clone https://github.com/Mrunmaimg/Multiprogramming-Operating-System-MOS-Simulation--Phase1-2.git
    ```
2. Compile and run the Java files:


## Input File Format
- **Control Cards**:
  - `$AMJ`: Start of a new job (with job ID, TTL, and TLL).
  - `$DTA`: Marks the start of the data segment.
  - `$END`: Marks the end of the job.
- **Program and Data Cards**: These represent the instructions and data for the job.


## Error Handling
The MOS system handles and logs several error types:
- **Out of Data (1)**: The program attempts to read past the available input.
- **Line Limit Exceeded (2)**: The job writes more lines than allowed.
- **Time Limit Exceeded (3)**: The job exceeds its total time limit.
- **Operation Code Error (4)**: An invalid instruction code is encountered.
- **Operand Error (5)**: The operand in an instruction is invalid.
- **Invalid Page Fault (6)**: A page fault occurs, but the page cannot be allocated.
- **Time Limit Exceeded + Operand Error (7)**: Both the time limit is exceeded and an operand error is encountered.
- **Time Limit Exceeded + Operation Code Error (8)**: Both the time limit is exceeded and an invalid operation is encountered.

## Contributing
Feel free to fork the repository and contribute! We welcome improvements, optimizations, and new features. Please submit a pull request with a detailed explanation of your changes.

