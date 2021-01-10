<h1 align="center"> ITF22519 - Introduction to Operating Systems </h1>
<h3 align="center"> Autumn 2020 </h2>
<h3 align="center"> Lab 11: Operating System Security  </h2>

Before you start, remember to `commit` and `push` your previous lab to your git repository. Then, try to `pull` the new lab:

```bash
$ cd ITF22519/labs
$ git pull upstream master
$ git pull origin master
$ cd lab11
```

In this lab we are going to examine file permissions and a few recent bugs in software that have cause major security concerns.

## Hidden Directories
Try making a directory named "test" with the `mkdir test` command.  

Run `ls` to view the files and directories in your current folder.  

Next make a hidden directory by renaming the directory one last time to ".test" with the `mv test/ .test/` command.  

Run `ls` and note that a ".test" folder is not listed.  

Navigate into the hidden folder by running the `cd .test/` command.  You are now inside a hidden directory.  

Go back up a directory by running the `cd ..` command.  

Run the  command `ls -la`.  The `-l` flag lists items line by line and the `-a` flag lists all files and directories (including hidden files and directories).  Note that these "hidden" files or directories are not really a security mechanism because access is not restricted (security through obscurity).  

Hidden files are commonly used for storing user preferences or preserving the state of a utility.  Usually the intent of hidden files is simply to reduce "clutter" in the display of the contents of a directory listing with files the user did not directly create.

### Question 1 (10 pts)
After running `ls -la` in any directory you will see two directories named "." and "..".  What are these directories and why are they in every directory? 

## File Permissions

In your current directory type `ls -l`.  The `l` option tells the `ls` program to list each file/sub directory as a line.  Notice that each line looks something like:
```
drwxrwxr-x  2 nal nal 4096 aug.  28 09:34 lab1
drwxrwxr-x  2 nal nal 4096 sep.   1 10:27 lab2
drwxrwxr-x  2 nal nal 4096 okt.  15 12:35 lab3
drwxrwxr-x  2 nal nal 4096 okt.  15 12:35 lab4
drwxrwxr-x  3 nal nal 4096 okt.  15 12:35 lab5
drwxrwxr-x  2 nal nal 4096 okt.  15 12:35 lab6
drwxrwxr-x  2 nal nal 4096 okt.  15 12:35 lab7
drwxrwxr-x  2 nal nal 4096 okt.  15 12:35 lab8
drwxrwxr-x  3 nal nal 4096 okt.  15 12:35 lab9
drwxrwxr-x  2 nal nal 4096 aug.  28 09:34 oblig1
drwxrwxr-x  2 nal nal 4096 okt.  15 12:35 oblig2
drwxrwxr-x  2 nal nal 4096 okt.  15 12:35 oblig3
drwxrwxr-x  2 nal nal 4096 okt.  15 12:35 oblig4
```
You can now see the file permissions associated with each item.  Each file and directory has three user based permission groups: owner, group, and everyone (all users).  Each file or directory also has three basic permission types: read, write, and execute.

Each line of output resulting from the `ls -l` command is displayed as: `_rwxrwxrwx 1 owner:group`.  The `_` is a special permission flag that is typically used to denote whether the item is a directory (represented with the character `d`) or a file (represented with the character `-`).  The three sets of `rwx` stand for read, write, and execute permissions being granted for the owner, group, and all users permission groups (respectively).  If the permission is not granted then a `-` character is substituted in place of the permission type. The last piece of information is the owner and group assignment.

Before answering the questions below you may want to read the man pages for the following programs `chmod`, `chown`, and `chgrp`.

### Question 2 (10 pts)
What command would you type to find the owner of a file named "secrets.dat"? 

### Question 3 (10 pts)
What command would you type to change the owner of a file named "secrets.dat" to your username?

### Question 4 (10 pts)
What would be the effect of running the command `chmod -R 777 .`? Why would you want to be very careful when running this particular command?

## Shellshock (Bash bug)

By some estimates the Shellshock bug has gone undiscovered for nearly 25 years.  There are some good explanations of the bug online.

