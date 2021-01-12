#include "scheduling.h"

void first_come_first_served(process_t *proc)
{
	printf("\n\nFirst come first served\n");
  
  	int tick = 0; //simulated CPU clock
  	
  	process_t* current_process = proc;	
  	while(1)
  	{
  		if(current_process == NULL)
  			break;
  			
  		//If the current process already arrived
		if(current_process->arrivaltime <= tick)
		{
			//If it is NOT running, run it
			if(!current_process->isRunning)
			{
				current_process->isRunning = 1;
				current_process->starttime = tick; 
			}
			else //the process already started
			{
	  			current_process->remainingtime--;
	  			
	  			//if the process is done
	  			if(current_process->remainingtime <= 0)
	  			{
	  				current_process->isDone = 1;
	  				current_process->isRunning = 0;
	  				current_process->endtime = tick;
	  				
	  				//Update to the next process
	  				current_process = current_process->next;
	  				//if the next process can start now
	  				if(current_process != NULL && current_process->arrivaltime <= tick)
	  				{
	  					current_process->isRunning = 1;
						current_process->starttime = tick; 
	  				}
	  				
	  			}
			}
			
		}
		
  		tick++;
  		
  	}
}

