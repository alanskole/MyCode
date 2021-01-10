<h1 align="center"> ITF22519 - Introduction to Operating Systems </h1>
<h3 align="center"> Autumn 2020 </h2>
<h3 align="center"> Lab 5: Introduction to Unix Processes </h2>

Before you start, remember to `commit` and `push` your previous lab to your git repository. Then, try to `pull` the new lab:

```bash
$ cd ITF22519/labs
$ git pull upstream master
$ git pull origin master
$ cd lab5
```
Please remember these steps for the future labs.

> I wrote this lab using Markdown (`.md`) which is a very simple markup language for documentation. You can find an example of markdown [here](https://github.com/ITF22519/demo-markdown). I was thinking maybe you should also try using markdown to write your reports (or enter your answers of the lab exercises directly into this file). **This is not mandatory, just a suggestion**. I just wanted to introduce to you a very cool language that developers use to document their code.

## About Unix Processes
1. When a system is booted, the first user space process is `systemd` (or `/sbin/init` depending on your Linux Distribution), and has a PID of 1. This process will in turn launch startup scripts and eventually login prompts. If you do a `ps -el`, you should see that process 1 is `systemd`. It will be the ancestor of all other user processes on the system.
2.  When you login or start a terminal, a process for the shell is started. The shell will then launch other processes, which will be children of the shell. If the parent process dies (for example, you exit the shell), `systemd` will adopt the orphaned processes (on Linux and many other Unix variants). 
3. The *status* of a Unix process is shown as the second column of the process table (viewed by executing the `ps` command). Some of the states are R: *running*, S: *sleeping*, Z: *zombie*.

## Process Table
The program `print_pid.c` prints out its process id as well as its parent’s id, and then sleeps for 2 minutes. Run the program `print_pid.c` twice, both times as a background process (i.e. suffix it with an ampersand “`&`”):
```bash
$ ./print_pid &
Ctrl+C
$ ./print_pid &
Ctrl+C
```
Pay attention to the id of the parent processes printed by this program. Once both processes are running as background processes, view the process table (use `ps -l` to view only the processes running on your terminal, or `ps -el` to view all processes on the system). If you see the message “*I am awake*”, the process has finished and will no longer show up in the process table. The first line from a `ps -l` is duplicated here:
```bash
F S     UID     PID  PPID   C PRI    NI ADDR     SZ WCHAN     TTY   TIME CMD
```
A short description of the fields could be found here: https://docs.oracle.com/cd/E19455-01/805-7229/spprocess-47/index.html. You should be able to find more details of these fields in the `ps` manual page (`man ps`).

## Exercise 1 (10 pts)
- **(8 pts)** In the report, include only relevant lines from `ps -l` for your programs, and point out the following items:
  - process name (Process CMD)
  - process state (decode the letter!)
  - process ID (PID)
  - parent process ID (PPID)
- **(2 pts)** Find out the name of the process that started your `print_pid` programs. What is it, and what does it do?

## Kill processes

Now that you know how to find a PID for a running program, you can use `kill` to terminate an unwanted process. Just type `kill` followed by the process ID. If a process refuses to terminate, `kill -9` followed by the process ID will terminate any process you have permission to kill. You might also try `killall` to kill all processes with the same name (try `man killall` for more information).

```bash
$ killall print_pid
```



## The fork() system call
The `fork()` call creates a child that is a copy of the parent process. Because the child is a copy, both it and its parent process begin from just after the `fork()`. All of the statements after a call to `fork()` are executed by both the parent and child processes. However, both processes are now separate and changes in one do not affect the other.

The code fragment below calls `fork()` once, which results in the creation of one child process. Then, each process prints out its id and the parent’s id.

``` c
int main() {
    fork();
    printf("Process %d's parent process ID is %d\n", getpid(), getppid());
    return 0;
}
```

The output of this program could be:

```bash
Process 14427's parent process ID is 6891
Process 14428's parent process ID is 14427
```

Here, the id of the main function is 14427, the child id is 14428, and the id of bash shell is 6891. The process tree of this program is showed below.

<img src="./img/process_tree.png" width="100">

## Exercise 2 (10 pts)
Compile and execute the program `fork_ex1.c`.
-   **(1 pts)** Include the output of the program.
-   **(5 pts)** Draw the process tree (label processes with PIDs).
-   **(4 pts)** Explain how the tree was built.

## Make the new Process do Something Different
Since the `fork()` function call creates a clone of the parent process and returns different values for the parent and child processes. Using these two return values, the child process can be made to do something that is not the same as the parent. Firstly, we can use the fact that the value returned by `fork()` is zero for the child. To differentiate between which process is which, the simplest is to use an if statement.

## Exercise 3 (5 pts)
The program `fork_ex2.c` prints different messages in the child and parent processes. Complete the condition of the if statements in this program.

## Waiting on the Child Process
When running the program `fork_ex2.c`, the parent and child process will each execute at their own pace, and either can finish execution before the other. In the event that this is not the desired behavior, there are two system calls that the parent can run that will guarantee that the parent waits for the child process to exit. The two functions are `wait()` and `waitpid()`. Full information on what each of these functions do and how they are used can be found at the manual page:

```bash
$ man 2 wait
```

To use `wait()` to make the parent process wait for the child process to complete, the following snippet of code is used:

``` c
#include <sys/types.h>
#include <sys/wait.h>
...
int status = 0;
...
child = fork();
if(child == 0)
{
        //does something cool
}
else if(child > 0)
{
        wait(&status);
        printf("child process is done, status is: %d\n", status);
        return 0;
}
else
{
        perror("fork");
        exit(-1);
}
```

This snippet will make sure that the parent suspends execution until one of its children terminates. In the event that there are multiple children, and knowledge of a specific child process’ termination is of importance, then `waitpid()` should be used to tell the parent process to wait. The code snippet to do that is:

``` c
#include <sys/types.h>
#include <sys/wait.h>
...
int status = 0;
...
child = fork();
if(child == 0)
{
        //does something cool
}
else if(child > 0)
{
        waitpid(child, &status, 0);
        printf("child process is done, status is: %d\n", status);
        return 0;
}
else
{
        perror("fork");
        exit(-1);
}
```

This will guarantee that the parent process wait for the child process with the process id stored in `child` to terminate before continuing. Note that, as shown in the man pages for `wait()`, to make this snippet of code work like the previous snippet, change the value of the first argument for`waitpid()` (the argument that is occupied by the variable `child`) to `-1` and the behavior of this snippet and the previous snippet would be the same. So effectively:

```c
wait(&status);        // is the same as waitpid(-1, &status, 0);
```

The call to the `wait()` function results in a number of actions:
-   If the calling process has no children, `wait()` returns `-1`.
-   If the calling process has a child that has terminated (a zombie), that child’s PID is returned and it is removed from the process table.
-   Otherwise, the call blocks (suspends the process) until a child terminates. The `waitpid()` function allows the `WNOHANG` flag to be specified to return immediately in all cases.

It is highly recommended that the student read through the man page for `wait()` and `waitpid()` to fully understand how to use `wait()` and `waitpid()` and experiment with multiple `fork()` and `wait()` programs until fully comfortable with what is actually happening. What is presented here is merely a quick overview of how to use the two.

## Exercise 4 (5 pts)
Summarize the usage of `wait()` and `waitpid()`.

## Making a Process Run Another Program
To make a process run another program, the following lines of code are needed:

```c
#include <unistd.h>
.
.
.
execve(programExecutable, argArray, envArray);
.
.
.
```

With the above snippet, the process should run the program`programExecutable` with the arguments `argArray` and environments `envArray`. For more information about the exec family of function calls, how they work, which one to choose (there is more than just `execve()`), what causes them to have errors, etc., please consult the man pages at:

```bash
$ man 3 exec
$ man 2 execve
```

Also, please go through this tutorial: <https://linuxhint.com/exec_linux_system_call_c/> for more information about the usage of the `exec` family.

## Exercise 5 (5 pts)
Run the program `execl.c` and explain the results.

```c
main() 
{
  execl("/bin/ls", "ls", NULL);
  printf("What happened?\n");
}
```

Under what conditions is the `printf` statement executed? Why isn’t it always executed? 

(Hint: Consider what would happen if you changed the program to execute something other than `/bin/ls`.)

## Exercise 6 (5 pts)
The program `print_args.c` prints out the values stores inside the two arguments: `argc` and `argv` passed into the `main` function. Compile and play around with this program:

```bash
$ ./print_args 

$ ./print_args op1

$ ./print_args op1 op2 op3

$ ./print_args op1 op2 -a op3

...
```

Based on the outputs, explain the usage of `argc` and `argv`.

## Exercise 7 (20 pts)
The program `cal.c` takes three input arguments: `op` (add, sub, mul, div), `num1`, and `num2` and executes the computation accordingly. The expected output of this program showed below. Compile and try to execute this program.

```bash
$ gcc -o cal cal.c

$ ./cal add 3 5
3 add 5 equals 8

$ ./cal sub 8 4
8 sub 4 equals 4

$ ./cal mul 4 5
4 mul 5 equals 20

$ ./cal div 8 2
8 div 2 equals 4
```

In this exercise, you are asked to complete the implementation of the program `exec_cal.c`. This program reads input from the user for the operator (add, mul, div, sub), num1 and num2. Then it creates a new child process, and executes the `cal` program with the collected arguments from the user. Follow the `TODO` instructions in the file for what should be done. One sample output of this program could be:

```bash
$ ./exec_cal
Enter the operator (add, sub, mul, div)
add
Enter the first number
3
Enter the second number
4
3 add 4 equals 7
Done!
```
## Exercise 8 (40 pts)
With information on how to create a child process, and make this child process run a different program, the task for the student for this exercise is to create a program `run_command.c` that when given the terminal command as input arguments, it would execute the command in a child process. The specification of the program is as follows:
- The program could take any arbitrary arguments depending on the given command. For example:
  - `run_command ls`
  - `run_command pwd`
  - `run_command ls -l`
  - `run_command wc -l report.txt`
- The program will only have to support the following commands: `ls, cat, ps, pwd, wc`.
- If there is no argument, or the input command is not supported, a usage message should be printed and the program exits.
-  In the event that an error occurred, there should be an error message associated with it (so use `perror`), and the program should exit. There should be no segmentation faults.
-  The parent process should wait on the child process (the process that executes the command) to terminate and print out the termination status.

Example usage:

```bash
$ ./run_command ls -a
.  ..  cal  cal.c  exec_cal  exec_cal.c run_command  run_command.c
Parent process exited succesfully!

$ ./run_command pwd
/home/nal/ITF22519/labs/lab5
Parent process exited succesfully!

$./run_command wc -l cal.c
47 cal.c
Parent process exited succesfully!

$ ./run_command cat cal.c
#include <stdio.h>
#include <sys/types.h>
#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
....
....

Parent process exited succesfully!
```

## Extra credit
- **(5 pts)** What is a zombie process? 
- **(15 pts)** Write a program that creates a zombie process and describe how to verify that the zombie process has been created.
- **(10 pts)** What does `vfork()` do that `fork()` does not do? Why is `vfork()` preferred when the child process immediately call `exec`?

## What To Submit
Complete the the exercises in this lab and put your code along with **lab5_report** (either PDF, Markdown, or TXT file) into the **lab5** directory of your repository. Run `git add .` and `git status` to ensure the files have been added and commit the changes by running `git commit -m Commit Message`. Finally, submit your files to GitHub by running `git push`. Check the GitHub website to make sure all files have been submitted.
