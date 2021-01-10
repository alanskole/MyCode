<template id="user">
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
    <h2>User</h2>

    <p>User-ID: {{ user.id }}</p>
    <p>Role: {{ user.role }}</p>
    <p>Firstname: {{ user.firstname }}</p>
    <p>Surname: {{ user.surname }}</p>
    <p>Address: {{ user.location.address }} {{ user.location.number }}</p>
    <p>ZipCode: {{ user.location.zipcode }}</p>
    <p>Area: {{ user.location.area }}</p>
    <p>City: {{ user.location.city }}</p>

    <p>
      <a class="linkbutton" :href="`/users/${user.id}/update`">Update</a>
    </p>
    <p>
      <a class="linkbutton" :href="`/api/users/delete/${user.id}`">Delete</a>
    </p>
  </div>

</template>
<script>
Vue.component("user", {
  template: "#user",
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
    fetch(`/api/users/${userid}`)
        .then(res => res.json())
        .then(res => this.user = res)
        .catch(() => alert("User not found!"));
  }, methods: {
    checkAccess: function () {
      setTimeout(function () {
        if (this.currentUser === "logged out")
          location.replace("/")
        else if (this.access === false && this.currentUser.id !== this.user.id)
          location.replace("/users/" + this.currentUser.id)
      }.bind(this), 500);
    }
  }
});
</script>
