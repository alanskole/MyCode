#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <limits.h>

typedef struct processes
{
  /* Values initialized for each process */
  int id;    /* Id of the process */
  int arrivaltime;  /* Time process arrives and wishes to start */
  int runtime;      /* Time process requires to complete job */
  int priority;     /* Priority of the process */

 
  int starttime; // the time this task started
  int endtime; // the time this task finished
  int remainingtime; // the estimatted remaining time this task will run for
  
  int isDone;
  int isRunning;

  struct processes *next;
} process_t;


void first_come_first_served(process_t *proc);
void shortest_remaining_time(process_t *proc);
void round_robin(process_t *proc);
void priority(process_t *proc);

process_t* read_processes_from_file(char filename[]);
void print_processes(process_t* processes, char* filename);
process_t* sort_process(process_t *proc);
void delete_processes(process_t *proc);
