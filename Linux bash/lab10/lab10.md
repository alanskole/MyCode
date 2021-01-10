<h1 align="center"> ITF22519 - Introduction to Operating Systems </h1>
<h3 align="center"> Autumn 2020 </h2>
<h3 align="center"> Lab 10: Introduction To Shell Script </h2>

Before you start, remember to `commit` and `push` your previous lab to your git repository. Then, try to `pull` the new lab:

```bash
$ cd ITF22519/labs
$ git pull upstream master
$ git pull origin master
$ cd lab10
```

In this lab, you will learn how to write simple shell scripts using the Bourne Again SHell (Bash), and how to implement some of Bash’s built-in functions. 

## A Quick and Simple Guide to Bash Scripting
The terms shell and terminal are often used interchangeably; however, they are in fact different things.  The terminal provides an interface to type commands into the computer.  A shell such as Bash is a program which interprets and executed the commands.

Here is a ‘hello world’ Bash script:
```bash
#! /bin/bash
# 
echo “Hello World”
```

Saving this code into a file called `hello_world.sh` and run it either by typing the command:
```bash
$ bash hello_world.sh
```
or make the script executable, and run it as such:
```bash
$ chmod +x hello_world.sh
$ ./hello_world.sh
```
## Script file format
With this simple example of `hello_world.sh`, some explanation of what each line in the file `hello_world.sh` does and how this can be extended. 

### First line
The first line of a script file should tell what type of file it is, and which program should interpret it.  For example, shell scripts that start with 
```bash
#! /bin/bash
```
or
```bash
#! /bin/sh
```
are shell scripts meant to be interpreted by Bash and SH respectively.  Other scripts can include `#! /bin/python`, `#! /bin/perl`, etc.  When a script is executed on the command line the shell will search for the correct interpreter to start using this first line.  Therefore with the above example, calling 
```bash
$ ./hello_world.sh
```
is interpreted as
```bash
$ /bin/bash hello_world.sh
```
### Comments
Any line starting with a `#` and not followed by an `!` is considered a comment line and ignored by Bash.  Comments can appear anywhere in the file.  Note that most interpreters will not accept partial line comments as follow:
```bash
#! /bin/bash
echo “hello world” # print hello world
```
Instead the correct way to write this for maximum portability would be
```bash
#! /bin/bash
# Print hello world
echo “hello world”
```

### Commands
A command is anything the script is to execute.  Script commands are identical to typing commands on the command line.  For example the following set of commands:
```bash
$ git pull upstream/master
$ git status
```
could be replaced with a single shell script, which will be called `update-git.sh`:
```bash
#! /bin/bash
git pull upstream/master
git status
```
and then executed using the signal command `./update-git.sh`.  

Another example of a command would be the line `echo “Hello World”` in the script file `hello_world.sh` created earlier.

Though not very important to the use of the Bash shell, this information will be useful for portability purposes of the script and for the implementation of the task for the lab.  So far, commands have been treated as something that will work for every interpreter.  This may not be true for all commands that are in the scripts.  The set of commands that may not always work for every interpreter are known as *builtin* commands, or commands that are built-in to the interpreter.  These built-in commands are potentially different for different interpreters.  The other set of commands are aliases, functions, executables, and keywords; of which we are mainly interested in executables.  To check if a command is a builtin or not, simply type into Bash:
```bash
$ type commandToCheck
```
This will return the type of the command in `commandToCheck`.  If a command is a built-in command, `type` will return 'builtin'. If a command is an executable, `type` will return 'hashed'.

Use the `type` command to check the type of `cd` and `ls` commands:
```bash
$ type cd
$ type ls
```

### Variables
Variables could be created and initialized with the sign `=`.  To access the variable, prefix its name with a `$` sign.
Here is a 'Hello World' example with variable:
```bash
#!/bin/sh 
MY_MESSAGE="Hello World"  
echo  $MY_MESSAGE
```

The Shell does not care about types of variables. 
```bash
#!/bin/sh
X=1
echo "X = $X"
X=$((X+1))
echo "X = $X"
```
Another example which reads user name from the standard input (with `read` command) and create a file named `username_file` (with `touch` command). Notice the *curly brackets* around the variable :
```bash
#!/bin/sh  
echo  "What is your user name?"  
read USER_NAME 
echo  "Hello $USER_NAME"  
echo  "I will create you a file called ${USER_NAME}_file" 
touch "${USER_NAME}_file"
```

### Command Line Arguments
Command line arguments can be passed to a shell script just like any c program.  The number of command line arguments passed is stored in a variable named `$#` and each argument is stored in `$1`, `$2`,…, `$n`.  The variable `$0` is the name of the program by convention.  Here is an example. Put following scripts inside `print_variable.sh` and check the output:
~~~bash
#! /bin/bash
echo "$0 was called with $# arguments"
~~~
### Condition
The syntax for condition is:
```bash
if [ ... ]; then  
	# if-code  
else  
	# else-code  
fi
```

