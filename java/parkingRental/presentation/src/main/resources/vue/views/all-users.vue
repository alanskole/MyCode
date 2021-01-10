<template id="all-users">
  <div>
    <span style="display: none">{{ checkAccess() }}</span>


    <div class="nav">
      <div class="navbutton">
        <a :href="`/api/logout/`">
          <p>Log-out</p>
        </a>
      </div>

      <div class="navbutton">
        <a :href="`/users/${user.id}/update`">
          <p>Update Account</p>
        </a>
      </div>

      <div class="navbutton">
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

    <h2>All Users</h2>
    <div class="flex-wrapper">
      <div v-for="user in users">
        <div class="flex-container">
          <a :href="`/users/${user.id}`">
            <div>
              <p>User-ID: {{ user.id }}</p>
              <p>Firstname: {{ user.firstname }}</p>
              <p>Surname: {{ user.surname }}</p>
              <p>User City: {{ user.location.city }}</p>
              <p>User Address: {{ user.location.address }} {{ user.location.number }}</p>
              <p>User ZipCode: {{ user.location.zipcode }}, {{ user.location.area }}</p>
              <p>Role: {{ user.role }}</p>
            </div>
          </a>
          <p>
            <a class="linkbutton" :href="`/users/${user.id}/update`">Update</a>
          </p>
          <span v-if="isAdmin === true">
            <p>
              <a class="linkbutton" :href="`/api/users/delete/${user.id}`">Delete</a>
            </p>
          </span>
        </div>
      </div>
    </div>
  </div>

</template>
<script>
Vue.component("all-users", {
  template: "#all-users",
  data: () => ({
    users: [],
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
    fetch(`/api/users/`)
        .then(res => res.json())
        .then(res => this.users = res)
        .catch(() => alert("No users found!"));
  }, methods: {
    checkAccess: function () {
      setTimeout(function () {
        if (this.access === false)
          location.replace("/lot/")
      }.bind(this), 500);
    }
  }
});
</script>