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
        <el-form-item label="质保卡号:" prop="qualityCardNo">
          <el-input v-model="form.qualityCardNo" placeholder="质保卡号" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="姓名:" prop="name">
          <el-input v-model="form.name" placeholder="姓名" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="手机号:" prop="phone">
          <el-input v-model="form.phone" placeholder="手机号" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="施工单位:" prop="workCompany">
          <el-input v-model="form.workCompany" placeholder="施工单位" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="施工部位:" prop="workPart">
          <el-input v-model="form.workPart" placeholder="施工部位" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="施工技师:" prop="workStaff">
          <el-input v-model="form.workStaff" placeholder="施工技师" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="施工时间:" prop="workDate">
          <el-date-picker
            v-model="form.workDate"
            type="date"
            placeholder="施工时间"
          >
          </el-date-picker>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="汽车品牌:" prop="carBrand">
          <el-input v-model="form.carBrand" placeholder="汽车品牌" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="汽车型号:" prop="carModel">
          <el-input v-model="form.carModel" placeholder="汽车型号" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="汽车颜色:" prop="carColor">
          <el-input v-model="form.carColor" placeholder="汽车颜色" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="车架号:" prop="vin">
          <el-input v-model="form.vin" placeholder="车架号" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="车牌号:" prop="plateNo">
          <el-input v-model="form.plateNo" placeholder="车牌号" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="产品代码:" prop="productNo">
          <el-select v-model="form.productNo" clearable placeholder="产品信息">
            <el-option
              v-for="item in tenantList"
              :key="item.productNo"
              :label="item.productNo + '_' + item.productName"
              :value="item.productNo"
            >
            </el-option>
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="产品级别代码:" prop="productLevelNo">
          <el-select
            v-model="form.productLevelNo"
            clearable
            placeholder="产品信息"
          >
            <el-option
              v-for="item in productList"
              :key="item.productLevelNo"
              :label="item.productLevelNo + '_' + item.productLevelName"
              :value="item.productLevelNo"
            >
            </el-option>
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="贴车价格(元):" prop="price">
          <el-input v-model="form.price" placeholder="价格" type="number" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="质保年限:" prop="guaranteePeriod">
          <el-input
            v-model="form.guaranteePeriod"
            placeholder="价格"
            type="number"
          />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="盒号:" prop="boxNumber">
          <el-input v-model="form.boxNumber" placeholder="盒头号" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="卷心:" prop="rollNumber">
          <el-input v-model="form.rollNumber" placeholder="卷心号" />
        </el-form-item>
      </el-col>
      <el-form-item label="审核标识:" prop="approveFlag">
        <el-switch
          style="display: block"
          v-model="form.approveFlag"
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
  name: "AILIQualityAdd",
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
    };
  },
  computed: {
    tenantList() {
      return this.$store.state.car_film_tenant.tenantList || [];
    },
    productList() {
      return this.$store.state.car_film_product.productList || [];
    },
  },
  methods: {
    onSubmit() {
      if (this.form.productNo) {
        this.form.productName = this.tenantList.find(
          (e) => e.productNo == this.form.productNo
        ).productName;
      }
      if (this.form.productLevelNo) {
        this.form.productLevelName = this.productList.find(
          (e) => e.productLevelNo == this.form.productLevelNo
        ).productLevelName;
      }
      if (!this.form.workDate || !this.form.guaranteePeriod) {
        this.$message({
          message: "请设置施工日期和质保年限",
          type: "error",
        });
      }
      this.form.validityDate = new Date(this.form.workDate);
      this.form.validityDate.setFullYear(
        this.form.workDate.getFullYear() + Number(this.form.guaranteePeriod)
      );
      this.$store
        .dispatch("car_film_quality/save", this.form)
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