<template>
  <lamp-card>
    <div style="margin-left: 20px; margin-top: 20px; width: 100%">
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
        循环返利（并非只有首次消费返利）通过您的邀请链接注册的用户下单时您会获得其购买金额20%的佣金奖励。
      </div>
      <div style="display: flex; align-items: center">
        <el-input
          style="width: 50%"
          size="medium"
          v-model="url"
          :disabled="true"
        >
        </el-input>
        <LampButton
          style="margin-left: 10px"
          v-clipboard:copy="url"
          v-clipboard:success="onCopySuccess"
        >
          复制链接
        </LampButton>
      </div>
      <div
        style="
          display: flex;
          align-items: center;
          height: 50px;
          font-size: 0.9rem;
        "
      >
        <div
          :class="menu == 'reward' ? 'active' : 'inactive'"
          @click="changeMenu('reward')"
        >
          返利记录
        </div>
        <!-- <div
          :class="menu == 'invete' ? 'active' : 'inactive'"
          style="margin-left: 20px"
          @click="changeMenu('invete')"
        >
          邀请记录
        </div> -->
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
            margin: 10px 20px 10px 0;
            padding-left: 10px;
            border: 1px solid #e4e7ed;
            border-radius: 5px;
          "
          v-for="item in member.rewardRecordList"
          :key="item.id"
        >
          <div style="width: 40px; height: 40px">
            <img src="./avatar.jpeg" class="user-avatar" />
          </div>
          <div style="height: 55px; margin-left: 10px; font-size: 0.9rem">
            <div>用户：{{ item.refereeEmail }}</div>
            <div style="margin-top: 2px">时间：{{ item.rewardDate }}</div>
            <div style="margin-top: 2px">返利：¥ {{ item.rewardAmount }}</div>
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
    device() {
      return this.$store.state.app.device;
    },
    member() {
      return this.$store.state.member.member;
    },
    url() {
      return (
        "https://alamp.cc/signup?referrerCode=" +
        this.$store.state.member.member.referralCode
      );
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
    changeMenu(menu) {
      this.menu = menu;
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
  border-radius: 7px;
  font-weight: 400;
  cursor: pointer;
}

.inactive {
  color: #697a8d;
  font-weight: 500;
  background-color: #fff;
  padding: 7px;
  border-radius: 7px;
  font-weight: 400;
  cursor: pointer;
}
</style>
