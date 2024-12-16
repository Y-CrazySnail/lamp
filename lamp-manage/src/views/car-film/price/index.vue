<template>
  <div class="app-container">
    <div style="margin: 0px 0px 15px 0px">
      <el-row>
        <el-form
          ref="queryParams"
          :model="queryParams"
          :inline="true"
          size="mini"
          label-width="90px"
        >
          <el-col :span="6">
            <el-form-item label="产品:" prop="productNo">
              <el-select v-model="queryParams.productNo" clearable>
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
          <el-col :span="6">
            <el-form-item label="产品级别:" prop="productLevelNo">
              <el-select v-model="queryParams.productLevelNo" clearable>
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
        </el-form>
        <el-button size="mini" type="primary" icon="el-icon-plus" @click="add">
          创建
        </el-button>
        <el-button
          size="mini"
          type="primary"
          icon="el-icon-search"
          @click="handleQuery"
        >
          查询
        </el-button>
        <el-button
          size="mini"
          type="info"
          icon="el-icon-refresh"
          @click="handleReset"
        >
          重置
        </el-button>
      </el-row>
    </div>
    <v-table
      :table-property.sync="tableProperty"
      :table-data.sync="tableData"
      @fetchData="fetchData"
    >
      <template v-slot:productNo="data">
        {{ getProductField(data.scope) }}
      </template>
      <template v-slot:productLevelNo="data">
        {{ getProductLevelField(data.scope) }}
      </template>
      <template v-slot:carLevelNo="data">
        {{ getLevelField(data.scope) }}
      </template>
      <template v-slot:operation="scope">
        <el-button
          size="mini"
          icon="el-icon-edit"
          @click="edit(scope.scope.row)"
          >编辑</el-button
        >
        <el-popconfirm
          confirm-button-text="确认"
          cancel-button-text="取消"
          icon="el-icon-info"
          icon-color="red"
          title="确认删除？"
          @confirm="remove(scope.scope.row)"
        >
          <el-button size="mini" icon="el-icon-delete" slot="reference"
            >删除</el-button
          >
        </el-popconfirm>
      </template>
    </v-table>
    <el-dialog
      class="snail_dialog"
      title="创建"
      :visible.sync="addDialogVisible"
    >
      <v-add
        v-if="addDialogVisible"
        :add-dialog-visible.sync="addDialogVisible"
      />
    </el-dialog>
    <el-dialog
      class="snail_dialog"
      title="编辑"
      :visible.sync="editDialogVisible"
    >
      <v-edit
        v-if="editDialogVisible"
        :form.sync="editForm"
        :edit-dialog-visible.sync="editDialogVisible"
      />
    </el-dialog>
  </div>
</template>

