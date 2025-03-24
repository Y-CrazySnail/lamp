<template>
  <lamp-card>
    <div
      style="
        width: 100%;
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
      "
      v-show="surplusDays == 0"
    >
      <h5>购买服务</h5>
      <p style="color: #697a8d">您还没有生效中的服务，请前往会员商店购买</p>
      <LampButton @click="toStore" style="width: 130px; margin-top: 10px">
        <img
          src="./cart.png"
          style="width: 1.1rem; height: 1.1rem; margin-right: 3px"
        />
        会员商店
      </LampButton>
    </div>
    <div style="width: 100%" v-show="surplusDays > 0">
      <div
        style="
          display: flex;
          color: #697a8d;
          justify-content: space-between;
          width: 100%;
          height: 80px;
          padding: 30px;
        "
      >
        <div style="display: flex">
          <div style="">
            <img src="./briefcase.png" class="user-avatar" />
          </div>
          <div style="height: 50px; margin-left: 15px">
            <div style="font-size: 15px">基础专线服务</div>
            <div style="font-size: 13px; margin-top: 5px">
              订单号：{{ order ? order.orderNo : "-" }}
            </div>
          </div>
        </div>
        <div class="flex-center" style="height: 50px">
          <div
            style="
              padding: 2px;
              font-size: 12px;
              background-color: #71dd37;
              color: #fff;
              border-radius: 4px;
              padding: 4px 4px;
              font-weight: 600;
            "
          >
            生效中
          </div>
        </div>
      </div>
      <div
        style="
          display: flex;
          color: #697a8d;
          justify-content: space-between;
          width: 100%;
          height: 50px;
          padding: 0 30px;
          flex-wrap: wrap;
        "
      >
        <div
          style="
            background-color: #f5f5f5;
            display: flex;
            flex-direction: column;
            justify-content: space-between;
            padding: 5px;
            border-radius: 5px;
            font-size: 14px;
            line-height: 1.1;
          "
        >
          <div>金额：¥ {{ order ? order.price : "-" }}</div>
          <div>
            实付：¥ {{ order ? order.price - order.deductBalance : "-" }}
          </div>
        </div>
        <div
          style="
            display: flex;
            flex-direction: column;
            justify-content: space-between;
            padding: 5px;
            border-radius: 5px;
            font-size: 14px;
            line-height: 1.1;
          "
        >
          <div>生效日期：{{ order ? order.orderTime : "-" }}</div>
          <div>失效日期：{{ member.expiryDate + " 00:00:00" }}</div>
        </div>
      </div>
      <div style="margin: 10px 0">
        <div style="border-bottom: 1px solid #d9dee3; height: 1px"></div>
      </div>
      <div
        style="
          display: flex;
          color: #697a8d;
          justify-content: space-between;
          width: 100%;
          height: 30px;
          padding: 0 30px;
        "
      >
        <div
          style="
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 5px;
            border-radius: 5px;
            font-size: 13px;
            line-height: 1.1;
          "
        >
          <div>已使用/总时长：{{ usedDays }}/{{ allDays }} 天</div>
        </div>
        <div
          style="
            display: flex;
            padding: 5px;
            border-radius: 5px;
            font-size: 13px;
            line-height: 1.1;
          "
        >
          <div
            style="
              font-size: 12px;
              background-color: #ffab00;
              color: #fff;
              border-radius: 4px;
              padding: 4px 6px;
              font-weight: 600;
            "
          >
            {{ surplusDays }}天后到期
          </div>
        </div>
      </div>
      <div
        style="
          display: flex;
          color: #697a8d;
          justify-content: space-between;
          width: 100%;
          height: 30px;
          padding: 0 30px;
        "
      >
        <div
          style="
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 5px;
            border-radius: 5px;
            font-size: 13px;
            line-height: 1.1;
          "
        >
          <div>
            已使用/总流量：{{
              (
                (member.monthBandwidthDown + member.monthBandwidthUp) /
                1024 /
                1024 /
                1024
              ).toFixed(2)
            }}/{{ (member.monthBandwidth / 1024 / 1024 / 1024).toFixed(2) }} GB
          </div>
        </div>
        <div
          style="
            display: flex;
            padding: 5px;
            border-radius: 5px;
            font-size: 13px;
            line-height: 1.1;
          "
        >
          剩余：{{
            (
              ((member.monthBandwidth -
                member.monthBandwidthDown +
                member.monthBandwidthUp) /
                member.monthBandwidth) *
              100
            ).toFixed(2)
          }}%
        </div>
      </div>
      <div style="padding: 0 35px; margin: 10px 0">
        <el-progress
          :text-inside="true"
          :stroke-width="20"
          :percentage="
            member.monthBandwidth
              ? Math.floor(
                  ((member.monthBandwidth -
                    member.monthBandwidthDown +
                    member.monthBandwidthUp) /
                    member.monthBandwidth) *
                    100
                )
              : 0
          "
          status="success"
        />
      </div>
      <div
        style="
          display: flex;
          color: #697a8d;
          justify-content: space-between;
          width: 100%;
          height: 30px;
          padding: 0 30px;
        "
      >
        <div
          style="
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 5px;
            border-radius: 5px;
            font-size: 13px;
            line-height: 1.1;
          "
        >
          <div>重置日期：{{ resetDay }}</div>
        </div>
      </div>
    </div>
  </lamp-card>
