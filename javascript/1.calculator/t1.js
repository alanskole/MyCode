window.onload = startup;

function startup() {
  var btnhi = document.getElementById("btnhi");
  btnhi.addEventListener("click", msghi);
  var btnBye = document.getElementById("btnBye");
  btnBye.addEventListener("click", msgBye);
}

function msghi() {
  alert('Hi you!');
}

function msgBye() {
  alert('Get out of my sight plebeian!');
}
