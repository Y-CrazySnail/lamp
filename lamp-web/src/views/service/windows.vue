<template>
  <div
    v-show="device !== 'mobile'"
    style="background-color: #f5f5f8; width: 100vw; min-height: 100vh"
  >
    <div style="width: 100%; height: 21vw; display: flex; flex-wrap: wrap">
      <div class="container-card">
        <div class="container-card-title">账户</div>
        <div class="hr-like" />
        <div class="container-card-item">
          <div style="margin-left: 20px; font-weight: 600">账号</div>
          <div style="margin-right: 20px; font-weight: 600">
            {{ member.email }}
          </div>
        </div>
        <div class="container-card-item">
          <div style="margin-left: 20px; font-weight: 600">密码</div>
          <div style="margin-right: 20px">
            <el-input
              class="container-card-input"
              size="mini"
              placeholder="请输入密码"
              v-model="member.password"
              show-password
            ></el-input>
            <el-button
              class="container-card-button"
              type="info"
              size="mini"
              @click="updatePassword"
              plain
            >
              修改密码
            </el-button>
          </div>
        </div>
        <div class="container-card-item">
          <div style="margin-left: 20px; font-weight: 600">余额</div>
          <div style="margin-right: 20px; font-weight: 600">
            ¥ {{ member.balance }}
          </div>
        </div>
        <div class="container-card-item">
          <div style="margin-left: 20px; font-weight: 600">推荐</div>
          <div style="margin: 0 20px; color: #666" class="flex-center">
            <span style="margin-right: 3px; font-size: 0.8vw">
              {{
                "https://alamp.cc/signup?referrerCode=" + member.referralCode
              }}
            </span>
            <el-button
              class="container-card-button"
              size="mini"
              plain
              v-clipboard:copy="
                'https://alamp.cc/signup?referrerCode=' + member.referralCode
              "
              v-clipboard:success="onCopySuccess"
            >
              复制
            </el-button>
          </div>
        </div>
        <div class="container-card-item">
          <div style="margin: 0 20px; color: #333; font-size: 0.8vw">
            推荐奖励：推荐新用户注册购买服务后，即可获得消费金额15%的奖励，后续您推荐的用户续费时，您依然可以享受15%的奖励。
          </div>
        </div>
      </div>
      <div class="container-card">
        <div class="container-card-title">服务</div>
        <div class="hr-like" />
        <div class="container-card-item">
          <div style="margin-left: 20px; font-weight: 600">本月流量</div>
          <div style="margin-right: 20px; font-weight: 600">
            {{
              parseFloat(Number(member.bandwidth) / 1024 / 1024 / 1024).toFixed(
                2
              )
            }}GB<span style="color: #5f5f5f">（每月1号0点刷新流量）</span>
          </div>
        </div>
        <div class="container-card-item">
          <div style="margin-left: 20px; font-weight: 600">本月剩余</div>
          <div style="margin-right: 20px; font-weight: 600">
            {{
              parseFloat(
                (Number(member.monthBandwidth) -
                  (Number(member.monthBandwidthDown) +
                    Number(member.monthBandwidthUp))) /
                  1024 /
                  1024 /
                  1024
              ).toFixed(2)
            }}GB
          </div>
        </div>
        <div class="container-card-item">
          <div style="margin-left: 20px; font-weight: 600">到期时间</div>
          <div style="margin-right: 20px; font-weight: 600">
            {{ member.expiryDate }}
          </div>
        </div>
        <div class="container-card-item">
          <div style="margin-left: 20px; font-weight: 600">Clash订阅</div>
          <div style="margin-right: 20px; font-weight: 600">
            <el-button
              class="container-card-button"
              size="mini"
              v-clipboard:copy="
                'http://alamp.cc:80/server/subscribe/clash/' +
                member.referralCode
              "
              v-clipboard:success="onCopySuccess"
            >
              复制
            </el-button>
            <el-button
              class="container-card-button"
              size="mini"
              @click="importConfig"
            >
              一键导入
            </el-button>
          </div>
        </div>
        <div class="container-card-item">
          <div
            style="margin: 0 20px; width: 100%; color: #888; font-size: 0.8vw"
          >
            http://alamp.cc:80/server/subscribe/clash/{{ member.referralCode }}
          </div>
        </div>
      </div>
      <div class="container-card">
        <div class="container-card-title">教程</div>
        <div class="hr-like" />
        <div class="container-card-item">
          <div style="margin-left: 20px; font-weight: 600">iPhone/iPad</div>
          <div style="margin-right: 20px; font-weight: 600">
            <a
              @click="toIOS()"
              style="color: #0071e3; text-decoration: underline"
              >配置教程</a
            >
            <a
              style="
                margin-left: 30px;
                color: #0071e3;
                text-decoration: underline;
              "
            >
              更新订阅教程
            </a>
          </div>
        </div>
        <div class="container-card-item">
          <div style="margin-left: 20px; font-weight: 600">Mac</div>
          <div style="margin-right: 20px; font-weight: 600">
            <a
              @click="toMac()"
              style="color: #0071e3; text-decoration: underline"
              >配置教程</a
            >
            <a
              style="
                margin-left: 30px;
                color: #0071e3;
                text-decoration: underline;
              "
              >更新订阅教程
            </a>
          </div>
        </div>
        <div class="container-card-item">
          <div style="margin-left: 20px; font-weight: 600">Windows</div>
          <div style="margin-right: 20px; font-weight: 600">
            <a
              @click="toWindows()"
              style="color: #0071e3; text-decoration: underline"
              >配置教程</a
            >
            <a
              style="
                margin-left: 30px;
                color: #0071e3;
                text-decoration: underline;
              "
              >更新订阅教程
            </a>
          </div>
        </div>
        <div class="container-card-item">
          <div style="margin-left: 20px; font-weight: 600">安卓</div>
          <div style="margin-right: 20px; font-weight: 600">
            <a
              @click="toAndroid()"
              style="color: #0071e3; text-decoration: underline"
              >配置教程</a
            >
            <a
              style="
                margin-left: 30px;
                color: #0071e3;
                text-decoration: underline;
              "
              >更新订阅教程
            </a>
          </div>
        </div>
      </div>
    </div>
    <div style="width: 100%; height: 30vw">
      <div
        style="
          width: 50%;
          height: 25vw;
          margin: 2vw;
          background-color: #fff;
          border-radius: 8px;
          overflow-y: auto;
        "
      >
        <div class="container-card-title">通知</div>
        <div class="hr-like" />
        <div v-for="item in noticeList" :key="item.id">
          <div class="container-card-item">
            <div style="margin-left: 20px; font-weight: 600">
              {{ item.title }}
            </div>
            <div style="margin-right: 20px; font-weight: 400; color: #333">
              {{ item.content }}
            </div>
          </div>
          <div class="hr-like" />
        </div>
      </div>
    </div>
    <v-footer />
  </div>
