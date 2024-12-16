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
        <el-form-item label="ID:" prop="id">
          <el-input
            v-model="form.id"
            placeholder="ID"
            style="width: 300px"
            disabled
          />
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="流量剩余:" prop="surplus">
          <el-tag type="success">{{ form.surplus }}GB</el-tag>
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="UUID:" prop="uuid">
          <el-input
            v-model="form.uuid"
            placeholder="UUID"
            style="width: 300px"
            disabled
          />
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="开始时间:" prop="beginDate">
          <el-date-picker
            v-model="form.beginDate"
            type="date"
            placeholder="结束时间"
            style="width: 300px"
            disabled
          />
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="结束时间:" prop="endDate">
          <el-date-picker
            v-model="form.endDate"
            type="date"
            placeholder="结束时间"
            style="width: 300px"
            format="yyyy-MM-dd"
            value-format="yyyy-MM-dd"
          >
          </el-date-picker>
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="流量(GB):" prop="dataTraffic">
          <el-input
            v-model="form.dataTraffic"
            placeholder="流量(GB)"
            style="width: 300px"
          />
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="周期(月):" prop="period">
          <el-input
            v-model="form.period"
            placeholder="周期(月)"
            style="width: 300px"
          />
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="价格(元):" prop="price">
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
      <el-button type="primary" size="small" @click="onSubmit">更新</el-button>
    </div>
  </div>
</template>

<script>
export default {
  name: "AladdinServiceEdit",
  props: {
    form: {},
    editDialogVisible: {
      type: Boolean,
      required: true,
    },
  },
  mounted() {},
  data() {
    return {};
  },
  methods: {
    onSubmit() {
      this.$store
        .dispatch("aladdin_service/update", this.form)
        .then((response) => {
          this.$message.success("更新成功");
          this.$emit("update:editDialogVisible", false);
        });
    },
    onCancle() {
      this.$emit("update:editDialogVisible", false);
    },
  },
  destroyed() {
    this.form = {};
  },
};
</script>

<style>
</style>