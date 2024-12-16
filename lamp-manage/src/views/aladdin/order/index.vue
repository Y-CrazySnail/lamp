<template>
  <div class="app-container">
    <div style="margin: 0px 0px 15px 0px">
      <el-row>
        <el-form
          ref="queryParams"
          :model="queryParams"
          :inline="true"
          size="mini"
        >
          <el-form-item>
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
          </el-form-item>
        </el-form>
      </el-row>
    </div>
    <v-table
      :table-property.sync="tableProperty"
      :table-data.sync="tableData"
      @fetchData="fetchData"
    >
      <template v-slot:operation="scope">
        <el-button size="mini" @click="pay(scope.scope.row)" v-if="scope.scope.row.status == -1">支付</el-button>
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
    <el-dialog
      class="snail_dialog"
      title="创建"
      :visible.sync="addDialogVisible"
      width="50%"
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
      width="50%"
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
import Table from "@/components/Table/index";
import Add from "@/views/aladdin/order/add";
import Edit from "@/views/aladdin/order/edit";
export default {
  name: "AladdinOrder",
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
          prop: "orderNo",
          label: "订单号",
          width: "200px",
        },
        {
          prop: "tradeNo",
          label: "支付号",
          width: "200px",
        },
        {
          prop: "memberId",
          label: "会员ID",
          width: "60px",
        },
        {
          prop: "status",
          label: "状态",
          width: "50px",
        },
        {
          prop: "serviceId",
          label: "服务ID",
          width: "60px",
        },
        {
          prop: "orderTime",
          label: "下单时间",
          width: "200px",
        },
        {
          prop: "completeTime",
          label: "完成时间",
          width: "200px",
        },
        {
          prop: "dataTraffic",
          label: "流量（GB）",
          width: "100px",
        },
        {
          prop: "period",
          label: "周期（月）",
          width: "100px",
        },
        {
          prop: "price",
          label: "价格（元）",
          width: "100px",
        },
        {
          prop: "remark",
          label: "备注",
          width: "100px",
        },
      ],
      queryParams: {
        wechat: "",
        email: "",
      },
      tableData: {},
      addDialogVisible: false,
      editDialogVisible: false,
      editForm: null,
    };
  },
  created() {
    this.fetchData(1, 15);
    this.$store.dispatch("aladdin_member/all", null);
    this.$store.dispatch("aladdin_package/all", null);
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
        .dispatch("aladdin_order/page", {
          current: current,
          size: size,
          wechat: this.queryParams.wechat,
          email: this.queryParams.email,
        })
        .then((response) => {
          this.tableData = response;
        });
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
    pay(order) {
      let param = {};
      param.id = order.id;
      this.$store.dispatch("aladdin_order/pay", param).then((e) => {
        window.location.href = e.payurl
      });
    },
    remove(row) {
      this.$store
        .dispatch("aladdin_order/remove", { id: row.id })
        .then((response) => {
          this.fetchData(this.tableData.current, this.tableData.size);
        })
        .catch(() => {});
    },
    package(row) {},
  },
};
</script>
