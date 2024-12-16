<template>
  <div>
    <el-form ref="form" :model="form" label-width="100px">
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
      <el-button type="primary" size="small" @click="onSubmit">创建</el-button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'PermissionAdd',
  props: {
    addDialogVisible: {
      type: Boolean,
      required: true
    }
  },
  mounted() {},
  data() {
    return {
      form: {
        name: null,
        description: null
      }
    }
  },
  methods: {
    onSubmit() {
      this.$store
        .dispatch('permission/save', this.form)
        .then(response => {
          this.$message.success(response)
          this.$emit('update:addDialogVisible', false)
        })
        .catch(() => {})
    },
    onCancle() {
      this.$emit('update:addDialogVisible', false)
    }
  },
  destroyed() {
    this.form = {
      name: null,
      description: null
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