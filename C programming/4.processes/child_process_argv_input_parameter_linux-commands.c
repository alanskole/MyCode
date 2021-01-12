#include <stdio.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#define ARRAY_SIZE 500

int main (int argc, char *argv[]) {
	
	if(argc < 2) {
        	printf("usage: run_command <command[ls, cat, ps, pwd, wc]>\n");
        	exit(0);
    	}
    	
	int cmd1, cmd2, cmd3, cmd4, cmd5; 
  	
  	cmd1 = strcmp(argv[1], "ls");
  	cmd2 = strcmp(argv[1], "cat");
  	cmd3 = strcmp(argv[1], "ps");
  	cmd4 = strcmp(argv[1], "pwd");
  	cmd5 = strcmp(argv[1], "wc");
  	
	if(cmd1 !=0 && cmd2 !=0 && cmd3 !=0 && cmd4 !=0 && cmd5 !=0) {
		printf("No valid command found\n");
		return 0;
	}
				
	char *str[argc-1];
		
	char path[ARRAY_SIZE];
	
	sprintf(path, "/bin/%s", argv[1]);
		
	pid_t pid = fork();
		
	if (pid == 0) {
		
		
		if(argc > 2) {
			
			for(int i = 0; i < argc; i++) {
				str[i] = argv[i+1];
			}
			
        		execv(path, str);		
		} 
		else {	
			execl(path, argv[1], NULL);
		}
			
		}
		else if(pid > 0) {
			int status;
        		waitpid(pid, &status, 0);
        		printf("Finished successfully\n");
        		return 0;
		}
		else {
        		perror("fork failed");
        		exit(-1);
		}
	
        return 0;
}
