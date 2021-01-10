#include <stdio.h>
#include <sys/types.h>
#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

void main (int argc, char *argv[])
{
    if(argc < 4)
    {
        printf("usage: cal <op> <num1> <num2>\n");
        exit(0);
    }
    
    int num1 = atoi(argv[2]);
    int num2 = atoi(argv[3]);
    int result = 0;
    
    if(strcmp(argv[1], "add") == 0)
    {
        result = num1 + num2;
    }
    else if(strcmp(argv[1], "sub") == 0)
    {
        result = num1 - num2;
    }
    else if(strcmp(argv[1], "mul") == 0)
    {
        result = num1 * num2;
    }
    else if(strcmp(argv[1], "div") == 0)
    {
        result = num1/num2;
    }
    else 
    {
        printf("Unknown Operator: %s\n", argv[1]);
        exit(0);
    }
    
    printf("%d %s %d equals %d\n", num1, argv[1], num2, result);
    
        
}


