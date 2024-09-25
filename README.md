# Memory Allocation Algorithm Simulator

This JavaFX-based simulator allows users to visualize and experiment with various memory allocation algorithms in operating systems. The implemented algorithms include **Buddy System**, **First Fit**, **Next Fit**, and **Best Fit**. The tool provides random memory partition assignments, supports manual or automatic process size input, and enables process release. Additionally, users can observe memory fragmentation and manage process queues.

## Features

- **Memory Allocation Algorithms**:
  - **Buddy System**: Efficiently divides memory into partitions using powers of two.
  - **First Fit**: Allocates the first available partition that is large enough.
  - **Next Fit**: Similar to First Fit but continues searching from the last allocated position.
  - **Best Fit**: Finds the smallest partition that is large enough to fit the process.
  
- **Interactive Partition Management**:
  - Randomly generate memory partitions that can either be occupied or free.
  - Manually or randomly input process sizes.
  - Visualize memory blocks with different colors indicating whether they are occupied or free.
  
- **Process Management**:
  - Users can manually release any process, freeing up its allocated partition.
  - View a queue of processes that could not be allocated due to lack of space.
  - Re-check the queue when memory is freed to allocate waiting processes.

- **Fragmentation Analysis**:
  - Displays the **internal fragmentation percentage** at the end of the simulation through a graph.
  - Fragmentation occurs when allocated memory is larger than the requested memory size.

- **Merge Free Blocks**:
  - Adjacent free memory blocks can be merged automatically to create larger free spaces, improving future allocations.

## Quick Concepts

- **Memory Fragmentation**:
  - **Internal Fragmentation**: Wasted memory within allocated regions due to differences between partition size and requested size.
  - **External Fragmentation**: Free memory scattered in small blocks across the memory, preventing the allocation of large processes.

- **Buddy System**:
  - Divides memory into blocks of powers of two. When memory is freed, it can merge adjacent "buddies" to form larger blocks.

- **First Fit**:
  - Allocates memory to the first available partition that is large enough, potentially leading to external fragmentation if large processes fail to find a contiguous space.

- **Next Fit**:
  - A variation of First Fit but resumes the search for the next partition from where the last allocation occurred, not from the start.

- **Best Fit**:
  - Tries to minimize wasted space by selecting the smallest free partition that fits the process size, which can lead to higher internal fragmentation.

## How to Use

1. **Run the Simulator**:
   - Launch the JavaFX application. A set of memory partitions will be displayed.
   
2. **Allocate Processes**:
   - Input the size of a process manually or generate it randomly.
   - Choose one of the four algorithms to allocate the process.
   
3. **Free Processes**:
   - Select a process from the allocated memory and release it to free up space.
   
4. **Check Fragmentation**:
   - View the fragmentation percentage after the simulation ends.
   
5. **Manage Waiting Queue**:
   - Processes that cannot be allocated immediately will be added to a queue.
   - When memory is freed, re-check the queue to see if waiting processes can now be allocated.

6. **Merge Free Blocks**:
   - Free adjacent memory blocks can be merged to create larger free partitions, improving memory utilization.

## Requirements

- **Java 11+**
- **JavaFX 17+**

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/memory-allocation-simulator.git
