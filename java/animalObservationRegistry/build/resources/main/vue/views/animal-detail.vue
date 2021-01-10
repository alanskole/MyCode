<template id="animal-detail">
    <div v-if="animalia" class="single-observation-container">
      <ul class="sortList">
        <li><a class="button" :href="`/observations/`">All Observations</a></li>
      </ul>
        <h1>{{animalia.englishName}}</h1>
        <p>Domain: {{animalia.domain}}</p>
        <p>Kingdom: {{animalia.kingdom}}</p>
        <p>Phylum: {{animalia.phylum}}</p>
        <p>Class: {{animalia.animalClass}}</p>
        <p>Order: {{animalia.order}}</p>
        <p>Family: {{animalia.family}}</p>
        <p>Genus: {{animalia.genus}}</p>
        <p>Species: {{animalia.species}}</p>
    </div>
</template>
<script>
    Vue.component("animal-detail", {
        template: "#animal-detail",
        data: () => ({
            animalia: null
        }),
        created() {
            const id = this.$javalin.pathParams["id"];
            fetch(`/api/observations/${id}/animal`)
                .then(res => res.json())
                .then(res => {
                    this.animalia = res;
                })
                .catch(() => alert("Error while fetching animal"));
        }
    });
</script>
<style>
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

    img.cover-image-frontpage {
        height: auto;
        width: 100%;
        padding-bottom: 20px;
        max-height: 280px;
    }

</style>