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
        <el-form-item label="类型:" prop="type">
          <el-input
            v-model="form.type"
            placeholder="类型"
            style="width: 300px"
          />
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="流量:" prop="dataTraffic">
          <el-input
            v-model="form.dataTraffic"
            placeholder="流量(GB)"
            style="width: 300px"
          />
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="周期:" prop="period">
          <el-input
            v-model="form.period"
            placeholder="周期(月)"
            style="width: 300px"
          />
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="价格:" prop="price">
          <el-input
            v-model="form.price"
            placeholder="价格(元)"
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
      <el-button type="primary" size="small" @click="onSubmit">创建</el-button>
    </div>
  </div>
</template>

<script>
export default {
  name: "AladdinPackageAdd",
  props: {
    addDialogVisible: {
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
        .dispatch("aladdin_package/save", this.form)
        .then((response) => {
          this.$message.success("添加成功");
          this.$emit("update:addDialogVisible", false);
        })
        .catch(() => {});
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

<style>
</style>