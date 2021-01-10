<template id="create-lot">
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
    <h2>Create Parkinglot</h2>

    <form :action=`api/create/${this.user.id}` method="post">

      <p>
        <label for="city">City: <label>
          <input type="text" name="city" id="city" required="required">
      </p>

      <p>
        <label for="address">Address: <label>
          <input type="text" name="address" id="address" required="required">
      </p>

      <p>
        <label for="number">Number: <label>
          <input type="number" name="number" min="1" id="number" required="required"
                 oninput="validity.valid||(value='');">
      </p>

      <p>
        <label for="zipcode">Zip code: <label>
          <input type="number" name="zipcode" min="0" id="zipcode" required="required"
                 oninput="validity.valid||(value='');">
      </p>

      <p>
        <label for="area">Area: <label>
          <input type="text" name="area" id="area" required="required">
      </p>

      <span v-if="access === true">
        <label for="user">User-Id: </label>
        <input type="number" name="user" min="1" id="user" v-model="user.id" required="required"
               oninput="validity.valid||(value='');">
      </span>

      <p>
        <input type="submit" value="Create">
      </p>

    </form>

  </div>

</template>
<script>
Vue.component("create-lot", {
  template: "#create-lot",
  data: () => ({
    city: null,
    address: null,
    number: null,
    zipcode: null,
    area: null,
    currentUser: "",
    user: null,
    access: null,
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