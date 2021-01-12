#include<stdio.h>
#include <stdlib.h>

void main() {
	int i;
	int A[10];
	int Sum = 0;
	
	freopen("Array.txt", "r", stdin);
	
	for (i = 0; i < 10; i++) {
		scanf("%d", &A[i]);
		Sum += A[i];
		printf("%d", A[i]);
		
		if (i != 9)
			printf(", ");
			
		else
			printf("\nSum: %d\n", Sum);
	}

	fclose (stdout);
}
