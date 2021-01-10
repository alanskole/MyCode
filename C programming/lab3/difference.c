#include <stdio.h>

void main() {
	float A, B;
	float Diff = 0;
	printf("Enter one float:");
	scanf("%f", &A);
	printf("Enter another float:");
	scanf("%f", &B);
	
	Diff = A - B;
	
	printf("The difference between %.2f and %.2f is %.2f\n", A,B,Diff);
}
