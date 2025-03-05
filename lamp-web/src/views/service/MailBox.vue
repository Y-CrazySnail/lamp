<template>
  <div style="display: flex; width: 100%">
    <div style="width: 35%; height: 100%">
      <div
        style="
          color: #697a8d;
          font-weight: 500;
          font-size: 1.125rem;
          line-height: 1.1;
        "
      >
        信箱服务
      </div>
      <div
        style="
          color: #697a8d;
          font-size: 1rem;
          line-height: 1.1;
          margin-top: 60px;
          font-size: 14px;
        "
      >
        如果有使用上的疑问，请联系在线客服;
      </div>
      <div
        style="
          color: #697a8d;
          font-size: 1rem;
          line-height: 1.1;
          margin-top: 10px;
          font-size: 14px;
        "
      >
        如果支付过程中遇到问题，请联系在线客服;
      </div>
    </div>
    <div
      style="width: 65%; height: 100%; overflow-y: auto; scrollbar-width: none"
    >
      <div
        style="
          height: 80px;
          width: 100%;
          background-color: #fff;
          border-left: 2px solid #4687ff;
          margin: 0 0 20px 0;
        "
        class="card"
      >
        <div
          style="
            width: 100%;
            display: flex;
            justify-content: space-between;
            align-items: center;
          "
        >
          <div style="padding-left: 20px">
            <div>
              <span style="color: #4687ff">[ 系统通知 ]</span>
              <span style="color: rgb(105, 122, 141); margin-left: 5px"
                >生效成功</span
              >
            </div>
            <div
              style="font-size: 85%; color: rgb(105, 122, 141); margin-top: 8px"
            >
              订单 ID:W2503011839YJQB 生效成功,到期时...
            </div>
          </div>
          <a style="padding-right: 20px; color: #4687ff">查看详情</a>
        </div>
      </div>
      <div
        style="
          height: 80px;
          width: 100%;
          background-color: #fff;
          border-left: 2px solid #4687ff;
          margin: 0 0 20px 0;
        "
        class="card"
      >
        <div
          style="
            width: 100%;
            display: flex;
            justify-content: space-between;
            align-items: center;
          "
        >
          <div style="padding-left: 20px">
            <div>
              <span style="color: #4687ff">[ 系统通知 ]</span>
              <span style="color: rgb(105, 122, 141); margin-left: 5px"
                >生效成功</span
              >
            </div>
            <div
              style="font-size: 85%; color: rgb(105, 122, 141); margin-top: 8px"
            >
              订单 ID:W2503011839YJQB 生效成功,到期时...
            </div>
          </div>
          <a style="padding-right: 20px; color: #4687ff">查看详情</a>
        </div>
      </div>
      <div
        style="
          height: 80px;
          width: 100%;
          background-color: #fff;
          border-left: 2px solid #4687ff;
          margin: 0 0 20px 0;
        "
        class="card"
      >
        <div
          style="
            width: 100%;
            display: flex;
            justify-content: space-between;
            align-items: center;
          "
        >
          <div style="padding-left: 20px">
            <div>
              <span style="color: #4687ff">[ 系统通知 ]</span>
              <span style="color: rgb(105, 122, 141); margin-left: 5px"
                >生效成功</span
              >
            </div>
            <div
              style="font-size: 85%; color: rgb(105, 122, 141); margin-top: 8px"
            >
              订单 ID:W2503011839YJQB 生效成功,到期时...
            </div>
          </div>
          <a style="padding-right: 20px; color: #4687ff">查看详情</a>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import LampCard from "@/components/LampCard/index";
export default {
  name: "Service",
  components: { LampCard },
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
