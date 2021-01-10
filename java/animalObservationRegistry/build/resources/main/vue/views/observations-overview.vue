<template id="observations-overview">
   <div>
       <h1>Observations</h1>
        <ul class="observations-overview-list">
            <ul class="sortList">
                <li><a v-if="sorting" :href="`/observations/?sort_by=id`">Sort by ID</a></li>
                <li><a v-if="sorting" :href="`/observations/?sort_by=time`">Sort by time</a></li>
                <li><a v-if="sorting" :href="`/observations/?sort_by=name`">Sort by name</a></li>
                <br>
            </ul>
            <ul class="sortList">
                <li><a class="button" :href="`/observation/create`">Create New Observation</a></li>
            </ul>
            <li v-for="observation in observations">
                <a :href="`/observations/${observation.id}`" class="link-to-observation-details">
                    <div class="single-observation-container" >
                        <h1>{{observation.id}} - {{observation.name}}</h1>
                        <h1>{{observation.animal.name}}</h1>
                    </div>
                </a>
            </li>
        </ul>
   </div>
</template>
<script>
    Vue.component("observations-overview", {
        template: "#observations-overview",
        data: () => ({
            observations: [],
            sorting: "id",
        }),
        created() {
            var sort = this.$javalin.queryParams["sort_by"];
            if (typeof sort != 'undefined')
                this.sorting = sort;
            fetch("/api/observations")
                    .then(res => res.json())
                .then(res => {
                   this.observations = res;
                })
                .catch(() => alert("Error while fetching observations"))
            fetch(`/api/observations/?sort_by=${this.sorting}`)
                .then(res => res.json())
                .then(res => this.observations = res)
                .catch(() => alert("Error while fetching observations"));
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