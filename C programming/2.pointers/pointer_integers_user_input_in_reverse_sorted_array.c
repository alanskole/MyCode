#include<stdio.h>
#include <stdlib.h>
int main(){
	int n;
	int *A;
	
	printf("How many integers do you want to enter? ");
	
	scanf("%d",&n);


	A = (int*)malloc(n*sizeof(int));
	if (A== NULL){
		printf("Error in Memory Allocation");
		exit (0);
	}
	
	printf("Enter %d integers: \n",n);
	
	for(int i = 0; i < n; i++) {
		scanf("%d",&A[i]);
	}
	
	int sz = 0;
	
	for(int i = 0; i < n; i++) {
		printf("%d ",A[i]);
		sz = i;
	}
	
	printf("\n");
	
	for(int i = sz; i >= 0; i--) {
		printf("%d ",A[i]);
	}
	
	printf("\n");
	
	free(A);
	
	return 0;
}
