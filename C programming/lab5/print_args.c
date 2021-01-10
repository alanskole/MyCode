#include <stdio.h>
#include <stdlib.h>
#include <string.h>


void main (int argc, char *argv[])
{
	printf("argc = %d\n", argc);
	for(int i = 0; i < argc; i++)
		printf("argv[%d] = %s \n", i, argv[i]);
		
}
