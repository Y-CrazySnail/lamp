<template>
  <div style="height: 500px; overflow-y: scroll;">
    <el-form ref="form" :model="form" label-width="100px">
      <el-form-item label="编号">
        <el-input v-model="form.id" :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="名称">
        <el-input v-model="form.name" />
      </el-form-item>
      <el-form-item label="描述">
        <el-input v-model="form.description"></el-input>
      </el-form-item>
      <el-form-item label="权限">
        <el-transfer
          v-if="form.permissionIdList"
          v-model="form.permissionIdList"
          :data="permissionList"
          :titles="['权限列表', '已选权限']"
        />
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
  name: "RoleEdit",
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
    this.form.permissionIdList = [];
    this.$store
      .dispatch("role/permissionIdList", { roleId: this.form.id })
      .then((response) => {
        this.form.permissionIdList = response;
      });
  },
  computed: {
    permissionList() {
      return this.$store.state.permission.permissions.map((e) => {
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
        .dispatch("role/update", this.form)
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