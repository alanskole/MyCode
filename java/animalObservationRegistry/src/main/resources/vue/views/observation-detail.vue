<template id="observation-detail">
    <div v-if="observation" class="single-observation-container">
      <ul class="sortList">
        <li><a class="button" :href="`/observations/`">All Observations</a></li>
      </ul>
        <ul class="sortList">
            <li><a class="button" :href="`/api/observations/${observation.id}/delete`">Delete</a></li>
            <li><a class="button" :href="`/observations/${observation.id}/update`">Edit</a></li>
        </ul>
        <h1>Observation id: {{observation.id}}</h1>
        <p>Name: {{observation.name}}</p>
        <p><a :href="`/observations/${observation.id}/animal`" class="link-to-observation-details">Animal: {{this.observation.animal.englishName}}</a></p>
        <p>Planet: {{this.observation.location.planet.name}}</p>
        <p>Latitude: {{this.observation.location.latitude}}</p>
        <p>Longitude: {{this.observation.location.longitude}}</p>

        <div class="hide">{{stopit = observation.location.biome.length}}</div>
        <div v-for="biome in observation.location.biome">
            <span v-if="i < stopit">
                {{biomenames += (biome.name)}}
                {{i++}}
                <span v-if="i < (stopit)"> {{biomenames += ", "}}</span>
            </span>
        </div>

        <p v-if="stopit > 1">Biomes: {{biomenames}}</p>

        <p v-else>Biome: {{observation.location.biome[0].name}}</p>

        <p>Date and time: {{observation.timeAndDate}}</p>
        <p>Amount: {{observation.amount}}</p>
        <p>Comment: {{observation.comment}}</p>
        <img v-if="observation.picturePath" class="cover-image" v-bind:src="observation.picturePath">
    </div>
</template>
<script>
    Vue.component("observation-detail", {
        template: "#observation-detail",
        data: () => ({
            observation: null,
            biomenames: "",
            i: 0,
            stopit: null,
        }),
        created() {
            const id = this.$javalin.pathParams["id"];
            fetch(`/api/observations/${id}`)
                .then(res => res.json())
                .then(res => {
                    this.observation = res
                })
                .catch(() => alert("Error while fetching observation"))
        }
    });
</script>
<style>

    .sortList{
        display: flex;
        flex-wrap: wrap;
        justify-content: center;
        margin-top: 30px;
        margin-bottom: 30px;
    }

    .sortList li{
        padding: 10px;
        margin: 10px;
        border: 1px solid white;
        color: white;
        border-radius: 15px;
    }

    .sortList li a{
        color: white;
        text-decoration: none;
        font-weight: bold;
    }

    .sortList li:hover{
        border: 2px solid white;
    }

    .hide {
        display: none;
    }

    li{
        list-style-type: none;
    }

    .observation-overview-list{
        display: flex;
        flex-wrap: wrap;
        justify-content: center;
    }

    .observation-overview-list li{
        padding: 10px;
        border: 1px solid white;
        border-radius: 15px;
    }

    .link-to-observation-details{
        width: 400px;
        height:100px;
        text-decoration: none;
        color: white;
    }

    div.single-observation-container{
        overflow: hidden;
        width: 500px;
        background-color: #000000;
        margin: 0 auto;
        opacity: 0.96;
        text-align: center;
        color: white;
    }

    div.single-observation-container:hover{
        opacity: 1.0;
        overflow: hidden;
        -webkit-box-shadow: 10px 10px 5px 0px rgba(0,0,0,0.25);
        -moz-box-shadow: 10px 10px 5px 0px rgba(0,0,0,0.25);
        box-shadow: 10px 10px 5px 0px rgba(0,0,0,0.25);
    }

    img.cover-image {
        height: 320px;
        width: 320px;
        padding-bottom: 20px;
    }


</style>