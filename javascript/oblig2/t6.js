function prime(n){ //lager en funksjon med parameteren n
     for(var x = 2; x < n; x++) { // lager en for loop der var x = 2(starter fra 2 siden det er det minste primtallet), og kjører frem til den når tallet før funksjon parameteren n, starter fra 2 og øker med 1 for hver runde
      if(n % x === 0) { //hvis n modulo x er lik 0 så er det ikke et primtall, da returneres false
        return false;
      }
    } return n > 1; //hvis tallet ikke er 1, så returner true, dette er fordi 1 ikke er et primtall men fortsatt hadde vært true hvis ikke for denne linjen
  }

console.log(prime(-13));
console.log(prime(1));
console.log(prime(2));
console.log(prime("sixtyfour"));
console.log(prime(2473));
console.log(prime(2475));
console.log(prime(3301));

/*
hvis vi tar primtallet 5 som eksempel, prime(5);
1.runde
for(var x = 2; 2 < 5; 2++)

5 modulo 2 = 1

2.runde
for(var x = 3; 3 < 5; 3++)

5 modulo 3 = 2

3.runde
for(var x = 4; 4 < 5; 4++)

5 modulo 4 = 1

4.runde
for var x = 5; 5 <5; 5++) dette stemmer ikke siden 5 ikke er mindre enn 5 så loopen er stoppet

return false; siden vi aldri fikk 0 som svar på noen av modulo regnestykkene vi kjørte hopper den over return false siden dette ikke stemmer

return value > 1; dette stemmer siden 5 er større enn 1, og siden 5 er et primtall

hvis vi tar oddetallet 9 som eksemepel, prime(9);
1.runde
for(var x = 2; 2 < 9; 2++)

9 modulo 2 = 1

2.runde
for(var x = 3; 3 < 9; 3++)

9 modulo 3 = 0 siden svaret er 0 returneres false siden dette ikke er et primtall fordi 9/3=3

if(n % x === 0) { denne gjør at det blir false, siden n(9) % x(3 siden dette er andre runde av loopen og 2 har økt med 1=3)===0
  return false;
}
*/
