<template>
  <div>
    <el-form ref="form" :model="form" label-width="120px">
      <el-form-item label="客户">
        <el-select
          v-model="form.tenantId"
          clearable
          placeholder="客户"
          @change="onChangeTenant"
        >
          <el-option
            v-for="item in tenantList"
            :key="item.id"
            :label="item.tenantName"
            :value="item.id"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="店铺">
        <el-select
          v-model="form.storeId"
          clearable
          placeholder="店铺"
          @change="onChangeStore"
        >
          <el-option
            v-for="item in storeList"
            :key="item.id"
            :label="item.storeName"
            :value="item.id"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="分类">
        <el-select v-model="form.categoryId" clearable placeholder="分类">
          <el-option
            v-for="item in categoryList"
            :key="item.id"
            :label="item.categoryName"
            :value="item.id"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="SPU名称">
        <el-input v-model="form.spuName" />
      </el-form-item>
      <el-form-item label="SPU排序">
        <el-input v-model="form.spuSort" type="number" />
      </el-form-item>
      <el-form-item label="SPU状态">
        <el-switch
          v-model="form.spuStatus"
          active-text="上架"
          inactive-text="下架"
        />
      </el-form-item>
      <el-form-item :label="'属性'">
        <div style="display: flex">
          <div style="width: 30%; color: rgb(176, 0, 0); font-weight: 600">
            规格-键（例如：颜色、尺寸）
          </div>
          <div
            style="
              width: 60%;
              color: rgb(176, 0, 0);
              font-weight: 600;
              margin-left: 5px;
            "
          >
            规格-值（例如：黑色、白色）
          </div>
          <el-button
            style="margin-left: 5px"
            @click="addAttribute"
            type="success"
          >
            新增
          </el-button>
        </div>
      </el-form-item>
      <vuedraggable v-model="spuAttributeList" :animation="400">
        <el-form-item
          :label="'属性'"
          v-for="(attribute, index) in spuAttributeList"
          :key="index"
        >
          <el-input v-model="attribute.attributeKey" style="width: 30%" />
          <el-input
            v-model="attribute.attributeValue"
            style="width: 60%; margin-left: 5px"
          />
          <el-button
            style="margin-left: 5px"
            @click="removeAttribute(index)"
            type="danger"
          >
            删除
          </el-button>
        </el-form-item>
      </vuedraggable>
      <div style="width: 470px">
        <el-form-item label="展示图">
          <el-upload
            action="http://localhost:8111/one/manage/spu/upload"
            :file-list="spuShowImageList"
            :headers="headers"
            list-type="picture-card"
            :on-success="showHandleSuccess"
            :limit="1"
          >
            <i class="el-icon-plus"></i>
          </el-upload>
        </el-form-item>
      </div>
      <div style="width: 470px">
        <el-form-item label="轮播图">
          <el-upload
            action="http://localhost:8111/one/manage/spu/upload"
            :file-list="spuSwiperImageList"
            drag
            :headers="headers"
            :on-success="swiperHandleSuccess"
          >
            <i class="el-icon-upload"></i>
            <div class="el-upload__text">
              将文件拖到此处，或<em>点击上传</em>
            </div>
          </el-upload>
        </el-form-item>
      </div>
      <div>
        <el-form-item label="轮播图顺序">
          <vuedraggable
            v-model="spuSwiperImageList"
            style="display: flex"
            :animation="400"
          >
            <div v-for="(file, index) in spuSwiperImageList" :key="index">
              <img
                style="width: 150px; height: 150px; margin: 5px 5px 5px 5px"
                :src="file.url"
              />
            </div>
          </vuedraggable>
        </el-form-item>
      </div>
      <div style="width: 470px">
        <el-form-item label="详情图">
          <el-upload
            action="http://localhost:8111/one/manage/spu/upload"
            :file-list="spuDetailImageList"
            drag
            :headers="headers"
            :on-success="detailHandleSuccess"
          >
            <i class="el-icon-upload"></i>
            <div class="el-upload__text">
              将文件拖到此处，或<em>点击上传</em>
            </div>
          </el-upload>
        </el-form-item>
      </div>
      <div>
        <el-form-item label="详情图顺序">
          <vuedraggable
            v-model="spuDetailImageList"
            style="display: flex"
            :animation="400"
          >
            <div v-for="(file, index) in spuDetailImageList" :key="index">
              <img
                style="width: 150px; height: 150px; margin: 5px 5px 5px 5px"
                :src="file.url"
              />
            </div>
          </vuedraggable>
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
  name: "OneTenantEdit",
  props: {
    editDialogVisible: {
      type: Boolean,
      required: true,
    },
  },
  components: {
    vuedraggable: draggable,
  },
  mounted() {
    this.headers.Authorization = `Bearer ` + getToken();
    this.form = this.editForm;
    this.spuAttributeList = JSON.parse(this.form.spuAttribute);
    this.spuShowImageList = [
      {
        url: this.form.spuShowImage,
      },
    ];
    this.spuSwiperImageList = this.form.spuSwiperImage.split(",").map((e) => {
      return {
        url: e,
      };
    });
    this.spuDetailImageList = this.form.spuDetailImage.split(",").map((e) => {
      return {
        url: e,
      };
    });
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
      spuAttributeList: [],
      spuShowImageList: [],
      spuSwiperImageList: [],
      spuDetailImageList: [],
    };
  },
  computed: {
    tenantList() {
      let tenantList = this.$store.state.one_tenant.tenantList;
      return tenantList || [];
    },
    storeList() {
      let storeList = this.$store.state.one_store.storeList;
      if (this.form.tenantId) {
        storeList =
          storeList.filter((e) => e.tenantId == this.form.tenantId) || [];
      }
      return storeList || [];
    },
    categoryList() {
      let categoryList = this.$store.state.one_category.categoryList;
      if (this.form.storeId) {
        categoryList =
          categoryList.filter((e) => e.storeId == this.form.storeId) || [];
      }
      return categoryList || [];
    },
  },
  methods: {
    onSubmit() {
      this.form.spuAttribute = JSON.stringify(this.spuAttributeList);
      this.form.spuSwiperImage = this.spuSwiperImageList
        .map((image) => image.url)
        .join(",");
      this.form.spuDetailImage = this.spuDetailImageList
        .map((image) => image.url)
        .join(",");
      this.form.spuShowImage = this.spuShowImageList
        .map((image) => image.url)
        .join(",");
      this.$store
        .dispatch("one_spu/update", this.form)
        .then((response) => {
          this.$message.success("更新成功");
          this.$emit("update:editDialogVisible", false);
        })
        .catch(() => {});
    },
    onCancle() {
      this.$emit("update:editDialogVisible", false);
    },
    onChangeTenant() {
      this.form.storeId = null;
      this.form.categoryId = null;
    },
    onChangeStore() {
      this.form.categoryId = null;
    },
    showHandleSuccess(response, file, fileList) {
      this.spuShowImageList = fileList.map((file) => {
        if (file.response) {
          file.url = file.response;
        }
        return file;
      });
    },
    swiperHandleSuccess(response, file, fileList) {
      this.spuSwiperImageList = fileList.map((file) => {
        if (file.response) {
          file.url = file.response;
        }
        return file;
      });
    },
    detailHandleSuccess(response, file, fileList) {
      this.spuDetailImageList = fileList.map((file) => {
        if (file.response) {
          file.url = file.response;
        }
        return file;
      });
    },
    addAttribute() {
      this.spuAttributeList.push({ attributeKey: "", attributeValue: "" });
    },
    removeAttribute(index) {
      this.spuAttributeList.splice(index, 1);
    },
  },
  destroyed() {
    this.form = {};
  },
};
</script>

<style scoped>
</style>