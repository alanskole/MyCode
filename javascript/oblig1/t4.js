window.onload = function () {
  var button1 = document.getElementById('pluss');
  button1.onclick = pluss;
  var button2 = document.getElementById('minus');
  button2.onclick = minus;
  var button3 = document.getElementById('gange');
  button3.onclick = gange;
  var button4 = document.getElementById('dele');
  button4.onclick = dele;
}

function pluss() {
  document.getElementById("rg").innerHTML = n1.value + "\x2b" + n2.value;
  var res = document.getElementById("rs").innerHTML = parseFloat(n1.value) + parseFloat(n2.value);
  if (res % 2 == 0) {
    document.getElementById("pt").innerHTML = "partall";
  }
  else {
    document.getElementById("pt").innerHTML = "oddetall";
  }
}

function minus() {
  document.getElementById("rg").innerHTML = n1.value + '\u2212' + n2.value;
  var res = document.getElementById("rs").innerHTML = parseFloat(n1.value) - parseFloat(n2.value);
  if (res % 2 == 0) {
    document.getElementById("pt").innerHTML = "partall";
  }
  else {
    document.getElementById("pt").innerHTML = "oddetall";
  }
}

function gange() {
  document.getElementById("rg").innerHTML = n1.value + "\u2715" + n2.value;
  var res = document.getElementById("rs").innerHTML = parseFloat(n1.value) * parseFloat(n2.value);
  if (res % 2 == 0) {
    document.getElementById("pt").innerHTML = "partall";
  }
  else {
    document.getElementById("pt").innerHTML = "oddetall";
  }
}

function dele() {
  document.getElementById("rg").innerHTML = n1.value + '\u2215' + n2.value;
  var res = document.getElementById("rs").innerHTML = parseFloat(n1.value) / parseFloat(n2.value);
  if (res % 2 == 0) {
    document.getElementById("pt").innerHTML = "partall";
  }
  else {
    document.getElementById("pt").innerHTML = "oddetall";
  }
}
