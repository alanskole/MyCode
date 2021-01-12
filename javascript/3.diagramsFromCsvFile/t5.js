/* lager en annonym startfunksjon som aktiverer funskjonen getfile når knappen med id uploadfile klikkes */
window.onload = function () {
  let fileuploader = document.getElementById('uploadfile');
  fileuploader.addEventListener('change', getfile);
}

/* funksjon som laster opp og leser filen man velger etter man klikker på "Browse" knappen*/
function getfile() {
  let file = event.target.files[0];
  let readfile = new FileReader();

  readfile.readAsText(file);
  readfile.onload = function () {
    /* lager en array av innholdet i csv filen som deles opp ved hver "\n" */
    let array = readfile.result.split('\n');
    /* lager en loop som gjør at innholdet i csv filen som deles opp ved hver "," blir en 2d array */
    for (var i = 0; i < array.length; i++) {
      array[i] = array[i].split(",");
    }
    /* henter inn tabellen med id table fra html filen */
    let table = document.getElementById("table");
    /* en loop som lager antall rader (16) */
    for(var i = 0; i < array.length; i++) {
      /* lager variabelen row, insertRow() legger til ny rad (<tr>), knyttet til variabelen table og lager 16 rader */
      let row = table.insertRow();
      /* en "nested" loop som henter inn alle elementene til hver eneste rad */
      for(var j = 0; j < array[i].length; j++) {
        /* insertCell() legger til felter (<td>) i alle radene (<tr>), knyttet til variabelen row, feltene inneholder dataen fra array[j] som er alle elementene i arrayen */
        let cell = row.insertCell();
        /* skriver alle radene (<tr>) og feltene (<td>) med elementene (array[j]) */
        cell.innerHTML = array[i][j];
      }
    }
  }
}
