<h1 align="center"> ITF22519 - Introduction to Operating Systems </h1>
<h3 align="center"> Autumn 2020 </h2>
<h3 align="center"> Lab 9: Process Scheduling </h2>

Before you start, remember to `commit` and `push` your previous lab to your git repository. Then, try to `pull` the new lab:

```bash
$ cd ITF22519/labs
$ git pull upstream master
$ git pull origin master
$ cd lab9
```
In this lab, we are going to design several scheduling algorithms and compare their response times and other features. First, please take a look around the provided code in the `source` folder. Nearly all of this has been finished for you. However, it is good to look through it and understand how the different parts work.

## Compile and Run 

To compile the `scheduling` project:

``` bash
$ make
```

 To run the program:

```bash
$ ./scheduling <input file>
```

To remove all built files:

```bash
$ make clean
```

## Process  Structure

The file `scheduling.c` contains the main function that will call four routines (one for each algorithm implementation) with the same list of process structures. The structure is defined in the `scheduling.h` file as follow:

```C
typedef struct processes
{
  /* Values initialized for each process */
  int id;    /* Id of the process */
  int arrivaltime;  /* Time process arrives and wishes to start */
  int runtime;      /* Time process requires to complete job */
  int priority;     /* Priority of the process */

 
  int starttime; // the time this task started
  int endtime; // the time this task finished
  int remainingtime; // the estimatted remaining time this task will run for
  
  int isDone;
  int isRunning;
  
  struct processes *next;
} process_t;
```
The first four values `(id, arrivaltime, runtime, and priority)` are inputs to the scheduling algorithm and cannot be modified by your implementation of the scheduling algorithm. 

The next five values `(starttime, endtime, remainingtime), isDone, and isRunning` are available to the algorithm for storing information, and you can use this as *workspace* for your algorithm. All time values are given in seconds. You are free to add more values that are necessary for your implementation.

The last value `next` is a pointer that points to the next process in the list. This is used for the implementation of the linked list of the processes.

## Input File and Process Linked List

The `scheduling.c` program takes one input argument which is the `.in` file that stores the information about the processes. You can take a look at `processes1.in` and `processes2.in` files for more information about the format of the file. In general, it contains four columns that are corresponding to the four values of the `process_t` structure. Each line corresponds to one process.

```
Process arrival runtime priority
0	0	10	0
1	2	20	2
2	9	50	0
```

The input file will be read, and a linked list of the processes will be created as follow:

![](./LinkedList.png)

**The linked list will be sorted based on process arrival time**. The `head` of the linked list, which is a pointer to the first process of the list, will be taken as input to the four scheduling algorithms. 

## Output File

Each scheduling algorithm will create a `.out` file that stores the information about the scheduled processes. An example of the output file for the FCFS algorithm is shown below.

```
Process arrival runtime priority starttime endtime
0       0       10      0       0       10
1       2       20      2       10      30
2       9       50      0       30      80
```

## Scheduling Algorithms: 

We are going to simulate four scheduling algorithms:

#### Fist Come First Served

This algorithm simply chooses the process that arrives first and runs it to completion. If two or more processes have the same arrival time, the algorithm should choose the process that appears first in the linked list. 

The FCFS algorithm is already implemented in the `fcfs.c` file. You can take a look to get an idea of how to implement the other algorithms.

#### Least Remaining Time

This algorithm will choose the program that has the least remaining execution time left to run. Again, on a tie, select the first process in the process linked list. 

The implementation of this algorithm should be done inside `srt.c` file.

#### Round Robin

This algorithm tries to be fair in time allocation and will give each process that is available to run an equal amount of time (**a quantum**). In this implementation, assume that the scheduler can switch between processes every second.

The implementation of this algorithm should be done inside `rr.c` file.

#### Preemptive Priority

Assume the largest value of the priority variable indicates the highest priority. In this context, a process with priority level 1 will not run until all pending level 2 processes that have arrived finish, and level 0 processes will not run until all pending level 1 processes finish. If a lower priority process has started, and a higher priority process arrives, the lower priority process will be suspended, and the higher priority process will start running (preemptive).

The implementation of this algorithm should be done inside `prio.c` file.

## Exercise

**(20 pts)** Implement the function `void delete_processes(process_t *proc)` in the `scheduling.c` file.

**(60 pts)** Choose either ONE of the other three algorithms left to implement.

**(20 pts)** Create at least TWO test cases (`.in` and `.out` files) MANUALLY for your chosen algorithm. In your report, use these test cases to explain how your algorithm runs. 

## Extra Credits

Implement the other two algorithms left, **50 pts** for each, including the test cases.


## What To Submit
Complete this lab and put your code along with **lab9_report** (either PDF, Markdown, or TXT file) into the **lab9** directory of your repository. Run `git add .` and `git status` to ensure the files have been added and commit the changes by running `git commit -m Commit Message`. Finally, submit your files to GitHub by running `git push`. Check the GitHub website to make sure all files have been submitted.
