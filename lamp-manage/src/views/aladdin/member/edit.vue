<template>
  <div v-loading="loading">
    <el-form
      ref="member"
      :model="member"
      label-width="80px"
      size="mini"
      :inline="true"
      style="margin-left: 10px"
    >
      <el-col :span="24">
        <el-form-item label="ID" prop="id">
          <el-input
            v-model="member.id"
            placeholder="ID"
            style="width: 300px"
            disabled
          />
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="微信" prop="wechat">
          <el-input
            v-model="member.wechat"
            placeholder="微信"
            style="width: 300px"
          />
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="邮箱" prop="email">
          <el-input
            v-model="member.email"
            placeholder="邮箱"
            style="width: 300px"
          />
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="密码" prop="password">
          <el-input
            v-model="member.password"
            placeholder="密码"
            style="width: 300px"
          />
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="备注" prop="remark">
          <el-input
            v-model="member.remark"
            placeholder="备注"
            style="width: 300px"
          />
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="推荐码" prop="referralCode">
          <el-input
            v-model="member.referralCode"
            placeholder="推荐码"
            style="width: 300px"
          />
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="推荐人" prop="referrerCode">
          <el-input
            v-model="member.referrerCode"
            placeholder="推荐人"
            style="width: 300px"
          />
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="会员等级" prop="level">
          <el-input
            v-model="member.level"
            placeholder="会员等级"
            style="width: 300px"
          />
        </el-form-item>
      </el-col>
    </el-form>
    <el-form
      ref="service"
      :model="service"
      label-width="80px"
      size="mini"
      :inline="true"
      style="margin-left: 10px"
    >
      <el-col :span="24">
        <el-form-item label="UUID" prop="uuid">
          <el-input v-model="service.uuid" style="width: 300px" disabled />
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="开始日期" prop="beginDate">
          <el-date-picker
            v-model="service.beginDate"
            type="date"
            style="width: 200px"
          />
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="结束日期" prop="endDate">
          <el-date-picker
            v-model="service.endDate"
            type="date"
            style="width: 200px"
          />
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="流量/月" prop="bandwidth">
          <el-input-number
            v-model="service.bandwidth"
            :precision="2"
            :step="1"
            :max="500"
          />
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="周期(月)" prop="period">
          <el-input-number v-model="service.period" :step="1" :max="60" />
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="价格(元)" prop="price">
          <el-input-number v-model="service.price" :step="1" :max="1000" />
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item
          label="当月流量"
          prop="bandwidth"
          v-if="service.currentServiceMonth"
        >
          <el-input
            v-model="service.currentServiceMonth.bandwidth"
            style="width: 200px"
          />
          <el-tag type="info" size="small" style="margin-left: 5px"
            >{{
              Number(
                service.currentServiceMonth.bandwidth / 1024 / 1024 / 1024
              ).toFixed(2)
            }}GB</el-tag
          >
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item
          label="上行流量"
          prop="bandwidthUp"
          v-if="
            service.currentServiceMonth &&
            service.currentServiceMonth.bandwidthUp
          "
        >
          <el-tag type="info" size="small" style="margin-left: 5px"
            >{{
              Number(
                service.currentServiceMonth.bandwidthUp / 1024 / 1024 / 1024
              ).toFixed(2)
            }}GB</el-tag
          >
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item
          label="下行流量"
          prop="bandwidthDown"
          v-if="
            service.currentServiceMonth &&
            service.currentServiceMonth.bandwidthDown
          "
        >
          <el-tag type="info" size="small" style="margin-left: 5px"
            >{{
              Number(
                service.currentServiceMonth.bandwidthDown / 1024 / 1024 / 1024
              ).toFixed(2)
            }}GB</el-tag
          >
        </el-form-item>
      </el-col>
    </el-form>
    <div style="margin-left: 30px">
      <el-button size="small" @click="onCancle" style="margin-right: 10px">
        取消
      </el-button>
      <el-button type="primary" size="small" @click="onSubmit">更新</el-button>
    </div>
  </div>
</template>

<script>
export default {
  name: "AladdinMemberEdit",
  props: {
    editDrawerVisible: {
      type: Boolean,
      required: true,
    },
    id: {
      type: Number,
      require: true,
    },
  },
  mounted() {
    console.log(this.id);
    this.$store
      .dispatch("aladdin_member/getById", { id: this.id })
      .then((member) => {
        this.member = member;
        console.log(this.member);
        this.$store
          .dispatch("aladdin_service/getByMemberId", {
            memberId: this.id,
          })
          .then((serviceList) => {
            if (serviceList && serviceList.length > 0) {
              this.service = serviceList[0];
              console.log(this.service);
            } else {
              this.service = {};
            }
            this.loading = false;
          });
      });
  },
  data() {
    return {
      loading: true,
      member: {},
      service: {},
    };
  },
  methods: {
    onSubmit() {
      this.$store.dispatch("aladdin_member/update", this.member).then(() => {
        this.$store.dispatch("aladdin_service/update", this.service).then(() => {
          this.$message.success("更新成功");
          this.$emit("update:editDrawerVisible", false);
        });
      });
    },
    onCancle() {
      this.$emit("update:editDrawerVisible", false);
    },
  },
  destroyed() {
    this.member = {};
    this.service = {};
  },
};
</script>

<style>
</style>