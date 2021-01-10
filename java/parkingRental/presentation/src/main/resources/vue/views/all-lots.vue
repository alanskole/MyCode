<template id="all-lots">
  <div>
    <span style="display:none">{{ checkAccess() }}</span>


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

    <h2>All Parkinglots</h2>

    <div class="button">
      <a :href="`/lot/create/new/lot/${currentUser.id}`">
        <p>Create parkinglot</p>
      </a>
    </div>


    <br>
    <br>
    <div class="flex-wrapper">
      <div v-for="parkinglot in parkinglots">
        <div class="flex-container">
          <a :href="`/lot/${parkinglot.id}`">
            <p>ID: {{ parkinglot.id }}</p>
            <p>City: {{ parkinglot.location.city }}</p>
            <p>Address: {{ parkinglot.location.address }} {{ parkinglot.location.number }}</p>
            <p>ZipCode: {{ parkinglot.location.zipcode }}, {{ parkinglot.location.area }}</p>
            <br>
            <p>Owner-ID: {{ parkinglot.owner.id }}</p>
            <p>Name: {{ parkinglot.owner.firstname }} {{ parkinglot.owner.surname }}</p>
            <p>Owner City: {{ parkinglot.owner.location.city }}</p>
            <p>Owner Address: {{ parkinglot.owner.location.address }} {{ parkinglot.owner.location.number }}</p>
            <p>Owner ZipCode: {{ parkinglot.owner.location.zipcode }}, {{ parkinglot.owner.location.area }}</p>
            <p>Owner Role: {{ parkinglot.owner.role }}</p>

          </a>

          <span v-if="parkinglot.spots !== null">
            <span v-for="spot in parkinglot.spots">
              <p>Spot-ID: {{ spot.spotid }}, Type: {{ spot.type }}</p>
            </span>

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
              <p>
                <br>
                <a class="linkbutton" :href="`/bsch/${parkinglot.id}`">Search for spot</a>
              </p>
            </span>
          </span>

          <span v-if="access === true || currentUser.id === parkinglot.owner.id">
            <br>
            <a class="linkbutton" :href="`/api/lot/${parkinglot.id}/delete/`">Delete parkinglot</a>
          </span>
        </div>
      </div>
    </div>
  </div>


</template>
<script>
Vue.component("all-lots", {
  template: "#all-lots",
  data: () => ({
    parkinglots: [],
    days: 1,
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
    fetch(`/api/lot/`)
        .then(res => res.json())
        .then(res => this.parkinglots = res)
        .catch(() => alert("No parkinglots found!"));
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