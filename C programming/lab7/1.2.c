#include <pthread.h> 
#include <stdio.h> 
#include <stdlib.h> 
#include <errno.h> 
#include <sys/types.h> 
#include <unistd.h>
#define NUM_OF_THREADS	10

int count = 0;
//added code here
pthread_mutex_t lock;

void* PrintMessage(void* ThreadId) { 
	
	long tid;
	int i;

	tid = (long)ThreadId; 
	
	//added code here
	pthread_mutex_lock(&lock);
	
	for (i = 0; i < 5; i++) {
		printf("Hello World from Thread %ld, count = %d! \n", tid, count);
		count++; sleep(2);
	}
	
	//added code here
	pthread_mutex_unlock(&lock); 
}

int main(int argc, char* argv[]) { 
	pthread_t threads[NUM_OF_THREADS]; 
	int ret;
	long i;
	
	//added code here
	if (pthread_mutex_init(&lock, NULL) != 0) { 
        	printf("\n mutex init has failed\n"); 
        	return -1; 
    	} 

	pthread_create(&threads[0], NULL, PrintMessage, (void*)0); 
	pthread_create(&threads[1], NULL, PrintMessage, (void*)1); 
	pthread_create(&threads[2], NULL, PrintMessage, (void*)2); 
	pthread_create(&threads[3], NULL, PrintMessage, (void*)3); 
	pthread_create(&threads[4], NULL, PrintMessage, (void*)4);

	pthread_join(threads[0], NULL); 
	pthread_join(threads[1], NULL); 
	pthread_join(threads[2], NULL); 
	pthread_join(threads[3], NULL); 
	pthread_join(threads[4], NULL);
	
	//added code here
	pthread_mutex_destroy(&lock); 
	
	return 0;
}
