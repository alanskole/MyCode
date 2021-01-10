#!/bin/sh  
while :  
do  
  if [ -z "`acpi -a | grep on-line`" ]; then
    batlvl=`acpi -b | awk '{print $4}' | grep -Eo "[0-9]+" | paste -sd+ | bc`
  if [ $batlvl -le 15 ]; then
    if ! on_ac_power; then
      (sleep 1 && wmctrl -F -a "Low battery" -b add,above) &
(zenity --info --title="Low battery" --icon-name='battery-low' --text="Please connect the charger, the battery level is $batlvl%                 
                                                       ")
    fi
  fi
  fi
  sleep 300
done
