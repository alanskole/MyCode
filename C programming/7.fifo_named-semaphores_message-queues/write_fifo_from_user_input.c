#include <fcntl.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <unistd.h>
#include <stdio.h> 
#include <string.h>

int main() {
    int fd;
    char * fifo = "testfifo";


    mkfifo(fifo, 0666);


    fd = open(fifo, O_WRONLY);
    char str[500]; 
    fgets(str, 500, stdin);
    write(fd, str, strlen(str));
    close(fd);

    unlink(fifo);

    return 0;
}
