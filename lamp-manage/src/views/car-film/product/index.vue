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
            <el-form-item label="产品:" prop="tenantNo">
              <el-select
                v-model="queryParams.tenantNo"
                clearable
                placeholder="产品信息"
              >
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
      :operate-width="'275px'"
    >
      <template v-slot:status="data">{{
        data.scope === "1" ? "上线" : "下线"
      }}</template>
      <template v-slot:operation="scope">
        <el-button
          size="mini"
          icon="el-icon-edit"
          @click="edit(scope.scope.row)"
        >
          编辑
        </el-button>
        <el-button
          size="mini"
          icon="el-icon-price-tag"
          @click="edit(scope.scope.row)"
          style="margin-right: 10px"
        >
          报价
        </el-button>
        <el-popconfirm
          confirm-button-text="确认"
          cancel-button-text="取消"
          icon="el-icon-info"
          icon-color="red"
          title="确认删除？"
          @confirm="remove(scope.scope.row)"
        >
          <el-button size="mini" icon="el-icon-delete" slot="reference">
            删除
          </el-button>
        </el-popconfirm>
      </template>
    </v-table>
    <el-drawer
      title="创建"
      :visible.sync="addDialogVisible"
      size="50%"
    >
      <v-add
        v-if="addDialogVisible"
        :add-dialog-visible.sync="addDialogVisible"
      />
    </el-drawer>
    <el-drawer
      title="编辑"
      :visible.sync="editDialogVisible"
      size="50%"
    >
      <v-edit
        v-if="editDialogVisible"
        :edit-id.sync="editId"
        :edit-dialog-visible.sync="editDialogVisible"
      />
    </el-drawer>
  </div>
</template>

<script>
import Table from "@/components/Table/index.vue";
import Add from "@/views/car-film/product/add";
import Edit from "@/views/car-film/product/edit";
export default {
  name: "CarFilmProductIndex",
  components: {
    "v-table": Table,
    "v-edit": Edit,
    "v-add": Add,
  },
  data() {
    return {
      tableProperty: [
        {
          prop: "tenantNo",
          label: "客户代码",
          width: "150px",
        },
        {
          prop: "productType",
          label: "产品类型",
          width: "200px",
        },
        {
          prop: "productNo",
          label: "产品代码",
          width: "150px",
        },
        {
          prop: "productName",
          label: "产品名称",
          width: "150px",
        },
        {
          prop: "channel",
          label: "渠道",
          width: "150px",
        },
        {
          prop: "status",
          label: "状态",
          width: "150px",
          slot: true,
        },
        {
          prop: "sort",
          label: "排序",
          width: "150px",
        },
      ],
      tableData: {},
      queryParams: {
        productNo: "",
      },
      addDialogVisible: false,
      // 编辑页面标识
      editDialogVisible: false,
      // 编辑表单ID
      editId: null,
    };
  },
  created() {
    this.fetchData(1, 15);
    this.$store.dispatch("cf_tenant/list", null);
  },
  computed: {
    tenantList() {
      return this.$store.state.cf_tenant.tenantList || [];
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
        .dispatch("cf_product/page", {
          current: current,
          size: size,
          tenantNo: this.queryParams.tenantNo,
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
      this.editId = row.id;
      this.editDialogVisible = true;
    },
    remove(row) {
      this.$store
        .dispatch("car_film_product/remove", { id: row.id })
        .then((response) => {
          this.fetchData(this.tableData.current, this.tableData.size);
        })
        .catch(() => {});
    },
  },
};
</script>