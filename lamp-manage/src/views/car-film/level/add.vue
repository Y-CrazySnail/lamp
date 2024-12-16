<template>
  <div>
    <el-form
      ref="form"
      :model="form"
      label-width="auto"
      size="mini"
      :inline="true"
      class="demo-form-inline"
    >
      <el-col :span="24">
        <el-form-item label="品牌名称:" prop="name">
          <el-input v-model="form.productNo" placeholder="品牌名称" />
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-upload
          action="https://yeemcloud.com/zero-api/manager-zero-tencent-cos/upload"
          :file-list="showFile"
          list-type="picture"
          :headers="header"
          :multiple="false"
          :limit="1"
          :on-success="onSuccessShowFile"
          :on-remove="onRemoveShowFile"
        >
          <el-button size="small" type="primary" v-if="showFile.length < 1">
            点击上传
          </el-button>
        </el-upload>
      </el-col>
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
  name: "carBrandAdd",
  props: {
    addDialogVisible: {
      type: Boolean,
      required: true,
    },
  },
  mounted() {},
  data() {
    return {
      form: {},
      showFile: [],
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
      this.form.imageShowPath =
        this.showFile.length > 0 ? this.showFile[0].url : "";
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
    onSuccessShowFile(response, file, fileList) {
      this.showFile = [];
      this.showFile.push({ url: response });
    },
    onRemoveShowFile(file, fileList) {
      this.showFile = [];
    },
  },
  destroyed() {
    this.form = {};
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