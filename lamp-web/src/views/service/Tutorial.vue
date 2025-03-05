<template>
  <lamp-card>
    <div
      style="
        width: 100%;
        height: 100%;
        display: flex;
        justify-content: center;
        align-items: center;
        flex-direction: column;
      "
    >
      <div
        style="
          width: 40px;
          height: 40px;
          background-color: #f0fcff;
          border-radius: 50%;
          display: flex;
          justify-content: center;
          align-items: center;
        "
      >
        <img :src="tutorial.logo" style="width: 30px; height: 30px" />
      </div>
      <div
        style="
          margin-top: 0.5rem;
          color: #566a7f;
          font-weight: 500;
          line-height: 1.1;
          font-size: 1.125rem;
        "
      >
        {{ tutorial.title }}
      </div>
      <div style="margin-top: 0.2rem; color: #a1acb8; font-size: 0.8rem">
        {{ tutorial.description }}
      </div>
      <div style="margin-top: 0.5rem">
        <LampButton>前往帮助中心</LampButton>
      </div>
    </div>
  </lamp-card>
</template>

<script>
import LampCard from "@/components/LampCard/index";
import LampButton from "@/components/LampButton/index";
export default {
  name: "Service",
  components: { LampCard, LampButton },
  props: {
    tutorial: {
      type: Object,
      required: true,
    },
  },
  computed: {
    serviceList() {
      return this.$store.state.service.serviceList;
    },
    device() {
      return this.$store.state.app.device;
    },
    member() {
      return this.$store.state.member.member;
    },
  },
  created() {},
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
.container {
  display: flex;
  width: 100%;
  flex-wrap: wrap;
  color: #566a7f;
  line-height: 1.1;
  padding: 2rem;
}

@media (min-width: 768px) {
  .welcome-container {
    width: 40%;
  }

  .info-container {
    margin-left: 40px;
    width: calc(60% - 40px);
  }
}

@media (max-width: 768px) {
  .welcome-container {
    width: 100%;
    height: 40%;
  }

  .info-container {
    width: 100%;
    height: 60%;
    margin-top: 2rem;
  }
}

.menu {
  position: relative;
  padding: 10px;
  cursor: pointer;
  background-color: #e5f5ff;
  width: 50px;
  height: 50px;
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 4px;
}

.menu:hover {
  background-color: #d4e4ff;
}

.menu:hover .tooltip {
  opacity: 1;
  visibility: visible;
}
</style>
