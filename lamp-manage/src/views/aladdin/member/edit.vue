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
        <el-form-item label="uuid" prop="uuid">
          <el-input
            v-model="member.uuid"
            placeholder="uuid"
            style="width: 300px"
            disabled
          />
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="每月流量" prop="bandwidth">
          <el-input
            v-model="member.bandwidth"
            placeholder="流量（月）"
            style="width: 200px"
          />
          <span style="margin-left: 10px; font-weight: bold">
            {{ (member.bandwidth / 1024 / 1024 / 1024).toFixed(2) }}GB
          </span>
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="结束日期" prop="expiryDate">
          <el-input
            v-model="member.expiryDate"
            placeholder="结束日期"
            style="width: 300px"
          />
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="本月流量" prop="monthBandwidth">
          <el-input
            v-model="member.monthBandwidth"
            placeholder="本月流量"
            style="width: 200px"
          />
          <span style="margin-left: 10px; font-weight: bold">
            {{ (member.monthBandwidth / 1024 / 1024 / 1024).toFixed(2) }}GB
          </span>
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="本月上行" prop="monthBandwidthUp">
          <span style="margin-left: 10px; font-weight: bold">
            {{ (member.monthBandwidthUp / 1024 / 1024 / 1024).toFixed(2) }}GB
          </span>
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="本月下行" prop="monthBandwidthDown">
          <span style="margin-left: 10px; font-weight: bold">
            {{ (member.monthBandwidthDown / 1024 / 1024 / 1024).toFixed(2) }}GB
          </span>
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
    <div style="margin-left: 30px; margin-bottom: 20px">
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
    this.$store
      .dispatch("aladdin_member/getById", { id: this.id })
      .then((member) => {
        this.member = member;
        this.loading = false;
      });
  },
  data() {
    return {
      loading: true,
      member: {},
    };
  },
  methods: {
    onSubmit() {
      this.loading = true;
      this.member.lastSyncTime = null;
      this.$store.dispatch("aladdin_member/update", this.member).then(() => {
        this.$message.success("更新成功");
        this.loading = false;
        this.$emit("update:editDrawerVisible", false);
      });
    },
    onCancle() {
      this.$emit("update:editDrawerVisible", false);
    },
  },
  destroyed() {
    this.member = {};
  },
};
</script>

<style>
</style>
