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
    /* kaller på neste funskjon, har array som parameter for å koble arrayen til funskjonen main */
    main(array);
  }
}

/* funskjonen inneholder en 2d array som heter stats som inneholder resultatene fra funksjonene nedenfor */
function main(array) {
  /* hvis noen har to parametere så representerer den andre parameteren field som er indeksen til hver "nested" array */
  var stats = [[transactions(array, 1)],
  [locations(array, 2)],
  [totalspent(array, 3)],
  [average(array, 3)],
  [maximum(array, 3)],
  [minimum(array, 3)],
  [days(array)]];
  /* lager variabelen table som henter inn tabellraden med id data fra html */
  var table = document.getElementById("data");
  /* en loop som itererer gjennom stats arrayen */
  for(var i = 0; i < stats.length; i++) {
    /* skriver alle elementene i tabellfelt. skriver <td> (lager nytt felt), skriver alle elementene i stats arrayen (stats[i]) inn i feltet som lages og til slutt skrives </td> som lukker feltet */
    table.innerHTML += "<td>"+stats[i]+"</td>";
    /* let td = document.createElement("td");
    td.innerHTML = stats[i];
    table.appendChild(td); */
  }
}

/* funskjonen finner antall unike transaksjonstyper, parameteren array lenker den til den første arrayen med all dataen, og parameteren field er indeksen til hver "nested" array */
function transactions(array, field) {
  /* lager en tom array tottrans */
  let tottrans = [];
  /* loop som fyller arrayen tottrans med indeks 1 av alle "nested" arrays til hovedarrayen, indeks 1 pga 1 som parameter nr2(field) når funksjonen kalles på i main funskjonen*/
  for (var i = 0; i < array.length; i++) {
    tottrans[i] = array[i][field];
    /* Set og Array.from er es6 built in objects, Array.from(new Set(tottrans) gjør at kun unike (ikke duplikate) elementer blir sendt til tottrans arrayen, så tottrans = [Food, Entertainment, Transportation] */
    var uniquetrans = Array.from(new Set(tottrans))
   } 
  /* returnerer antall elementer i arrayen tottrans som er 3 */
  return uniquetrans.length;
}

/* funskjonen finner antall unike lokasjoner, parameteren array lenker den til den første arrayen med all dataen, og parameteren field er indeksen til hver "nested" array */
function locations(array, field) {
  /* lager en tom array totloc */
  let totloc = [];
  /* loop som fyller arrayen totloc med indeks 2 av alle "nested" arrays til hovedarrayen, indeks 2 pga 2 som parameter nr2(field) når funksjonen kalles på i main funskjonen */
  for (var i = 0; i < array.length; i++) {
    totloc[i] = array[i][field];
    /* Set og Array.from er es6 built in objects, Array.from(new Set(totloc) gjør at kun unike (ikke duplikate) elementer blir sendt til totloc arrayen, så tottrans = [Halden, Oslo, Fredrikstad, Stavanger] */
    var uniqueloc = Array.from(new Set(totloc))
  } 
  /* returnerer antall elementer i arrayen tottrans som er 4 */
  return uniqueloc.length;
}

/* funskjon som finner totalsummen av alle transaksjonene */
function totalspent(array, field) {
  /* lager en variabel med 0 i verdi */
  let tot = 0;
  /* lager en loop går gjennom alle elementene til indeks 3 til hver "nested" array, indeks 3 pga 3 som parameter nr2(field) når funksjonen kalles på i main funskjonen */
  for (var i = 0; i < array.length; i++) {
    tot += Number(array[i][field]);
  }  
  /* returnerer totalsummen med 2 desimaler */ 
  return Math.round(tot*100)/100;
}  

/* funskjon som finner gjennomsnittet av alle transaksjonene */
function average(array, field) {
  /* lager en variabel med 0 i verdi */
  let total = 0;
  /* lager en loop går gjennom og legger sammen alle elementene til indeks 3 til hver "nested" array, indeks 3 pga 3 som parameter nr2(field) når funksjonen kalles på i main funskjonen */
  for (var i = 0; i < array.length; i++) {
    total += Number(array[i][field]);
  }
   /* returnerer totalsummen/antall elementer, med 2 desimaler */ 
  return Math.round((total / array.length)*100)/100;
}  

/* funskjon som finner det høyeste engangsbeløpet */
function maximum(array, field) {
  /* lager en variabel med 0 i verdi */
  let max = 0;
  /* lager en loop går gjennom alle elementene og endrer verdien til variabelen max til det tallet den looper over, til indeks 3 til hver "nested" array, indeks 3 pga 3 som parameter nr2(field) når funksjonen kalles på i main funskjonen */
  for (var i = 0; i < array.length; i++) {
    /* sjekker om hvert tall er større enn verdien til max */
    if (array[i][field] > max) {
      max = Number(array[i][field]);
    }  
  }  
  return max;
}  

/* funskjon som finner det laveste engangsbeløpet */
function minimum(array, field) {
  /* lager en variabel med 1024 i verdi siden 1024 er det høyeste enkeltbeløpet */
  let min = 1024;
  /* lager en loop går gjennom alle elementene og endrer verdien til variabelen min til det tallet den looper over, til indeks 3 til hver "nested" array, indeks 3 pga 3 som parameter nr2(field) når funksjonen kalles på i main funskjonen */
  for (var i = 0; i < array.length; i++) {
    /* sjekker om hvert tall er mindre enn verdien til min */
    if (array[i][field] < min) {
      min = Number(array[i][field]);
    }  
  }  
  return min;
}  

/* funskjon som regner ut antall dager fra første til siste transaksjonsdato */
function days (array) {
  /* variabel date1 henter startdato, ved å hente det første elementet til den første indekse */
  let date1 = new Date(array[0][0]);
  /* variabel date2 henter sluttdato, ved å hente det siste elementet til den første indeksen */
  let date2 = new Date(array[array.length-1][0]);
  /* variabel datedifference trekker startdato fra sluttdato, svaret er i millisekunder pga .getTime() */
  let datedifference = date2.getTime() - date1.getTime();
  /* deler svaret i datedifference på 86400000, siden 24timer=86400000millisekunder for å konvertere svaret til dager fra millisekunder */
  let daydiff = datedifference / 86400000;
  return daydiff;
}