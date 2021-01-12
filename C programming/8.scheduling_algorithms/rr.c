#include "scheduling.h"

int amountOfProcesses = 0;
int tickrr = 0; //simulated CPU clock

void findAmountOfProcesses(process_t *proc)
{
	while (proc != NULL)
	{
		proc = proc->next;
		amountOfProcesses++;
	}
}

process_t *findValid(process_t *head, process_t *current)
{
	process_t *hd = head;
	process_t *cur = current;

	while (cur != NULL)
	{
		if (cur->arrivaltime <= tickrr && cur->isDone == 0)
		{
			return cur;
		}
		cur = cur->next;
	}

	while (hd != NULL)
	{
		if (hd->arrivaltime <= tickrr && hd->isDone == 0)
		{
			return hd;
		}

		hd = hd->next;
	}
	return NULL;
}

void round_robin(process_t *proc)
{

	printf("\n\nRound Robin\n");
	
	process_t *current_process = proc;

	int amountOfCompletedProcesses = 0;

	findAmountOfProcesses(current_process);

	int quantum = 0;

	while (amountOfCompletedProcesses != amountOfProcesses)
	{
		if (quantum == 1)
		{

			if (current_process->next != NULL && current_process->next->arrivaltime <= tickrr)
			{
				current_process = current_process->next;
			}
			else if (current_process->next == NULL || current_process->next->arrivaltime > tickrr)
			{
				current_process = proc;
			}
			quantum = 0;
		}

		if (current_process == NULL || current_process->isDone == 1)
		{
			process_t *tmp = findValid(proc, current_process);
			if (tmp != NULL)
			{
				current_process = tmp;
			}
		}

		//If the current process already arrived
		if (current_process != NULL && current_process->arrivaltime <= tickrr && current_process->isDone == 0)
		{
			//If it is NOT running, run it
			if (current_process->isRunning == 0)
			{
				current_process->isRunning = 1;

				current_process->starttime = tickrr;
			}

			if (current_process->isRunning == 1)
			{
				current_process->remainingtime--;
				if (amountOfCompletedProcesses + 1 != amountOfProcesses)
				{
					quantum = 1;
				}	
			}

			//if the process is done
			if (current_process->remainingtime <= 0)
			{
				current_process->isDone = 1;
				current_process->isRunning = 0;
				current_process->endtime = tickrr+1;
				amountOfCompletedProcesses++;
			}
		}
		tickrr++;
	}
}
