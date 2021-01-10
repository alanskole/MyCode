window.onload = function () {
  let fileuploader = document.getElementById('uploadfile');
  /* lytter på uploadfile knappen og aktiverer funskjonen getfile når knappen klikkes */
  fileuploader.addEventListener('change', getfile);
  let input = document.getElementById("filter");
  /* lytter på drop down med lokasjoner, aktiverer funskjonen cleartable når ny lokasjon velges og tømmer tabelldata fra forrige */
  input.addEventListener('change', cleartable);
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
    for (let i = 0; i < array.length; i++) {
      array[i] = array[i].split(",");
    }
    /* starter starter funksjonen filterloc og sender med array  */
    filterloc(array);
    canvas(array);
  }
}

function canvas(array) {
  let arfr = [];
  let arha = [];
  let aros = [];
  let arst = [];
  /* lager en loop som går gjennom elementene i array  */
  for (let i = 0; i < array.length; i++) {
    /* hvis lokasjonen er fredrikstad bruk .push() for å overføre alle elementene under index 3 inn i den tomme arrayen arfr, Number() gjør at elementene blir registrert som numre */
    if (array[i][2] == "Fredrikstad") {
      arfr.push(Number(array[i][3]));
      /* lager en funksjon som legger sammen alle elementene til arrayen arfr ved å bruke built in object .reduce() */
      sumfr = arfr.reduce((a, b) => {
        return a + b
      });
    }
    if (array[i][2] == "Halden") {
      arha.push(Number(array[i][3]))
      sumha = arha.reduce((a, b) => {
        return a + b
      });
    }
    if (array[i][2] == "Oslo") {
      aros.push(Number(array[i][3]))
      sumos = aros.reduce((a, b) => {
        return a + b
      });
    }
    if (array[i][2] == "Stavanger") {
      arst.push(Number(array[i][3]))
      sumst = arst.reduce((a, b) => {
        return a + b
      });
    }
  }
  let canvas = document.getElementById('canvas');
  let ctx = canvas.getContext('2d');
  /* totalt forbruk i alle lokasjonene */
  let totalamount = sumfr+sumha+sumos+sumst;
  /* lager en array med alle bynavnene, totalbeløpet(bruker Math.round() for å unngå desimaler) for hver lokasjon og deler hvert totalbeløp på totalamount og ganger med canvas.height så den holder seg innenfor høyden til canvas */
  let data = [['Fredrikstad', Math.round(sumfr), sumfr / totalamount * canvas.height], ['Halden', Math.round(sumha), sumha / totalamount * canvas.height], ['Oslo', Math.round(sumos), sumos /totalamount * canvas.height], ['Stavanger', Math.round(sumst), sumst / totalamount * canvas.height]];
  /* bredden på hver søyle */
  let barwidth = 50;
  /* mellomrom mellom søylene og bunnen av canvas */ 
  let ygap = 20;
  /* mellomrom mellom søylene, dette er inkludert søylebredden så mellomrommet er 50px */
  let bargap = 100;
  /* mellomrom fra første søyle til venstre canvaslinje */
  let leftgap = 25;
  /* gjør at det blir mellomrom mellom bunnen av y aksen og bunnlinjen til canvas */
  let bottomgap = canvas.height - ygap;
  /* looper gjennom data arrayen */
  for (let i = 0; i < data.length; i++) {
    /* gjør at søylene er plasses overfor teksten under søylene */
    ctx.textBaseline = 'top';
    /* skriver lokasjonene under hver søyle */
    ctx.fillText(data[i][0], leftgap, bottomgap + 5);
    /* gjør at søylene starter fra bunnen i stedet for toppen som canvas egentlig starter å tegner fra */
    let topgap = bottomgap - data[i][2];
    /* skriver inn teksten på toppen av søylene */
    ctx.fillText(data[i][1], leftgap, topgap - 15);
    /* fyller hver søyle */
    ctx.fillRect(leftgap, topgap, barwidth, data[i][2]);
    /* tegner mellomrommet mellom hver søyle */
    leftgap += bargap
  }
}

