#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <errno.h>
#include <mqueue.h>

int main(int argc, char **argv)
{
    mqd_t mq;

    struct mq_attr attr;
    attr.mq_flags = 0;
    attr.mq_maxmsg = 1;
    attr.mq_msgsize = 1024;
    attr.mq_curmsgs = 0;

    char buffer[attr.mq_maxmsg];

    // create the message queue
    mq = mq_open("/QueueLab8", O_CREAT | O_RDONLY, 0644, &attr);
    if (mq == -1)
    {
        perror("mq_open in read\n");
        return -1;
    }

    // receive the message
    ssize_t bytes_read = mq_receive(mq, buffer, attr.mq_msgsize, NULL);
    if (bytes_read == -1)
    {
        perror("mq_receive in read");
        return -1;
    }

    printf("Received message: %s", buffer);

    mq_close(mq);
    mq_unlink("/QueueLab8");

    return 0;
}