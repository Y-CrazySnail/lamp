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
      <el-col :span="12">
        <el-form-item label="产品编号:" prop="productNo">
          <el-input v-model="form.productNo" placeholder="产品编号" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="产品名称:" prop="productName">
          <el-input v-model="form.productName" placeholder="产品名称" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="企业名称:" prop="companyName">
          <el-input v-model="form.companyName" placeholder="企业名称" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="统一社会信用代码:" prop="companyNo">
          <el-input v-model="form.companyNo" placeholder="营业执照" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="管理人姓名:" prop="managerName">
          <el-input v-model="form.managerName" placeholder="管理人姓名" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="管理人手机:" prop="managerPhone">
          <el-input v-model="form.managerPhone" placeholder="管理人手机" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="管理人邮箱:" prop="managerEmail">
          <el-input v-model="form.managerEmail" placeholder="管理人邮箱" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="产品运营维护价格:" prop="operationPrice">
          <el-input
            v-model="form.operationPrice"
            placeholder="产品运营维护价格"
          />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="开通小程序:" prop="miniProgramFlag">
          <el-switch
            v-model="form.miniProgramFlag"
            active-value="1"
            inactive-value="0"
          />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="微信小程序名称:" prop="miniProgramName">
          <el-input
            :disabled="form.miniProgramFlag === '0'"
            v-model="form.miniProgramName"
            placeholder="微信小程序名称"
          />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="添加官方网站:" prop="officialWebsiteFlag">
          <el-switch
            v-model="form.officialWebsiteFlag"
            active-value="1"
            inactive-value="0"
          />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="官方网站域名:" prop="officialWebsiteDomain">
          <el-input
            :disabled="form.officialWebsiteFlag === '0'"
            v-model="form.officialWebsiteDomain"
            placeholder="官方网站域名"
          />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="授权用户名:" prop="authorizedUsername">
          <el-input v-model="form.authorizedUsername" placeholder="授权用户名；逗号分隔" />
        </el-form-item>
      </el-col>
      <el-form-item label="产品成立日期:" prop="publishDate">
        <el-date-picker
          v-model="form.publishDate"
          :picker-options="pickerOptions"
          type="date"
          placeholder="选择日期"
          value-format="yyyy-MM-dd"
          style="width: 165px"
        ></el-date-picker>
      </el-form-item>
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
  name: "CarFilmTenantAdd",
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
        .dispatch("car_film_tenant/save", this.form)
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
.dialog-footer {
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>