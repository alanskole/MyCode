<template id="login">

  <div>
    <h2>Log in</h2>

    <p>
      <a class="linkbutton" :href="`/users/create/new/user/`">Create new user</a>
    </p>
    <br>

    <div class="flex-wrapper">
      <div v-for="user in users">
        <div class="flex-container">
          <a :href="`/api/currentuser/${user.id}`">
            <div>
              <p>User-ID: {{ user.id }} Role: {{ user.role }}</p>
              <p>Name: {{ user.firstname }} {{ user.surname }}</p>
              <p>Address: {{ user.location.address }} {{ user.location.number }}</p>
              <p>ZipCode: {{ user.location.zipcode }}, {{ user.location.area }}</p>
              <p>City: {{ user.location.city }}</p>
            </div>
          </a>
        </div>
      </div>
    </div>
  </div>


</template>
<script>
Vue.component("login", {
  template: "#login",
  data: () => ({
    users: [],
  }),
  created() {
    fetch(`/api/users/`)
        .then(res => res.json())
        .then(res => this.users = res)
        .catch(() => alert("No users found!"));
  },
});
</script>
