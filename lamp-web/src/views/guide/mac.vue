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
        <span>打开App Store</span>
        <br />
        <img
          src="./stash-app-store.png"
          style="width: 100px; height: 105px; margin-top: 5px"
        />
      </el-card>
      <el-card style="margin-top: 10px">
        <span style="font-weight: 600"> 第二步： </span>
        <span> 退出当前账号，点击屏幕左上角【商店】-【退出】 </span>
        <br />
        <img src="./mac-logout.jpg" style="width: 40vw; margin-top: 5px" />
      </el-card>
      <el-card style="margin-top: 10px">
        <span style="font-weight: 600"> 第三步： </span>
        <span> 点击【登录】</span>
        <br />
        <img src="./mac-login.jpg" style="width: 40vw; margin-top: 5px" />
      </el-card>
      <el-card style="margin-top: 10px">
        <span style="font-weight: 600"> 第四步： </span>
        <span> 登录账号；输入账号密码 </span>
        <br />
        <div style="margin-top: 10px">
          {{ apple.username }}
          <el-button
            style="background-color: black; border-color: black; color: white"
            type="info"
            size="mini"
            v-clipboard:copy="apple.username"
            v-clipboard:success="onCopySuccess"
            plain
          >
            复制
          </el-button>
        </div>
        <div style="margin-top: 5px">
          密码：{{ apple.password }}
          <el-button
            style="background-color: black; border-color: black; color: white"
            type="info"
            size="mini"
            v-clipboard:copy="apple.password"
            v-clipboard:success="onCopySuccess"
            plain
          >
            复制
          </el-button>
        </div>
      </el-card>
      <el-card style="margin-top: 10px">
        <span style="font-weight: 600"> 第五步： </span>
        <span> 选择【其他选项】 </span>
        <br />
        <img src="./mac-no-verify.jpg" style="width: 40vw; margin-top: 5px" />
      </el-card>
      <el-card style="margin-top: 10px">
        <span style="font-weight: 600"> 第六步： </span>
        <span> 选择【不升级】 </span>
        <br />
        <img src="./mac-no-upgrade.jpg" style="width: 40vw; margin-top: 5px" />
      </el-card>
      <el-card style="margin-top: 10px">
        <span style="font-weight: 600"> 第七步： </span>
        <span> 搜索、下载Stash </span>
        <br />
        <img src="./mac-download.jpg" style="width: 40vw; margin-top: 5px" />
      </el-card>
      <el-card style="margin-top: 10px">
        <span style="font-weight: 600"> 第八步： </span>
        <el-button
          style="background-color: #000000; border-color: #000000; color: white"
          type="info"
          size="mini"
          @click="importConfig"
          plain
        >
          一键导入
        </el-button>
        点击当前教程页【一键导入】按钮（点击后弹出尝试打开stash软件）
      </el-card>
      <el-card style="margin-top: 10px">
        <span style="font-weight: 600"> 第九步： </span>
        <span>启用</span>
        <br />
        <img src="./mac-run.jpg" style="width: 40vw; margin-top: 5px" />
      </el-card>
      <el-card style="margin-top: 10px">
        <span style="font-weight: 600"> 第十步： </span>
        <span>授权-【允许】</span>
        <br />
        <img src="./mac-allow.png" style="width: 20vw; margin-top: 5px" />
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
        <span>展开【节点选择】 指定节点</span>
        <br />
        <img src="./mac-select.png" style="width: 40vw; margin-top: 5px" />
      </el-card>
      <el-card style="margin-top: 10px">
        <span style="font-weight: 600"> 更新订阅： </span>
        <span>点击配置文件-右键点击订阅-【立即更新】</span>
        <br />
        <img src="./mac-txt.png" style="width: 40vw; margin-top: 5px" />
        <br />
        <img src="./mac-update.png" style="width: 40vw; margin-top: 5px" />
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
    this.$store.dispatch("service/apple").then((res) => {
      console.log(res);
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
    apple() {
      return this.$store.state.service.apple;
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
          "http://sub.alamp.cc:50230/server/subscribe/clash/" +
            this.member.uuid
        ) +
        "&name=" +
        encodeURIComponent("aladdinslamp.cc");
      window.location.href = url;
    },
  },
};
</script>

<style lang="scss">
.discription-lable {
  width: 80px;
}
</style>
