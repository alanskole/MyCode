#include <stdio.h>
#include <unistd.h>

int main() {
    execl("/bin/lffs", "ls", NULL);
    printf("What happened?\n");
}
