<template>
  <lamp-card>
    <div style="width: 100%;">
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
              订单号：W2503011839YJQB
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
              padding: 4px 6px;
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
          <div>金额：¥59.00</div>
          <div>实付：¥50.15</div>
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
          <div>失效日期：25-03-31 18:39</div>
          <div>生效日期：25-03-01 18:39</div>
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
          <div>已使用/总时长：0/30 天</div>
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
            30天后到期
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
          <div>已使用/总流量：0/140 G</div>
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
          剩余：40%
        </div>
      </div>
      <div style="padding: 0 35px; margin: 10px 0">
        <el-progress
          :text-inside="true"
          :stroke-width="20"
          :percentage="40"
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
          <div>重置日期：无</div>
        </div>
      </div>
    </div>
  </lamp-card>
</template>

<script>
import LampCard from "@/components/LampCard/index";
export default {
  name: "Service",
  components: { LampCard },
  computed: {
    serviceList() {
      return this.$store.state.service.serviceList;
    },
    device() {
      return this.$store.state.app.device;
    },
    member() {
      return this.$store.state.member.member;
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
    toPackage() {
      this.$router.push(`/package/index`);
    },
    toIOSshadowrocket(service) {
      this.$store.state.service.service = service;
      this.$router.push(`/guide/ios-stash`);
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
