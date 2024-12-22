<template>
  <div v-show="device !== 'mobile'">
    <div style="display: flex">
      <div style="width: 60%; height: 100vh">
        <div class="container">
          <div class="package-container">
            <div class="infomation-container">
              <div class="infomation-title">服务信息</div>
              <div class="infomation-option" @click="toPackage">
                <img class="img-16" src="./img/renew.png" />
                <span style="margin-left: 3px; font-size: 14px">续费</span>
              </div>
            </div>
          </div>
          <div class="hr-like" />
          <div class="infomation-item">
            <img class="img-20" src="./img/data.png" />
            <span style="color: #0f0f0f" class="infomation-item-content">
              每月流量：{{
                parseFloat(
                  (Number(member.bandwidth)) /
                    1024 /
                    1024 /
                    1024
                ).toFixed(2)
              }}GB<span style="color: #5f5f5f;">（每月1号0点刷新流量）</span>
            </span>
          </div>
          <div class="infomation-item">
            <img class="img-20" src="./img/data.png" />
            <span style="color: #0f0f0f" class="infomation-item-content">
              本月剩余：{{
                parseFloat(
                  (Number(member.monthBandwidth) -
                    (Number(member.monthBandwidthDown) +
                      Number(member.monthBandwidthUp))) /
                    1024 /
                    1024 /
                    1024
                ).toFixed(2)
              }}GB
            </span>
          </div>
          <div class="infomation-item">
            <img class="img-20" src="./img/end.png" />
            <span style="color: #0f0f0f" class="infomation-item-content">
              到期时间：{{ member.expiryDate }}
            </span>
          </div>
          <div class="infomation-item">
            <img class="img-20" src="./img/clash.png" />
            <span style="color: #0f0f0f" class="infomation-item-content">
              Clash订阅地址：
              <img
                style="margin-left: 5px"
                class="img-16"
                src="./img/copy.png"
                v-clipboard:copy="
                  'http://aladdinslamp.cc:80/server/subscribe/clash/' +
                  member.uuid
                "
                v-clipboard:success="onCopySuccess"
              />
              <span
                style="
                  font-size: 12px;
                  margin-left: 5px;
                  font-weight: 600;
                  cursor: pointer;
                "
                v-clipboard:copy="
                  'http://aladdinslamp.cc:80/server/subscribe/clash/' +
                  member.uuid
                "
                v-clipboard:success="onCopySuccess"
              >
                复制
              </span>
            </span>
          </div>
          <div class="subsbription-container">
            <div class="subscription-content">
              http://aladdinslamp.cc:80/server/subscribe/clash/{{ member.uuid }}
            </div>
          </div>
          <div style="padding-bottom: 30px"></div>
        </div>
      </div>
      <div style="width: 40%; height: 100vh" v-show="member">
        <div class="guide-container" @click="toIOS()">
          <img class="img-20" src="./img/apple.png" />
          <div style="margin-left: 5px; font-size: 18px; font-weight: 600">
            iPhone/iPad 教程
          </div>
        </div>
        <div class="guide-container" @click="toMac()">
          <img class="img-20" src="./img/mac.png" />
          <div style="margin-left: 5px; font-size: 18px; font-weight: 600">
            Mac 教程
          </div>
        </div>
        <div class="guide-container" @click="toWindows()">
          <img class="img-20" src="./img/windows.png" />
          <div style="margin-left: 5px; font-size: 18px; font-weight: 600">
            Windows 教程
          </div>
        </div>
        <div class="guide-container" @click="toAndroid()">
          <img class="img-20" src="./img/android.png" />
          <div style="margin-left: 5px; font-size: 18px; font-weight: 600">
            Android 教程
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import VueQr from "vue-qr";
export default {
  name: "Service",
  components: { VueQr },
  computed: {
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
    toIOS() {
      this.$router.push(`/ios/index`);
    },
    toWindows() {
      this.$router.push(`/windows/index`);
    },
    toMac() {
      this.$router.push(`/mac/index`);
    },
    toAndroid() {
      this.$router.push(`/android/index`);
    },
  },
};
</script>

<style lang="scss" scoped>
.container {
  background-color: #fbfbfb;
  margin: 20px 30px 0 30px;
  border-radius: 3px 3px 0 0;
  border-width: 1px;
  border-color: #ffffff;
  border-style: none;
}

.package-container {
  margin: 20px 0 0 20px;
  padding: 10px 0 0 0;

  .package-title {
    font-size: 30px;
    font-weight: 500;
    padding-top: 10px;
  }

  .package-description {
    font-size: 14px;
    font-weight: 400;
    color: gray;
    margin-top: 5px;
  }
}

.infomation-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-left: 10px;

  .infomation-title {
    font-size: 17px;
    font-weight: 600;
    color: #1d1d1f;
  }

  .infomation-option {
    display: flex;
    align-items: center;
    height: 26px;
    margin-right: 20px;
  }
}

.infomation-item {
  display: flex;
  align-items: center;
  margin: 12px 0 10px 20px;

  .infomation-item-content {
    margin-left: 10px;
    font-weight: 600;
    font-size: 14px;
  }
}

.guide-container {
  display: flex;
  align-items: center;
  margin-left: 20px;

  .guide-title {
    font-size: 18px;
    font-weight: 600;
    color: #1d1d1f;
  }
}

.subsbription-container {
  margin: 5px 20px 0 20px;
  padding: 10px;
  border-radius: 8px;
  border-color: #ffffff;
  border-style: none;
  box-shadow: 8px 8px 16px 0 rgba(0, 0, 0, 0.08);
  background: #f1f1f1;

  .subscription-content {
    word-break: break-all;
    font-size: 13px;
    color: #0c0c0c;
  }
}

.img-14 {
  width: 14px;
  height: 14px;
}

.img-16 {
  width: 16px;
  height: 16px;
}

.img-20 {
  width: 20px;
  height: 20px;
}

.hr-like::before {
  content: "";
  margin: 10px 0 27px 0;
  display: block;
  width: 100%;
  height: 1px;
  text-align: center;
  background: linear-gradient(to right, #f0f0f0, #e0e0e0, #f0f0f0);
  /* 渐变色分割线 */
}

.guide-container {
  margin: 15px 30px 0 30px;
  border-radius: 4px;
  // width: 80%;
  height: 40px;
  border-color: #ffffff;
  border-style: none;
  box-shadow: 8px 8px 16px 0 rgba(0, 0, 0, 0.08);
  background: #f1f1f1;
  cursor: pointer;
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>