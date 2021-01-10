#include<stdio.h>
#include <stdlib.h>
#include <errno.h>
#include <pthread.h>

struct args {
    int inpA[64][64], inpB[64][64];
};

void *read(void* arg) {
	struct args *arr = (struct args*)arg;

	FILE *inputFile = fopen("MatrixA.txt","r");
	if(!inputFile) {
   		perror("Error opening MatrixA.txt");
   		exit(-1);
	}

	for(int i = 0; i < sizeof((*arr).inpA[0])/sizeof((*arr).inpA[0][0]); i++) {
		for(int j = 0; j < sizeof((*arr).inpA[0])/sizeof((*arr).inpA[0][0]); j++) {
			fscanf(inputFile, "%d ", &(*arr).inpA[i][j]);
		}
	}
	
	FILE *inputFile2 = fopen("MatrixB.txt","r");
	if(!inputFile) {
   		perror("Error reading MatrixB.txt");
   		exit(-1);
	}
	
	for(int i = 0; i < sizeof((*arr).inpB[0])/sizeof((*arr).inpB[0][0]); i++) {
		for(int j = 0; j < sizeof((*arr).inpB[0])/sizeof((*arr).inpB[0][0]); j++) {
			fscanf(inputFile2, "%d ", &(*arr).inpB[i][j]);
		}
	}
	fclose(inputFile);
	fclose(inputFile2);
}	

void *write(void* arg) {
	struct args *arr = (struct args*)arg;
	int answ[64][64];
	
	FILE *outFile = fopen("MatrixC.txt","w");
	if(!outFile) {
   		perror("Error creating to MatrixC.txt");
   		exit(-1);
	}
	
	for(int i = 0; i < sizeof(answ[0])/sizeof(answ[0][0]); i++) {    
    		for(int j = 0; j < sizeof(answ[0])/sizeof(answ[0][0]); j++) {  
    			answ[i][j]=0;    
    			for(int k = 0; k < 64; k++) {
    			answ[i][j] += (*arr).inpA[i][k] * (*arr).inpB[k][j];    
    			}    
    		}    
    	}    
    
    	for(int i = 0; i < sizeof(answ[0])/sizeof(answ[0][0]); i++) {    
    		for(int j = 0; j < sizeof(answ[0])/sizeof(answ[0][0]); j++) {    
    			fprintf(outFile, "%d ",answ[i][j]);    
    		}    
    		fprintf(outFile, "\n");	
    	}
    	fclose(outFile);
}
 
int main () {
	struct args *arguments = (struct args *)malloc(sizeof(struct args));
	int status = 0;
	pthread_t thread1;
	pthread_t thread2;
	
	status = pthread_create(&thread1,NULL,read,(void *)arguments);
	
	if (status){
		printf("ERROR in creating thread; return ERROR code %d\n", status);
		exit(-1);
	}
	
	pthread_join(thread1, NULL);
	
	status = pthread_create(&thread2,NULL,write,(void *)arguments);
	
	if (status){
		printf("ERROR in creating thread; return ERROR code %d\n", status);
		exit(-1);
	}
	
	pthread_join(thread2, NULL);
	
	free(arguments);
	
	pthread_exit(NULL);
	
	return 0;
}
