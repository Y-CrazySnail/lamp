<template>
  <div>
    <el-form ref="form" :model="form" label-width="120px">
      <el-form-item label="SKU名称">
        <el-input v-model="form.skuName" />
      </el-form-item>
      <el-form-item label="价格（分）">
        <el-input v-model="form.skuPrice" type="number" />
      </el-form-item>
      <el-form-item label="库存（件）">
        <el-input v-model="form.skuStock" type="number" />
      </el-form-item>
      <el-form-item label="销量（件）">
        <el-input v-model="form.skuSales" type="number" />
      </el-form-item>
      <el-form-item label="排序">
        <el-input v-model="form.skuSort" type="number" />
      </el-form-item>
      <el-form-item label="SKU状态">
        <el-switch
          v-model="form.skuStatus"
          active-text="上架"
          inactive-text="下架"
        />
      </el-form-item>
      <el-form-item :label="'属性'">
        <div style="display: flex">
          <div style="width: 20%; color: rgb(176, 0, 0); font-weight: 600">
            规格-键
          </div>
          <div
            style="
              width: 60%;
              color: rgb(176, 0, 0);
              font-weight: 600;
              margin-left: 5px;
            "
          >
            规格-值
          </div>
          <el-button style="margin-left: 5px" @click="addAttribute">
            新增
          </el-button>
        </div>
      </el-form-item>
      <vuedraggable v-model="skuAttributeList" :animation="400">
        <el-form-item
          :label="'属性'"
          v-for="(attribute, index) in skuAttributeList"
          :key="index"
        >
          <el-input v-model="attribute.attributeKey" style="width: 20%" />
          <el-input
            v-model="attribute.attributeValue"
            style="width: 60%; margin-left: 5px"
          />
          <el-button style="margin-left: 5px" @click="removeAttribute(index)">
            删除
          </el-button>
        </el-form-item>
      </vuedraggable>
      <div style="width: 470px">
        <el-form-item label="展示图">
          <el-upload
            action="http://localhost:8111/one/manage/sku/upload"
            :file-list="skuShowImageList"
            :headers="headers"
            list-type="picture-card"
            :on-success="showHandleSuccess"
            :limit="1"
          >
            <i class="el-icon-plus"></i>
          </el-upload>
        </el-form-item>
      </div>
    </el-form>
    <div class="dialog-footer">
      <el-button @click="onCancle" style="margin: 0 10px 20px 120px">
        取消
      </el-button>
      <el-button type="primary" @click="onSubmit">更新</el-button>
    </div>
  </div>
</template>

<script>
import { getToken } from "@/utils/auth";
import draggable from "vuedraggable";
export default {
  name: "OneSKUEdit",
  components: {
    vuedraggable: draggable,
  },
  mounted() {
    this.headers.Authorization = `Bearer ` + getToken();
    this.form = this.editForm;
    this.skuAttributeList = JSON.parse(this.form.skuAttribute);
    if (this.form.skuShowImage) {
      this.skuShowImageList = [
        {
          url: this.form.skuShowImage,
        },
      ];
    }
  },
  props: {
    editForm: {
      type: Object,
      required: true,
    },
    editDialogVisible: {
      type: Boolean,
      required: true,
    },
  },
  data() {
    return {
      form: {},
      headers: {},
      skuAttributeList: [],
      skuShowImageList: [],
    };
  },
  computed: {},
  methods: {
    onSubmit() {
      this.form.skuAttribute = JSON.stringify(this.skuAttributeList);
      this.form.skuShowImage = this.skuShowImageList
        .map((image) => image.url)
        .join(",");
      this.$store
        .dispatch("one_sku/update", this.form)
        .then((response) => {
          this.$message.success("更新成功");
          this.$emit("update:editDialogVisible", false);
        })
        .catch(() => {});
    },
    onCancle() {
      this.$emit("update:editDialogVisible", false);
    },
    showHandleSuccess(response, file, fileList) {
      this.skuShowImageList = fileList.map((file) => {
        if (file.response) {
          file.url = file.response;
        }
        return file;
      });
    },
    addAttribute() {
      this.skuAttributeList.push({ attributeKey: "", attributeValue: "" });
    },
    removeAttribute(index) {
      this.skuAttributeList.splice(index, 1);
    },
  },
  destroyed() {
    this.form = {};
  },
};
</script>

<style scoped>
</style>