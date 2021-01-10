#include <stdio.h>

int main(){
	int i = 7, *p = &i;
	printf("Value %d is stored at the address %p.\n", i, p);
	return 0;
}
