#include<stdio.h>
#include <stdlib.h>

int main() {
	int n = 0;
	int *ar;
	int max = 0;
	
	FILE *inputFile = fopen("Array.txt","r");
	
	fscanf(inputFile, "%d ", &n);
	
	ar = (int*)malloc(n*sizeof(int));
	
	for(int i = 0; i < n; i++) {
   		if(feof(inputFile)) {
   			if (i+1 != n)
   				printf("The file doesn't have enough elements to match the value of n");
        		break;
      		}
      		
      		fscanf(inputFile, "%d ", &ar[i]);
		
		printf("%d ", ar[i]);
		
		if (ar[i] > max)
			max = ar[i];
	}
	
	printf("\nGreatest number %d\n", max);
	
	fclose(inputFile);
	free(ar);
	return 0;
}
