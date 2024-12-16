<template>
  <div>
    <div style="height: 100%; min-height: 400px; overflow-y: scroll">
      <div style="width: 90%; margin-left: 5%; margin-right: 5%">
        <el-steps
          :active="Number(form.status) - 1"
          finish-status="success"
          process-status="success"
        >
          <el-step title="已下单"></el-step>
          <el-step title="已付款"></el-step>
          <el-step title="已发货"></el-step>
          <el-step title="已收货"></el-step>
        </el-steps>
        <el-divider></el-divider>
        <div
          style="
            font-size: 16px;
            font-weight: 700;
            color: black;
            margin-bottom: 20px;
          "
        >
          商品信息
        </div>
        <el-table :data="form.orderItemList" style="width: 100%" :border="true">
          <el-table-column
            prop="productName"
            label="商品名称"
            align="center"
          >
          </el-table-column>
          <el-table-column prop="price" label="单品价格（元）" align="center">
          </el-table-column>
          <el-table-column
            prop="quantity"
            label="下单数量（件）"
            align="center"
          >
          </el-table-column>
          <el-table-column prop="amount" label="总价（元）" align="center">
          </el-table-column>
        </el-table>
        <el-divider></el-divider>
        <el-alert
          style="margin: 5px 0 5px 0"
          :title="'卖家备注：' + form.remark"
          type="warning"
          :closable="false"
          v-if="form.remark"
        />
        <el-descriptions
          title="订单信息"
          :column="2"
          :border="true"
          style="margin-top: 25px"
        >
          <el-descriptions-item label="订单编号">
            {{ form.orderNo }}
          </el-descriptions-item>
          <el-descriptions-item label="下单时间">
            {{ form.orderTime }}
          </el-descriptions-item>
          <el-descriptions-item label="付款时间" v-if="form.status > 1">
            {{ form.paymentTime }}
          </el-descriptions-item>
          <el-descriptions-item label="付款金额" v-if="form.status > 1">
            {{ form.amount }}元
          </el-descriptions-item>
          <el-descriptions-item label="运单号" v-if="form.status > 2">
            {{ form.waybillNo }}
          </el-descriptions-item>
          <el-descriptions-item label="发货时间" v-if="form.status > 2">
            {{ form.shipmentTime }}
          </el-descriptions-item>
          <el-descriptions-item label="收货时间" v-if="form.status > 3">
            {{ form.estimatedShipmentTime }}
          </el-descriptions-item>
          <el-descriptions-item label="关闭时间" v-if="form.status == 0">
            {{ form.closeTime }}
          </el-descriptions-item>
        </el-descriptions>
        <div v-if="form.status >= 2">
          <span style="font-weight: 600">快递运单号：</span>
          <el-input
            style="width: 300px; margin: 5px 10px 0 0"
            v-model="waybillNo"
            placeholder="请输入快递运单号"
          ></el-input>
          <el-button type="primary" plain @click="shipment" v-if="form.status == 2">发货</el-button>
          <el-button type="primary" plain @click="shipment" v-if="form.status > 2">修改</el-button>
        </div>
        <el-descriptions
          title="付款信息"
          :column="2"
          border
          style="margin-top: 25px"
          v-if="form.status > 1"
        >
          <el-descriptions-item label="商户交易号">
            {{ form.paymentTransactionId }}
          </el-descriptions-item>
          <el-descriptions-item label="交易类型">
            {{ form.paymentTradeType }}
          </el-descriptions-item>
          <el-descriptions-item label="交易状态">
            {{ form.paymentTradeState }}
          </el-descriptions-item>
          <el-descriptions-item label="交易成功时间">
            {{ form.paymentSuccessTime }}
          </el-descriptions-item>
          <el-descriptions-item label="银行类型">
            {{ form.paymentBankType }}
          </el-descriptions-item>
          <el-descriptions-item label="货币">
            {{ form.paymentCurrency }}
          </el-descriptions-item>
          <el-descriptions-item label="总额（分）">
            {{ form.paymentTotal }}
          </el-descriptions-item>
        </el-descriptions>
        <el-descriptions
          title="退款信息"
          :column="2"
          border
          style="margin-top: 25px"
          v-if="form.refundFlag != '0'"
        >
          <el-descriptions-item label="退款类型">
            {{ form.refundType == 1 ? "仅退款" : "退货退款" }}
          </el-descriptions-item>
          <el-descriptions-item label="申请退款金额（元）">
            {{ form.refundAmount }}
          </el-descriptions-item>
          <el-descriptions-item label="申请退款原因">
            {{ form.refundReason }}
          </el-descriptions-item>
          <el-descriptions-item label="微信支付退款号">
            {{ form.refundId }}
          </el-descriptions-item>
          <el-descriptions-item label="退款单号">
            {{ form.refundNo }}
          </el-descriptions-item>
          <el-descriptions-item label="退款入账账户">
            {{ form.refundReceivedAccount }}
          </el-descriptions-item>
          <el-descriptions-item label="退款成功时间">
            {{ form.refundSuccessTime }}
          </el-descriptions-item>
          <el-descriptions-item label="退款渠道">
            {{ form.refundChannel }}
          </el-descriptions-item>
          <el-descriptions-item label="退款状态">
            {{ form.refundStatus }}
          </el-descriptions-item>
          <el-descriptions-item label="资金账户">
            {{ form.refundFundsAccount }}
          </el-descriptions-item>
          <el-descriptions-item label="用户退款金额（分）">
            {{ form.refundPayerRefund }}
          </el-descriptions-item>
          <el-descriptions-item label="手续费退款金额">
            {{ form.refundFee }}
          </el-descriptions-item>
          <el-descriptions-item label="处理退款">
            <el-button
              type="danger"
              plain
              @click="refund"
              size="mini"
              v-show="form.refundFlag == '-1' && !form.refundId"
            >
              同意
            </el-button>
            <el-tag
              type="success"
              v-show="form.refundFlag == '-1' && form.refundId"
            >
              退款中
            </el-tag>
            <el-tag type="success" v-show="form.refundFlag == 1">
              已退款
            </el-tag>
          </el-descriptions-item>
        </el-descriptions>
        <div
          style="
            margin: 5px 0 5px 0;
            display: flex;
            align-items: center;
            height: 40px;
          "
          v-if="form.status > 2 && form.logistics"
        >
          <span style="font-weight: 600; font-size: 15px">快递：</span>
          <el-avatar :size="40" :src="form.logistics.logo"></el-avatar>
          <span style="font-weight: 600; font-size: 14px">{{
            form.logistics.expressName
          }}</span>
          <el-tag style="margin-left: 10px" type="success">{{
            getLogisticsStatus(form.logistics.status)
          }}</el-tag>
        </div>
        <el-timeline
          style="margin-top: 10px"
          v-if="form.status > 2 && form.logistics"
        >
          <el-timeline-item
            v-for="(activity, index) in form.logistics.logisticsList"
            :key="index"
            :timestamp="activity.time"
          >
            {{ activity.context }}
          </el-timeline-item>
        </el-timeline>
        <el-divider></el-divider>
        <el-descriptions
          title="商品邮寄地址信息"
          :column="2"
          border
          style="margin-top: 25px"
        >
          <el-descriptions-item label="姓名">
            {{ form.addressName }}
          </el-descriptions-item>
          <el-descriptions-item label="手机号">
            {{ form.addressPhone }}
          </el-descriptions-item>
          <el-descriptions-item label="省市区">
            {{
              form.addressProvince +
              " " +
              form.addressCity +
              " " +
              form.addressDistrict
            }}
          </el-descriptions-item>
          <el-descriptions-item label="详细地址">
            {{ form.addressStreet + ' ' + form.addressDetail }}
          </el-descriptions-item>
        </el-descriptions>
      </div>
    </div>
  </div>
