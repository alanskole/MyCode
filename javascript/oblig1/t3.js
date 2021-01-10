window.onload = function () {
  var met = prompt("Velg regnemetoden: +  -  x  /")
  if (met == '+') {
    var n1 = prompt("Tast inn det første tallet");
    var n2 = prompt("Tast inn det andre tallet");
    document.getElementById('lol').innerHTML = (Number(n1) + Number(n2));
  }

  else if (met == '-') {
    var n1 = prompt("Tast inn det første tallet");
    var n2 = prompt("Tast inn det andre tallet");
    document.getElementById('lol').innerHTML = (Number(n1) - Number(n2));
  }

  else if (met == 'x') {
    var n1 = prompt("Tast inn det første tallet");
    var n2 = prompt("Tast inn det andre tallet");
    document.getElementById('lol').innerHTML = (Number(n1) * Number(n2));

  }

  else if (met == '/') {
    var n1 = prompt("Tast inn det første tallet");
    var n2 = prompt("Tast inn det andre tallet");
    document.getElementById('lol').innerHTML = (Number(n1) / Number(n2));
  }
}
