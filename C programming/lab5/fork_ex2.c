#include <sys/types.h>
#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main() {
    pid_t pid;
    pid = fork();
    if (pid == 0) 
    {
        /* this is the child process */
        printf("The child process ID is %d\n", getpid());
        printf("The child's parent process ID is %d\n", getppid());
    } 
    else if (pid > 0)
    {
        /* this is the parent process */
        printf("The parent process ID is %d\n", getpid());
        printf("The parent's parent process ID is %d\n", getppid());
    }
    else
    {
    	perror("fork");
    	exit(-1);
    }
    sleep(2);
    return 0;
}
