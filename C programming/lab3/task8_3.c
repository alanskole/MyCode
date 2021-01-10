#include<stdio.h>
#include<math.h>

int fibb(int A) {
	int F[A][A];
	
	F[0][0] = 1;
	
	printf("%d \n", F[0][0]);
	
	if (A == 0)
		return 0;

	
	F[1][0] = 1;
	F[1][1] = 1;
	
	printf("%d ", F[1][0]);
		
	printf("%d \n", F[1][1]);
	
	if (A == 1)
		return 0;
	
	for (int i = 2; i < A; i++){
	
		F[i][0] = 1;
		F[i][i] = 1;
		
		printf("%d ", F[i][0]);
		
		for (int j = 1; j < i; j++){
			F[i][j] = F[i-1][j] + F[i-1][j-1];
			printf("%d ", F[i][j]);
		}
		
		printf("%d \n", F[i][i]);
	}
	
	return 0;
}

void main() {
	int A = 0;
	
	printf("Enter one interger: ");
	scanf("%d", &A);
	
	fibb(A);
}
