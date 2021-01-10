#include <stdio.h>
#include <string.h>

int main() {
	char s[] = "This Is The Operating System Course";
	char ch = 'e';
	int count = 0;
	
	for (int i = 0; i < strlen(s); i++) {
		if (ch == s[i])
			count++;
	}
	
	printf("The character %c appears %d times\n", ch, count);
	return 0;
}
