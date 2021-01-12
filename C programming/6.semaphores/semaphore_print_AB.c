#include <stdlib.h>
#include <semaphore.h>
#include <pthread.h>
#include <stdio.h>
#define size 20

char str[size];
int i = 0;
int a = 0;
int b = 0;
sem_t semaphore;
sem_t semaphore2;

void* first(void *arg) {
    for (; i < size;) {
    	
    	if (sem_wait(&semaphore) != 0) {
		printf("\n sem wait has failed\n");
		exit(1);
    	}
    
    	str[i] = 'A';
    	i++;

    	if (sem_post(&semaphore2) != 0) {
		printf("\n sem post has failed\n");
		exit(1);
    	}
    }
}

void* second(void *arg) {
    for (;i < size;) {
    
    	if (sem_wait(&semaphore2) != 0) {
		printf("\n sem wait has failed\n");
		exit(1);
	}
	
	str[i] = 'B';
     	i++;

	if (sem_post(&semaphore) != 0) {
		printf("\n sem post has failed\n");
		exit(1);
	} 	     	
    }
}

int main(int argc, char** argv) {
    int err = 0;
    pthread_t t1;
    pthread_t t2;
    
    if (sem_init(&semaphore, 0, 1) != 0) {
	printf("\n sem init has failed\n");
	exit(1);
    }
    
    if (sem_init(&semaphore2, 0, 0) != 0) {
	printf("\n sem init has failed\n");
	exit(1);
    }
    
    err = pthread_create(&t1, NULL, (void *)second, (void *)1);

    if (err != 0) {
	perror("pthread_create encountered an error");
	exit(1);
    }
    
    else {
	err = 0;
    }
    
    err = pthread_create(&t2, NULL, (void *)first, (void *)2);

    if (err != 0) {
	perror("pthread_create encountered an error");
	exit(1);
    }
    
    else {
	err = 0;
    }
    
    pthread_join(t1, NULL);
    pthread_join(t2, NULL);
    sem_destroy(&semaphore);
    sem_destroy(&semaphore2);
   
    printf("%s \n",str); 
    return 0;
}
