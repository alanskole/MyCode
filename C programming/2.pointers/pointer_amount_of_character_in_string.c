#include<stdio.h>
#include <string.h>

int main(){
	char c;
	char str[1000];
	
	
	printf("Enter a character: ");
	scanf("%c", &c);
	
	printf("Enter a string: ");
	
	getchar(); //or else fgets won't wait for user input. this happens when scanf is ran before fgets
	
	fgets(str,1000,stdin);
	
	int count = 0;
	
	//strlen - 1 because of '\0' at the end of string in c
	for(int i = 0; i < strlen(str)-1; i++) {
		if (str[i] == c)
			count++;
	}
	
	printf("The letter %c appeared %d times in your string.\n" ,c,count);
	
	return 0;
}
