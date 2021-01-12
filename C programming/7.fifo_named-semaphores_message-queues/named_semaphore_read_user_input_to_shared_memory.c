#include <stdio.h>
#include <stdlib.h>
#include <sys/mman.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>
#include <sys/wait.h>

#include "shm.h"

int main(int argc, char** argv){
    sem_t *sem;
	int fd;
	fd = shm_open("/SHMExercise9", O_CREAT | O_RDWR, S_IRUSR | S_IWUSR | S_IRGRP);
	if(!fd){
		perror("shm_open\n");
		return -1;
	}
	if(ftruncate(fd, sizeof(struct SHM_SHARED_MEMORY))){
		perror("ftruncate\n");
		return -1;
	}
	struct SHM_SHARED_MEMORY* shared_mem;
	shared_mem = mmap(NULL, sizeof(struct SHM_SHARED_MEMORY), PROT_READ | PROT_WRITE, MAP_SHARED, fd, 0);
	if(!shared_mem){
		perror("mmap\n");
		return -1;
	}
	if(close(fd)){
		perror("close\n");
		return -1;
	}

    sem=sem_open("/semaph", O_CREAT, 0600, 0);
    if(sem == SEM_FAILED) {
        perror("read sem_open failed");
        return -1;
    }

    sem_wait(sem);
	sleep(5);
	printf("Input string = %s\n", shared_mem->a_string);
	sem_close(sem);
    shm_unlink("/SHMExercise9");
    sem_unlink("/semaph");
}

