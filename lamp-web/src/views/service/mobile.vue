<template>
  <div
    v-show="device === 'mobile'"
    style="background-color: #f5f5f8; width: 100vw; padding: 0; margin: 0"
  >
    <div style="width: calc(100%-2rem); padding: 1rem">
      <welcome class="welcome" />
    </div>
    <div style="width: calc(100%-2rem); padding: 1rem">
      <lamp-service class="service" />
    </div>
    <div style="width: calc(100%-2rem); padding: 1rem">
      <mail-box class="mail-box"></mail-box>
    </div>
    <div style="width: calc(100%-2rem); padding: 1rem">
      <reward class="reward" />
    </div>
    <div style="width: calc(100%-2rem); padding: 1rem">
      <div
        style="width: 100%; margin-bottom: 20px;"
        v-for="tutorial in tutorialList"
        :key="tutorial.title"
        @click="toGuide(tutorial.url)"
      >
        <Guide style="height: 100%; height: 180px" :tutorial="tutorial">
        </Guide>
      </div>
    </div>
  </div>
</template>

<script>
import Welcome from "./Welcome.vue";
import VueQr from "vue-qr";
import Service from "./Service.vue";
import MailBox from "./MailBox.vue";
import Reward from "./Reward.vue";
import Guide from "./Guide.vue";
export default {
  name: "Service",
  components: {
    VueQr,
    Welcome,
    "lamp-service": Service,
    MailBox,
    Reward,
    Guide,
  },
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
  data() {
    return {
      tutorialList: [
        {
          title: "Windows",
          description: "Windows 10 或更高版本",
          logo: require("@/assets/Windows.png"),
          url: "/windows/index",
        },
        {
          title: "Android",
          description: "Android 9.0 或更高版本",
          logo: require("@/assets/Android.png"),
          url: "/android/index",
        },
        {
          title: "iOS",
          description: "iOS 12 或更高版本",
          logo: require("@/assets/IOS.png"),
          url: "/ios/index",
        },
        {
          title: "Mac OS",
          description: "MacOS 10.14 或更高版本",
          logo: require("@/assets/Mac.png"),
          url: "/mac/index",
        },
      ],
    };
  },
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
          "http://sub.alamp.cc:50230/server/subscribe/clash/" +
            this.member.referralCode
        ) +
        "&name=" +
        encodeURIComponent("alamp.cc");
      window.location.href = url;
    },
    toGuide(url) {
      this.$router.push(url);
    },
  },
};
</script>

<style lang="scss" scoped>
@media (max-width: 768px) {
  .welcome {
    width: 100%;
    height: 560px;
    margin-right: 30px;
  }
}

@media (min-width: 768px) {
  .welcome {
    width: 100%;
    height: 300px;
    margin-right: 30px;
  }
}

.service {
  width: 100%;
  height: 300px;
}

.mail-box {
  width: 100%;
  height: 400px;
}

.reward {
  width: 100%;
  height: 430px;
}

.flex-center {
  display: flex;
  justify-content: center;
  align-items: center;
}

.container-card {
  width: 83vw;
  height: 52vw;
  margin: 2vw;
  background-color: #fff;
  border-radius: 8px;
}

.container-card-title {
  height: 10vw;
  font-weight: 600;
  font-size: 3.1vw;
  display: flex;
  justify-content: center;
  align-items: center;
}

.container-card-item {
  height: 7vw;
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 3.1vw;
}

.container-card-input {
  font-size: 3.1vw;
  width: 30vw;
  height: 1vw;
}

.container-card-button {
  background-color: #0071e3;
  border-color: #0071e3;
  color: white;
  margin-left: 10px;
  padding: 1.2vw;
  font-size: 3vw;
}

.container {
  background-color: #f1f1f1;
  margin: 20px 30px 0 30px;
  border-radius: 3px;
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
    margin-right: 10px;
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
  margin-left: 10px;

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
