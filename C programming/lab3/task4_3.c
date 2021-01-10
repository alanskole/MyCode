#include <stdio.h>

void main() {
	int A, B, C;
	
	printf("Enter an integer: ");
	
	scanf("%d", &A);
	
	printf("Enter another integer: ");
	
	scanf("%d", &B);
	
	printf("Enter another integer: ");
	
	scanf("%d", &C);
	
	if (A == B || A == C || B == C)
	  printf("Some or all of the integers are equal \n");
	else 
	printf("None of the integers are equal \n");
}
