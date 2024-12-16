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
            <el-form-item label="产品代码:" prop="productNo">
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
            <el-form-item label="手机号:" prop="phone">
              <el-input
                v-model="queryParams.phone"
                clearable
                placeholder="手机号"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="质保卡号:" prop="qualityCardNo">
              <el-input
                v-model="queryParams.qualityCardNo"
                clearable
                placeholder="质保卡号"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="车牌号:" prop="plateNo">
              <el-input
                v-model="queryParams.plateNo"
                clearable
                placeholder="车牌号"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="车架号:" prop="vin">
              <el-input
                v-model="queryParams.vin"
                clearable
                placeholder="车架号"
              />
            </el-form-item>
          </el-col>
        </el-form>
        <el-col :span="10">
          <el-button
            size="mini"
            type="primary"
            icon="el-icon-plus"
            @click="add"
          >
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
        </el-col>
      </el-row>
    </div>
    <v-table
      :table-property.sync="tableProperty"
      :table-data.sync="tableData"
      @fetchData="fetchData"
    >
      <template v-slot:approveFlag="data">
        <el-tag type="success" v-if="data.scope == '1'">已审核</el-tag>
        <el-tag type="danger" v-if="data.scope == '0'">未审核</el-tag>
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
import Add from "@/views/car-film/quality/add";
import Edit from "@/views/car-film/quality/edit";
export default {
  name: "carFilmQualityIndex",
  components: {
    "v-table": Table,
    "v-edit": Edit,
    "v-add": Add,
  },
  data() {
    return {
      tableProperty: [
        {
          prop: "approveFlag",
          label: "审核标识",
          width: "100px",
          slot: true,
        },
        {
          prop: "name",
          label: "姓名",
          width: "100px",
        },
        {
          prop: "phone",
          label: "手机号",
          width: "100px",
        },
        {
          prop: "qualityCardNo",
          label: "质保卡号",
          width: "200px",
        },
        {
          prop: "plateNo",
          label: "车牌号",
          width: "100px",
        },
        {
          prop: "vin",
          label: "车架号",
          width: "150px",
        },
        {
          prop: "productNo",
          label: "产品代码",
          width: "100px",
        },
        {
          prop: "productName",
          label: "产品名称",
          width: "100px",
        },
        {
          prop: "productLevelNo",
          label: "产品级别代码",
          width: "100px",
        },
        {
          prop: "productLevelName",
          label: "产品级别名称",
          width: "150px",
        },
        {
          prop: "price",
          label: "价格",
          width: "100px",
        },
        {
          prop: "carModel",
          label: "汽车型号",
          width: "100px",
        },
        {
          prop: "carColor",
          label: "汽车颜色",
          width: "100px",
        },
        {
          prop: "workDate",
          label: "施工时间",
          width: "100px",
        },
        {
          prop: "validityDate",
          label: "质保有效期",
          width: "100px",
        },
        {
          prop: "workCompany",
          label: "施工单位",
          width: "250px",
        },
        {
          prop: "workStaff",
          label: "施工技师",
          width: "100px",
        },
        {
          prop: "workPart",
          label: "施工部位",
          width: "100px",
        },
        {
          prop: "rollNumber",
          label: "卷心号",
          width: "200px",
        },
        {
          prop: "boxNumber",
          label: "盒头号",
          width: "200px",
        },
      ],
      tableData: {},
      queryParams: {
        productNo: "",
        phone: "",
        qualityCardNo: "",
        plateNo: "",
        vin: "",
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
  },
  computed: {
    tenantList() {
      return this.$store.state.car_film_tenant.tenantList || [];
    },
    productList() {
      return this.$store.state.car_film_product.productList || [];
    },
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
  methods: {
    fetchData(current, size) {
      this.$store
        .dispatch("car_film_quality/pages", {
          current: current,
          size: size,
          productNo: this.queryParams.productNo,
          phone: this.queryParams.phone,
          qualityCardNo: this.queryParams.qualityCardNo,
          plateNo: this.queryParams.plateNo,
          vin: this.queryParams.vin,
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
        .dispatch("car_film_quality/remove", { id: row.id })
        .then((response) => {
          this.fetchData(this.tableData.current, this.tableData.size);
        })
        .catch(() => {});
    },
  },
};
</script>