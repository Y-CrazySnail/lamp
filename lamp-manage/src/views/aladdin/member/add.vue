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
    </el-form>
    <div style="margin-left: 30px; margin-bottom: 20px">
      <el-button size="small" @click="onCancle" style="margin-right: 10px">
        取消
      </el-button>
      <el-button type="primary" size="small" @click="onSubmit">创建</el-button>
    </div>
  </div>
</template>

<script>
export default {
  name: "AladdinMemberAdd",
  props: {
    addDrawerVisible: {
      type: Boolean,
      required: true,
    },
  },
  mounted() {},
  data() {
    return {
      loading: false,
      member: {
        id: null,
        wechat: "wechat1",
        email: "email1@example.com",
        username: null,
        password: "password12",
        uuid: this.$uuid.v4(),
        bandwidth: 100,
        expiryDate: "2023-01-01",
        level: 1,
        referrerCode: "OOOO023",
        referralCode: "DDDDD23",
        remark: "remark1",
        balance: 100.1,
      },
    };
  },
  methods: {
    onSubmit() {
      this.loading = true;
      this.$store.dispatch("aladdin_member/save", this.member).then(() => {
        this.$message.success("更新成功");
        this.loading = false;
        this.$emit("update:addDrawerVisible", false);
      });
    },
    onCancle() {
      this.$emit("update:addDrawerVisible", false);
    },
    onAddService() {
      const { v4: uuidv4 } = require("uuid");
      const currentDate = new Date();
      const year = currentDate.getFullYear(); // 获取年份
      const month = currentDate.getMonth() + 1; // 获取月份，月份从0开始，所以加1
      const day = currentDate.getDate(); // 获取日期
      // 将月份和日期格式化为两位数
      const formattedMonth = month < 10 ? "0" + month : month;
      const formattedDay = day < 10 ? "0" + day : day;
      // 拼接成 yyyy-mm-dd 格式
      const formattedDate = `${year}-${formattedMonth}-${formattedDay}`;
      let service = {
        uuid: uuidv4(),
        bandwidth: 0,
        expiryDate: formattedDate,
      };
      this.member.serviceList.push(service);
    },
    onDeleteService(index) {
      this.member.serviceList.splice(index, 1);
    },
  },
  destroyed() {
    this.member = {};
  },
};
</script>

<style>
</style>
