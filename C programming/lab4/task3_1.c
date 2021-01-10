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
	
	for(int i = 0; i < n; i++) {
		printf("%d\n",A[i]);
	}
	
	free(A);
	
	return 0;
}
