<template id="search">
  <div>

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
    <h2>Find Available</h2>

    <p>City: {{ parkinglot.location.city }}</p>
    <p>Address: {{ parkinglot.location.address }} {{ parkinglot.location.number }}</p>
    <p>ZipCode: {{ parkinglot.location.zipcode }}, {{ parkinglot.location.area }}</p>

    <br>

    <form :action=`api/${this.parkinglot.id}` method="post">

      <p>
        <label for="date">Date and time you wish to book on</label><br>
        <br>
        <select name="selectdate" id="selectdate" @change="fillText()">
          <option>{{ firstOption }}</option>
        </select>
        <br>
        <br>
        <input type="text" name="date" id="date" required="required">
      </p>

      <p>
        <label for="type">Type</label><br>
        <br>
        <select name="type" id="type">
          <option>{{ firstOptionType }}</option>
        </select>
        <br>
        <br>
      </p>

      <p>
        <label for="hours">Hours you wish to book for <label><br>
          <input type="number" name="hours" min="1" id="hours" required="required"
                 oninput="validity.valid||(value='');">
      </p>

      <p>
        <input type="submit" value="Get spots">
      </p>

    </form>


    <h3>Below is a list of available spots at the given times</h3>
    <div v-for="(value, key) in bookingSchedule.schedule">
      <span v-for="(value2, key2) in value">
        <span v-if="value2 == true">
          {{ key2 }} {{ key }} <br>
          {{ keyToAdd.indexOf(key2) === -1 ? keyToAdd.push(key2) : null }}
        </span>
      </span>
    </div>

    {{ fillSelect() }}

  </div>

</template>
<script>
Vue.component("search", {
  template: "#search",
  data: () => ({
    bookingSchedule: null,
    parkinglot: null,
    spots: [],
    date: null,
    hours: null,
    currentUser: "",
    access: "",
    keyToAdd: [],
    firstOption: "Choose date and time",
    firstOptionType: "All types",
    alreadyDone: false,
    alreadyDoneType: false,
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
    const lotid = this.$javalin.pathParams["lotid"];
    fetch(`/api/bsch/${lotid}`)
        .then(res => res.json())
        .then(res => {
          this.bookingSchedule = res
          this.parkinglot = this.bookingSchedule.parkinglot
          this.spots = this.parkinglot.spots
        })
        .catch(() => alert("Schedule not found!"));
  }, methods: {
    checkAccess: function () {
      setTimeout(function () {
        if (this.currentUser === "logged out")
          location.replace("/")
      }.bind(this), 500);
    }, fillSelect: function () {
      setTimeout(function () {
        if (this.alreadyDone === false) {
          this.fillSelectType();

          let s = new Set(this.keyToAdd);
          let it = s.values();
          this.keyToAdd = [];
          this.keyToAdd = Array.from(it);

          this.keyToAdd.sort(function (date1, date2) {
            date1 = new Date(date1);
            date2 = new Date(date2);
            if (date1 > date2) return 1;
            if (date1 < date2) return -1;
          })

          let select = document.getElementById('selectdate');
          let i = 0;
          for (; i < this.keyToAdd.length; i++) {
            let option = document.createElement("option");
            option.text = this.keyToAdd[i];
            select.appendChild(option);
            this.alreadyDone = true;
          }
        }
      }.bind(this), 1000);
    }, fillText: function () {
      let x = document.getElementById("selectdate").value;
      if (x === this.firstOption)
        document.getElementById("date").value = "";
      else
        document.getElementById("date").value = x;
    }, fillSelectType: function () {
      setTimeout(function () {
        let select = document.getElementById('type');
        let i = 0;
        for (; i < this.spots.length; i++) {
          let option = document.createElement("option");
          option.text = this.spots[i].type;
          select.appendChild(option);
        }
      }.bind(this), 1500);
    }
  }
});
</script>