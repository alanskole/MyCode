<template id="booking">
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

    <h2>Booking</h2>

    <div class="grid">
      <div class="booking">
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
        <p>ZipCode: {{ booking.parkinglot.location.zipcode }}</p>
        <p>Area: {{ booking.parkinglot.location.area }}</p>
        <p>Parkinglot Owner-ID: {{ booking.parkinglot.owner.id }}</p>
        <p>Owner Name: {{ booking.parkinglot.owner.firstname }} {{ booking.parkinglot.owner.surname }}</p>
        <p>Owner Address: {{ booking.parkinglot.owner.location.address }} {{ booking.parkinglot.owner.location.number }}</p>
        <p>Owner ZipCpde: {{ booking.parkinglot.owner.location.zipcode }} , {{ booking.parkinglot.owner.location.area }}</p>
        <p>Owner City: {{ booking.parkinglot.owner.location.city }}</p>
        <br>
        <p>Booking User-ID: {{ booking.user.id }}</p>
        <p>User Name: {{ booking.user.firstname }} {{ booking.user.surname }}</p>
        <p>User Address: {{ booking.user.location.address }} {{ booking.user.location.number }}</p>
        <p>User ZipCpde: {{ booking.user.location.zipcode }}, {{ booking.user.location.area }}</p>
        <p>User City: {{ booking.parkinglot.owner.location.city }}</p>
        <br>
        <p>Price: {{ booking.price }}</p>
        <p>
          <a class="linkbutton" :href="`/api/bookings/delete/${booking.id}`">Delete</a>
        </p>
      </div>
    </div>
  </div>

</template>
<script>
Vue.component("booking", {
  template: "#booking",
  data: () => ({
    booking: null,
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
    const bid = this.$javalin.pathParams["bid"];
    fetch(`/api/bookings/${bid}`)
        .then(res => res.json())
        .then(res => this.booking = res)
        .catch(() => alert("Booking not found!"));
  }, methods: {
    checkAccess: function () {
      setTimeout(function () {
        if (this.currentUser === "logged out")
          location.replace("/")
        else if (this.access === false && this.booking.user.id !== this.currentUser.id)
          location.replace("/users/" + this.currentUser.id)
      }.bind(this), 500);
    }
  }
});
</script>