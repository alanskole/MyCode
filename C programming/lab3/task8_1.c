#include<stdio.h>

int Sum() {
	int Sum = 0;
	
	for (int i = 0; i < 100; i++) {
		Sum += i;
	}
	
	return Sum;
}

void main() {
	printf("%d \n", Sum());
}
