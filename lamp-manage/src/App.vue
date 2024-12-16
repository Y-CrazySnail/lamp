<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script>
import { getToken, removeToken, removePermission } from "@/utils/auth";
export default {
  name: "App",
  components: {},
  created() {
    if (!getToken()) {
      removeToken();
      this.$router.push("/");
    }
  },
};
</script>
