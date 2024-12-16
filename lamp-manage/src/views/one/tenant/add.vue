<template>
  <div style="
      height: 300px;
      position: relative;
      display: flex;
      flex-direction: column;
      align-items: left;
      margin-right: 50px;
    ">
    <el-form ref="form" :model="form" label-width="120px" class="dialog-body">
      <div class="dialog-body-item">
        <el-form-item label="名称" class="inner">
          <el-input v-model="form.tenantName" />
        </el-form-item>
        <el-form-item label="归属用户" class="inner">
          <el-input v-model="form.belongUsername" />
        </el-form-item>
      </div>
      <div class="dialog-body-item">
        <el-form-item label="手机" class="inner">
          <el-input v-model="form.tenantPhone" />
        </el-form-item>
        <el-form-item label="邮箱" class="inner">
          <el-input v-model="form.tenantEmail" />
        </el-form-item>
      </div>
      <div class="dialog-body-item">
        <el-form-item label="小程序AppId" class="inner">
          <el-input v-model="form.wechatAppId" />
        </el-form-item>
        <el-form-item label="小程序密钥" class="inner">
          <el-input v-model="form.wechatAppSecret" />
        </el-form-item>
      </div>
    </el-form>
    <div style="margin-left: 120px;">
      <el-button @click="onCancle" style="margin-right: 10px">取消</el-button>
      <el-button type="primary" @click="onSubmit">创建</el-button>
    </div>
  </div>
</template>

<script>
  export default {
    name: "OneTenantAdd",
    props: {
      addDialogVisible: {
        type: Boolean,
        required: true,
      },
    },
    mounted () { },
    data () {
      return {
        form: {},
      };
    },
    methods: {
      onSubmit () {
        this.$store
          .dispatch("one_tenant/save", this.form)
          .then((response) => {
            this.$message.success("创建成功");
            this.$emit("update:addDialogVisible", false);
          })
          .catch(() => { });
      },
      onCancle () {
        this.$emit("update:addDialogVisible", false);
      },
    },
    destroyed () {
      this.form = {};
    },
  };
</script>