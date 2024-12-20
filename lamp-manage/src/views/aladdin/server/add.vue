<template>
  <div v-loading="loading">
    <el-form ref="form" :model="form" label-width="auto" size="mini" :inline="true">
      <el-col :span="24">
        <el-form-item label="APIID:" prop="apiIp">
          <el-input v-model="form.apiIp" placeholder="APIID" style="width: 300px" />
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="API端口:" prop="apiPort">
          <el-input v-model="form.apiPort" placeholder="API端口" style="width: 300px" />
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="API用户名:" prop="apiUsername">
          <el-input v-model="form.apiUsername" placeholder="API用户名" style="width: 300px" />
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="API密码:" prop="apiPassword">
          <el-input v-model="form.apiPassword" placeholder="API密码" style="width: 300px" />
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="到期时间:" prop="expiryDate">
          <el-input v-model="form.expiryDate" placeholder="到期时间" style="width: 300px" />
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="地区:" prop="region">
          <el-input v-model="form.region" placeholder="地区" style="width: 300px" />
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="备注:" prop="remark">
          <el-input v-model="form.remark" placeholder="备注" style="width: 300px" />
        </el-form-item>
      </el-col>
      <el-col :span="24" />
    </el-form>
    <div class="dialog-footer">
      <el-button size="small" @click="onCancle" style="margin-right: 10px">
        取消
      </el-button>
      <el-button type="primary" size="small" @click="onSubmit">创建</el-button>
    </div>
  </div>
</template>

<script>
export default {
  name: "AladdinServerAdd",
  props: {
    addDialogVisible: {
      type: Boolean,
      required: true,
    },
  },
  mounted() {
  },
  data() {
    return {
      form: {
        loading: false,
      },
    };
  },
  methods: {
    onSubmit() {
      this.loading = true,
        this.$store
          .dispatch("aladdin_server/save", this.form)
          .then((response) => {
            this.$message.success("添加成功");
            this.loading = false,
              this.$emit("update:addDialogVisible", false);
          })
          .catch(() => { });
    },
    onCancle() {
      this.$emit("update:addDialogVisible", false);
    },
  },
  destroyed() {
    this.form = {};
  },
};
</script>

<style></style>