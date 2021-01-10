#include <stdio.h>

void ArrayFiller() {
	int Nums[10];
	
	for (int i = 0; i < sizeof(Nums)/sizeof(Nums[0]); i++){
	      printf("Enter an integer:");
	      scanf("%d", &Nums[i]);
	}
	
	for (int i = 0; i < sizeof(Nums)/sizeof(Nums[0]); i++){
	      printf("%d", Nums[i]);
	      
	      if (i != sizeof(Nums)/sizeof(Nums[0]) - 1)
	      	printf(", ");
	      	
	      else
	      	printf("\n");
	}
}

void main() {
	ArrayFiller();
}
