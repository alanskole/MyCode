#include <stdio.h>

void main() {
	int A, B;
	int Max;
	
	printf("Enter one interger:");
	
	scanf("%d", &A);
	
	printf("Enter another integer:");
	
	scanf("%d", &B);
	
	if (A < B)
	  Max = B;
	else
	  Max = A;
	  
	printf("Max is %d \n", Max);
}
