<template>
  <div style="height: 500px; overflow-y: scroll;">
    <el-form ref="form" :model="form" label-width="100px">
      <el-form-item label="名称">
        <el-input v-model="form.name" />
      </el-form-item>
      <el-form-item label="描述">
        <el-input v-model="form.description"></el-input>
      </el-form-item>
      <el-form-item label="权限">
        <el-transfer
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
      <el-button type="primary" size="small" @click="onSubmit">创建</el-button>
    </div>
  </div>
</template>

<script>
export default {
  name: "RoleAdd",
  props: {
    addDialogVisible: {
      type: Boolean,
      required: true,
    },
  },
  mounted() {},
  data() {
    return {
      form: {
        name: null,
        description: null,
        permissionIdList: [],
      },
    };
  },
  computed: {
    permissionList() {
      return this.$store.state.permission.permissions.map((e) => {
        return { key: e.id, label: e.name };
      });
    },
  },
  methods: {
    onSubmit() {
      this.$store
        .dispatch("role/save", this.form)
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
      name: null,
      description: null,
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