[Shellshock Code and the Bash Bug - Computerphile](https://www.youtube.com/watch?v=MyldPMn95kk)

The impact of the Shellshock bug was originally under estimated.  The impact of the bug became much more clear as many researchers realized that several programs (including popular web servers such as Apache) make heavy use of environment variables.

Example: [http://www.securitysift.com/shellshock-targeting-non-cgi-php/](http://www.securitysift.com/shellshock-targeting-non-cgi-php/)

### Question 6 (30 pts)
What is an environment variable and how could it be used in conjunction with the Shellshock bug to remotely exploit a web server? 

## Buffer Overflows

The basic buffer overflow example we have looked at in class is shown below.

~~~c
#include <stdio.h>
int main(int argc, char **argv) {
  char buf[64];
  strcpy(buf, argv[1]);
}
~~~

Remember that it is vulnerable because:

- The program is soliciting input from the user through the program arguments
 - Input is stored to memory (buf)
 - Input bounds are not checked and data in
memory can be overwritten
 - The main function has a return address that can be overwritten to point to data in the buffer
 
Year after year, buffer overflows have ranked among the worst offenders when it comes to serious security issues.  Programmers are slowly learning to code more securely and there are many tools that can analyze your code and point out security issues during development.  With all the modern technology trying to eliminate buffer overflows, you might be wondering how they keep working their way into our code!

Please read *section 9.7.1 Buffer Overflow Attacks*  in the main textbook before continuing. 

Take a look at the infamous Sendmail Crackaddr bug below.  This code also contains a buffer overflow.  Can you spot it?  If you need help check out the extra resources below.

~~~c
# define BUFFERSIZE 200
# define TRUE 1
# define FALSE 0
int copy_it ( char * input , unsigned int length ) {
  char c, localbuf [ BUFFERSIZE ];
  unsigned int upperlimit = BUFFERSIZE - 10;
  unsigned int quotation = roundquote = FALSE ;
  unsigned int inputIndex = outputIndex = 0;
  while ( inputIndex < length ) {
    c = input [ inputIndex ++];
    if ((c == '<') && (! quotation )) {
      quotation = TRUE ; 
      upperlimit --;
    }
    if ((c == '>') && ( quotation )) {
      quotation = FALSE ; 
      upperlimit ++;
    }
    if ((c == '(') && (! quotation ) && ! roundquote ) {
      roundquote = TRUE ; 
      // upperlimit --; // decrementation was missing in bug
    }
    if ((c == ')') && (! quotation ) && roundquote ) {
      roundquote = FALSE ; 
      upperlimit ++;
    }
    // if there is sufficient space in the buffer , write the character
    if ( outputIndex < upperlimit ) {
      localbuf [ outputIndex ] = c; 
      outputIndex ++; 
    }
  }
  if ( roundquote ) {
    localbuf [ outputIndex ] = ')'; 
    outputIndex ++; 
  }
  if ( quotation ) {
    localbuf [ outputIndex ] = '>'; 
    outputIndex ++; 
  }
}
~~~

The bug was discovered in 2003 by Mark Dowd.  Later Thomas Dullien extracted a smaller toy example shown above.  The original flaw was contained in about 500 lines of code and the toy example is about 50 lines of code.  Finding the bug in this code is difficult enough with a single loop, but the original bug contains about 10 loops (with nested loops of depth of 4), GOTOs, lots of pointers, pointer arithmetic, and calls to string functions. Most program analysis tools are good at finding surface level bugs like the examples in class, but this tool is nested much "deeper" in the code.  Finding these types of bugs is an ongoing research project.

Resource: [Sendmail Crackaddr Talk](https://bytebucket.org/mihaila/bindead/wiki/resources/crackaddr-talk.pdf)

### Question 7 (30 pts)
Explain how an attacker can exploit the buffer overflow.

## What To Submit
Complete this lab and put your **lab11_report** (either PDF, Markdown, or TXT file) into the **lab11** directory of your repository. Run `git add .` and `git status` to ensure the files have been added and commit the changes by running `git commit -m Commit Message`. Finally, submit your files to GitHub by running `git push`. Check the GitHub website to make sure all files have been submitted.


## License 
Part of this lab is created based on the materials that are  provided by Jeramie Vens at ISU and are distributed under the MIT License.
<!--stackedit_data:
eyJoaXN0b3J5IjpbMTA5MzcwODkzMywtMTgyMjE2MjA4MywtOD
M0MDY3ODAyLC0xNDc2NzI1NDA4LC0xNDI1MjgxNjFdfQ==
-->