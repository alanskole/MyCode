/*
Write a program that (a) reads the first element of Array.txt and assigns it to a variable
n, (b) creates an array A with the size n and dynamically allocates memory for A, (c) reads
the next n elements of Array.txt and assigns these elements with the corresponding elements
in array A - if there are not enough n valid elements in Array.txt, print out error message,
(d) prints array A and (e) finds the maximum element of A.
*/

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
