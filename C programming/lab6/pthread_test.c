#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <errno.h>
#include <sys/types.h>
#include <unistd.h>
#define NUM_OF_THREADS 10

void *PrintMessage(void *ThreadId){
	long tid;
	tid = (long)ThreadId;
	printf("Hello World from Thread #%ld!\n", tid);
	pthread_exit(NULL);
}

int main (int argc, char *argv[]){
	pthread_t threads[NUM_OF_THREADS];
	int ret;
	long i;
	
	for(i=0; i<NUM_OF_THREADS; i++){
		printf("Creating Thread %ld in the main() function\n", i);
		ret = pthread_create(&threads[i], NULL, PrintMessage, (void *)i);
		
		if (ret){
			printf("ERROR in creating thread; return ERROR code %d\n", ret);
			exit(-1);
		}
		pthread_join(threads[i], NULL);
	}
	
	pthread_exit(NULL);
	
	return 0;
}
