<template id="result-alltypes">
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
    <h2>Book</h2>

    <form :action=`booking/book/${this.bookingSchedule.parkinglot.id}/${this.user.id}` method="post">

      <p>
        <label for="date">Date and time to book on</label><br>
        <br>
        <select name="selectdate" id="selectdate" @change="fillTxt()">
          <option>{{ firstOption }}</option>
        </select>
        <br>
        <br>
        <input type="text" name="date" id="date" required="required">
      </p>

      <span v-if="access === true">
        <label for="user">User-Id</label><br>
        <input type="number" name="user" min="1" id="user" v-model="user.id" required="required" oninput="validity.valid||(value='');">
      </span>

      <p>
        <label for="hours">Hours you wish to book </label><br>
        <input type="number" name="hours" min="1" id="hours" required="required" oninput="validity.valid||(value='');">
      </p>

      <p>Select a spot to book<br>
        <select name="spotid" id="spotid">
        </select>
      </p>

      <p>
        <input type="submit" value="Book">
      </p>

    </form>


    <div v-for="(value, key) in bookingSchedule.schedule">
      <span v-for="(value2, key2) in value">
        {{ key2 }} {{ key }} <br>
        {{ spotToAdd.indexOf(key) === -1 ? spotToAdd.push(key) : null }}
        {{ keyToAdd.indexOf(key2) === -1 ? keyToAdd.push(key2) : null }}
        </span>
    </div>
    {{ fillSel() }}
  </div>

</template>
<script>
Vue.component("result-alltypes", {
  template: "#result-alltypes",
  data: () => ({
    bookingSchedule: null,
    spotToAdd: [],
    userid: null,
    date: null,
    hours: null,
    currentUser: "",
    access: "",
    user: null,
    keyToAdd: [],
    firstOption: "Choose date and time",
    alreadyDone: false,
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
    fetch(`/bsch/api/result/alltypes`)
        .then(res => res.json())
        .then(res => this.bookingSchedule = res)
        .catch(() => alert("None found!"));
  }, methods: {
    checkAccess: function () {
      setTimeout(function () {
        if (this.currentUser === "logged out")
          location.replace("/")
      }.bind(this), 500);
    }, fillSel: function () {
      setTimeout(function () {
        if (this.alreadyDone === false) {
          let s = new Set(this.keyToAdd);
          let it = s.values();
          this.keyToAdd = [];
          this.keyToAdd = Array.from(it);

          this.fillSelectSpot();

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
    }, fillSelectSpot: function () {
      setTimeout(function () {
        let s = new Set(this.spotToAdd);
        let it = s.values();
        this.spotToAdd = [];
        this.spotToAdd = Array.from(it);

        let select = document.getElementById('spotid');
        let i = 0;
        for (; i < this.spotToAdd.length; i++) {
          let option = document.createElement("option");
          option.text = this.spotToAdd[i].split('Spot-ID: ').pop().split(',')[0];
          select.appendChild(option);
        }
      }.bind(this), 1500);
    }, fillTxt: function () {
      let x = document.getElementById("selectdate").value;
      if (x === this.firstOption)
        document.getElementById("date").value = "";
      else
        document.getElementById("date").value = x;
    }
  }
});
</script>