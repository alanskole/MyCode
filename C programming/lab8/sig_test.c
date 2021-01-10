#include <stdio.h>
#include <stdlib.h>
#include <signal.h>
#include <unistd.h>
#include <sys/wait.h>

void my_quit_handler(int i)
{
	printf("\nAha. You are trying to quit but it is not that simple ~~~~ \n");
	fflush(stdout);
}

int main(int argc, char** argv)
{
	signal(SIGINT, my_quit_handler);
	while(1)
	{
		printf(".");
		fflush(stdout);
		sleep(1);
	}
	return 0;
}



