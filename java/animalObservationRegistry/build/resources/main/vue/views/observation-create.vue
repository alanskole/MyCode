<template id="observation-create">
    <div class="form-style">
      <h2>Create new observation</h2>
      <ul class="sortList">
        <li><a class="button" :href="`/observations/`">All Observations</a></li>
      </ul>
      <form class="create" @submit="checkForm" :action=`/api/observation/create` method="post">
            <div v-if="errors.length">
                <b>Please correct the following error(s):</b>
                <ul>
                    <li v-for="error in errors">{{ error }}</li>
                </ul>
            </div>

            <p>
                <label for="name">Name<label>
                    <input type="text" name="name" id="name" required="required" v-model="name">
            </p>

            <p>
                <label for="animalClass">Bird, reptile or amphibian?<label>
                    <input type="text" name="animalClass" id="animalClass" required="required" v-model="animalClass">
            </p>

            <p>
                <label for="canFly">If it's a bird, can it fly? Answer with yes or no.<label>
                    <input type="text" name="canFly" id="canFly" v-model="canFly">
            </p>

            <p>
                <label for="venomous">If it's a reptile, is it venomous? Answer with yes or no.<label>
                    <input type="text" name="venomous" id="venomous" v-model="venomous">
            </p>

            <p>
                <label for="limbs">If it's a reptile, does it have 0, 2 or 4 limbs??<label>
                    <input type="text" name="limbs" id="limbs" v-model="limbs">
            </p>

            <p>
                <label for="limbless">If it's an amphibian, does it have limbs? Answer with yes or no.<label>
                    <input type="text" name="limbless" id="limbless" v-model="limbless">
            </p>

            <p>
                <label for="order">Order<label>
                    <input type="text" name="order" id="order" required="required" v-model="order">
            </p>
            <p>
                <label for="family">Family<label>
                    <input type="text" name="family" id="family" required="required" v-model="family">
            </p>
            <p>
                <label for="genus">Genus<label>
                    <input type="text" name="genus" id="genus" required="required" v-model="genus">
            </p>
            <p>
                <label for="species">Species<label>
                    <input type="text" name="species" id="species" required="required" v-model="species">
            </p>
            <p>
                <label for="englishName">English name<label>
                    <input type="text" name="englishName" id="englishName" required="required" v-model="englishName">
            </p>
            <p>
                <label for="planetName">Planet<label>
                    <input type="text" name="planetName" id="planetName" required="required" v-model="planetName">
            </p>
            <p>
                <label for="latitude">Latitude<label>
                    <input type="number" step="0.000001" name="latitude" id="latitude" required="required" v-model="latitude">
            </p>
            <p>
                <label for="longitude">Longitude<label>
                    <input type="number" step="0.000001" name="longitude" id="longitude" required="required" v-model="longitude">
            </p>
            <p>
                <label for="biome">Biome, if more than one Biome seperate with a comma WITHOUT space after the comme<label>
                    <input type="text" name="biome" id="biome" required="required" v-model="biome">
            </p>
            <p>
                <label for="timeAndDate">Date and time<label>
                    <input type="text" name="timeAndDate" id="timeAndDate" required="required" pattern="([0][1-9]|[1][0-9]|[2][0-9]|[3][0-1])-([0][1-9]|[1][0-2])-(233[7-9]|23[4-9][0-9]|2[4-9][0-9]{2}|[3-9][0-9]{3})( ([0-1][0-9]|[2][0-3]):[0-5][0-9])" v-model="timeAndDate">
            </p>
            <p>
                <label for="amount">Amount<label>
                    <input type="number" name="amount" id="amount" required="required" v-model="amount">
            </p>
            <p>
                <label for="picturePath">Picture Url<label>
                    <input type="text" name="picturePath" id="picturePath" required="required" pattern="(https?:\/\/(?:www\.|(?!www))[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\.[^\s]{2,}|www\.[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\.[^\s]{2,}|https?:\/\/(?:www\.|(?!www))[a-zA-Z0-9]+\.[^\s]{2,}|www\.[a-zA-Z0-9]+\.[^\s]{2,})" v-model="picturePath">
            </p>
            <p>
                <label for="comment">Comment<label>
                    <input type="text" name="comment" id="comment" required="required" v-model="comment">
            </p>

            <p>
                <input type="submit" value="Create Observation">
            </p>

        </form>
    </div>

