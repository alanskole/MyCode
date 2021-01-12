#include "scheduling.h"

int amountOfProcessesprio = 0;
int tickprio = 0; //simulated CPU clock

void findAmountOfProcessesprio(process_t *proc)
{
	while (proc != NULL)
	{
		proc = proc->next;
		amountOfProcessesprio++;
	}
}

process_t *findCorrectProcess(process_t *head)
{
	int highestPri = -1;

	process_t *hd = head;

	process_t *found;

	while (hd != NULL)
	{
		if (hd->arrivaltime <= tickprio && hd->isDone == 0 && hd->priority > highestPri)
		{
			found = hd;
			highestPri = found->priority;
		}
		hd = hd->next;
	}
	return found;
}

void priority(process_t *proc)
{

	printf("\n\nPriority (Premptive)\n");

	process_t *current_process = proc;

	int amountOfCompletedProcesses = 0;

	findAmountOfProcessesprio(current_process);

	while (amountOfCompletedProcesses != amountOfProcessesprio)
	{
		current_process = findCorrectProcess(proc);

		//If the current process already arrived
		if (current_process != NULL && current_process->arrivaltime <= tickprio && current_process->isDone == 0)
		{
			//If it is NOT running, run it
			if (current_process->isRunning == 0)
			{
				current_process->isRunning = 1;

				current_process->starttime = tickprio;
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
				current_process->endtime = tickprio+1;
				amountOfCompletedProcesses++;
			}
		}
		tickprio++;
	}
}