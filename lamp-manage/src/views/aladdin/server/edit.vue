<template>
  <div>
    <el-form
      ref="form"
      :model="form"
      label-width="auto"
      size="mini"
      :inline="true"
    >
      <el-col :span="24">
        <el-form-item label="ID:" prop="id">
          <el-input
            v-model="form.id"
            placeholder="ID"
            style="width: 300px"
            disabled
          />
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="APIID:" prop="apiIp">
          <el-input
            v-model="form.apiIp"
            placeholder="APIID"
            style="width: 300px"
          />
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="API端口:" prop="apiPort">
          <el-input
            v-model="form.apiPort"
            placeholder="API端口"
            style="width: 300px"
          />
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="API用户名:" prop="apiUsername">
          <el-input
            v-model="form.apiUsername"
            placeholder="API用户名"
            style="width: 300px"
          />
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="API密码:" prop="apiPassword">
          <el-input
            v-model="form.apiPassword"
            placeholder="API密码"
            style="width: 300px"
          />
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="节点名称:" prop="nodeRemark">
          <el-input
            v-model="form.nodeRemark"
            placeholder="节点名称"
            style="width: 300px"
          />
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="节点端口:" prop="nodePort">
          <el-input
            v-model="form.nodePort"
            placeholder="节点端口"
            style="width: 300px"
          />
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="订阅地址:" prop="subscribeIp">
          <el-input
            v-model="form.subscribeIp"
            placeholder="订阅地址"
            style="width: 300px"
          />
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="订阅端口:" prop="subscribePort">
          <el-input
            v-model="form.subscribePort"
            placeholder="订阅端口"
            style="width: 300px"
          />
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="订阅名称前缀:" prop="subscribeNamePrefix">
          <el-input
            v-model="form.subscribeNamePrefix"
            placeholder="订阅名称前缀"
            style="width: 300px"
          />
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="说明:" prop="postscript">
          <el-input
            v-model="form.postscript"
            placeholder="说明"
            style="width: 300px"
          />
        </el-form-item>
      </el-col>
      <el-col :span="24" />
    </el-form>
    <div class="dialog-footer">
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
    form: {},
    editDialogVisible: {
      type: Boolean,
      required: true,
    },
  },
  mounted() {
    this.form.uuid = this.$uuid.v4();
  },
  data() {
    return {
      form: {
        uuid: "",
      },
    };
  },
  methods: {
    onSubmit() {
      this.$store
        .dispatch("aladdin_server/update", this.form)
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

<style>
</style>