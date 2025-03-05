<template>
  <div style="padding: 0 15px" @click="toggleClick">
    <svg
      t="1740843524687"
      :class="{ 'is-active': isActive }"
      class="hamburger"
      viewBox="0 0 1024 1024"
      version="1.1"
      xmlns="http://www.w3.org/2000/svg"
      p-id="1375"
      width="200"
      height="200"
    >
      <path
        d="M128 192h768v128H128V192z m0 256h512v128H128V448z m0 256h768v128H128v-128z m576-320l192 128-192 128V384z"
        fill="#ffffff"
        p-id="1376"
      ></path>
    </svg>
  </div>
</template>

<script>
export default {
  name: "Hamburger",
  props: {
    isActive: {
      type: Boolean,
      default: false,
    },
  },
  methods: {
    toggleClick() {
      this.$emit("toggleClick");
    },
  },
};
</script>

<style scoped>
.hamburger {
  display: inline-block;
  vertical-align: middle;
  width: 20px;
  height: 20px;
}

.hamburger.is-active {
  transform: rotate(180deg);
}
</style>
