<template>
  <div>
    <div style="margin: 30px">
      <el-steps :space="300" :active="active" finish-status="success">
        <el-step title="配置"></el-step>
        <el-step title="进阶"></el-step>
      </el-steps>
    </div>
    <div style="margin: 20px" v-show="active == 0">
      <el-card>
        <span style="font-weight: 600"> 第一步： </span>
        <span>下载Clash软件</span>
        <br />
        <div style="margin-top: 10px">
          地址：https://www.clashverge.dev/install.html
          <el-button
            style="background-color: black; border-color: black; color: white"
            type="info"
            size="mini"
            v-clipboard:copy="'https://www.clashverge.dev/install.html'"
            v-clipboard:success="onCopySuccess"
            plain
          >
            复制
          </el-button>
          <el-button
            style="background-color: black; border-color: black; color: white"
            type="info"
            size="mini"
            plain
            @click="download()"
          >
            下载
          </el-button>
        </div>
      </el-card>
      <el-card>
        <span style="font-weight: 600"> 第二步： </span>
        <span>安装Clash软件</span>
      </el-card>
      <el-card style="margin-top: 10px">
        <span style="font-weight: 600"> 第三步： </span>
        <el-button
          style="background-color: #000000; border-color: #000000; color: white"
          type="info"
          size="mini"
          @click="importConfig"
          plain
        >
          一键导入
        </el-button>
        点击当前教程页【一键导入】按钮（点击后弹出尝试打开clash软件）
      </el-card>
      <el-card style="margin-top: 10px">
        <span style="font-weight: 600"> 第四步： </span>
        <span>点击【订阅】-选中导入的订阅，选中后变为蓝色</span>
        <br />
        <img src="./win-select.jpg" style="width: 40vw; margin-top: 5px" />
      </el-card>
      <el-card style="margin-top: 10px">
        <span style="font-weight: 600"> 第五步：</span>
        <span>
          点击【设置】-开启代理（这个【系统代理】是开关，不使用的时候在这里关闭）
        </span>
        <br />
        <img src="./win-run.jpg" style="width: 40vw; margin-top: 5px" />
      </el-card>
      <div style="text-align: center; margin-top: 10px">
        <el-button
          type="info"
          style="background-color: #000000; border-color: #000000; color: white"
          plain
          @click="active = 1"
        >
          下一步
          <i class="el-icon-arrow-right el-icon--right"> </i>
        </el-button>
      </div>
    </div>
    <div style="margin: 30px" v-show="active == 1">
      <el-card style="margin-top: 10px">
        <span style="font-weight: 600"> 指定节点： </span>
        <span>点击【代理】-展开【节点选择】 -指定节点</span>
        <br />
        <img src="./win-proxy.jpg" style="width: 40vw; margin-top: 5px" />
      </el-card>
      <el-card style="margin-top: 10px">
        <span style="font-weight: 600"> 更新订阅： </span>
        <span>点击【订阅】-更新图标</span>
        <br />
        <img src="./win-update.jpg" style="width: 40vw; margin-top: 5px" />
      </el-card>
      <div style="text-align: center; margin-top: 10px">
        <el-button
          style="background-color: black; border-color: black; color: white"
          type="info"
          icon="el-icon-arrow-left"
          @click="active = 0"
          plain
        >
          上一步
        </el-button>
      </div>
    </div>
  </div>
</template>

<script>
import VueQr from "vue-qr";
export default {
  name: "Package",
  components: { VueQr },
  mounted() {
    const loading = this.$loading({
      lock: true,
      text: "加载中...",
      spinner: "el-icon-loading",
    });
    this.$store.dispatch("member/get").then((res) => {
      loading.close();
    });
  },
  data() {
    return {
      active: 0,
    };
  },
  computed: {
    member() {
      return this.$store.state.member.member;
    },
  },
  methods: {
    onCopySuccess() {
      this.$message.success("已复制至剪切板");
    },
    importConfig() {
      let url =
        "clash://install-config?url=" +
        encodeURIComponent(
          "http://aladdinslamp.cc:80/server/subscribe/clash/" +
            this.member.uuid
        ) +
        "&name=" +
        encodeURIComponent("aladdinslamp.cc");
      window.location.href = url;
    },
    download() {
      window.open("https://www.clashverge.dev/install.html", "_blank");
    },
  },
};
</script>

<style lang="scss">
.discription-lable {
  width: 80px;
}
</style>
