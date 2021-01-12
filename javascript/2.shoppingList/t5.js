window.onload = function() { //denne oppgaven var veldig vanskelig så jeg måtte bruke https://www.youtube.com/watch?v=ItOmYp9AKOA jeg har gjort om på mye av koden for å bevise at jeg har forstått koden og ikke plagiert har jeg skrevet hvordan koden kjøres nedenfor

  var rows = 8; //variabel på 8 rader siden treet skal være 8 rader
  for (var i = 0; i < rows; i++) { //lager en loop som starter fra null og går fram til og med 7, hvis den er sann(variabel i er mellom 0-7) så kjøres neste linje som er enda en loop
    for (var j = 0; j < rows - i; j++) { //lager en nested loop som som kjøres hvis forrige loop er sann. her trekker den fra variabel i som er verdien på forrige linje(loop) slik at vi får trekantform. variabel i er 0 i første runde og øker med en for hver runde frem til og med den er 7
      container.innerHTML += "&nbsp;" //skriver inn antall mellomrom i radene basert på resultatet av loop'en ovenfor slik at trekanten ikke blir rettvinklet. for hver gang/rad så blir det et mellomrom mindre siden verdien til variabelen i øker med en for hver runde
    }
    for (var k = 0; k <= i; k++) { //enda en nested loop som sjekker om variabel k er mindre enn eller lik var i
      container.innerHTML += "* "; //skriver antall stjerner basert på resultatet av loop'en ovenfor
    }
    container.innerHTML += "<br>"; //lager en ny linje når loop'ene ovenfor ikke lenger stemmer og starter fra første loop igjen frem til den har nådd 8 rader
  }
}
/* forklaring av koden steg for steg:
1.runde
0 < 8

0 < 8 legger til 8 mellomrom
1 < 8
2 < 8
3 < 8
4 < 8
5 < 8
6 < 8
7 < 8

0 <= 0 legger til 1 stjerne

ny linje

2.runde
1 < 8

1 < 8 legger til 7 mellomrom
2 < 8
3 < 8
4 < 8
5 < 8
6 < 8
7 < 8

0 <= 1 legger til 2 stjerner
1 <= 1

ny linje

3.runde
2 < 8

2 < 8 legger til 6 mellomrom
3 < 8
4 < 8
5 < 8
6 < 8
7 < 8

0 <= 2 legger til 3 stjerner
1 <= 2
2 <= 2

ny linje

4.runde
3 < 8

3 < 8 legger til 5 mellomrom
4 < 8
5 < 8
6 < 8
7 < 8

0 <= 3 legger til 4 stjerner
1 <= 3
2 <= 3
3 <= 3

ny linje

5.runde
4 < 8

4 < 8 legger til 4 mellomrom
5 < 8
6 < 8
7 < 8

0 <= 4 legger til 5 stjerner
1 <= 4
2 <= 4
3 <= 4
4 <= 4

ny linje

6.runde
5 < 8

5 < 8 legger til 3 mellomrom
6 < 8
7 < 8

0 <= 5 legger til 6 stjerner
1 <= 5
2 <= 5
3 <= 5
4 <= 5
5 <= 5

ny linje

7.runde
6 < 8

6 < 8 legger til 2 mellomrom
7 < 8

0 <= 6 legger til 7 stjerner
1 <= 6
2 <= 6
3 <= 6
4 <= 6
5 <= 6
6 <= 6

ny linje

8.runde
7 < 8

7 < 8 legger til 1 mellomrom

0 <= 7 legger til 8 stjerner
1 <= 7
2 <= 7
3 <= 7
4 <= 7
5 <= 7
6 <= 7
7 <= 7

ny linje

8 !< 8 loop'en slutter siden påstanden ikke er sann
*/
