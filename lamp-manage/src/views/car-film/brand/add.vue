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
        <el-form-item label="品牌名称:" prop="name">
          <el-input v-model="form.productNo" placeholder="品牌名称" />
        </el-form-item>
      </el-col>
      <el-col :span="24">
        
      </el-col>
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
  name: "CarFilmProductAdd",
  props: {
    addDialogVisible: {
      type: Boolean,
      required: true,
    },
  },
  computed: {
    tenantList() {
      return this.$store.state.car_film_tenant.tenantList || [];
    },
  },
  mounted() {
    console.log(this.tenantList);
  },
  data() {
    return {
      form: {},
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
        .dispatch("car_film_product/save", this.form)
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