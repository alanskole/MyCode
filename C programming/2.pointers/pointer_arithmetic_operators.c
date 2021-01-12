#include <stdio.h>

int main(){
	int A, B, Sum, Diff, Mult, *a = &A, *b = &B;
	float Div;
	
	printf("Enter one interger:");
	scanf("%d", &A);
	printf("Enter another integer:");
	scanf("%d", &B);
	
	printf("Value %d is stored at the address %p.\n", A, a);
	printf("Value %d is stored at the address %p.\n", B, b);
	
	Sum = A + B;
	Diff = A - B;
	Mult = A * B;
	Div = (float)A / (float)B;
	
	printf("Sum is %d\n", Sum);
	printf("Difference is %d\n", Diff);
	printf("Multiplication is %d\n", Mult);
	printf("Division is %.2f\n", Div);
	
	return 0;
}
