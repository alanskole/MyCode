#include <stdio.h> 
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h> 
#include <stdlib.h>
#include <string.h>

int main(int argc, char* argv[])
{
	pid_t pid;

	char op[10];
	int num1, num2; 
	
	printf("Enter the operator (add, sub, mul, div)\n");
	
	scanf("%s", op);
	
	if(strcmp(op, "add") == 0|| strcmp(op, "sub") == 0|| strcmp(op, "mul") == 0 || strcmp(op, "div") == 0) {
		printf("Enter the first number\n");

		int scnf1 = scanf("%d", &num1);
    		if (scnf1 != 1) {
    			printf("Cannot read number\n");
        		exit(-1);
        	}
        	
		printf("Enter the second number\n");
		int scnf2 = scanf("%d", &num2);
    		if (scnf2 != 1) {
    			printf("Cannot read number\n");
        		exit(-1);
        	}
	}
	
	else {
		printf("Didn't find a valid operator\n");
		exit(-1);
	}
	
	pid = fork();
	if (pid ==  0)  //Child process
	{
		char num1_str[20], num2_str[20];

		sprintf(num1_str, "%d", num1);
		sprintf(num2_str, "%d", num2);
		
		//executing cal program
		execl("./cal", "./cal", op, num1_str, num2_str, NULL);
			
	}
	else //Parent process
	{	
	    	int status;
		waitpid(pid, &status, 0);
		printf("Done!\n");
	}
	
	return 0;
}
