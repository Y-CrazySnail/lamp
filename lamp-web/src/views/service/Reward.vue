<template>
  <lamp-card>
    <div style="margin-left: 20px; margin-top: 20px">
      <div
        style="
          color: #4687ff;
          font-weight: 500;
          line-height: 1.1;
          font-size: 1.125rem;
        "
      >
        邀请返利基础规则说明 🎉
      </div>
      <div style="color: #697a8d; margin: 10px 20px 20px 0; font-size: 0.9rem">
        循环返利（并非只有首次反佣），通过您的邀请链接注册的用户下单时您会获得其购买金额20%的佣金奖励。
      </div>
      <div style="display: flex; align-items: center">
        <el-input
          style="width: 50%"
          size="medium"
          v-model="member.uuid"
          :disabled="true"
        >
        </el-input>
        <LampButton style="margin-left: 10px">复制链接</LampButton>
      </div>
      <div
        style="
          display: flex;
          align-items: center;
          height: 50px;
          font-size: 0.9rem;
        "
      >
        <div class="active">返利记录</div>
        <div class="inactive" style="margin-left: 20px">邀请记录</div>
      </div>
      <div
        style="
          overflow-y: auto;
          height: 200px;
          width: 100%;
          scrollbar-width: none;
        "
      >
        <div
          style="
            display: flex;
            height: 60px;
            align-items: center;
            color: #697a8d;
            margin: 5px 0;
          "
        >
          <div style="width: 40px; height: 40px">
            <img src="./avatar.jpeg" class="user-avatar" />
          </div>
          <div style="height: 55px; margin-left: 10px; font-size: 0.9rem">
            <div>用户：haisong0230@outlook.com</div>
            <div style="margin-top: 2px">时间：2025-01-01 01:00:00</div>
            <div style="margin-top: 2px">返利：¥ 25.00</div>
          </div>
        </div>
        <div
          style="
            display: flex;
            height: 60px;
            align-items: center;
            color: #697a8d;
            margin: 5px 0;
          "
        >
          <div style="width: 40px; height: 40px">
            <img src="./avatar.jpeg" class="user-avatar" />
          </div>
          <div style="height: 55px; margin-left: 10px; font-size: 0.9rem">
            <div>用户：haisong0230@outlook.com</div>
            <div style="margin-top: 2px">时间：2025-01-01 01:00:00</div>
            <div style="margin-top: 2px">返利：¥ 25.00</div>
          </div>
        </div>
        <div
          style="
            display: flex;
            height: 60px;
            align-items: center;
            color: #697a8d;
            margin: 5px 0;
          "
        >
          <div style="width: 40px; height: 40px">
            <img src="./avatar.jpeg" class="user-avatar" />
          </div>
          <div style="height: 55px; margin-left: 10px; font-size: 0.9rem">
            <div>用户：haisong0230@outlook.com</div>
            <div style="margin-top: 2px">时间：2025-01-01 01:00:00</div>
            <div style="margin-top: 2px">返利：¥ 25.00</div>
          </div>
        </div>
        <div
          style="
            display: flex;
            height: 60px;
            align-items: center;
            color: #697a8d;
            margin: 5px 0;
          "
        >
          <div style="width: 40px; height: 40px">
            <img src="./avatar.jpeg" class="user-avatar" />
          </div>
          <div style="height: 55px; margin-left: 10px; font-size: 0.9rem">
            <div>用户：haisong0230@outlook.com</div>
            <div style="margin-top: 2px">时间：2025-01-01 01:00:00</div>
            <div style="margin-top: 2px">返利：¥ 25.00</div>
          </div>
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
  data() {
    return {
      // reward：返利记录 invete：邀请记录
      menu: "reward",
    };
  },
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
.active {
  color: #fff;
  font-weight: 500;
  background-color: #3279ff;
  padding: 7px;
  border-radius: 3px;
  font-weight: 400;
  cursor: pointer;
}

.inactive {
  color: #697a8d;
  font-weight: 500;
  background-color: #fff;
  padding: 7px;
  border-radius: 3px;
  font-weight: 400;
  cursor: pointer;
}
</style>