<script>
import Table from "@/components/Table/index.vue";
import Add from "@/views/car-film/price/add";
import Edit from "@/views/car-film/price/edit";
export default {
  name: "carFilmPriceenant",
  components: {
    "v-table": Table,
    "v-edit": Edit,
    "v-add": Add,
  },
  data() {
    return {
      tableProperty: [
        {
          prop: "id",
          label: "编号",
          width: "50px",
        },
        {
          prop: "productNo",
          label: "产品代码",
          width: "200px",
          slot: true,
        },
        {
          prop: "productLevelNo",
          label: "产品级别代码",
          width: "250px",
          slot: true,
        },
        {
          prop: "carLevelNo",
          label: "汽车级别代码",
          width: "100px",
          slot: true,
        },
        {
          prop: "price0",
          label: "整车价格(元)",
          width: "100px",
        },
        {
          prop: "priceKey1",
          label: "价格Key-1",
          width: "100px",
        },
        {
          prop: "priceValue1",
          label: "价格Value-1(元)",
          width: "120px",
        },
        {
          prop: "priceKey2",
          label: "价格Key-2",
          width: "100px",
        },
        {
          prop: "priceValue2",
          label: "价格Value-2(元)",
          width: "120px",
        },
        {
          prop: "priceKey3",
          label: "价格Key-3",
          width: "100px",
        },
        {
          prop: "priceValue3",
          label: "价格Value-3(元)",
          width: "120px",
        },
        {
          prop: "priceKey4",
          label: "价格Key-4",
          width: "100px",
        },
        {
          prop: "priceValue4",
          label: "价格Value-4(元)",
          width: "120px",
        },
        {
          prop: "priceKey5",
          label: "价格Key-5",
          width: "100px",
        },
        {
          prop: "priceValue5",
          label: "价格Value-5(元)",
          width: "120px",
        },
        {
          prop: "priceKey6",
          label: "价格Key-6",
          width: "100px",
        },
        {
          prop: "priceValue6",
          label: "价格Value-6(元)",
          width: "120px",
        },
        {
          prop: "priceKey7",
          label: "价格Key-7",
          width: "100px",
        },
        {
          prop: "priceValue7",
          label: "价格Value-7(元)",
          width: "120px",
        },
        {
          prop: "priceKey8",
          label: "价格Key-8",
          width: "100px",
        },
        {
          prop: "priceValue8",
          label: "价格Value-8(元)",
          width: "120px",
        },
        {
          prop: "priceKey9",
          label: "价格Key-9",
          width: "100px",
        },
        {
          prop: "priceValue9",
          label: "价格Value-9(元)",
          width: "120px",
        },
        {
          prop: "priceKey10",
          label: "价格Key-10",
          width: "100px",
        },
        {
          prop: "priceValue10",
          label: "价格Value-10(元)",
          width: "120px",
        },
        {
          prop: "priceKey11",
          label: "价格Key-11",
          width: "100px",
        },
        {
          prop: "priceValue11",
          label: "价格Value-11(元)",
          width: "120px",
        },
        {
          prop: "priceKey12",
          label: "价格Key-12",
          width: "100px",
        },
        {
          prop: "priceValue12",
          label: "价格Value-12(元)",
          width: "120px",
        },
        {
          prop: "priceKey13",
          label: "价格Key-13",
          width: "100px",
        },
        {
          prop: "priceValue13",
          label: "价格Value-13(元)",
          width: "120px",
        },
        {
          prop: "priceKey14",
          label: "价格Key-14",
          width: "100px",
        },
        {
          prop: "priceValue14",
          label: "价格Value-14(元)",
          width: "120px",
        },
        {
          prop: "priceKey15",
          label: "价格Key-15",
          width: "100px",
        },
        {
          prop: "priceValue15",
          label: "价格Value-15(元)",
          width: "120px",
        },
      ],
      tableData: {},
      queryParams: {
        productNo: "",
        productName: "",
        companyName: "",
        companyNo: "",
        managerName: "",
        managerPhone: "",
        miniProgramFlag: "",
        officialWebsiteFlag: "",
      },
      addDialogVisible: false,
      // 编辑页面标识
      editDialogVisible: false,
      // 编辑表单
      editForm: null,
    };
  },
  created() {
    this.fetchData(1, 15);
    this.$store.dispatch("car_film_tenant/list", null);
    this.$store.dispatch("car_film_product/list", null);
    this.$store.dispatch("car_film_price/levelList", null);
  },
  watch: {
    addDialogVisible(newVal, oldVal) {
      if (!newVal) {
        this.fetchData(this.tableData.current, this.tableData.size);
      }
    },
    editDialogVisible(newVal, oldVal) {
      if (!newVal) {
        this.fetchData(this.tableData.current, this.tableData.size);
      }
    },
  },
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
  methods: {
    fetchData(current, size) {
      this.$store
        .dispatch("car_film_price/pages", {
          current: current,
          size: size,
          productNo: this.queryParams.productNo,
          productLevelNo: this.queryParams.productLevelNo,
        })
        .then((response) => {
          this.tableData = response;
        })
        .catch(() => {});
    },
    handleQuery() {
      this.fetchData(this.tableData.current, this.tableData.size);
    },
    handleReset() {
      this.$refs.queryParams.resetFields();
      this.fetchData(this.tableData.current, this.tableData.size);
    },
    add() {
      this.addDialogVisible = true;
    },
    edit(row) {
      this.editForm = row;
      this.editDialogVisible = true;
    },
    remove(row) {
      this.$store
        .dispatch("car_film_price/remove", { id: row.id })
        .then((response) => {
          this.fetchData(this.tableData.current, this.tableData.size);
        })
        .catch(() => {});
    },
    getProductField(productNo) {
      let tenant = this.tenantList.find((e) => e.productNo == productNo);
      if (tenant) {
        return tenant.productNo + "_" + tenant.productName;
      }
    },
    getProductLevelField(productLevelNo) {
      let product = this.productList.find(
        (e) => e.productLevelNo == productLevelNo
      );
      if (product) {
        return product.productLevelNo + "_" + product.productLevelName;
      }
    },
    getLevelField(levelNo) {
      let level = this.levelList.find((e) => e.levelNo == levelNo);
      if (level) {
        return level.levelName;
      }
    },
  },
};
</script>