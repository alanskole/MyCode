<template id="update-lot">
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
    <h2>Update Parkinglot</h2>

    <form :action=`api/update/${this.user.id}` method="post">

      <p>
        <label for="city">City<label><br>
          <input type="text" name="city" id="city" required="required" v-model="parkinglot.location.city">
      </p>

      <p>
        <label for="address">Address<label><br>
          <input type="text" name="address" id="address" required="required" v-model="parkinglot.location.address">
      </p>

      <p>
        <label for="number">Number<label><br>
          <input type="number" name="number" min="1" id="number" v-model="parkinglot.location.number"
                 required="required" oninput="validity.valid||(value='');">
      </p>

      <p>
        <label for="zipcode">Zip code<label><br>
          <input type="number" name="zipcode" min="0" id="zipcode" v-model="parkinglot.location.zipcode"
                 required="required" oninput="validity.valid||(value='');">
      </p>

      <p>
        <label for="area">Area<label><br>
          <input type="text" name="area" id="area" required="required" v-model="parkinglot.location.area">
      </p>

      <span v-if="access === true">
        <label for="user">User-Id</label><br>
        <input type="number" name="user" min="1" id="user" v-model="user.id" required="required"
               oninput="validity.valid||(value='');">
      </span>

      <p>
        <input type="submit" value="Update">
      </p>

    </form>

    <br>
    <br>
    <br>

    <a :href="`/lot/${parkinglot.id}/update/create/spot`">
      <p>Create spot</p>
    </a>

    <span v-if="parkinglot.spots !== null">
      <div class="grid" v-for="spot in parkinglot.spots">
      <div class="spot">
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
      </div>
      </div>
    </span>


    <br>
    <br>
    <br>

  </div>

</template>
<script>
Vue.component("update-lot", {
  template: "#update-lot",
  data: () => ({
    parkinglot: null,
    userid: "",
    currentUser: "",
    user: null,
    access: "",
  }),
  created() {
    fetch(`/api/access/`)
        .then(res => res.json())
        .then(res => this.access = res)
        .catch(() => alert("Error while fetching current user"))
    fetch(`/api/currentuser/`)
        .then(res => res.json())
        .then(res => {
          this.currentUser = res
          this.user = this.currentUser
        })
        .catch(() => alert("Error while fetching current user"))
    const lotid = this.$javalin.pathParams["lotid"];
    fetch(`/api/lot/${lotid}/`)
        .then(res => res.json())
        .then(res => this.parkinglot = res)
        .catch(() => alert("Parkinglot not found!"));
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