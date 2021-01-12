window.onload = function () {
  document.getElementById('add'); //henter info om knappen "add"
  add.onclick = addtext; //hvis knapped "add" blir trykket på kjøres funksjonen addtext
  document.getElementById('removelast') //henter info om knappen "removelast"
  removelast.onclick = deletelast; //hvis knapped "removelast" blir trykket på kjøres funksjonen deletelast
  document.getElementById('btnremove') //henter info om knappen "btnremove"
  btnremove.onclick = removenumber; //hvis knapped "btnremove" blir trykket på kjøres funksjonen removenumber
}

var list = []; //lager en tom array
function addtext() {
  textvalue = document.getElementById('text').value; //henter verdien til feltet "text"
  list.push(textvalue); //legger til teksten som brukeren skriver i "text"feltet til arrayen "list"
  p1.innerHTML = "Liste: " + "<br>"  + list.join("<br>"); //skriver "Liste: " etterfulgt verdiene i arrayen i paragrafen p1, "<br>"=newline
  p2.innerHTML = " Første element: " + list[0] + "<br>" + " Siste element: " + list[list.length -1] + "<br>" + " Antall elementer: " + list.length; //skriver hva første og siste elementet i arrayen er, og antall elementer i paragrafen p2
  document.getElementById('text').value = ""; //nullstiller feltet "text"
}

function deletelast() {
  list.pop(); //sletter siste element
  p1.innerHTML = "Liste: " + "<br>" + list.join("<br>"); //skriver "Liste: " etterfulgt verdiene i arrayen i paragrafen p1, "<br>"=newline
  p2.innerHTML = " Første element: " + list[0] + "<br>" + " Siste element: " + list[list.length -1] + "<br>" + " Antall elementer: " + list.length; //skriver hva første og siste elementet i arrayen er, og antall elementer i paragrafen p2
}

function removenumber() {
 if (remove.value > list.length) { //hvis nummeret brukeren taster inn i feltet "remove" er større enn antall array elementer kjøres linjen/linjene fram til neste else if()
  alert("Ikke tilgjengelig!") //hvis det som står i if() stemmer kommer popup meldingen "Ikke tilgjengelig"
  document.getElementById('remove').value = ""; //nullstiller feltet "remove"
} else if (remove.value > 0) { //hvis verdien av nummeret brukeren taster inn i feltet "remove" er større enn 0 kjøres linje/linjene frem til else if()
    list.splice(remove.value-1, 1); //fjerner et element fra listen som brukeren velger. trekker fra 1 fra tallet i feltet "remove" siden arrayer teller fra og med 0, dette gjør at hvis brukeren vil fjerne element nummer 1 i arrayen så betyr det egentlig element nummer 0
    p1.innerHTML = "Liste: " + "<br>" + list.join("<br>"); //skriver "Liste: " etterfulgt verdiene i arrayen i paragrafen p1, "<br>"=newline
    p2.innerHTML = " Første element: " + list[0] + "<br>" + " Siste element: " + list[list.length -1] + "<br>" + " Antall elementer: " + list.length; //skriver hva første og siste elementet i arrayen er, og antall elementer i paragrafen p2
    document.getElementById('remove').value = ""; //nullstiller feltet "remove"
} else if (remove.value <= 0) { //hvis verdien av feltet "remove" er lik eller mindre enn 0 så kjøres neste linje
    alert("Nummeret må være større enn 0"); //hvis verdien av feltet "remove" er mindre enn 1 kommer popup meldingen "Nummeret må være større enn 0"
    document.getElementById('remove').value = ""; //nullstiller feltet "remove"
  }
}
