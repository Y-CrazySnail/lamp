<template>
  <div
    style="
      display: flex;
      justify-content: center;
      color: #697a8d;
      font-size: 0.9rem;
      line-height: 1.53;
      font-weight: 400;
    "
  >
    <div style="width: calc(100vw - 250px)">
      <div style="margin-top: 1.5rem">
        <h4>会员商店</h4>
      </div>
      <div style="display: flex; margin-top: 1.5rem">
        <step style="width: 160px" :info="step1"></step>
        <div style="display: flex; align-items: center">
          <i
            style="color: #a1acb8; font-size: 1.5rem"
            class="el-icon-d-arrow-right"
          ></i>
        </div>
        <step style="width: 160px" :info="step2"></step>
        <div style="display: flex; align-items: center">
          <i
            style="color: #a1acb8; font-size: 1.5rem"
            class="el-icon-d-arrow-right"
          ></i>
        </div>
        <step style="width: 160px" :info="step3"></step>
      </div>
      <div
        style="
          width: 100%;
          display: flex;
          justify-content: center;
          align-items: center;
          margin-top: 2.5rem;
        "
      >
        <h2>选择服务</h2>
      </div>
      <div
        style="
          width: 100%;
          display: flex;
          justify-content: center;
          align-items: center;
          margin-top: 1rem;
        "
      >
        我们在全世界范围建立了加速节点，方便满足不同使用类型的用户在全球范围内开展业务，以优质资源为您提供快速稳定的国际网络接入。
      </div>
      <div
        style="
          width: 100%;
          display: flex;
          justify-content: center;
          align-items: center;
          margin-top: 1rem;
        "
      >
        不同套餐售价不同意味成本预算不同,成本预算决定可用资源品质不同。
      </div>
      <div
        style="
          display: flex;
          justify-content: center;
          align-items: center;
          margin-top: 30px;
          font-weight: 500;
        "
      >
        <div
          style="
            padding: 0.7rem 1rem;
            border: 1px solid #4687ff;
            border-radius: 0.5rem 0 0 0.5rem;
            cursor: pointer;
          "
          :style="
            mode === '3'
              ? 'background-color: #4687ff; color: #fff'
              : 'background-color: #fff; color: #4687ff;'
          "
          @click="onChangeMode('3')"
        >
          季付
        </div>
        <div
          style="
            padding: 0.7rem 1rem;
            border-top: 1px solid #4687ff;
            border-bottom: 1px solid #4687ff;
            cursor: pointer;
          "
          :style="
            mode === '6'
              ? 'background-color: #4687ff; color: #fff'
              : 'background-color: #fff; color: #4687ff;'
          "
          @click="onChangeMode('6')"
        >
          半年付
        </div>
        <div
          style="
            padding: 0.7rem 1rem;
            border: 1px solid #4687ff;
            border-radius: 0 0.5rem 0.5rem 0;
            cursor: pointer;
          "
          :style="
            mode === '12'
              ? 'background-color: #4687ff; color: #fff'
              : 'background-color: #fff; color: #4687ff;'
          "
          @click="onChangeMode('12')"
        >
          年付
        </div>
        <div
          style="
            margin-left: 3rem;
            padding: 0.7rem 1rem;
            border: 1px solid #4687ff;
            border-radius: 0.5rem;
            cursor: pointer;
          "
          :style="
            mode === '0'
              ? 'background-color: #4687ff; color: #fff'
              : 'background-color: #fff; color: #4687ff;'
          "
          @click="onChangeMode('0')"
        >
          流量包
        </div>
      </div>
      {{ spu1 }}
      {{ spu2 }}
      {{ spu3 }}
    </div>
  </div>
</template>

<script>
import Step from "./Step.vue";
export default {
  name: "Package",
  components: {
    Step,
  },
  mounted() {},
  data() {
    return {
      step1: {
        no: 1,
        title: "选择服务",
        description: "根据需求选择",
        state: "todo",
      },
      step2: {
        no: 2,
        title: "确认订单",
        description: "确认订单明细",
        state: "process",
      },
      step3: {
        no: 3,
        title: "完成购买",
        description: "订单购买完成",
        state: "todo",
      },
      // 12为年付，6为半年付，3为季付 0为流量包
      mode: "12",
      spu0: {},
      spu1: {},
      spu2: {},
      spu3: {},
    };
  },
  computed: {
    packageList() {
      return this.$store.state.packages.packageList;
    },
    spuList() {
      if (this.$store.state.packages.spuList.length == 0) {
        return [];
      } else {
        this.spu0 = this.$store.state.packages.spuList.find((item) => item.spuName == "流量包");
        this.spu1 = this.$store.state.packages.spuList.find((item) => item.spuName == "入门专线服务");
        this.spu2 = this.$store.state.packages.spuList.find((item) => item.spuName == "常规专线服务");
        this.spu3 = this.$store.state.packages.spuList.find((item) => item.spuName == "尊享专线服务");
        return this.$store.state.packages.spuList;
      }
    },
  },
  mounted() {
    this.onChangeMode('12');
  },
  methods: {
    onChangeMode(mode) {
      this.mode = mode;
      if (mode != "0") {
        console.log(this.spu1);
        this.spu1.selected = this.spu1.skuList.find((item) => item.skuAttribute.duration == mode);
        this.spu2.selected = this.spu2.skuList.find((item) => item.skuAttribute.duration == mode);
        this.spu3.selected = this.spu3.skuList.find((item) => item.skuAttribute.duration == mode);
      }
    },
    placeOrder(packageId) {
      let param = {};
      param.packageId = packageId;
      this.$store.dispatch("order/place", param).then((res) => {
        this.$router.push("/order/index");
      });
    },
  },
};
</script>

<style lang="scss">
.discription-lable {
  width: 80px;
}
</style>
