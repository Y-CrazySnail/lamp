<template>
  <div style="height: 100%; height: 500px; overflow-y: scroll">
    <el-form
      ref="form"
      :model="form"
      label-width="130px"
      size="mini"
      :inline="true"
      class="demo-form-inline"
    >
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
        <el-form-item label="汽车级别代码:" prop="carLevelNo">
          <el-select
            v-model="form.carLevelNo"
            clearable
            placeholder="汽车级别代码"
          >
            <el-option
              v-for="item in levelList"
              :key="item.levelNo"
              :label="item.levelName"
              :value="item.levelNo"
            >
            </el-option>
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="整车价格(元):" prop="price0">
          <el-input
            v-model="form.price0"
            placeholder="整车价格(元)"
            type="number"
          />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="价格Key-1:" prop="priceKey1">
          <el-input v-model="form.priceKey1" placeholder="价格Key-1" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="价格Value-1(元):" prop="priceValue1">
          <el-input
            v-model="form.priceValue1"
            placeholder="价格Value-1(元)"
            type="number"
          />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="价格Key-2:" prop="priceKey2">
          <el-input v-model="form.priceKey2" placeholder="价格Key-2" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="价格Value-2(元):" prop="priceValue2">
          <el-input
            v-model="form.priceValue2"
            placeholder="价格Value-2(元)"
            type="number"
          />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="价格Key-3:" prop="priceKey3">
          <el-input v-model="form.priceKey3" placeholder="价格Key-3" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="价格Value-3(元):" prop="priceValue3">
          <el-input
            v-model="form.priceValue3"
            placeholder="价格Value-3(元)"
            type="number"
          />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="价格Key-4:" prop="priceKey4">
          <el-input v-model="form.priceKey4" placeholder="价格Key-4" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="价格Value-4(元):" prop="priceValue4">
          <el-input
            v-model="form.priceValue4"
            placeholder="价格Value-4(元)"
            type="number"
          />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="价格Key-5:" prop="priceKey5">
          <el-input v-model="form.priceKey5" placeholder="价格Key-5" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="价格Value-5(元):" prop="priceValue5">
          <el-input
            v-model="form.priceValue5"
            placeholder="价格Value-5(元)"
            type="number"
          />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="价格Key-6:" prop="priceKey6">
          <el-input v-model="form.priceKey6" placeholder="价格Key-6" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="价格Value-6(元):" prop="priceValue6">
          <el-input
            v-model="form.priceValue6"
            placeholder="价格Value-6(元)"
            type="number"
          />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="价格Key-7:" prop="priceKey7">
          <el-input v-model="form.priceKey7" placeholder="价格Key-7" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="价格Value-7(元):" prop="priceValue7">
          <el-input
            v-model="form.priceValue7"
            placeholder="价格Value-7(元)"
            type="number"
          />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="价格Key-8:" prop="priceKey8">
          <el-input v-model="form.priceKey8" placeholder="价格Key-8" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="价格Value-8(元):" prop="priceValue8">
          <el-input
            v-model="form.priceValue8"
            placeholder="价格Value-8(元)"
            type="number"
          />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="价格Key-9:" prop="priceKey9">
          <el-input v-model="form.priceKey9" placeholder="价格Key-9" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="价格Value-9(元):" prop="priceValue9">
          <el-input
            v-model="form.priceValue9"
            placeholder="价格Value-9(元)"
            type="number"
          />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="价格Key-10:" prop="priceKey10">
          <el-input v-model="form.priceKey10" placeholder="价格Key-10" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="价格Value-10(元):" prop="priceValue10">
          <el-input
            v-model="form.priceValue10"
            placeholder="价格Value-10(元)"
            type="number"
          />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="价格Key-11:" prop="priceKey11">
          <el-input v-model="form.priceKey11" placeholder="价格Key-11" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="价格Value-11(元):" prop="priceValue11">
          <el-input
            v-model="form.priceValue11"
            placeholder="价格Value-11(元)"
            type="number"
          />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="价格Key-12:" prop="priceKey12">
          <el-input v-model="form.priceKey12" placeholder="价格Key-12" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="价格Value-12(元):" prop="priceValue12">
          <el-input
            v-model="form.priceValue12"
            placeholder="价格Value-12(元)"
            type="number"
          />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="价格Key-13:" prop="priceKey13">
          <el-input v-model="form.priceKey13" placeholder="价格Key-13" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="价格Value-13(元):" prop="priceValue13">
          <el-input
            v-model="form.priceValue13"
            placeholder="价格Value-13(元)"
            type="number"
          />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="价格Key-14:" prop="priceKey14">
          <el-input v-model="form.priceKey14" placeholder="价格Key-14" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="价格Value-14(元):" prop="priceValue14">
          <el-input
            v-model="form.priceValue14"
            placeholder="价格Value-14(元)"
            type="number"
          />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="价格Key-15:" prop="priceKey15">
          <el-input v-model="form.priceKey15" placeholder="价格Key-15" />
        </el-form-item>
      </el-col>
      <el-form-item label="价格Value-15(元):" prop="priceValue15">
        <el-input
          v-model="form.priceValue15"
          placeholder="价格Value-15(元)"
          type="number"
        />
      </el-form-item>
    </el-form>
    <div class="dialog-footer">
      <el-button size="small" @click="onCancle" style="margin-right: 10px"
        >取消</el-button
      >
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
  name: "AILIBrandEdit",
  props: {
    form: {},
    editDialogVisible: {
      type: Boolean,
      required: true,
    },
  },
  mounted() {},
  computed: {
    tenantList() {
      return this.$store.state.car_film_tenant.tenantList || [];
    },
    productList() {
      return this.$store.state.car_film_product.productList || [];
    },
    levelList() {
      return this.$store.state.car_film_price.levelList || [];
    },
  },
  data() {
    return {};
  },
  methods: {
    onSubmit() {
      this.$store
        .dispatch("car_film_price/edit", this.form)
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
  align-items: center;
}
</style>