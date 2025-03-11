<template>
  <lamp-card>
    <div class="container">
      <div class="welcome-container card-separator">
        <div
          style="
            color: #697a8d;
            font-weight: 500;
            font-size: 1.125rem;
            line-height: 1.1;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 40px;
            width: 100%;
          "
        >
          账户概况
        </div>
        <div
          style="
            display: flex;
            justify-content: start;
            align-items: center;
            height: 50px;
          "
        >
          <div>
            <img src="./avatar.jpeg" class="user-avatar" />
          </div>
          <div style="margin-left: 20px">
            <span
              style="
                font-size: calc(1.325rem + 0.9vw);
                line-height: 1.1;
                color: #566a7f;
                font-weight: 500;
              "
            >
              Welcome
            </span>
          </div>
        </div>
        <div style="display: flex; margin: 20px 0">
          <span style="font-size: 1.123rem; line-height: 1.1; color: #566a7f">
            {{ member.email }}
          </span>
        </div>
        <div style="display: flex; align-items: center; margin-top: 20px">
          <div class="credit-card is-theme-1">
            <div class="shape"></div>
            <div class="top">
              <div class="card-number">
                <span></span>
              </div>
            </div>
            <div class="bottom"><span>CNY</span></div>
          </div>
          <div style="margin-left: 20px">
            <div style="font-weight: 600">账户余额</div>
            <div
              style="
                font-size: 1.6rem;
                font-weight: 600;
                line-height: 1.2;
                margin-top: 5px;
              "
            >
              ¥ {{ member.balance }}
            </div>
          </div>
        </div>
      </div>
      <div class="info-container">
        <div style="font-size: 1.125rem">🎉 欢迎来到 LampCloud</div>
        <div style="margin-top: 30px">请保留以下联系方式，以便找到我们</div>
        <div style="margin-top: 15px">永久跳转：https://alamp.cc</div>
        <div style="margin-top: 15px">官方邮箱：i.wick@tuta.io</div>
        <div style="display: flex; margin-top: 30px">
          <LampButton>
            <img src="./cart.png" style="width: 1.1rem; height: 1.1rem; margin-right: 3px;" />
            会员商店
          </LampButton>
          <LampButton style="margin-left: 10px">
            <img src="./order.png" style="width: 1.1rem; height: 1.1rem; margin-right: 3px;" />
            订单中心
          </LampButton>
        </div>
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
  computed: {
    device() {
      return this.$store.state.app.device;
    },
    member() {
      return this.$store.state.member.member;
    },
    noticeList() {
      return this.$store.state.notice.noticeList;
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
    height: 50%;
  }

  .info-container {
    width: 100%;
    height: 50%;
    margin-top: 2rem;
  }
}

.menu {
  position: relative;
  padding: 10px;
  cursor: pointer;
  width: 50px;
  height: 50px;
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 4px;
  color: #4687ff;
  border-color: transparent;
  background-color: #e5f5ff;
}

.menu:hover {
  border-color: transparent;
  box-shadow: 0 0.125rem 0.25rem 0 rgba(105, 108, 255, 0.4);
  transform: translateY(-1px);
}

.menu:hover .tooltip {
  opacity: 1;
  visibility: visible;
}

.credit-card {
  position: relative;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  height: 80px;
  width: 120px;
  min-width: 120px;
  border-radius: 10px;
  padding: 6px 10px 8px;
  overflow: hidden;
}

.credit-card.is-theme-1 {
  background-color: #4687ff;
  border-color: #4687ff;
  box-shadow: 0 0.125rem 0.25rem 0 rgba(0, 108, 255, 0.4);
}

.credit-card .shape {
  position: absolute;
  bottom: -10px;
  inset-inline-end: -10px;
  height: 46px;
  width: 46px;
  background-color: #fff;
  border-radius: 9999px;
  opacity: 0.15;
}

.credit-card .top .card-number {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.credit-card .top .card-number span {
  font-size: 0.8rem;
  font-weight: 500;
  color: var(--smoke-white);
  display: block;
}

.credit-card .top .card-number img {
  display: block;
  width: 100%;
  max-width: 28px;
}

.credit-card .bottom {
  font-size: 0.7rem;
  font-weight: 600;
  color: #fff;
}
</style>
