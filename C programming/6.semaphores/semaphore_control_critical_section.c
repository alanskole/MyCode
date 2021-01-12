
#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <semaphore.h>

sem_t semaphore;

void *Thread1PrintMessage() {
    printf("Welcome to");
    sem_post(&semaphore);
    return NULL;
}

void *Thread2PrintMessage() {
    sem_wait(&semaphore);
    printf(" Østfold University College\n");
    sem_post(&semaphore);
    return NULL;
}

int main() {
    int err;
    pthread_t t1, t2;

    err = sem_init(&semaphore, 1, 0);
    if (err != 0) {
        perror("sem_init encountered an error");
        exit(-1);
    }
    
    err = pthread_create(&t2, NULL, Thread2PrintMessage, NULL);
    if (err != 0) {
        perror("pthread_create encountered an error");
        exit(-1);
    }

    err = pthread_create(&t1, NULL, Thread1PrintMessage, NULL);
    if (err != 0) {
        perror("pthread_create encountered an error");
        exit(-1);
    }

    
    pthread_join(t1, NULL);
    pthread_join(t2, NULL);

    err = sem_destroy(&semaphore);
    if (err != 0) {
        perror("sem_destroy encountered an error");
        exit(-1);
    }
    return 0;
}
