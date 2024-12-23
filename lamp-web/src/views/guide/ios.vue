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
        <span> 点右上角头像 </span>
        <br />
        <img src="./stash-avatar.jpg" style="width: 230px; margin-top: 5px" />
      </el-card>
      <el-card style="margin-top: 10px">
        <span style="font-weight: 600"> 第三步： </span>
        <span> 滑动至当前页面最下方、点击【退出账号】 </span>
        <br />
        <img src="./stash-logout.jpg" style="width: 230px; margin-top: 5px" />
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
        <img src="./stash-other.jpg" style="width: 230px; margin-top: 5px" />
      </el-card>
      <el-card style="margin-top: 10px">
        <span style="font-weight: 600"> 第六步： </span>
        <span> 选择【不升级】 </span>
        <br />
        <img
          src="./stash-no-upgrade.jpg"
          style="width: 230px; margin-top: 5px"
        />
      </el-card>
      <el-card style="margin-top: 10px">
        <span style="font-weight: 600"> 第七步： </span>
        <span> 点击【App】 </span>
        <br />
        <img src="./stash-app.jpg" style="width: 230px; margin-top: 5px" />
      </el-card>
      <el-card style="margin-top: 10px">
        <span style="font-weight: 600"> 第八步： </span>
        <span> 搜索、下载Stash </span>
        <br />
        <img src="./stash-download.jpg" style="width: 230px; margin-top: 5px" />
      </el-card>
      <el-card style="margin-top: 10px">
        <span style="font-weight: 600"> 第九步： </span>
        <span> 授权点击【无线局域网与蜂窝网络】</span>
        <br />
        <img src="./stash-allow.jpg" style="width: 230px; margin-top: 5px" />
      </el-card>
      <el-card style="margin-top: 10px">
        <span style="font-weight: 600"> 第十步： </span>
        <el-button
          style="background-color: #000000; border-color: #000000; color: white"
          type="info"
          size="mini"
          @click="importConfig"
          plain
        >
          一键导入
        </el-button>
      </el-card>
      <el-card style="margin-top: 10px">
        <span style="font-weight: 600"> 第十一步： </span>
        <span>启用</span>
        <br />
        <img src="./stash-run.jpg" style="width: 230px; margin-top: 5px" />
      </el-card>
      <el-card style="margin-top: 10px">
        <span style="font-weight: 600"> 第十二步： </span>
        <span>授权-【允许】</span>
        <br />
        <img src="./stash-allow-v.jpg" style="width: 230px; margin-top: 5px" />
      </el-card>
      <div style="text-align: center; margin-top: 10px">
        <el-button
          style="background-color: black; border-color: black; color: white"
          type="info"
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
        <span>【策略】-【节点选择】 指定节点</span>
        <br />
        <img src="./stash-select.jpg" style="width: 230px; margin-top: 5px" />
      </el-card>
      <el-card style="margin-top: 10px">
        <span style="font-weight: 600"> 更新订阅： </span>
        <span>点击配置文件-向左滑动-点击【更新】</span>
        <br />
        <img
          src="./stash-update-pre.jpg"
          style="width: 230px; margin-top: 5px"
        />
        <br />
        <img src="./stash-update.jpg" style="width: 230px; margin-top: 5px" />
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
          "http://aladdinslamp.cc:80/server/subscribe/clash/" +
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
