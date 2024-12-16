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
          地址：
          <el-button
            style="background-color: black; border-color: black; color: white"
            type="info"
            size="mini"
            v-clipboard:copy="
              'https://down.clashcn.com/soft/clashcn.com_cmfa-2.10.1-meta-universal-release.apk'
            "
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
        <span>点击【配置】</span>
        <br />
        <img src="./android-config.jpg" style="width: 230px; margin-top: 5px" />
      </el-card>
      <el-card style="margin-top: 10px">
        <span style="font-weight: 600"> 第四步：</span>
        <span>点击右上角➕</span>
        <br />
        <img src="./android-add.jpg" style="width: 230px; margin-top: 5px" />
      </el-card>
      <el-card style="margin-top: 10px">
        <span style="font-weight: 600"> 第五步： </span>
        <span>点击【从URL导入】</span>
        <br />
        <img
          src="./android-url-import.jpg"
          style="width: 230px; margin-top: 5px"
        />
      </el-card>
      <el-card style="margin-top: 10px">
        <span style="font-weight: 600"> 第六步： </span>
        <br />
        <span>URL-填入订阅地址</span>
        <el-button
          style="
            background-color: black;
            border-color: black;
            color: white;
            margin-left: 5px;
          "
          type="info"
          size="mini"
          v-clipboard:copy="
            'http://aladdinslamp.cc:80/server/subscribe/clash/' + service.uuid
          "
          v-clipboard:success="onCopySuccess"
          plain
        >
          复制
        </el-button>
        <br />
        <span style="margin-top: 5px">自动更新-选择15分钟</span>
        <br />
        <span style="margin-top: 5px">完成后点击右上角【保存】</span>
        <br />
        <img src="./android-input.jpg" style="width: 230px; margin-top: 5px" />
      </el-card>
      <el-card style="margin-top: 10px">
        <span style="font-weight: 600">第七步：</span>
        <span style="margin-top: 5px">点击-选中按钮</span>
        <br />
        <img src="./android-select.jpg" style="width: 230px; margin-top: 5px" />
      </el-card>
      <el-card style="margin-top: 10px">
        <span style="font-weight: 600">第八步：</span>
        <span style="margin-top: 5px">选中后返回主页</span>
        <br />
        <img src="./android-return.jpg" style="width: 230px; margin-top: 5px" />
      </el-card>
      <el-card style="margin-top: 10px">
        <span style="font-weight: 600">第九步：</span>
        <span style="margin-top: 5px">点击【启动】</span>
        <br />
        <img src="./android-run.jpg" style="width: 230px; margin-top: 5px" />
      </el-card>
      <el-card style="margin-top: 10px">
        <span style="font-weight: 600">第十步：</span>
        <span style="margin-top: 5px">点击【确定】</span>
        <br />
        <img src="./android-request.jpg" style="width: 230px; margin-top: 5px" />
      </el-card>
      <el-card style="margin-top: 10px">
        <span style="font-weight: 600">第十一步：</span>
        <span style="margin-top: 5px">点击【始终允许】</span>
        <br />
        <img src="./android-notify.jpg" style="width: 230px; margin-top: 5px" />
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
        <span style="font-weight: 600"> 更新订阅： </span>
        <span>点击【订阅】-更新图标</span>
        <br />
        <img src="./android-update-subscribe.jpg" style="width: 230px; margin-top: 5px" />
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
    this.$store.dispatch("service/list").then((res) => {
      loading.close();
    });
  },
  data() {
    return {
      active: 0,
    };
  },
  computed: {
    service() {
      return this.$store.state.service.serviceList[0];
    },
    serviceList() {
      return this.$store.state.service.serviceList;
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
            this.service.uuid
        ) +
        "&name=" +
        encodeURIComponent("aladdinslamp.cc");
      window.location.href = url;
    },
    download() {
      window.open(
        "https://down.clashcn.com/soft/clashcn.com_cmfa-2.10.1-meta-universal-release.apk",
        "_blank"
      );
    },
  },
};
</script>

<style lang="scss">
.discription-lable {
  width: 80px;
}
</style>
