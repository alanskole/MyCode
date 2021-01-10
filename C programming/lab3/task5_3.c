#include<stdio.h>

void main() {
	int i = 1;
	int x = 1;
	
	for( i; i < 101; i++ ){
		printf("%d ", i);
		x++;
		if (x == 11){
		  x = 1;
		  printf("\n");
		}
	}
}