An example of multiple if else conditions is shown below. Notice the *spaces* around the *square brackets* as well as the *quote* around the variables:
```bash
#!/bin/sh
echo "Enter a number"
read X
if [ "$X" -lt "10" ]; then
   echo "The number $X is less than 10"
else
   if [ "$X" -eq "10" ]; then
      echo "The number $X is equal to 10"
   else
      echo "The number $X is greater than 10"
   fi
fi
```

Here https://www.shellscript.sh/quickref.html you can find a list of useful comparisons.

### Further Information
There are far more capabilities to Bash scripting than discussed here.  Examples include conditional if statements, loops, and math.  Excelent resources to learn more is:
- [https://www.shellscript.sh/](https://www.shellscript.sh/)
- [http://linuxcommand.org/lc3_writing_shell_scripts.php](http://linuxcommand.org/lc3_writing_shell_scripts.php)

## How to Set and Use Environment Variables
When a shell is started, it has to keep track of a lot of settings for resource access and properties.  How it keeps track of all of this is through what is called an environment.  This is a list of variables that hold all sorts of information for the correct execution of the shell.  An interesting property of the environment is that any child shell or process of the shell will inherit the variables when started from the parent shell.  To list all environment variables that the shell has access to, the following command is used:
```bash
$ printenv
```
For better readability use:
```bash
$ printenv | less
```
The output should show some familiar variables, such as `PATH`, `SHELL`, and `HOME`.  In the event that printing out the environment variables are insufficiently interesting, creation of personalized environment variables can be performed as well.  To create a new environment variable, use the following command:
```bash
$ export VAR_TEST=valueForVar
```
This command will place `VAR_TEST` in the list of environment variables with the value of `valueForVar`.  Note that `valueForVar` will be interpreted as a string, regardless of what its value is.  To use a variable(or more specifically, expand it), place a `$` before the variable name, as such:
```bash
$ echo $PATH
```
This would expand the `PATH` variable.  An example that some students may be familiar with in this regard is appending a directory to the `PATH`:
```bash
$ export PATH=$PATH:path/to/new/executable/directory
```

This example would add the path `path/to/new/executable/directory` to the PATH variable, allowing the user to access the new executable installed in the directory without typing out the full path to it.

<!-- ## Exercise (100 pts)
In this lab, your are required to implement a script interpreter similar to Bash called the Cyclone Advanced SHell (CASH) that can interpret the bash commands that were outlined in the previous section.  The idea is to use all of the information and system calls covered `(getenv, setenv, chdir, getcwd)`or reviewed in the previous sections to create something that functionally works like a shell script interpreter.  

The exact specification of the program `cash.c` is as follows:
- The interpreter will have one argument, the script that it will interpret (e.g., `cash my_script.sh`).
- If there is no argument, or the argument is not a `cash`script file, it should print out usage information before exiting. To determine if a file is not a script file, check the first line of the file to see if it is `#! /bin/cash`
- If any errors are encountered during program execution, the program should print out error information and exit.  
- No segmentation faults should be allowed to occur.
- The interpreter should be able to support the following types of commands in the script:
   1. `cd` (use `chdir`)
   2. `pwd` (use `getcwd`)
   3. `export` (use `getenv` and `setenv`)
   4. `echo` (use `printf` or any other printing functions)
   5. any program that is either user created (if given the path to it), or can be found in one of the directories specified in `$PATH` (use `exec` function)
- The interpreter should be able to ignore all text following a comment.
- The interpreter should be able to run any command in the background (if the command has a `&` at the end of it), and when the background process is complete, a line should be printed in the following format (use `fork` and `wait` functions):
```
[process_id] exit_status		command
```
In your report, explain any features you implemented and an example script that shows them working.

## Hints
These man pages may be of interest to the students:
 - `man 3 getenv`
 - `man 3 setenv`
 - `man 2 chdir`
 - `man 3 getcwd`
 
 ## Extra Credit
Extra credit will be given for implementing additional features from Bash into your interpreter: 
- Aliasing commands (**20 pts per feature)**
- Variables (**30 pts per feature)**
- Conditions (**40 pts per feature)** -->

## Exercise (100 pts)
Create a script file `copy.sh` that does the following:
+ The script takes two input parameters: `source_file` and `dest_file`: `copy.sh <source_file> <dest_file>`
+ If the number of input parameters is not two, the script should print out usage message and exit.
+ If the `source_file` exists, copy it to the `dest_file`, and print out the number of lines in this file.
+ If the `source_file` does not exist, print out the error message and exit.

Expected output of the script is showed below:
```bash
$ ./copy.sh test.txt
Usage: ./copy.sh <source_file> <dest_file>

$ ./copy.sh random_file1 random_file2
The file random_file1 does not exist

$ ./copy.sh test.txt test2.txt
Copying the file test.txt to test2.txt
The file test.txt has 8 lines
```
### Hints
To get number of the lines of a file, use `wc -l` together with `awk`: `wc -l <file_name> | awk '{print $1}`

## What To Submit
Complete this lab and put your files into the **lab10** directory of your repository. Run `git add .` and `git status` to ensure the files have been added and commit the changes by running `git commit -m Commit Message`. Finally, submit your files to GitHub by running `git push`. Check the GitHub website to make sure all files have been submitted.
