#include "scheduling.h"
#include <limits.h>

int amountOfProcessessrt = 0;
int ticksrt = 0; //simulated CPU clock

void findAmountOfProcessessrt(process_t *proc)
{
	while (proc != NULL)
	{
		proc = proc->next;
		amountOfProcessessrt++;
	}
}

process_t *findNextProcess(process_t *head)
{
	int shortest = INT_MAX;

	process_t *hd = head;

	process_t *found;

	while (hd != NULL)
	{
		if (hd->arrivaltime <= ticksrt && hd->isDone == 0 && hd->remainingtime < shortest)
		{
			found = hd;
			shortest = found->remainingtime;
		}
		hd = hd->next;
	}
	return found;
}

void shortest_remaining_time(process_t *proc)
{
	printf("\n\nShorted Rmaining Time\n");
	process_t *current_process = proc;

	int amountOfCompletedProcesses = 0;

	findAmountOfProcessessrt(current_process);

	while (amountOfCompletedProcesses != amountOfProcessessrt)
	{
		current_process = findNextProcess(proc);

		//If the current process already arrived
		if (current_process != NULL && current_process->arrivaltime <= ticksrt && current_process->isDone == 0)
		{
			//If it is NOT running, run it
			if (current_process->isRunning == 0)
			{
				current_process->isRunning = 1;

				current_process->starttime = ticksrt;
			}
			if (current_process->isRunning == 1)
			{
				current_process->remainingtime--;
			}

			//if the process is done
			if (current_process->remainingtime <= 0)
			{
				current_process->isDone = 1;
				current_process->isRunning = 0;
				current_process->endtime = ticksrt+1;
				amountOfCompletedProcesses++;
			}
		}
		ticksrt++;
	}
}