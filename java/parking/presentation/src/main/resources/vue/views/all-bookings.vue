<template id="all-bookings">
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
    <h2>All Bookings</h2>

    <div v-if="isAdmin === true">
      <p>
        <a class="linkbutton" :href="`api/bookings/csv`">Write all to CSV</a>
      </p>
    </div>

    <div class="flex-wrapper">
      <div v-for="booking in bookings">
        <div class="flex-container">
          <a :href="`/bookings/${booking.id}`">
            <div>
              <p>Booking-ID: {{ booking.id }}</p>
              <p>Spot-ID: {{ booking.spot.spotid }} {{ booking.spot.type }} </p>

              <span style="display:none;">{{ i = 0 }}</span>

              <p>Bookingdates:
                <span v-for="dates in booking.dateAndTime">
                  {{ dates }}<span v-if="i + 1 !== booking.dateAndTime.length">, </span>
                  <span style="display:none;">{{ i++ }}</span>
                </span>
              </p>

              <p>Parkinglot Location City: {{ booking.parkinglot.location.city }}</p>
              <p>Address: {{ booking.parkinglot.location.address }}</p>
              <p>Number: {{ booking.parkinglot.location.number }}</p>
              <p>ZipCode: {{ booking.parkinglot.location.zipcode }}, {{ booking.parkinglot.location.area }}</p>
              <br>
              <p>Parkinglot Owner-ID: {{ booking.parkinglot.owner.id }}</p>
              <p>Owner Name: {{ booking.parkinglot.owner.firstname }}, {{ booking.parkinglot.owner.surname }}</p>
              <br>
              <p>Booking User-ID: {{ booking.user.id }}</p>
              <p>User Name: {{ booking.user.firstname }} {{ booking.user.surname }}</p>
              <p>Price: {{ booking.price }}</p>
            </div>
          </a>
          <p>
            <a class="linkbutton" :href="`/api/bookings/delete/${booking.id}`">Delete</a>
          </p>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
Vue.component("all-bookings", {
  template: "#all-bookings",
  data: () => ({
    bookings: [],
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
    fetch(`/api/bookings/`)
        .then(res => res.json())
        .then(res => this.bookings = res)
        .catch(() => alert("User doesn't have any bookings"));
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