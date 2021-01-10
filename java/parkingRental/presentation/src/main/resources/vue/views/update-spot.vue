<template id="update-spot">
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
    <h2>Update Spot</h2>

    <form :action=`${this.parkinglot.id}/${this.parkingspot.spotid}` method="post">
      <p>
        <label for="type">Type</label><br>
        <br>
        <select name="type" id="type">
          <option>Regular</option>
          <option>Handicap</option>
          <option>Truck</option>
        </select>
        <br>
        <br>
      </p>

      <p>
        <input type="submit" value="Update">
      </p>

    </form>

  </div>

</template>
<script>
Vue.component("update-spot", {
  template: "#update-spot",
  data: () => ({
    parkinglot: null,
    parkingspot: null,
    type: null,
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
    const spotid = this.$javalin.pathParams["spotid"];
    const lotid = this.$javalin.pathParams["lotid"];
    fetch(`/api/lot/${lotid}`)
        .then(res => res.json())
        .then(res => this.parkinglot = res)
        .catch(() => alert("Parkinglot not found!"))
    fetch(`/api/parkingspot/${lotid}/${spotid}`)
        .then(res => res.json())
        .then(res => this.parkingspot = res)
        .catch(() => alert("Parkingspot not found!"));
  }, methods: {
    checkAccess: function () {
      setTimeout(function () {
        if (this.currentUser === "logged out")
          location.replace("/")
        else if (this.access === false && this.parkinglot.owner.id !== this.currentUser.id)
          location.replace("/lot/" + this.parkinglot.id)
      }.bind(this), 500);
    }
  }
});
</script>