#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <mqueue.h>

int main(int argc, char **argv)
{
  
  mqd_t mq;

  struct mq_attr attr;
  attr.mq_flags = 0;
  attr.mq_maxmsg = 1;
  attr.mq_msgsize = 1024;
  attr.mq_curmsgs = 0;

  char buffer[attr.mq_msgsize];

  // open the message queue
  mq = mq_open("/QueueLab8", O_CREAT | O_WRONLY, 0644, &attr);
  if (mq == -1)
  {
    perror("mq_open in write\n");
    return -1;
  }

  printf("Write a message: ");

  memset(buffer, 0, attr.mq_msgsize);
  fgets(buffer, attr.mq_msgsize, stdin);

  // send the message
  if (mq_send(mq, buffer, strlen(buffer), 0))
  {
    perror("mq_send in write");
    return -1;
  }
  mq_close(mq);

  return 0;
}