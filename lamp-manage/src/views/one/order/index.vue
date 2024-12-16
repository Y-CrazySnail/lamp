<template>
  <div class="app-container">
    <div>
      <div style="margin: 0px 0px 15px 0px">
        <el-row>
          <el-form
            :model="queryParam"
            :inline="true"
            size="mini"
            label-width="90px"
          >
            <el-col :span="6">
              <el-form-item label="客户名称:">
                <el-input
                  v-model="queryParam.orderName"
                  clearable
                  placeholder="客户名称"
                />
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="客户手机:">
                <el-input
                  v-model="queryParam.orderPhone"
                  clearable
                  placeholder="客户手机"
                />
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="客户邮箱:">
                <el-input
                  v-model="queryParam.orderEmail"
                  clearable
                  placeholder="客户邮箱"
                />
              </el-form-item>
            </el-col>
          </el-form>
        </el-row>
        <el-row>
          <el-button size="mini" type="primary" icon="el-icon-plus" @click="add"
            >创建</el-button
          >
          <el-button
            size="mini"
            type="primary"
            icon="el-icon-search"
            @click="fetchData(1, 15)"
            >查询</el-button
          >
          <el-button
            size="mini"
            type="info"
            icon="el-icon-refresh"
            @click="reset"
          >
            重置
          </el-button>
        </el-row>
      </div>
    </div>
    <v-table
      :table-property.sync="tableProperty"
      :table-data.sync="tableData"
      @fetchData="fetchData"
    >
      <template v-slot:operation="scope">
        <el-button size="mini" @click="edit(scope.scope.row)">编辑</el-button>
        <el-popconfirm
          confirm-button-text="确认"
          cancel-button-text="取消"
          icon="el-icon-info"
          icon-color="red"
          title="确认删除？"
          @confirm="remove(scope.scope.row)"
        >
          <el-button size="mini" slot="reference">删除</el-button>
        </el-popconfirm>
      </template>
    </v-table>
    <el-drawer
      title="编辑"
      :visible.sync="editDialogVisible"
      direction="rtl"
      :destroy-on-close="true"
      :wrapperClosable="false"
      size="50%"
    >
      <v-deal
        v-if="editDialogVisible"
        :form.sync="editForm"
        :edit-dialog-visible.sync="editDialogVisible"
      />
    </el-drawer>
  </div>
</template>

<script>
import Table from "@/components/Table/index";
import Deal from "@/views/one/order/deal";
export default {
  components: {
    "v-table": Table,
    "v-deal": Deal,
  },
  data() {
    return {
      tableProperty: [
        {
          prop: "id",
          label: "编号",
          width: "100px",
        },
        {
          prop: "storeName",
          label: "店铺名称",
          width: "150px",
        },
        {
          prop: "orderNo",
          label: "订单号",
          width: "150px",
        },
        {
          prop: "orderName",
          label: "订单名称",
          width: "150px",
        },
        {
          prop: "orderStatus",
          label: "订单状态",
          width: "200px",
        },
        {
          prop: "orderAmount",
          label: "订单总金额",
          width: "200px",
        },
        {
          prop: "orderRemark",
          label: "订单备注",
          width: "200px",
        },
      ],
      tableData: {},
      addDialogVisible: false,
      // 编辑页面标识
      editDialogVisible: false,
      // 编辑表单
      editForm: null,
      queryParam: {
        orderName: "",
        orderPhone: "",
        orderEmail: "",
      },
    };
  },
  created() {
    this.fetchData(1, 15);
  },
  watch: {
    addDialogVisible(newVal, oldVal) {
      if (!newVal) {
        this.fetchData(1, 15);
      }
    },
    editDialogVisible(newVal, oldVal) {
      if (!newVal) {
        this.fetchData(1, 15);
      }
    },
  },
  methods: {
    fetchData(current, size) {
      this.$store
        .dispatch("one_order/page", {
          current: current,
          size: size,
          orderName: this.queryParam.orderName,
          orderPhone: this.queryParam.orderPhone,
          orderEmail: this.queryParam.orderEmail,
        })
        .then((response) => {
          this.tableData = response;
        })
        .catch(() => {});
    },
    add() {
      this.addDialogVisible = true;
    },
    edit(row) {
      this.$store
        .dispatch("one_order/get", { id: row.id })
        .then((response) => {
          this.editForm = response;
          this.editDialogVisible = true;
        })
        .catch(() => {});
    },
    remove(row) {
      this.$store
        .dispatch("one_order/remove", { id: row.id })
        .then((response) => {
          this.fetchData(this.tableData.current, this.tableData.size);
        })
        .catch(() => {});
    },
    reset() {
      this.queryParam = {};
      this.fetchData(1, 15);
    },
  },
};
</script>
