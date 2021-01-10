#!/bin/bash
sudo surfshark-vpn status
printf "\n"
while read -p " 1: Connect to closest server.`echo $'\n '`2: Connect to VPN.`echo $'\n '`3: Activate killswitch.`echo $'\n '`4: Disconnect VPN connection.`echo $'\n '`5: Deactivate killswitch.`echo $'\n '`6: Multihop.`echo $'\n '`7: Status.`echo $'\n '`8: Version.`echo $'\n '`9: Stop the program.`echo $'\n\n '`Choose an option: " && [[ $REPLY != 9 ]]; do
printf " "
case $REPLY in  
1) printf "\n" && echo -ne '\n' | sudo surfshark-vpn attack ;;
2) printf "\n" && sudo surfshark-vpn ;;
3) printf "\n" && sudo ufw reset -y
sudo ufw default deny incoming
sudo ufw default deny outgoing
sudo ufw allow out on tun0 from any to any
sudo ufw enable ;; 
4) printf "\n" && sudo surfshark-vpn down ;; 
5) printf "\n" && sudo ufw reset -y
sudo ufw default deny incoming
sudo ufw default allow outgoing
sudo ufw enable ;; 
6) printf "\n" && sudo surfshark-vpn multi ;;
7) printf "\n" && sudo surfshark-vpn status ;; 
8) printf "\n" && sudo surfshark-vpn version ;;
esac
printf "\n"
done


