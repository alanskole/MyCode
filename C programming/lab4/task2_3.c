#include<stdio.h>

int main() {
	
	char str[1000];
	char *point = str;
	int count = 0;
	
	printf("\nEnter a string: ");
	fgets(str,1000,stdin);
	int i = 0;
	
	while(*point !='\0') {
	
		if (count == 1000)
        		break;
        	
        	if(str[i] == ' ')
        		count--;	
		
		point++;
        	i++;
        	count++;
    	}
	
	if (count > 0)
		count--;
	
	printf("%d letters in your string \n", count);
	
	return 0;
}
