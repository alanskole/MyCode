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
  
  console.log(readfile);
} 