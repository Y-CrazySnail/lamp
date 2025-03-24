<template>
  <div style="width: 100%">
    <div style="display: flex; justify-content: center; align-items: center">
      <div :style="noStyle">
        {{ info.no }}
      </div>
      <div style="margin-left: 0.8rem">
        <div :style="titleStyle">
          {{ info.title }}
        </div>
        <div
          style="
            margin-top: 0.2rem;
            font-size: 0.75rem;
            font-weight: 400;
            color: #a1acb8;
          "
        >
          {{ info.description }}
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { title } from "@/settings";

export default {
  name: "Step",
  computed: {
    member() {
      return this.$store.state.member.member;
    },
    noStyle() {
      if (this.info.state == "process") {
        return {
          width: "2.5rem",
          height: "2.5rem",
          backgroundColor: "#4687ff",
          color: "#fff",
          borderRadius: "0.375rem",
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
          boxShadow: "0 0.1875rem 0.375rem 0 rgba(0, 108, 255, 0.4)",
        };
      } else if (this.info.state == "finish") {
      } else if (this.info.state == "todo") {
        return {
          width: "2.5rem",
          height: "2.5rem",
          backgroundColor: "rgba(67, 89, 113, 0.16)",
          color: "#697a8d",
          borderRadius: "0.375rem",
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
        };
      }
    },
    titleStyle() {
      if (this.info.state == "process") {
        return {
          color: "#4687ff",
          fontSize: "0.9rem",
          lineHeight: "1",
          fontWeight: "600",
        };
      } else if (this.info.state == "finish") {
      } else if (this.info.state == "todo") {
        return {
          color: "#697a8d",
          fontSize: "0.9rem",
          lineHeight: "1",
          fontWeight: "600",
        };
      }
    },
  },
  props: {
    info: {
      type: Object,
      required: true,
    },
  },
  created() {},
  methods: {},
};
</script>

<style lang="scss" scoped>
</style>
