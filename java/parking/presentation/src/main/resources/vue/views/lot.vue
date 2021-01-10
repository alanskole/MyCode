<template id="lot">
  <div>

    <span style="display: none">{{ checkAccess() }}</span>

    <div class="nav">
      <div class="navbutton">
        <a :href="`/api/logout/`">
          <p>Log-out</p>
        </a>
      </div>

      <div class="navbutton">
        <a :href="`/users/${currentUser.id}/update`">
          <p>Update Account</p>
        </a>
      </div>

      <div class="navbutton" v-if="access === true">
        <a :href="`/users/`">
          <p>All users</p>
        </a>
      </div>

      <div class="navbutton">
        <a :href="`/lot/`">
          <p>All parkinglots</p>
        </a>
      </div>

      <div class="navbutton">
        <a :href="`/bookings/`">
          <p>All bookings</p>
        </a>
      </div>

      <div class="navbutton">
        <a :href="`/bookings/ownedspots`">
          <p>All bookings of owned spots</p>
        </a>
      </div>
    </div>
    <h2>Parkinglot</h2>


    <div class="parkinglotedit">
      <p>ID: {{ parkinglot.id }}</p>
      <p>City: {{ parkinglot.location.city }}</p>
      <p>Address: {{ parkinglot.location.address }} {{ parkinglot.location.number }}</p>
      <p>ZipCode: {{ parkinglot.location.zipcode }}, {{ parkinglot.location.area }}</p>
      <p>Owner-ID: {{ parkinglot.owner.id }}</p>
      <p>Owner Name: {{ parkinglot.owner.firstname }} {{ parkinglot.owner.surname }}</p>
      <p>Owner City: {{ parkinglot.owner.location.city }}</p>
      <p>Owner Address: {{ parkinglot.owner.location.address }} {{ parkinglot.owner.location.number }}</p>
      <p>Owner ZipCode: {{ parkinglot.owner.zipcode }}, {{ parkinglot.owner.location.area }}</p>

      <span v-if="parkinglot.spots !== null">
      <span v-if="parkinglot.spots.length > 0">
        <span v-if="access === true || currentUser.id === parkinglot.owner.id">
          <br>
          <p>Create a schedule for
            <br>
            <input type="number" min="1" v-model="days" oninput="validity.valid||(value='');">
            <br>
            days
            <p>
              <a class="linkbutton" :href="`api/bsch/${parkinglot.id}/create/${days}`">Create schedule</a>
            </p>
          </p>
        </span>
        <br>
        <p>
          <a class="linkbutton" :href="`/bsch/${parkinglot.id}`">Search for spot</a>
        </p>
      </span>
    </span>

      <span v-if="access === true || currentUser.id === parkinglot.owner.id">
        <br>
        <p>
          <a class="linkbutton" :href="`/lot/${parkinglot.id}/update/create/spot`">Create spot</a>
        </p>
        <p>
          <a class="linkbutton" :href="`/lot/${parkinglot.id}/update/${currentUser.id}`">Update parkinglot</a>
        </p>
        <p>
          <a class="linkbutton" :href="`/api/lot/${parkinglot.id}/delete/`">Delete parkinglot</a>
        </p>
      </span>
    </div>


    <div class="grid" v-if="parkinglot.spots !== null">
      <div class="spot" v-for="spot in parkinglot.spots">
        <p>Spot-ID: {{ spot.spotid }}</p>
        <p>Type: {{ spot.type }}</p>

        <span v-if="access == true || currentUser.id === parkinglot.owner.id">
          <p>
            <a class="linkbutton" :href="`/lot/${parkinglot.id}/update/spot/${spot.spotid}`">Update spot</a>
          </p>
          <p>
            <a class="linkbutton" :href="`/api/parkingspot/${parkinglot.id}/delete/${spot.spotid}/`">Delete spot</a>
          </p>
        </span>

        <br>

      </div>
    </div>
  </div>


</template>
<script>
Vue.component("lot", {
  template: "#lot",
  data: () => ({
    parkinglot: null,
    days: 1,
    pspot: null,
    currentUser: "",
    access: "",
  }),
  created() {
    fetch(`/api/access/`)
        .then(res => res.json())
        .then(res => this.access = res)
        .catch(() => alert("Error while fetching current user"))
    fetch(`/api/currentuser/`)
        .then(res => res.json())
        .then(res => this.currentUser = res)
        .catch(() => alert("Error while fetching current user"))
    const lotid = this.$javalin.pathParams["lotid"];
    fetch(`/api/lot/${lotid}`)
        .then(res => res.json())
        .then(res => this.parkinglot = res)
        .catch(() => alert("Parkinglot not found!"));
  }, methods: {
    checkAccess: function () {
      setTimeout(function () {
        if (this.currentUser === "logged out")
          location.replace("/")
      }.bind(this), 500);
    }
  }
});
</script>
