<template>
  <div>
    <div style="margin: 10px 0 15px 20px; color: grey">产品信息设置</div>
    <el-form
      ref="form"
      :model="form"
      label-width="130px"
      size="mini"
      :inline="true"
      class="demo-form-inline"
    >
      <el-row>
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
        <el-col :span="12">
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
        </el-col>
      </el-row>
    </el-form>
    <div style="margin: 10px 0 15px 20px; color: grey">整车报价设置</div>
    <el-form
      ref="form"
      :model="form"
      label-width="130px"
      size="mini"
      :inline="true"
      class="demo-form-inline"
    >
      <el-row>
        <el-col :span="12" v-for="(item, index) in form.priceList" :key="index">
          <el-form-item :label="item.levelName">
            <el-input
              v-model="form.priceList[index].price"
              placeholder="整车报价"
            />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div style="margin: 10px 0 15px 20px; color: grey; font-weight: 600">
      报价部位设置
    </div>
    <el-form
      ref="form"
      :model="form"
      label-width="130px"
      size="mini"
      :inline="true"
      class="demo-form-inline"
    >
      <el-row style="margin: 5px 5px 5px 20px" :gutter="20">
        <el-col :span="6"> 部位名称 </el-col>
        <el-col :span="6"> 价格占比</el-col>
        <el-col :span="6"> 排序 </el-col>
        <el-col :span="6"> 操作 </el-col>
      </el-row>
      <el-row
        v-for="(item, index) in form.priceConfigList"
        :key="index"
        style="margin: 5px 5px 5px 20px"
        :gutter="20"
      >
        <el-col :span="6" :offset="-1">
          <el-input
            v-model="form.priceConfigList[index].item"
            placeholder="部位名称"
            size="small"
          />
        </el-col>
        <el-col :span="6">
          <el-input
            v-model="form.priceConfigList[index].percent"
            placeholder="价格占比%"
            size="small"
          />
        </el-col>
        <el-col :span="6">
          <el-input
            v-model="form.priceConfigList[index].sort"
            placeholder="排序"
            size="small"
          />
        </el-col>
        <el-col :span="6">
          <el-button
            size="small"
            type="danger"
            @click="removePriceConfig(index)"
          >
            删除
          </el-button>
        </el-col>
      </el-row>
      <el-row>
        <el-col>
          <el-button
            size="small"
            type="primary"
            @click="addPriceConfig"
            style="margin-left: 30px"
          >
            新增
          </el-button>
        </el-col>
      </el-row>
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
        <el-button
          slot="reference"
          type="primary"
          size="small"
          :loading="loading"
          >修改</el-button
        >
      </el-popconfirm>
    </div>
  </div>
</template>

<script>
export default {
  name: "CFProductEdit",
  props: {
    editId: {
      type: Number,
      required: true,
    },
    editDialogVisible: {
      type: Boolean,
      required: true,
    },
  },
  mounted() {
    let param = {};
    param.id = this.editId;
    this.$store.dispatch("cf_product/getById", param).then((res) => {
      this.form = res;
      this.fetchProductType();
      this.fetchChannel();
    });
  },
  computed: {
    tenantList() {
      return this.$store.state.cf_tenant.tenantList || [];
    },
  },
  data() {
    return {
      productTypeList: [],
      channelList: [],
      form: {},
      loading: false,
    };
  },
  methods: {
    onSubmit() {
      this.loading = true;
      this.$store
        .dispatch("cf_product/edit", this.form)
        .then((response) => {
          this.$message.success("更新成功");
          this.$emit("update:editDialogVisible", false);
          this.loading = false;
        })
        .catch(() => {
          this.loading = false;
        });
    },
    onCancle() {
      this.$emit("update:editDialogVisible", false);
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
    addPriceConfig() {
      let priceConfig = {};
      priceConfig.item = "";
      priceConfig.percent = 0;
      priceConfig.sort = 0;
      this.form.priceConfigList.push(priceConfig);
    },
    removePriceConfig(index) {
      this.form.priceConfigList.splice(index, 1);
    },
  },
};
</script>

<style>
.dialog-footer {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-bottom: 20px;
}
</style>