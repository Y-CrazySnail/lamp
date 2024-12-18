<template>
  <div v-loading="loading">
    <el-form ref="server" :model="server" label-width="80px" size="mini" :inline="true" style="margin-left: 10px">
      <el-col :span="24">
        <el-form-item label="ID:" prop="id">
          <el-input v-model="server.id" placeholder="ID" style="width: 300px" disabled />
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="API地址:" prop="API地址">
          <el-input v-model="server.apiIp" placeholder="API地址" style="width: 300px" />
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="API端口:" prop="API端口">
          <el-input v-model="server.apiPort" placeholder="API端口" style="width: 300px" />
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="API用户:" prop="apiUsername">
          <el-input v-model="server.apiUsername" placeholder="API用户名" style="width: 300px" />
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="API密码:" prop="apiPassword">
          <el-input v-model="server.apiPassword" placeholder="API密码" style="width: 300px" />
        </el-form-item>
      </el-col>
      <el-col :span="24" />
    </el-form>
    <div style="margin-left: 30px; margin-bottom: 20px">
      <el-button size="small" @click="onCancle" style="margin-right: 10px">
        取消
      </el-button>
      <el-button type="primary" size="small" @click="onSubmit">更新</el-button>
    </div>
  </div>
</template>

<script>
export default {
  name: "AladdinserverEdit",
  props: {
    id: {
      type: Number,
      require: true,
    },
    editDialogVisible: {
      type: Boolean,
      required: true,
    },
  },
  mounted() {
    this.$store
      .dispatch("aladdin_server/getById", { id: this.id })
      .then((server) => {
        this.server = server;
        this.loading = false;
      });
  },
  data() {
    return {
      loading: true,
      server: {}
    };
  },
  methods: {
    onSubmit() {
      this.$store
        .dispatch("aladdin_server/update", this.server)
        .then((response) => {
          this.$message.success("更新成功");
          this.$emit("update:editDialogVisible", false);
        });
    },
    onCancle() {
      this.$emit("update:editDialogVisible", false);
    },
  },
  destroyed() {
    this.form = {};
  },
};
</script>

<style></style>