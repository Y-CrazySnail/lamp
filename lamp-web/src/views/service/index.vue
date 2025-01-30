<template>
  <div>
    <windows></windows>
    <mobile></mobile>
    <empty></empty>
  </div>
</template>

<script>
import VueQr from "vue-qr";
import Windows from "./windows.vue";
import Mobile from "./mobile.vue";
import Empty from "./empty.vue";
export default {
  name: "Service",
  components: { VueQr, Windows, Mobile, Empty, },
  computed: {
    serviceList() {
      return this.$store.state.service.serviceList;
    },
    device() {
      return this.$store.state.app.device;
    },
  },
  created() {
    const loading = this.$loading({
      lock: true,
      text: "加载中...",
      spinner: "el-icon-loading",
    });
    this.$store.dispatch("member/get").then((res) => {
      loading.close();
    });
    this.$store.dispatch("notice/list").then((res) => {
      loading.close();
    });
  },
  methods: {
    onCopySuccess() {
      this.$message.success("已复制至剪切板");
    },
    updateUUID(id) {
      let param = {};
      param.uuid = this.$uuid.v4();
      param.id = id;
      this.$store.dispatch("service/updateUUID", param).then((e) => {
        this.$store.dispatch("service/list");
      });
    },
    toPackage() {
      this.$router.push(`/package/index`);
    },
    toIOSshadowrocket(service) {
      this.$store.state.service.service = service;
      this.$router.push(`/guide/ios-stash`);
    },
  },
};
</script>

<style lang="scss" scoped>
.info-container {
  margin: 10px 0 0 20px;
  width: 250px;
}

.info-time {
  display: flex;
  align-items: center;
  height: 30px;
  background-color: greenyellow;
  border-radius: 5px;
}

.info-data {
  display: flex;
  align-items: center;
  height: 30px;
  margin-top: 5px;
  background-color: greenyellow;
  border-radius: 5px;
}

.system {
  margin: 10px 0 0 20px;
}

.system-button {
  width: 150px;
}

.hr-like::before {
  content: "";
  display: block;
  width: 100%;
  height: 1px;
  background: linear-gradient(
    to right,
    #ffffff,
    #e0e0e0,
    #ffffff
  ); /* 渐变色分割线 */
}
</style>
