#include <stdio.h>

int Calculate_Sum(int A, int B) {
	return A + B;
}

void main() {
	int A, B;
	printf("Enter one interger:");
	scanf("%d", &A);
	printf("Enter another integer:");
	scanf("%d", &B);
	printf("Sum is %d\n", Calculate_Sum(A,B));
}
