<template id="create-user">

  <div>


    <h2>Create User</h2>


    <form :action=`api/create` method="post">

      <p>
        <label for="firstname">Firstname: <label><br>
          <input type="text" name="firstname" id="firstname" required="required">
      </p>

      <p>
        <label for="surname">Surname: <label><br>
          <input type="text" name="surname" id="surname" required="required">
      </p>

      <p>
        <label for="city">City: <label><br>
          <input type="text" name="city" id="city" required="required">
      </p>

      <p>
        <label for="address">Address: <label><br>
          <input type="text" name="address" id="address" required="required">
      </p>

      <p>
        <label for="number">Number: <label><br>
          <input type="number" name="number" min="0" id="number" required="required"
                 oninput="validity.valid||(value='');">
      </p>

      <p>
        <label for="zipcode">Zip code: <label><br>
          <input type="number" name="zipcode" min="0" id="zipcode" required="required"
                 oninput="validity.valid||(value='');">
      </p>

      <p>
        <label for="area">Area: <label><br>
          <input type="text" name="area" id="area" required="required">
      </p>

      <p>
        <input type="submit" value="Create">
      </p>

    </form>
  </div>

</template>
<script>
Vue.component("create-user", {
  template: "#create-user",
  data: () => ({
    access: "",
  }),
  created() {
  }
});
</script>