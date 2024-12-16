<template>
  <div style="height: 500px; overflow-y: auto;">
    <el-form ref="form" :model="form" label-width="100px">
      <el-form-item label="账号">
        <el-input v-model="form.username" />
      </el-form-item>
      <el-form-item label="密码">
        <el-input v-model="form.password" show-password />
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
      <el-button size="small" @click="onCancle" style="margin-right: 10px"
        >取消</el-button
      >
      <el-button type="primary" size="small" @click="onSubmit">创建</el-button>
    </div>
  </div>
</template>

<script>
export default {
  name: "UserAdd",
  props: {
    addDialogVisible: {
      type: Boolean,
      required: true,
    },
  },
  mounted() {},
  computed: {
    roleList() {
      return this.$store.state.role.roles.map((e) => {
        return { key: e.id, label: e.name };
      });
    },
  },
  data() {
    return {
      form: {
        username: null,
        password: null,
        roleIdList: [],
        accountNonExpired: true,
        accountNonLocked: true,
        credentialsNonExpired: true,
        enabled: true,
      },
    };
  },
  methods: {
    onSubmit() {
      this.$store
        .dispatch("user/save", this.form)
        .then((response) => {
          this.$message.success(response);
          this.onCancle();
        })
        .catch(() => {});
    },
    onCancle() {
      this.$emit("update:addDialogVisible", false);
    },
  },
  destroyed() {
    this.form = {
      username: null,
      password: null,
      accountNonExpired: true,
      accountNonLocked: true,
      credentialsNonExpired: true,
      enabled: true,
    };
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