<template id="update-user">
  <div>
    <span style="display: none">{{checkAccess()}}</span>

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
    <h2>Update User</h2>


    <form :action =`api/update` method="post">

      <p>
        <label for="firstname">Firstname<label><br>
          <input type="text" name="firstname" id="firstname" required="required" v-model="user.firstname">
      </p>

      <p>
        <label for="surname">Surname<label><br>
          <input type="text" name="surname" id="surname" required="required" v-model="user.surname">
      </p>

      <p>
        <label for="city">City<label><br>
          <input type="text" name="city" id="city" required="required" v-model="user.location.city">
      </p>

      <p>
        <label for="address">Address<label><br>
          <input type="text" name="address" id="address" required="required" v-model="user.location.address">
      </p>

      <p>
        <label for="number">Number<label><br>
          <input type="number" name="number" min="0" id="number" v-model="user.location.number" required="required" oninput="validity.valid||(value='');">
      </p>

      <p>
        <label for="zipcode">Zip code<label><br>
          <input type="number" name="zipcode" min="0" id="zipcode" v-model="user.location.zipcode" required="required" oninput="validity.valid||(value='');">
      </p>

      <p>
        <label for="area">Area<label><br>
          <input type="text" name="area" id="area" required="required" v-model="user.location.area">
      </p>

      <p>
        <input type="submit" value="Update">
      </p>
    </form>

    <span v-if="isAdmin === true || currentUser.id === user.id">
      <p>
        <a class="linkbutton" :href="`/api/users/delete/${user.id}`">Delete User</a>
      </p>
    </span>

  </div>

</template>
<script>
Vue.component("update-user", {
  template: "#update-user",
  data: () => ({
    user: null,
    currentUser: "",
    access: "",
    isAdmin: false,
  }),
  created() {
    fetch(`/api/isadmin/`)
        .then(res => res.json())
        .then(res => this.isAdmin = res)
        .catch(() => alert("Error while fetching admin status"))
    fetch(`/api/access/`)
        .then(res => res.json())
        .then(res => this.access = res)
        .catch(() => alert("Error while fetching current user"))
    fetch(`/api/currentuser/`)
        .then(res => res.json())
        .then(res => this.currentUser = res)
        .catch(() => alert("Error while fetching current user"))
    const userid = this.$javalin.pathParams["userid"];
    fetch(`/api/users/${userid}/`)
        .then(res => res.json())
        .then(res => this.user = res)
        .catch(() => alert("User not found!"));
  }, methods: {
    checkAccess: function() {
      setTimeout(function () {
        if (this.currentUser === "logged out")
          location.replace("/")
        else if (this.access === false && this.currentUser.id !== this.user.id)
          location.replace("/users/"+this.currentUser.id)
      }.bind(this), 500);
    }
  }
});
</script>