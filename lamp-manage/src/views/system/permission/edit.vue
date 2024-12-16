<template>
  <div>
    <el-form ref="form" :model="form" label-width="100px">
      <el-form-item label="编号">
        <el-input v-model="form.id" :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="名称">
        <el-input v-model="form.name" />
      </el-form-item>
      <el-form-item label="路径">
        <el-input v-model="form.url" />
      </el-form-item>
      <el-form-item label="描述">
        <el-input v-model="form.description"></el-input>
      </el-form-item>
    </el-form>
    <div class="dialog-footer">
      <el-button size="small" @click="onCancle" style="margin-right:10px;">取消</el-button>
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
  name: 'PermissionEdit',
  props: {
    form: {
      type: Object,
      required: true
    },
    editDialogVisible: {
      type: Boolean,
      required: true
    }
  },
  mounted() {},
  data() {
    return {}
  },
  methods: {
    onSubmit() {
      this.$store
        .dispatch('permission/update', this.form)
        .then(response => {
          this.$message.success(response)
          this.onCancle()
        })
        .catch(() => {})
    },
    onCancle() {
      this.$emit('update:editDialogVisible', false)
    }
  }
}
</script>

<style>
.dialog-footer {
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>