<template>
  <div class="btn btn-primary" @click="handleClick" style="display: flex; align-items: center; justify-content: center;">
    <slot></slot>
  </div>
</template>

<script>
export default {
  name: "LampButton",
  props: {
    iconClass: {
      type: String,
      required: false,
    },
    className: {
      type: String,
      required: false,
      default: "",
    },
  },
  computed: {},
  methods: {
    handleClick(e) {
      this.$emit("click", e);
    },
  },
};
</script>

<style>
</style>
