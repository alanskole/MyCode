#include <stdio.h>
#include <wait.h>
#include <signal.h>
#include <sys/types.h>
#include <unistd.h>
#include <stdlib.h>
#include <string.h>

void handler1(int sig) {
    printf("Received SIGUSR1 from my child\n");
    fflush(stdout);
}

int main() {
    signal(SIGUSR1, handler1);
    if (fork() == 0) {
        while (1) {
            char str[500];
            fgets(str, 500, stdin);
            //removes newline character from the end of the string str, because fgets adds \n to the end of strings
            str[strcspn(str, "\n")] = 0;
            if (strcmp(str, "quit") == 0) {
                break;
            }
            else {
                kill(getppid(), SIGUSR1);
            }
        }
    }
    wait(NULL);
    return 0;
}
