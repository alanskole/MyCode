#include <time.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include "message.h"

static const char* message[] = {
  "Hello class",
  "Goodbye class",
  "This is ITF22519 course.",
  "This is a lab section."
};

void print_message() {
  int index;
  srand(time(NULL));
  index = rand()%4;
  printf("%s\n", message[index]);
}
