<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script>
import { getToken, removeToken, removePermission } from "@/utils/auth";

export default {
  name: "App",
  created() {
    if (!getToken()) {
      removeToken();
      // router.push("/");
    }
  },
};
</script>

