<template>
  <div style="display: flex; flex-wrap: wrap; justify-content: center">
    <el-card
      style="width: 350px; margin: 20px"
      v-for="order in orderList"
      :key="order.id"
    >
      <el-descriptions
        :column="1"
        size="mini"
        labelClassName="discription-lable"
        border
      >
        <template slot="title">
          <span style="font-size: 14px; font-weight: 600;">{{ order.orderNo }}</span>
        </template>
        <template slot="extra">
          <el-button
            type="success"
            size="mini"
            style="background-color: #000000; border-color: #000000"
            @click="pay(order.id)"
            v-if="order.status == -1"
          >
            支付
          </el-button>
          <el-tag type="info" v-if="order.status == 1" style="color: #000000">
            已完成
          </el-tag>
        </template>
        <el-descriptions-item>
          <template slot="label">
            <span style="">下单时间</span>
          </template>
          <div style="margin: 5px">{{ order.orderTime }}</div>
        </el-descriptions-item>
        <el-descriptions-item v-if="order.status == 1">
          <template slot="label">
            <span style="">完成时间</span>
          </template>
          <div style="margin: 5px">{{ order.completeTime }}</div>
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <span style="">套餐流量</span>
          </template>
          <div style="margin: 5px">{{ order.dataTraffic }}GB</div>
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <span style="">套餐时长</span>
          </template>
          <div style="margin: 5px">{{ order.period }}个月</div>
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <span style="">套餐价格</span>
          </template>
          <div style="margin: 5px">{{ order.price }}元</div>
        </el-descriptions-item>
      </el-descriptions>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "Order",
  computed: {
    orderList() {
      return this.$store.state.order.orderList;
    },
  },
  mounted() {
    this.$store.dispatch("order/list");
  },
  data() {
    return {};
  },
  methods: {
    pay(orderId) {
      let param = {};
      param.id = orderId;
      this.$store.dispatch("order/pay", param).then((e) => {
        window.location.href = e.payurl;
        console.log(e);
      });
    },
  },
};
</script>

<style lang="scss">
.discription-lable {
  width: 70px;
}
</style>
