#include<stdio.h>
#include <stdlib.h>

int main() {
	int i = 0;
	int A[100];
	int Sum = 0;
	int *ar = A;
	FILE *inputFile = fopen("Array.txt","r");

	while(1) {
   		if(feof(inputFile)) { 
        		printf("\nSum: %d\n", Sum);
        		break;
      		}
      		
      		fscanf(inputFile, "%d ", &ar[i]);
	
		
		Sum += ar[i];
		printf("%d", ar[i]);
		
		printf(" ");
		
		i++;
	}
	
	fclose(inputFile);
	
	return 0;
}
