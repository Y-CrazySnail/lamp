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
      <el-button @click="onCancle" style="margin-right: 10px">
        取消
      </el-button>
      <el-popconfirm confirm-button-text="确认" cancel-button-text="取消" icon="el-icon-info" title="确认更新？"
        @confirm="onSubmit">
        <el-button slot="reference" type="primary">修改</el-button>
      </el-popconfirm>
    </div>
  </div>
</template>

<script>
  export default {
    name: "OneTenantEdit",
    props: {
      form: {
        type: Object,
        required: true,
      },
      editDialogVisible: {
        type: Boolean,
        required: true,
      },
    },
    mounted () { },
    computed: {},
    data () {
      return {};
    },
    methods: {
      onSubmit () {
        this.$store
          .dispatch("one_tenant/update", this.form)
          .then((response) => {
            this.$message.success("更新成功");
            this.onCancle();
          })
          .catch(() => { });
      },
      onCancle () {
        this.$emit("update:editDialogVisible", false);
      },
    },
  };
</script>