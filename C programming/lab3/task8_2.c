#include <stdio.h>

int getZero(FILE *inputFile) {
	int A = 1;
	int i = 0;
	
	if(inputFile == NULL) {
		perror("Error in opening file");
		return -1;
	}
   
   	while(1) {
   		if(feof(inputFile)) { 
        		break;
      		}
      		
      		fscanf(inputFile, "%d ", &A); //added space after "%d" like "%d " to avoid an extra zero being added if there's a zero at the end because of white space
      		
      		if (A == 0) {
      			i++;
      		}
   	}
   	
   	if (i == 0)
   		printf("The file doesn't contain any zero-value elements!\n");
   	else
   		printf("The file contains %d zero-value elements.\n", i);
   		
   	fclose(inputFile);
   	
   	return i;
}

void main () {
	FILE *inputFile;
	
	getZero(inputFile = fopen("numbers.txt","r"));
}
