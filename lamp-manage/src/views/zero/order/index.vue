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
              <el-form-item label="订单状态:">
                <el-select
                  v-model="queryParam.status"
                  placeholder="订单状态"
                  clearable
                >
                  <el-option
                    v-for="item in statusDict"
                    :key="item.key"
                    :label="item.value"
                    :value="item.key"
                  />
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="分销商:">
                <el-select
                  v-model="queryParam.directReferrerUserId"
                  placeholder="分销商"
                  clearable
                >
                  <el-option
                    v-for="item in distributionUserList"
                    :key="item.id"
                    :label="item.nickName"
                    :value="item.id"
                  />
                </el-select>
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
            >重置</el-button
          >
        </el-row>
      </div>
    </div>
    <v-table
      :table-property.sync="tableProperty"
      :table-data.sync="tableData"
      @fetchData="fetchData"
    >
      <template v-slot:status="data">
        <el-tag v-if="data.scope == 0" type="info">
          {{ getStatusExpain(data.scope) }}
        </el-tag>
        <el-tag v-if="data.scope == 1" type="danger">
          {{ getStatusExpain(data.scope) }}
        </el-tag>
        <el-tag v-if="data.scope == 2" type="warning">
          {{ getStatusExpain(data.scope) }}
        </el-tag>
        <el-tag v-if="data.scope == 3" type="success">
          {{ getStatusExpain(data.scope) }}
        </el-tag>
        <el-tag v-if="data.scope == 4" type="primary">
          {{ getStatusExpain(data.scope) }}
        </el-tag>
      </template>
      <template v-slot:refundFlag="data">
        <el-tag v-if="data.scope == 1" type="danger">
          {{ getRefundFlagExpain(data.scope) }}
        </el-tag>
        <el-tag v-if="data.scope == -1" type="warning">
          {{ getRefundFlagExpain(data.scope) }}
        </el-tag>
        <el-tag v-if="data.scope == 0" type="success">
          {{ getRefundFlagExpain(data.scope) }}
        </el-tag>
      </template>
      <template v-slot:operation="scope">
        <el-button size="mini" @click="shipment(scope.scope.row)">
          详情
        </el-button>
      </template>
    </v-table>
    <el-dialog
      title="订单"
      :visible.sync="shipmentDialogVisible"
      width="70%"
      :destroy-on-close="true"
      :close-on-click-modal="false"
      :fullscreen="true"
    >
      <v-deal
        v-if="shipmentDialogVisible"
        :edit-dialog-visible.sync="shipmentDialogVisible"
      />
    </el-dialog>
  </div>
</template>

<script>
import Table from "@/components/Table/index";
import Deal from "@/views/zero/order/deal";
export default {
  name: "ZeroOrder",
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
          width: "50px",
        },
        {
          prop: "orderNo",
          label: "订单编号",
          width: "250px",
        },
        {
          prop: "orderName",
          label: "订单名称",
          width: "150px",
        },
        {
          prop: "status",
          label: "订单状态",
          width: "100px",
          slot: true,
        },
        {
          prop: "amount",
          label: "金额（元）",
          width: "100px",
        },
        {
          prop: "orderTime",
          label: "下单时间",
          width: "150px",
        },
        {
          prop: "paymentTime",
          label: "付款时间",
          width: "150px",
        },
        {
          prop: "shipmentTime",
          label: "发货时间",
          width: "150px",
        },
        {
          prop: "refundFlag",
          label: "退款标识",
          width: "100px",
          slot: true,
        },
      ],
      statusDict: [
        { key: "0", value: "交易关闭" },
        { key: "1", value: "已下单" },
        { key: "2", value: "已付款" },
        { key: "3", value: "已发货" },
        { key: "4", value: "已收货" },
        { key: "5", value: "已完成" },
      ],
      tableData: {},
      addDialogVisible: false,
      // 编辑页面标识
      editDialogVisible: false,
      // 发货页面标识
      shipmentDialogVisible: false,
      // 编辑表单
      editForm: null,
      queryParam: {},
    };
  },
  computed: {
    distributionUserList() {
      return this.$store.state.zero_member.distributionUserList;
    },
  },
  created() {
    this.$store.dispatch("zero_member/distributionUserList", {});
    this.fetchData(1, 15);
  },
  watch: {
    addDialogVisible(newVal, oldVal) {
      if (!newVal) {
        this.fetchData(tableData.current, tableData.size);
      }
    },
    editDialogVisible(newVal, oldVal) {
      if (!newVal) {
        this.fetchData(tableData.current, tableData.size);
      }
    },
  },
  methods: {
    fetchData(current, size) {
      this.$store
        .dispatch("zero_order/page", {
          current: current,
          size: size,
          status: this.queryParam.status,
          directReferrerUserId: this.queryParam.directReferrerUserId,
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
      this.editForm = row;
      console.log(row);
      this.editDialogVisible = true;
    },
    shipment(row) {
      this.$store
        .dispatch("zero_order/getById", { id: row.id })
        .then((response) => {
          this.editForm = response;
          this.shipmentDialogVisible = true;
        })
        .catch(() => {});
    },
    remove(row) {
      this.$store
        .dispatch("xpxl_brand/remove", { id: row.id })
        .then((response) => {
          this.fetchData();
        })
        .catch(() => {});
    },
    getStatusExpain(status) {
      return this.statusDict.find((e) => e.key == status).value;
    },
    getRefundFlagExpain(flag) {
      if (flag == 1) {
        return "已退款";
      }
      if (flag == -1) {
        return "退款中";
      }
      if (flag == 0) {
        return "无";
      }
    },
    reset() {
      this.queryParam = {};
    },
  },
};
</script>
<style>
.snail_dialog {
  width: 100%;
  overflow: hidden;
}
</style>