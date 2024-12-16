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
        <el-form-item label="客户信息:" prop="productNo">
          <el-select v-model="form.tenantNo" placeholder="客户信息">
            <el-option
              v-for="item in tenantList"
              :key="item.tenantNo"
              :label="item.tenantNo + '_' + item.tenantName"
              :value="item.tenantNo"
            >
            </el-option>
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="产品类型:" prop="productType">
          <el-select v-model="form.productType" placeholder="产品类型">
            <el-option
              v-for="item in productTypeList"
              :key="item.dictValue"
              :label="item.dictValue"
              :value="item.dictValue"
            >
            </el-option>
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="产品代码:" prop="productNo">
          <el-input v-model="form.productNo" placeholder="产品代码" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="产品名称:" prop="productName">
          <el-input v-model="form.productName" placeholder="产品名称" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="渠道:" prop="channel">
          <el-select v-model="form.channel" placeholder="渠道">
            <el-option
              v-for="item in channelList"
              :key="item.dictValue"
              :label="item.dictValue"
              :value="item.dictValue"
            >
            </el-option>
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="排序:" prop="sort">
          <el-input v-model="form.sort" placeholder="排序" />
        </el-form-item>
      </el-col>
      <el-form-item label="状态:" prop="status">
        <el-switch
          style="display: block"
          v-model="form.status"
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
      <el-button type="primary" size="small" @click="onSubmit">创建</el-button>
    </div>
  </div>
</template>

<script>
export default {
  name: "CFProductAdd",
  props: {
    addDialogVisible: {
      type: Boolean,
      required: true,
    },
  },
  computed: {
    tenantList() {
      return this.$store.state.cf_tenant.tenantList || [];
    },
  },
  mounted() {
    this.form.tenantNo = this.tenantList[0].tenantNo || "";
    this.form.productNo = this.tenantList[0].tenantNo || "";
    this.fetchProductType();
    this.fetchChannel();
  },
  data() {
    return {
      form: {
        tenantNo: "",
        channel: "",
        productNo: "",
      },
      productTypeList: [],
      channelList: [],
    };
  },
  methods: {
    onSubmit() {
      this.$store
        .dispatch("cf_product/save", this.form)
        .then((res) => {
          this.$message.success("添加成功");
          this.$emit("update:addDialogVisible", false);
        })
        .catch(() => {});
    },
    onCancle() {
      this.$emit("update:addDialogVisible", false);
    },
    fetchProductType() {
      let param = {};
      param.tenantNo = this.form.tenantNo;
      param.dictType = "productType";
      this.$store.dispatch("base_car_dictionary/list", param).then((res) => {
        this.productTypeList = res;
        if (this.productTypeList.length > 0) {
          this.form.productType = this.productTypeList[0].dictValue;
        }
      });
    },
    fetchChannel() {
      let param = {};
      param.tenantNo = this.form.tenantNo;
      param.dictType = "channel";
      this.$store.dispatch("base_car_dictionary/list", param).then((res) => {
        this.channelList = res;
        if (this.channelList.length > 0) {
          this.form.channel = this.channelList[0].dictValue;
        }
      });
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