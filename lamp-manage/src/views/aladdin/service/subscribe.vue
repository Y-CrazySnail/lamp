<template>
  <div>
    <el-descriptions class="margin-top" title="订阅地址" :column="1" border>
      <el-descriptions-item>
        <template slot="label">
          <el-tag size="small">shadowrocket</el-tag>
        </template>
        http://aladdinslamp.cc:80/server/subscribe/shadowrocket/{{ uuid }}
        <el-button
          type="primary"
          size="small"
          v-clipboard:copy="
            'http://aladdinslamp.cc:80/server/subscribe/shadowrocket/' + uuid
          "
          v-clipboard:success="onCopySuccess"
        >
          复制
        </el-button>
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <el-tag size="small">clash</el-tag>
        </template>
        http://aladdinslamp.cc:80/server/subscribe/clash/{{ uuid }}
        <el-button
          type="primary"
          size="small"
          v-clipboard:copy="
            'http://aladdinslamp.cc:80/server/subscribe/clash/' + uuid
          "
          v-clipboard:success="onCopySuccess"
        >
          复制
        </el-button>
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <el-tag size="small">shadowrocket二维码</el-tag>
        </template>
        <vue-qr
          :text="text"
          :size="200"
          :correctLevel="2"
          :margin="10"
          colorDark="#409EFF"
          colorLight="#FFFFFF"
          :logoCornerRadius="20"
        />
      </el-descriptions-item>
    </el-descriptions>
  </div>
</template>

<script>
import VueQr from "vue-qr";
export default {
  name: "MemberInfo",
  props: {
    uuid: {
      type: String,
      required: true,
    },
    subscribeDialogVisible: {
      type: Boolean,
      required: true,
    },
  },
  components: { VueQr },
  mounted() {
    let Base64 = require("js-base64").Base64;
    this.text =
      "sub://" +
      Base64.encode(
        "http://aladdinslamp.cc:80/server/subscribe/shadowrocket/" + this.uuid
      )
        .replace("=", "")
        .replace("=", "");
  },
  data() {
    return {
      text: "",
    };
  },
  methods: {
    onCancle() {
      this.$emit("update:infoDialogVisible", false);
    },
    onCopySuccess() {
      this.$message.success("已复制至剪切板");
    },
  },
};
</script>

<style>
</style>