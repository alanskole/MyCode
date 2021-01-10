#include <stdio.h>

float Calculate_Product(float A, float B) {
	return (A * B);
}

void main() {
	float A, B;
	printf("Enter one float:");
	scanf("%f", &A);
	printf("Enter another float:");
	scanf("%f", &B);
	printf("Product is %.2f\n", Calculate_Product(A,B));
}
