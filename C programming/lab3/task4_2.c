#include <stdio.h>

void main() {
	float A, B;
	float Min;
	
	printf("Enter one float:");
	
	scanf("%f", &A);
	
	printf("Enter another float:");
	
	scanf("%f", &B);
	
	if (A > B)
	  Min = B;
	else
	  Min = A;
	  
	printf("Min is %.2f \n", Min);
}
