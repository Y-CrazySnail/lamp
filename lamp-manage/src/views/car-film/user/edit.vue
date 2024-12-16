<template>
  <div>
    <el-form
      ref="form"
      :model="form"
      label-width="130px"
      size="mini"
      :inline="true"
      class="demo-form-inline"
    >
      <el-col :span="12">
        <el-form-item label="编号:" prop="productNo">
          <el-input v-model="form.id" placeholder="产品编号" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="产品编号:" prop="productNo">
          <el-input v-model="form.productNo" placeholder="产品编号" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="名称:" prop="nickName">
          <el-input v-model="form.nickName" placeholder="名称" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="手机号:" prop="phone">
          <el-input v-model="form.phone" placeholder="手机号" />
        </el-form-item>
      </el-col>
      <el-form-item label="质保录入权限:" prop="companyNo">
        <el-switch
          style="display: block"
          v-model="form.qualityPermission"
          active-color="#13ce66"
          inactive-color="#ff4949"
          active-value="1"
          inactive-value="0"
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
  name: "CarFilmTenantEdit",
  props: {
    form: {},
    editDialogVisible: {
      type: Boolean,
      required: true,
    },
  },
  mounted() {},
  data() {
    return {
      pickerOptions: {
        shortcuts: [
          {
            text: "今天",
            onClick(picker) {
              picker.$emit("pick", new Date());
            },
          },
        ],
      },
    };
  },
  methods: {
    onSubmit() {
      this.$store
        .dispatch("car_film_user/edit", this.form)
        .then((response) => {
          this.$message.success("更新成功");
          this.$emit("update:editDialogVisible", false);
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
}
</style>