<template>
  <footer class="footer">
    <div class="footer-links">
      <a :href="link.href" v-for="link in links" :key="link.href">
        {{ link.text }}
      </a>
    </div>
    <div class="copyright">&copy; {{ currentYear }} {{ copyrightText }}</div>
  </footer>
</template>
  
  <script>
export default {
  name: "Footer",
  data() {
    return {
      links: [
        { href: "/service", text: "用户中心" },
        { href: "/order/index", text: "订单中心" },
        { href: "/package/index", text: "会员商店" },
      ],
      copyrightText: " ALamp.cc",
    };
  },
  computed: {
    currentYear() {
      return new Date().getFullYear();
    },
  },
};
</script>
  
<style scoped>
/* 页脚样式 */
.footer {
  background-color: #fff; /* 黑色背景 */
  color: #000; /* 白色文字 */
  padding: 20px 0; /* 上下内边距 */
  text-align: center; /* 文字居中 */
  font-size: 14px; /* 字体大小 */
  position: fixed; /* 固定位置 */
  bottom: 0; /* 置于底部 */
  width: 100%; /* 宽度占满整个屏幕 */
  z-index: 1000; /* 确保页脚在其他内容之上 */
}

.footer a {
  color: #000; /* 链接颜色为白色 */
  text-decoration: none; /* 去掉下划线 */
  margin: 0 10px; /* 链接之间的间距 */
}

.footer a:hover {
  text-decoration: underline; /* 鼠标悬停时显示下划线 */
}

.footer .copyright {
  margin-top: 10px; /* 版权信息与链接之间的间距 */
}
</style>