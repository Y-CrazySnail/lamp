<template>
  <div>
    <el-form
      ref="form"
      :model="form"
      label-width="auto"
      size="mini"
      :inline="true"
    >
      <el-col :span="24">
        <el-form-item label="会员:" prop="memberid">
          <el-select
            v-model="form.memberId"
            filterable
            remote
            reserve-keyword
            placeholder="请输入关键词"
            :remote-method="filterMember"
            :loading="loading"
            style="width: 300px"
          >
            <el-option
              v-for="item in selectMemberList"
              :key="item.id"
              :label="item.email + '_' + item.wechat"
              :value="item.id"
            >
            </el-option>
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="UUID:" prop="uuid">
          <el-input
            v-model="form.uuid"
            placeholder="UUID"
            style="width: 300px"
          />
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="套餐:" prop="endDate">
          <el-select
            v-model="packageId"
            clearable
            placeholder="状态"
            @change="setService()"
          >
            <el-option
              v-for="item in packageList"
              :key="item.id"
              :label="
                item.dataTraffic +
                'GB_' +
                item.period +
                '月_' +
                item.price +
                '元'
              "
              :value="item.id"
            >
            </el-option>
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="结束时间:" prop="endDate">
          <el-date-picker
            v-model="form.endDate"
            type="date"
            placeholder="结束时间"
            style="width: 300px"
          >
          </el-date-picker>
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="流量(GB/月):" prop="dataTraffic">
          <el-input
            v-model="form.dataTraffic"
            placeholder="流量(GB/月)"
            style="width: 300px"
          />
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="周期(月):" prop="period">
          <el-input
            v-model="form.period"
            placeholder="周期(月)"
            style="width: 300px"
          />
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="价格(元):" prop="price">
          <el-input
            v-model="form.price"
            placeholder="价格(元)"
            style="width: 300px"
          />
        </el-form-item>
      </el-col>
      <el-col :span="24" />
    </el-form>
    <div class="dialog-footer">
      <el-button size="small" @click="onCancle" style="margin-right: 10px">
        取消
      </el-button>
      <el-button type="primary" size="small" @click="onSubmit">创建</el-button>
    </div>
  </div>
</template>

<script>
export default {
  name: "AladdinServiceAdd",
  props: {
    addDialogVisible: {
      type: Boolean,
      required: true,
    },
  },
  mounted() {
    this.form.uuid = this.$uuid.v4();
  },
  data() {
    return {
      form: {
        uuid: "",
      },
      loading: false,
      selectMemberList: [],
      packageId: null,
    };
  },
  computed: {
    memberList() {
      return this.$store.state.aladdin_member.memberList;
    },
    packageList() {
      return this.$store.state.aladdin_package.packageList;
    },
  },
  methods: {
    onSubmit() {
      let DateFormat = require("dateformat-util");
      if (!this.form.memberId) {
        this.$message.warning("请选择会员！");
        return;
      }
      if (!this.form.endDate) {
        this.$message.warning("请选择结束日期！");
        return;
      }
      this.form.beginDate = DateFormat.format(new Date(), "yyyy-MM-dd");
      this.form.endDate = DateFormat.format(this.form.endDate, "yyyy-MM-dd");
      console.log(this.form)
      this.$store
        .dispatch("aladdin_service/save", this.form)
        .then((response) => {
          this.$message.success("新增成功");
          this.$emit("update:addDialogVisible", false);
        })
        .catch(() => {});
    },
    onCancle() {
      this.$emit("update:addDialogVisible", false);
    },
    filterMember(query) {
      if (query != "") {
        this.loading = true;
        this.selectMemberList = this.memberList.filter(
          (e) =>
            (e.email && e.email.includes(query)) ||
            (e.wechat && e.wechat.includes(query))
        );
        console.log(this.selectMemberList);
        this.loading = false;
      } else {
        this.selectMemberList = [];
      }
    },
    setService() {
      let DateFormat = require("dateformat-util");
      this.form.endDate = DateFormat.addMonth(
        new Date(),
        Number(this.packageList.find((e) => e.id == this.packageId).period)
      );
      this.form.dataTraffic = this.packageList.find(
        (e) => e.id == this.packageId
      ).dataTraffic;
      this.form.period = this.packageList.find(
        (e) => e.id == this.packageId
      ).period;
      this.form.price = this.packageList.find(
        (e) => e.id == this.packageId
      ).price;
    },
  },
  destroyed() {
    this.form = {};
  },
};
</script>

<style>
</style>