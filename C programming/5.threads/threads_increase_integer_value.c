#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <errno.h>
#include <unistd.h>

void *Increase(void *a_void_ptr){
	int *a_ptr = (int *)a_void_ptr;
	
	*a_ptr = 1000;
 
   	printf("Increasing a finished thanks to the second thread\n");

}

int main (int argc, char *argv[]){
	pthread_t thread;
	int ret;
	int a = 0, b = 0;
	
	printf("Initial values: a = %d, b = %d\n", a, b);
	
	b = 1000;
	
	printf("Increasing b finished thanks to the main thread\n");

	ret = pthread_create(&thread, NULL, Increase, &a);
		
	if (ret){
		perror("pthread_create encountered an error!");
		exit(-1);
	}
	
	pthread_join(thread, NULL);
	
	printf("Final values: a= %d, b= %d\n", a, b);
	
	pthread_exit(NULL);
	
	return 0;
}
