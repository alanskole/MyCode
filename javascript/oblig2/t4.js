function xor(a,b){ //hvis resultatet er false, returneres enten false, 0, " ", undefined, NaN eller null
  return (a || b) && !(a && b);
}
console.log(xor(1,0), xor(0,0), xor("a","b"), xor(3,2), xor(0,""), xor("0",""), xor(0, undefined), xor(0, NaN), xor(0, null));

function xor1(a,b){ //ved å legge til !! foran får jeg oppgitt verdiene kun i true eller false
  return !!(a || b) && !(a && b);
}
console.log(xor1(1,0), xor1(0,0), xor1("a","b"), xor1(3,2), xor1(0,""), xor1("0",""), xor1(0, undefined), xor1(0, NaN), xor1(0, null));

function xor2(a,b) { //returner kun verdiene i 1 eller 0 i stedet for true eller false
  return (!a ^ !b);
}
console.log(xor2(1,0), xor2(0,0), xor2("a","b"), xor2(3,2), xor2(0,""), xor2("0",""), xor2(0, undefined), xor2(0, NaN), xor2(0, null));

function xor3(a,b) { //ved å legge til !! foran får jeg oppgitt verdiene kun i true eller false
  return !!(!a ^ !b);
}
console.log(xor3(1,0), xor3(0,0), xor3("a","b"), xor3(3,2), xor3(0,""), xor3("0",""), xor3(0, undefined), xor3(0, NaN), xor3(0, null));

function xor4(a,b) {
  return (!a != !b);
}
console.log(xor4(1,0), xor4(0,0), xor4("a","b"), xor4(3,2), xor4(0,""), xor4("0",""), xor4(0, undefined), xor4(0, NaN), xor4(0, null));

function xor5(a,b) {
  return !(!(a && !(a && b)) && !(b && !(a && b)));
}
console.log(xor5(1,0), xor5(0,0), xor5("a","b"), xor5(3,2), xor5(0,""), xor5("0",""), xor5(0, undefined), xor5(0, NaN), xor5(0, null));

function xor6(a,b) { //denne bestemmer true eller false basert på verdien av parameterne/variablene i stedet for den boolske verdien som i de tilfellene ovenfor. for eksempel blir resultatet av 2 og 3 blir true selv om den booleanske verdien til begge egentlig er true
  return (a!=b);
}
console.log(xor6(1,0), xor6(0,0), xor6("a","b"), xor6(3,2), xor6(0,""), xor6("0",""), xor6(0, undefined), xor6(0, NaN), xor6(0, null));
