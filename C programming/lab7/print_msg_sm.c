#include   <pthread.h>
#include   <semaphore.h>
#include   <stdio.h>
#include   <stdlib.h>
#include   <errno.h>
#include   <sys/types.h>
#include   <unistd.h>

sem_t semaphore;
pthread_mutex_t lock;

void* Thread1PrintMessage(void* ThreadId) {
     pthread_mutex_lock(&lock);
     
     if (sem_wait(&semaphore) != 0) { 
        	printf("\n sem wait has failed\n"); 
        	exit(1); 
     }

     printf("Welcome to ");

      
     if (sem_post(&semaphore) != 0) { 
        	printf("\n sem post has failed\n"); 
        	exit(1);
     }
     
     pthread_mutex_unlock(&lock);
}

void* Thread2PrintMessage(void* ThreadId) {
      pthread_mutex_lock(&lock);
      
      if (sem_wait(&semaphore) != 0) { 
        	printf("\n sem wait has failed\n"); 
        	exit(1);
      }

      printf("Østfold University College\n");

      
      if (sem_post(&semaphore) != 0) { 
        	printf("\n sem post has failed\n"); 
        	exit(1);
      }
      
      
      pthread_mutex_unlock(&lock); 
}

int main(int argc, char** argv) {
      int err = 0;
      pthread_t t1;
      pthread_t t2;
      
       if (pthread_mutex_init(&lock, NULL) != 0) { 
        	printf("\n mutex init has failed\n"); 
        	exit(1); 
    	}
    	
    	if (sem_init(&semaphore, 0, 1) != 0) { 
        	printf("\n sem init has failed\n"); 
        	exit(1); 
    	} 

       err = pthread_create(&t1, NULL, (void*)Thread1PrintMessage, (void*)1);
       if (err != 0) {
            perror("pthread_create encountered an error");
            exit(1);
       }
       else {
            err = 0;
       }
       err = pthread_create(&t2, NULL, (void*)Thread2PrintMessage, (void*)2);
       if (err != 0) {
            perror("pthread_create encountered an error");
            exit(1);
       }
       else {
         err = 0;
       }
       
       pthread_join(t1, NULL);
       pthread_join(t2, NULL);
       
       pthread_mutex_destroy(&lock);
       sem_destroy(&semaphore);
       return 0;
}
