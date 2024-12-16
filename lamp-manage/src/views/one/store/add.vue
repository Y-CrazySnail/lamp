<template>
  <div
    style="
      height: 500px;
      position: relative;
      display: flex;
      flex-direction: column;
      align-items: left;
      margin-right: 50px;
    "
  >
    <el-form ref="form" :model="form" label-width="120px" class="dialog-body">
      <div class="dialog-body-item">
        <el-form-item label="客户" class="inner">
          <el-select v-model="form.tenantId" clearable placeholder="客户">
            <el-option
              v-for="item in tenantList"
              :key="item.id"
              :label="item.tenantName"
              :value="item.id"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="名称" class="inner">
          <el-input v-model="form.storeName" />
        </el-form-item>
      </div>
      <div style="width: 470px">
        <el-form-item label="店铺头像">
          <el-upload
            action="http://localhost:8111/one/manage/store/upload"
            :file-list="form.storeLogoList"
            :headers="headers"
            list-type="picture-card"
            :on-success="logoHandleSuccess"
            :limit="1"
          >
            <i class="el-icon-plus"></i>
          </el-upload>
        </el-form-item>
      </div>
      <div style="width: 470px">
        <el-form-item label="店铺二维码">
          <el-upload
            action="http://localhost:8111/one/manage/store/upload"
            :file-list="form.storeWechatQrcodeList"
            :headers="headers"
            list-type="picture-card"
            :on-success="wechatQrcodeHandleSuccess"
            :limit="1"
          >
            <i class="el-icon-plus"></i>
          </el-upload>
        </el-form-item>
      </div>
      <div class="dialog-body-item">
        <el-form-item label="店铺微信" class="inner">
          <el-input v-model="form.storeWechat" />
        </el-form-item>
        <el-form-item label="店铺介绍" class="inner">
          <el-input v-model="form.storeDescription" />
        </el-form-item>
      </div>
      <div class="dialog-body-item">
        <el-form-item label="经度" class="inner">
          <el-input v-model="form.storeLongitude" />
        </el-form-item>
        <el-form-item label="纬度" class="inner">
          <el-input v-model="form.storeLatitude" />
        </el-form-item>
      </div>
      <div class="dialog-body-item">
        <el-form-item label="手机" class="inner">
          <el-input v-model="form.storePhone" />
        </el-form-item>
        <el-form-item label="地址" class="inner">
          <el-input v-model="form.storeAddress" style="width: 300px;" />
        </el-form-item>
      </div>
    </el-form>
    <div style="margin-left: 120px;">
      <el-button @click="onCancle" style="margin-right: 10px;">取消</el-button>
      <el-button type="primary" @click="onSubmit">创建</el-button>
    </div>
  </div>
</template>

<script>
import { getToken } from "@/utils/auth";
export default {
  name: "OneTenantAdd",
  props: {
    addDialogVisible: {
      type: Boolean,
      required: true
    }
  },
  mounted() {
    this.headers.Authorization = `Bearer ` + getToken();
  },
  data() {
    return {
      form: {
        storeLogoList: [],
        storeWechatQrcodeList: []
      },
      headers: {}
    };
  },
  computed: {
    tenantList() {
      return this.$store.state.one_tenant.tenantList || [];
    }
  },
  methods: {
    onSubmit() {
      this.form.storeLogo = this.form.storeLogoList
        .map(image => image.url)
        .join(",");
      this.form.storeWechatQrcode = this.form.storeWechatQrcodeList
        .map(image => image.url)
        .join(",");
      this.$store
        .dispatch("one_store/save", this.form)
        .then(response => {
          this.$message.success("创建成功");
          this.$emit("update:addDialogVisible", false);
        })
        .catch(() => {});
    },
    logoHandleSuccess(response, file, fileList) {
      this.form.storeLogoList = fileList.map(file => {
        if (file.response) {
          file.url = file.response;
        }
        return file;
      });
    },
    wechatQrcodeHandleSuccess(response, file, fileList) {
      this.form.storeWechatQrcodeList = fileList.map(file => {
        if (file.response) {
          file.url = file.response;
        }
        return file;
      });
    },
    onCancle() {
      this.$emit("update:addDialogVisible", false);
    }
  },
  destroyed() {
    this.form = {};
  }
};
</script>
