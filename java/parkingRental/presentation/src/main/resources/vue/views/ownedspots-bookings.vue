<template id="ownedspots-bookings">
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
    <h2>All Bookings of Your Owned Spots</h2>

    <div class="flex-wrapper">
      <div v-for="booking in bookings">
        <div class="flex-container">
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

            <p>Parkinglot City: {{ booking.parkinglot.location.city }}</p>
            <p>Address: {{ booking.parkinglot.location.address }} {{ booking.parkinglot.location.number }}</p>
            <p>ZipCode: {{ booking.parkinglot.location.zipcode }}, {{ booking.parkinglot.location.area }}</p>
            <p>Parkinglot Owner-ID: {{ booking.parkinglot.owner.id }}</p>
            <p>Owner Firstname: {{ booking.parkinglot.owner.firstname }}</p>
            <p>Owner Surname: {{ booking.parkinglot.owner.surname }}</p>
            <p>Owner City: {{ booking.parkinglot.owner.location.city }}</p>
            <p>Owner Address: {{ booking.parkinglot.owner.location.address }} {{ booking.parkinglot.owner.location.number }}</p>
            <p>Owner ZipCpde: {{ booking.parkinglot.owner.location.zipcode }}, {{booking.parkinglot.owner.location.area }}</p>

            <br>

            <p>Booking User-ID: {{ booking.user.id }}</p>
            <p>User Firstname: {{ booking.user.firstname }}</p>
            <p>User Surname: {{ booking.user.surname }}</p>
            <p>User Address: {{ booking.user.location.address }} {{ booking.user.location.number }}</p>
            <p>User ZipCode: {{ booking.user.location.zipcode }}, {{ booking.user.location.area }}</p>

            <p>Price: {{ booking.price }}</p>
          </div>
          <p>
            <a class="linkbutton" :href="`/api/bookings/delete/${booking.id}`">Delete</a>
          </p>
        </div>
      </div>
    </div>
  </div>

</template>
<script>
Vue.component("ownedspots-bookings", {
  template: "#ownedspots-bookings",
  data: () => ({
    bookings: [],
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
    fetch(`/api/bookings/ownedspots`)
        .then(res => res.json())
        .then(res => this.bookings = res)
        .catch(() => alert("No one has booked spots belonging to you!"));
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