</template>

<script>
import LampCard from "@/components/LampCard/index";
import LampButton from "@/components/LampButton/index";
export default {
  name: "Service",
  components: { LampCard, LampButton },
  computed: {
    order() {
      let o = null;
      let orderList = this.$store.state.member.member.orderList;
      if (!orderList) return o;
      for (let i = 0; i < orderList.length; i++) {
        if (orderList[i].status == 1 && orderList[i].type == "regular") {
          o = orderList[i];
          break;
        }
      }
      return o;
    },
    device() {
      return this.$store.state.app.device;
    },
    member() {
      return this.$store.state.member.member;
    },
    allDays() {
      if (!this.order) return "-";
      const start = new Date(this.order.orderTime.substring(0, 10));
      const expiryDate = new Date(this.member.expiryDate);
      const timeDifference = expiryDate - start;
      const daysDifference = Math.floor(timeDifference / (1000 * 60 * 60 * 24));
      return daysDifference;
    },
    usedDays() {
      if (!this.order) return "-";
      const start = new Date(this.order.orderTime.substring(0, 10));
      const today = new Date();
      today.setHours(0, 0, 0, 0);
      const timeDifference = today - start;
      const daysDifference =
        Math.floor(timeDifference / (1000 * 60 * 60 * 24)) + 2;
      return daysDifference;
    },
    surplusDays() {
      const today = new Date();
      const expiryDate = new Date(this.member.expiryDate);
      const timeDifference = expiryDate - today;
      const daysDifference = Math.floor(timeDifference / (1000 * 60 * 60 * 24));
      return daysDifference > 0 ? daysDifference : 0;
    },
    resetDay() {
      // 获取当前日期
      const today = new Date();

      // 获取下个月一号的日期
      const nextMonthFirstDay = new Date(
        today.getFullYear(),
        today.getMonth() + 1,
        1
      );
      // 格式化为 "YYYY-MM-DD"
      const formattedDate = `${nextMonthFirstDay.getFullYear()}-${String(
        nextMonthFirstDay.getMonth() + 1
      ).padStart(2, "0")}-${String(nextMonthFirstDay.getDate()).padStart(
        2,
        "0"
      )}`;
      return formattedDate;
    },
  },
  created() {},
  methods: {
    onCopySuccess() {
      this.$message.success("已复制至剪切板");
    },
    updateUUID(id) {
      let param = {};
      param.uuid = this.$uuid.v4();
      param.id = id;
      this.$store.dispatch("service/updateUUID", param).then((e) => {
        this.$store.dispatch("service/list");
      });
    },
    toStore() {
      this.$router.push(`/package/index`);
    },
  },
};
</script>

<style lang="scss" scoped>
.container {
  display: flex;
  width: 100%;
  flex-wrap: wrap;
  color: #566a7f;
  line-height: 1.1;
  padding: 2rem;
}

@media (min-width: 768px) {
  .welcome-container {
    width: 40%;
  }

  .info-container {
    margin-left: 40px;
    width: calc(60% - 40px);
  }
}

@media (max-width: 768px) {
  .welcome-container {
    width: 100%;
    height: 40%;
  }

  .info-container {
    width: 100%;
    height: 60%;
    margin-top: 2rem;
  }
}

.menu {
  position: relative;
  padding: 10px;
  cursor: pointer;
  background-color: #e5f5ff;
  width: 50px;
  height: 50px;
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 4px;
}

.menu:hover {
  background-color: #d4e4ff;
}

.menu:hover .tooltip {
  opacity: 1;
  visibility: visible;
}
</style>
