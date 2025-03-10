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
            <div style="font-size: 1.6rem; font-weight: 600; line-height: 1.2; margin-top: 5px;">
              ¥ 30
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
          <div class="menu">
            <div class="tooltip">会员商店</div>
            <svg
              t="1740846608381"
              style="width: 30px; height: 30px"
              viewBox="0 0 1024 1024"
              version="1.1"
              xmlns="http://www.w3.org/2000/svg"
              p-id="1405"
              width="200"
              height="200"
            >
              <path
                d="M346.112 806.912q19.456 0 36.864 7.168t30.208 19.968 20.48 30.208 7.68 36.864-7.68 36.864-20.48 30.208-30.208 20.48-36.864 7.68q-20.48 0-37.888-7.68t-30.208-20.48-20.48-30.208-7.68-36.864 7.68-36.864 20.48-30.208 30.208-19.968 37.888-7.168zM772.096 808.96q19.456 0 37.376 7.168t30.72 19.968 20.48 30.208 7.68 36.864-7.68 36.864-20.48 30.208-30.72 20.48-37.376 7.68-36.864-7.68-30.208-20.48-20.48-30.208-7.68-36.864 7.68-36.864 20.48-30.208 30.208-19.968 36.864-7.168zM944.128 227.328q28.672 0 44.544 7.68t22.528 18.944 6.144 24.064-3.584 22.016-13.312 37.888-22.016 62.976-23.552 68.096-18.944 53.248q-13.312 40.96-33.28 56.832t-49.664 15.872l-35.84 0-65.536 0-86.016 0-96.256 0-253.952 0 14.336 92.16 517.12 0q49.152 0 49.152 41.984 0 20.48-9.728 35.328t-38.4 14.848l-49.152 0-94.208 0-118.784 0-119.808 0-99.328 0-55.296 0q-20.48 0-34.304-9.216t-23.04-24.064-14.848-32.256-8.704-32.768q-1.024-6.144-5.632-29.696t-11.264-58.88-14.848-78.848-16.384-87.552q-19.456-103.424-44.032-230.4l-76.8 0q-15.36 0-25.6-7.68t-16.896-18.432-9.216-23.04-2.56-22.528q0-20.48 13.824-33.792t37.376-13.312l21.504 0 21.504 0 25.6 0 34.816 0q20.48 0 32.768 6.144t19.456 15.36 10.24 19.456 5.12 17.408q2.048 8.192 4.096 23.04t4.096 30.208q3.072 18.432 6.144 38.912l700.416 0zM867.328 194.56l-374.784 0 135.168-135.168q23.552-23.552 51.712-24.064t51.712 23.04z"
                fill="#4687ff"
                p-id="1406"
              ></path>
            </svg>
          </div>
          <div class="menu" style="margin-left: 50px">
            <div class="tooltip">增值服务</div>
            <svg
              t="1740846966244"
              style="width: 30px; height: 30px"
              viewBox="0 0 1024 1024"
              version="1.1"
              xmlns="http://www.w3.org/2000/svg"
              p-id="1465"
              width="200"
              height="200"
            >
              <path
                d="M805.4 928.9H218.6c-42.5 0-78.8-30.2-85.5-72.2L70.1 486c-6.1-37.9 11.8-74.2 46.1-91.6 34.3-17.4 74.2-11.8 101.4 15.9l82.9 82.9L470 332.9l-41-40.4c-43.5-45.6-43-118.8 1.5-163.3s117.8-45.6 163.3-1.5l0.5 0.5c22 22 34.3 51.2 34.3 82.4S616.3 271 594.3 293L554 333.4l169.5 160.3 82.9-82.9c27.1-27.1 67.1-33.8 101.4-15.9 34.3 17.4 52.2 53.8 46.1 91.6l-63 371.2c-6.7 40.4-43 71.2-85.5 71.2z"
                fill="#4687ff"
                p-id="1466"
              ></path>
            </svg>
          </div>
          <div class="menu" style="margin-left: 50px">
            <div class="tooltip">订单中心</div>
            <svg
              t="1740847066847"
              style="width: 30px; height: 30px"
              viewBox="0 0 1024 1024"
              version="1.1"
              xmlns="http://www.w3.org/2000/svg"
              p-id="1496"
              width="200"
              height="200"
            >
              <path
                d="M642.3552 155.19288889H380.12017778c-0.68266667 0-1.25155555-0.55751111-1.25155556-1.25155556V42.97386667c0-0.68266667 0.55751111-1.25155555 1.25155556-1.25155556h262.2464c0.68266667 0 1.25155555 0.55751111 1.25155555 1.25155556v110.96746666c-0.01137778 0.68266667-0.56888889 1.25155555-1.26293333 1.25155556z"
                fill="#4687ff"
                p-id="1497"
              ></path>
              <path
                d="M780.91377778 114.76764445h-103.05991111c-1.52462222 37.2736-32.26737778 67.35644445-69.91644445 67.35644444h-193.42222222c-37.62631111 0-68.38044445-30.08284445-69.91644445-67.35644444h-103.03715555c-56.97991111 0-103.58328889 46.61475555-103.58328889 103.58328888v587.04782222c0 56.96853333 46.60337778 103.58328889 103.58328889 103.5832889h539.35217778c56.97991111 0 103.58328889-46.61475555 103.58328889-103.5832889V218.35093333c0-56.97991111-46.60337778-103.58328889-103.58328889-103.58328888zM705.536 694.94328889H316.93937778c-14.31324445 0-25.89582222-11.58257778-25.89582223-25.89582222s11.58257778-25.89582222 25.89582223-25.89582222H705.536c14.31324445 0 25.89582222 11.58257778 25.89582222 25.89582222s-11.58257778 25.89582222-25.89582222 25.89582222z m0-157.86666667H316.93937778c-14.31324445 0-25.89582222-11.58257778-25.89582223-25.89582222s11.58257778-25.89582222 25.89582223-25.89582222H705.536c14.31324445 0 25.89582222 11.58257778 25.89582222 25.89582222s-11.58257778 25.89582222-25.89582222 25.89582222z m0-157.85528889H316.93937778c-14.31324445 0-25.89582222-11.58257778-25.89582223-25.89582222 0-14.31324445 11.58257778-25.89582222 25.89582223-25.89582222H705.536c14.31324445 0 25.89582222 11.58257778 25.89582222 25.89582222 0 14.31324445-11.58257778 25.89582222-25.89582222 25.89582222z"
                fill="#4687ff"
                p-id="1498"
              ></path>
            </svg>
          </div>
        </div>
      </div>
    </div>
  </lamp-card>
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
