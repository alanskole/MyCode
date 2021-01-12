#include<stdio.h>

int fibb(int A) {
	int F[A];
	
	F[0] = 0;
	F[1] = 1;
	
	if (A == 0 || A == 1)
		return F[A];
	
	
	for (int i = 2; i<= A; i++){
	      F[i] = F[i-1] +F[i-2];
	}

	return F[A];
}

void main() {
	int A = 0;
	
	printf("Enter one interger: ");
	scanf("%d", &A);
	
	printf("%d \n", fibb(A));
}