</template>

<script>
import VueQr from "vue-qr";
import Footer from "@/views/footer";
export default {
  name: "Service",
  components: { VueQr, "v-footer": Footer, },
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
  data() {
    return {};
  },
  created() {},
  methods: {
    onCopySuccess() {
      this.$message.success("已复制至剪切板");
    },
    updatePassword() {
      let param = {
        password: this.member.password,
      };
      this.$store.dispatch("member/updatePassword", param).then((res) => {
        this.$message.success("修改密码成功");
      });
    },
    importConfig() {
      let url =
        "clash://install-config?url=" +
        encodeURIComponent(
          "http://alamp.cc:80/server/subscribe/clash/" +
            this.member.referralCode
        ) +
        "&name=" +
        encodeURIComponent("alamp.cc");
      window.location.href = url;
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
.flex-center {
  display: flex;
  justify-content: center;
  align-items: center;
}

.container-card {
  width: 28vw;
  height: 16vw;
  margin: 2vw;
  background-color: #fff;
  border-radius: 8px;
}

.container-card-title {
  height: 3vw;
  font-weight: 600;
  display: flex;
  justify-content: center;
  align-items: center;
}

.container-card-item {
  height: 2vw;
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 0.8vw;
}

.container-card-input {
  font-size: 0.8vw;
  width: 10vw;
  height: 1vw;
}

.container-card-button {
  background-color: #0071e3;
  border-color: #0071e3;
  color: white;
  margin-left: 10px;
  font-size: 0.7vw;
}

.container {
  background-color: #f7f7f7;
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
  display: block;
  width: 100%;
  height: 1px;
  text-align: center;
  background: linear-gradient(to right, #f0f0f0, #e0e0e0, #f0f0f0);
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
