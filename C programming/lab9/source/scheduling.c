#include "scheduling.h"

//Read the processes from a file
process_t* read_processes_from_file(char filename[])
{
	FILE* pFile = fopen (filename,"r");
	if (pFile==NULL)
	{
		perror ("Error opening file");
		return NULL;
	} 
	
	fscanf(pFile, "%*[^\n]");  // Read and discard first line
	
	int id, arrival, runtime, priority;
	process_t *head_process = NULL, *prev_process = NULL, *new_process = NULL;
	
	while(fscanf(pFile, "%d%d%d%d", &id, &arrival, &runtime, &priority) != EOF)
	{	
		new_process = malloc(sizeof(process_t));
		new_process->id = id;
		new_process->arrivaltime = arrival;
		new_process->runtime = runtime;
		new_process->priority = priority;
		new_process->next = NULL;
		new_process->remainingtime = runtime;
		new_process->isRunning = 0;
		new_process->isDone = 0;
		
		/*printf("%d\t%d\t%d\t%d\n", new_process->id, new_process->arrivaltime, 
								   new_process->runtime, new_process->priority);*/
		
		if(prev_process == NULL)
			prev_process = new_process;
		else
		{
			prev_process->next = new_process;
			prev_process = new_process;
		}
			
		if(head_process == NULL)
			head_process = new_process;		
	}
	
	return head_process;
}

//print the scheduled processes to file and terminal
void print_processes(process_t* processes, char* filename)
{
	FILE* pFile = fopen (filename,"w");
	if (pFile==NULL)
	{
		perror ("Error creating file");
		return;
	} 
	
	process_t* current_process = processes;
	printf("Process arrival runtime priority starttime endtime\n");
	fprintf(pFile, "Process arrival runtime priority starttime endtime\n");
	
	int process_count = 0;
	float avg_turnaround = 0;
	float avg_weighted_turnaround = 0; 
	while(current_process != NULL)
	{
		printf("%d\t%d\t%d\t%d\t%d\t%d\n", current_process->id, current_process->arrivaltime, 
										 current_process->runtime, current_process->priority, 
										 current_process->starttime, current_process->endtime);
										 
		fprintf(pFile, "%d\t%d\t%d\t%d\t%d\t%d\n", current_process->id, current_process->arrivaltime, 
										 current_process->runtime, current_process->priority, 
										 current_process->starttime, current_process->endtime);
										
		process_count++;
		int turnarround =  current_process->endtime - current_process->arrivaltime;
		float weighted_turnaround = turnarround * 1.0/current_process->runtime;
		avg_turnaround += turnarround;
		avg_weighted_turnaround += weighted_turnaround;
										 
		current_process = current_process->next;
	}
	
	avg_turnaround = avg_turnaround/process_count;
	avg_weighted_turnaround = avg_weighted_turnaround/process_count;
	
	printf("Average turnarround = %f\n", avg_turnaround);
	printf("Average weighted turnarround = %f\n", avg_weighted_turnaround);
	
}

//sort the processes based on their arrival time
//Ref: Bubble sort https://en.wikipedia.org/wiki/Bubble_sort

process_t* sort_process(process_t *proc)
{
	int sorted = 0;
	process_t *head = proc;
	while(!sorted)
	{
		sorted = 1;
		process_t *prev, *cur, *temp;
		cur = head;
		prev = cur;
		while(cur!= NULL && cur->next != NULL)
		{
			if(cur->arrivaltime > cur->next->arrivaltime)
			{
				sorted = 0;

				if(prev != cur)
				{
					temp = cur->next;
					prev->next = temp;
					cur->next = temp->next;
					temp->next = cur;
				}
				else
				{
					temp = cur->next;
					cur->next = temp->next;
					temp->next = cur;
					head = temp;
				} 

			}
			prev = cur;
			cur = cur->next;
		}
		 
	}
	return head;
}

void delete_processes(process_t *proc)
{
	while (proc != NULL)
	{
		free(proc);
		proc = proc->next;
	}
}

int main(int argc, char** argv)
{
	if(argc < 2)
	{
		printf("usage: %s <input_file> \n", argv[0]);
		exit(0);
	}
	
	//Running FCFS
	
	process_t* processes = read_processes_from_file(argv[1]);
	processes = sort_process(processes);
  	first_come_first_served(processes);
	print_processes(processes, "fcfs.out");
	delete_processes(processes);
	
 
 	//Run SRT
 	
	processes = read_processes_from_file(argv[1]);
 	processes = sort_process(processes);
  	shortest_remaining_time(processes);
	print_processes(processes, "srt.out");
	delete_processes(processes);
	
	//Run RR
	
  	processes = read_processes_from_file(argv[1]);
 	processes = sort_process(processes);
  	round_robin(processes);
	print_processes(processes, "rr.out");
	delete_processes(processes);

	//Run PRIO
	
	processes = read_processes_from_file(argv[1]);
 	processes = sort_process(processes);
  	priority(processes);
  	print_processes(processes, "prio.out");
	delete_processes(processes);

  	return 0;
}


