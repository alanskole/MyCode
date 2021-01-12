/* lager en annonym startfunksjon som aktiverer funskjonen getfile når knappen med id uploadfile klikkes */
window.onload = function() {
    let fileuploader = document.getElementById('uploadfile');
    fileuploader.addEventListener('change', getfile)
  }
  /* funksjon som laster opp og leser filen man velger etter man klikker på "Browse" knappen*/
  function getfile() {
    let file = event.target.files[0];
    let readfile = new FileReader();
  
    readfile.readAsText(file);
    readfile.onload = function() {
      /* lager en array av innholdet i csv filen som deles opp ved hver "\n" */
      const array = readfile.result.split('\n');
      /* lager en loop som gjør at innholdet i csv filen som deles opp ved hver "," blir en 2d array */
      for(var i = 0; i < array.length; i++) {
        array[i] = array[i].split(",");
      } 
      console.log(array);
    }
  }
