#include<stdio.h>

void change(int *num1, int *num2) {
	int temp;
	temp = *num1;
	*num1 = *num2;
	*num2 = temp;
}

int main() {
	int num1, num2;
	
	printf("\nEnter the first number: ");
	scanf("%d", &num1);
	
	printf("\nEnter the second number: ");
	scanf("%d", &num2);
	
	change(&num1, &num2);
	
	printf("\n\nAfter changing two numbers:");
	
	printf("\nThe first number is: %d", num1);
	
	printf("\nThe second number is: %d\n", num2);
	
	return 0;
}
