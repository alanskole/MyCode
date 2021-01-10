/**
 * @brief     This file contains a broken program to be debugged with GDB.
 * @detail    This program allocates a buffer, reads upto 1024 characters from
 *			  STDIN and then prints the buffer to STDOUT.
 */

/// Include standard i/o and standard library
#include <stdio.h>
#include <stdlib.h>


int main(int argc, char const *argv[])
{
	char *buffer; // pointer to the buffer

	buffer = malloc(1024); // allocate a new buffer
	printf("Please enter your name:\n");
	fgets(buffer, 1024, stdin); // get upto 1024 characters from STDIN
	printf("You fixed the bug! Congratulations %s\n", buffer); // print the buffer to STDOUT

	return 0;
}
