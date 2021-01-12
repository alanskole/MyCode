window.onload = function () {
  document.getElementById('login'); //henter info om knappen "login"
  login.onclick = logincheck; //hvis knapped "login" blir trykket på kjøres funksjonen logincheck
}

function logincheck() {
  var criteriapswd = /^(?=.*\d)(?=.*[a-å])(?=.*[A-Å])(?=.*[^a-åA-Å0-9])(?!.*\s).{8,32}$/; //Passordet må inneholde mellom 8-32 tegn, minst en stor bokstav, en liten bokstav, et nummer og et spesialtegn
  var criteriausr = /^.{6,32}$/; //brukernavnet må inneholde mellom 6 og 32 tegn, alle tegn er godkjent
  var usr = document.getElementById("user").value; //lager en variabel "usr" av det brukeren skriver inn i feltet "user"
  var pswd = document.getElementById("pass").value; //lager en variabel "pswd" av det brukeren skriver inn i feltet "pass"
  if (usr == pswd) { //hvis brukernavn og passord er like
    alert("Brukernavnet og passordet må være ulike!"); //popup melding med teksten "Brukernavnet og passordet må være ulike!"
  } else if (pswd.length < 8) { //hvis passordet er mindre enn 8 tegn kommer popup meldingen "Passordet må inneholde minst 8 tegn og inneholde minst en stor bokstav, en liten bokstav, et nummer og et spesialtegn!"
    alert("Passordet må inneholde minst 8 tegn og inneholde minst \nen stor bokstav, en liten bokstav, et nummer og et spesialtegn!");
  } else if (pswd.length > 32) { //hvis passordet er større enn 32 tegn kommer popup meldingen "Passordet kan ikke inneholde mer enn 32 tegn og inneholde minst en stor bokstav, en liten bokstav, et nummer og et spesialtegn!"
    alert("Passordet kan ikke inneholde mer enn 32 tegn og inneholde minst \nen stor bokstav, en liten bokstav, et nummer og et spesialtegn!");
  } else if (!pswd.match(criteriapswd)) { //hvis passordet er mellom 8-32 men de andre kriteriene for passordet ikke møtes kommer popup meldingen "Passordet må inneholde minst en stor bokstav, en liten bokstav, et nummer og et spesialtegn!"
    alert("Passordet må inneholde minst en stor bokstav, \nen liten bokstav, et nummer og et spesialtegn!");
  } else if (!usr.match(criteriausr)) { //hvis kriteriene for brukeren ikke møtes kommer popup meldingen "Brukernavnet må inneholde mellom 6 og 32 tegn!"
    alert("Brukernavnet må inneholde mellom 6 og 32 tegn!");
  } else { //hvis alle kriteriene møtes kommer popup meldingen "Velkommen"
    alert("Velkommen")
  }
}