</template>
<script>
    Vue.component("observation-create", {
        template: "#observation-create",
        data: () => ({
            observations: [],
            name: null,
            animalClass: null,
            canFly: null,
            limbs: null,
            limbless: null,
            venomous: null,
            order: null,
            family: null,
            genus: null,
            species: null,
            englishName: null,
            planetName: null,
            latitude: null,
            longitude: null,
            biome: null,
            timeAndDate: null,
            amount: null,
            picturePath: null,
            comment: null,
            errors: [],
        }),
        methods: {
            checkForm: function (e) {
                this.errors = [];

                if (this.animalClass.toLowerCase() === "bird" || this.animalClass.toLowerCase() === "aves") {
                    if (this.canFly != null) return true;
                    else this.errors.push("Must specify if the bird can fly");
                } else if (this.animalClass.toLowerCase() === "reptile" || this.animalClass.toLowerCase() === "reptilia") {
                    if (this.venomous != null && this.limbs != null) return true;
                    else if (this.venomous == null) this.errors.push("Must specify if the reptile is venomous");
                    else this.errors.push("Must specify how many limbs the reptile has");
                } else if (this.animalClass.toLowerCase() === "amphibian" || this.animalClass.toLowerCase() === "amphibia") {
                    if (this.limbless != null ) return true;
                    else this.errors.push("Must specify if the amphibian is limbless");
                } else this.errors.push("Choose a valid animal class");

                e.preventDefault();
            }
        }
    });
</script>
<style>
    .form-style{
        font-family: 'Open Sans Condensed', arial, sans;
        width: 500px;
        padding: 30px;
        background: #191919;
        margin: 50px auto;
        box-shadow: 0px 0px 15px rgba(0, 0, 0, 0.22);
        -moz-box-shadow: 0px 0px 15px rgba(0, 0, 0, 0.22);
        -webkit-box-shadow:  0px 0px 15px rgba(0, 0, 0, 0.22);
        border: #dddddd;
    }
    .form-style h2{
        background: #4D4D4D;
        text-transform: uppercase;
        font-family: 'Open Sans Condensed', sans-serif;
        color: #FFFFFF;
        font-size: 18px;
        font-weight: 100;
        padding: 20px;
        margin: -30px -30px 30px -30px;
    }
    .form-style input[type="text"],
    .form-style input[type="date"],
    .form-style input[type="datetime"],
    .form-style input[type="email"],
    .form-style input[type="number"],
    .form-style input[type="search"],
    .form-style input[type="time"],
    .form-style input[type="url"],
    .form-style input[type="password"],
    .form-style textarea,
    .form-style select
    {
        box-sizing: border-box;
        -webkit-box-sizing: border-box;
        -moz-box-sizing: border-box;
        outline: none;
        display: block;
        width: 100%;
        padding: 7px;
        border: none;
        color: white;
        border-bottom: 1px solid #ddd;
        background: transparent;
        margin-bottom: 10px;
        font: 16px Arial, Helvetica, sans-serif;
        height: 45px;
    }
    .form-style textarea{
        resize:none;
        overflow: hidden;
    }
    .form-style input[type="button"],
    .form-style input[type="submit"]{
        background: none;
        display: inline-block;
        cursor: pointer;
        font-family: 'Open Sans Condensed', sans-serif;
        font-size: 14px;
        text-decoration: none;
        text-transform: uppercase;
        padding: 10px;
        margin: 10px;
        border: 1px solid white;
        color: white;
        border-radius: 15px;
    }
    .form-style input[type="button"]:hover,
    .form-style input[type="submit"]:hover {
        border: 2px solid white;
    }

    .create {
        color: white;
    }
</style>
