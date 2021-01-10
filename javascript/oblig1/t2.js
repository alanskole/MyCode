window.onload = numbers;

function numbers () {
  var a = 5;
  var b = 6;

  document.getElementById("p1").innerHTML = 'Numbers:'+ (a + b);
  document.getElementById("p2").innerHTML = 'Numbers:'+ (b - a);
  document.getElementById("p3").innerHTML = 'Numbers:'+ (a * b);
  document.getElementById("p4").innerHTML =  'Numbers:'+ (b / a);
  console.log(a + b +"\n", b - a +"\n", a * b +"\n", b / a);
}
