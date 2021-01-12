function calc(radius) {
  //lager variabel "circlearea" som regner ut r2 * pi
  var circlearea = Math.pow(Number(radius), 2) * Math.PI;
  //lager en variabel "res" som runder av til to desimaler
  var res = circlearea.toFixed(2); 
  //hvis radius er et tall større enn 0, return variabel "res"
  if (Number(radius) > 0) { 
    return res;
    //hvis radius er et tall mindre enn 0, return undefined
  } else if (Number(radius) < 0){ 
    return undefined;
    //hvis radius ikke er et tall så returner undefined
  } else if (Number.isInteger(radius) != true) { 
    return undefined;
  }
}
console.log(calc(1));
console.log(calc(8));
console.log(calc("fifteen"));
console.log(calc(-4));