</template>

<script>
const moment = require("moment");
export default {
  name: "ZeroShipment",
  props: {
    editDialogVisible: {
      type: Boolean,
      required: true,
    },
  },
  mounted() {},
  computed: {
    form: {
      get() {
        return this.$store.state.zero_order.order;
      },
      set() {},
    },
  },
  data() {
    return {
      waybillNo: "",
    };
  },
  methods: {
    shipment() {
      this.form.waybillNo = this.waybillNo;
      this.form.status = 3;
      const now = new Date();
      this.form.shipmentTime = moment(now).format("YYYY-MM-DD HH:mm:ss");
      this.$store
        .dispatch("zero_order/update", this.form)
        .then((response) => {
          this.$store
            .dispatch("zero_order/getById", { id: this.form.id })
            .then((order) => {
              this.form = order;
            });
          this.$message.success(response);
        })
        .catch(() => {});
    },
    refund() {
      this.$store.dispatch("zero_order/refund", this.form).then((response) => {
        this.$store
          .dispatch("zero_order/getById", { id: this.form.id })
          .then((order) => {
            this.form = order;
          });
        this.$message.success(response);
      });
    },
    getLogisticsStatus(status) {
      if (status == "1") {
        return "在途中";
      }
      if (status == "2") {
        return "派送中";
      }
      if (status == "3") {
        return "已签收";
      }
      if (status == "4") {
        return "派送失败";
      }
      if (status == "5") {
        return "揽收";
      }
      if (status == "6") {
        return "退回";
      }
      if (status == "7") {
        return "转单";
      }
      if (status == "8") {
        return "疑难";
      }
      if (status == "9") {
        return "退签";
      }
      if (status == "10") {
        return "待清关";
      }
      if (status == "11") {
        return "清关中";
      }
      if (status == "12") {
        return "已清关";
      }
      if (status == "13") {
        return "清关异常";
      }
    },
    onCancle() {
      this.$emit("update:editDialogVisible", false);
    },
  },
};
</script>

<style>
</style>