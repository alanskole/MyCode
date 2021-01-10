#include <fcntl.h>
#include <stdio.h>
#include <sys/stat.h>
#include <unistd.h>

int main() {
    int fd;
    char * fifo = "testfifo";
    char str[500];

    fd = open(fifo, O_RDONLY);
    read(fd, str, 500);
    printf("%s\n", str);
    close(fd);

    return 0;
}
