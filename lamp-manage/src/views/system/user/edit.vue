<template>
  <div style="height: 500px; overflow-y: auto;">
    <el-form ref="form" :model="form" label-width="100px">
      <el-form-item label="编号">
        <el-input v-model="form.id" :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="账号">
        <el-input v-model="form.username" :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="密码">
        <el-input v-model="form.password" :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="角色">
        <el-transfer
          v-if="form.roleIdList"
          v-model="form.roleIdList"
          :data="roleList"
          :titles="['权限列表', '已选权限']"
        />
      </el-form-item>
      <el-form-item label="账号未过期">
        <el-radio-group v-model="form.accountNonExpired" size="small">
          <el-radio :label="true" border>是</el-radio>
          <el-radio :label="false" border>否</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="账号未锁定">
        <el-radio-group v-model="form.accountNonLocked" size="small">
          <el-radio :label="true" border>是</el-radio>
          <el-radio :label="false" border>否</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="认证未过期">
        <el-radio-group v-model="form.credentialsNonExpired" size="small">
          <el-radio :label="true" border>是</el-radio>
          <el-radio :label="false" border>否</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="账号可用">
        <el-radio-group v-model="form.enabled" size="small">
          <el-radio :label="true" border>是</el-radio>
          <el-radio :label="false" border>否</el-radio>
        </el-radio-group>
      </el-form-item>
    </el-form>
    <div class="dialog-footer">
      <el-button size="small" @click="onCancle" style="margin-right: 10px">
        取消
      </el-button>
      <el-popconfirm
        confirm-button-text="确认"
        cancel-button-text="取消"
        icon="el-icon-info"
        title="确认更新？"
        @confirm="onSubmit"
      >
        <el-button slot="reference" type="primary" size="small">修改</el-button>
      </el-popconfirm>
    </div>
  </div>
</template>

<script>
export default {
  name: "UserEdit",
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
  mounted() {
    this.$store
      .dispatch("user/roleIdList", { userId: this.form.id })
      .then((response) => {
        this.form.roleIdList = response;
      });
  },
  computed: {
    roleList() {
      return this.$store.state.role.roles.map((e) => {
        return { key: e.id, label: e.name };
      });
    },
  },
  data() {
    return {};
  },
  methods: {
    onSubmit() {
      this.$store
        .dispatch("user/update", this.form)
        .then((response) => {
          this.$message.success(response);
          this.onCancle();
        })
        .catch(() => {});
    },
    onCancle() {
      this.$emit("update:editDialogVisible", false);
    },
  },
};
</script>

<style>
.dialog-footer {
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>