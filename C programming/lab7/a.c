#include <semaphore.h>
#include <pthread.h>
#include <stdio.h>
#define size 20

char str[size];
int i = 0;
int a = 0;
int b = 0;
sem_t semaphore;
pthread_mutex_t lock;

void* first(void *arg) {
    pthread_mutex_lock(&lock);
    for (; i < size;) {
     	if (a - b == 1) {
     		sem_post(&semaphore);
     		pthread_mutex_unlock(&lock);
     	} 
     	else {
     		sem_wait(&semaphore);
     		str[i] = 'A';
     		i++;
     		a++;
     	}
    }

}

void* second(void *arg) {        
    pthread_mutex_lock(&lock);
    for (;i < size;) {
     	if (b - a == 1) {
     		sem_post(&semaphore);
     		pthread_mutex_unlock(&lock);
		
     	} 
     	else {
     	     	sem_wait(&semaphore);
		str[i] = 'B';
     		i++;
     		b++;
     	}
     	     	
    }
}

int main(int argc, char** argv) {
    pthread_mutex_init(&lock, NULL);
    sem_init(&semaphore, 0, 1);
    
    pthread_t firstThr, secondThr; 
    
    pthread_create(&firstThr, NULL, first, NULL);
    pthread_create(&secondThr, NULL, second, NULL);
    
    pthread_join(firstThr, NULL);
    pthread_join(secondThr, NULL);
    
    pthread_mutex_destroy(&lock);
    sem_destroy(&semaphore);

       
    printf("%s \n",str); 
    return 0;
}