/* funksjonen filterloc filtrerer arrayen basert på valgt lokasjon og viser verdiene i en tabell */
function filterloc(array) {
  let input = document.getElementById("filter");
  let table = document.getElementById("table");
  let array2 = [];
  let filteredarray = [];
  /* lytter på drop down med lokasjoner, aktiverer funskjonen som viser data for relevant lokasjon */
  input.addEventListener('change', function () {
    /* en loop som lager antall rader (16) */
    for (let i = 0; i < array.length; i++) {
      /* fyller den tomme arrayen med dato, transaksjonstype og beløp siden vi kun trenger de tre til tabellen */
      array2[i] = [[array[i][0]], [array[i][1]], [array[i][3]]];
      /* lager variabelen row, insertRow() legger til ny rad (<tr>), knyttet til variabelen table og lager 16 rader */
      let row = table.insertRow();
      /* hvis verdien fra drop down er lik en lokasjon i arrayen hent bare dataen til den lokasjonen */
      if (input.value == array[i][2]) {
        /* filteredarray blir fylt med push() slik at den fylles med innehold som oppdateres basert med elementene til valgt lokasjon */
        filteredarray.push(array[i])
        /* en "nested" loop som henter inn alle elementene til hver eneste kolonne i array2 */
        for (let j = 0; j < array2[i].length; j++) {
          /* insertCell() legger til felter (<td>) i alle radene (<tr>), knyttet til variabelen row, feltene inneholder dataen fra array[j] som er alle elementene i arrayen */
          let cell = row.insertCell();
          /* skriver alle radene (<tr>) og feltene (<td>) med elementene (array[j]) */
          cell.innerHTML = array2[i][j];
        }
      }
    }
    /* starter funksjonen main og sender med filteredarray  */
    main(filteredarray)
  });
}

/* en funskjon som tømmer tabellene hver gang en ny lokasjon velges  slik at kun den nye lokasjonens data vises i tabellene */
function cleartable() {
  let table = document.getElementById("table");
  let table2 = document.getElementById("data");
  let tr = table.getElementsByTagName("tr");
  /* en for loop som tømmer alle radene utenom den første raden i tabell1 */
  for (let i = 1; i < tr.length; i++) {
    tr[i].innerHTML = "";
  }
  /* tømmer andre raden i tabell2 */
  table2.innerHTML = "";
}

/* funskjonen inneholder en 2d array som heter stats som inneholder resultatene fra funksjonene nedenfor */
function main(filteredarray) {
  /* hvis noen har to parametere så representerer den andre parameteren field som er indeksen til hver "nested" array i filteredarray*/
  var stats = [
    [average(filteredarray, 3)],
    [maximum(filteredarray, 3)],
    [minimum(filteredarray, 3)]
  ];
  /* lager variabelen table som henter inn tabellraden med id data fra html */
  var table2 = document.getElementById("data");
  /* en loop som itererer gjennom stats arrayen */
  for (var i = 0; i < stats.length; i++) {
    /* skriver alle elementene i tabellfelt. skriver <td> (lager nytt felt), skriver alle elementene i stats arrayen (stats[i]) inn i feltet som lages og til slutt skrives </td> som lukker feltet */
    table2.innerHTML += "<td>" + stats[i] + "</td>";
  }
}

/* funskjon som finner gjennomsnittet av alle transaksjonene */
function average(filteredarray, field) {
  /* lager en variabel med 0 i verdi */
  let total = 0;
  /* lager en loop går gjennom og legger sammen alle elementene til indeks 3 til hver "nested" array, indeks 3 pga 3 som parameter nr2(field) når funksjonen kalles på i main funskjonen */
  for (let i = 0; i < filteredarray.length; i++) {
    total += Number(filteredarray[i][field]);
  }
  /* returnerer totalsummen/antall elementer, med 2 desimaler */
  return Math.round((total / filteredarray.length) * 100) / 100;
}

/* funskjon som finner det høyeste engangsbeløpet */
function maximum(filteredarray, field) {
  /* lager en variabel med 0 i verdi */
  let max = 0;
  /* lager en loop går gjennom alle elementene og endrer verdien til variabelen max til det tallet den looper over, til indeks 3 til hver "nested" array, indeks 3 pga 3 som parameter nr2(field) når funksjonen kalles på i main funskjonen */
  for (let i = 0; i < filteredarray.length; i++) {
    /* sjekker om hvert tall er større enn verdien til max */
    if (filteredarray[i][field] > max) {
      max = Number(filteredarray[i][field]);
    }
  }
  /* returnerer max */
  return max;
}

/* funskjon som finner det laveste engangsbeløpet */
function minimum(filteredarray, field) {
  /* lager en variabel med 1024 i verdi siden 1024 er det høyeste enkeltbeløpet */
  let min = 1024;
  /* lager en loop går gjennom alle elementene og endrer verdien til variabelen min til det tallet den looper over, til indeks 3 til hver "nested" array, indeks 3 pga 3 som parameter nr2(field) når funksjonen kalles på i main funskjonen */
  for (let i = 0; i < filteredarray.length; i++) {
    /* sjekker om hvert tall er mindre enn verdien til min */
    if (filteredarray[i][field] < min) {
      min = Number(filteredarray[i][field]);
    }
  }
  /* tømmer filtered array for hver gang man velger ny lokasjon */
  filteredarray.length = 0;
  /* returnerer min */
  return min;
}