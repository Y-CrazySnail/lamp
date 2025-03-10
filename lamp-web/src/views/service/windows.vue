<template>
  <div>
    <div style="display: flex; justify-content: center">
      <div style="width: calc(100vw - 250px)">
        <div style="display: flex; flex-wrap: wrap; margin: 20px 0">
          <welcome
            style="width: calc(65% - 30px); height: 300px; margin-right: 30px"
          />
          <lamp-service style="width: calc(35%); height: 300px" />
        </div>
      </div>
    </div>
    <div style="display: flex; justify-content: center">
      <div style="width: calc(100vw - 250px)">
        <div style="display: flex; flex-wrap: wrap; margin: 20px 0">
          <mail-box
            style="width: calc(45% - 30px); height: 400px; margin-right: 30px"
          ></mail-box>
          <reward style="width: calc(55%); height: 400px" />
        </div>
      </div>
    </div>
    <div
      style="
        display: flex;
        justify-content: center;
        margin-top: 1rem;
        margin-bottom: 50px;
      "
    >
      <div
        style="
          width: calc(100vw - 250px);
          display: flex;
          justify-content: space-between;
        "
      >
        <div
          style="width: 22%"
          v-for="tutorial in tutorialList"
          :key="tutorial.title"
        >
          <Guide style="height: 100%; height: 180px" :tutorial="tutorial">
            1
          </Guide>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import VueQr from "vue-qr";
import Footer from "@/views/footer";
import LampButton from "@/components/LampButton/index";
import Welcome from "./Welcome.vue";
import Service from "./Service.vue";
import MailBox from "./MailBox.vue";
import Reward from "./Reward.vue";
import Guide from "./Guide.vue";
export default {
  name: "Service",
  components: {
    VueQr,
    "v-footer": Footer,
    LampButton,
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
  data() {
    return {
      tutorialList: [
        {
          title: "Windows",
          description: "Windows 10 或更高版本",
          logo: require("@/assets/Windows.png"),
        },
        {
          title: "Android",
          description: "Android 9.0 或更高版本",
          logo: require("@/assets/Android.png"),
        },
        {
          title: "iOS",
          description: "iOS 12 或更高版本",
          logo: require("@/assets/IOS.png"),
        },
        {
          title: "Mac OS",
          description: "MacOS 10.14 或更高版本",
          logo: require("@/assets/Mac.png"),
        },
      ],
    };
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
          "http://sub.alamp.cc:50230/server/subscribe/clash/" +
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
    test() {
      console.log(123);
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
