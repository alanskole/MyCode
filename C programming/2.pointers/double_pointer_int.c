#include<stdio.h>
#include<stdlib.h>
int main(){
	int m, n;
	
	printf("Enter the nmuber of rows and columns for matrix: ");
	
	scanf("%d%d", &m, &n);
	
	int **a;
	
	a = (int **) malloc(m * sizeof(int *));
	
	for(int i=0; i<m; i++){
		a[i] = (int *) malloc(n * sizeof(int));
	}
	
	for(int i = 0; i < m; i++) {
		for(int j = 0; j < n; j++) {
			printf("Enter the integers on row number %d ", i+1);
			scanf("%d",&a[i][j]);
			
		}
	}
	
	for(int i = 0; i < m; i++) {
		for(int j = 0; j < n; j++) {
			printf("%d ",a[i][j]);
		}
		printf("\n");
	}
	
	free(a);
	
	return 0;
}
