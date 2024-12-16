<template>
  <div style="height: 500px; overflow-y: scroll">
    <el-descriptions
      title="会员信息"
      :column="2"
      border
      style="margin-right: 20px"
    >
      <el-descriptions-item label="昵称">
        {{ form.nickName }}
      </el-descriptions-item>
      <el-descriptions-item label="手机号">
        {{ form.phoneNumber }}
      </el-descriptions-item>
      <el-descriptions-item label="小程序OpenID">
        {{ form.wechatOpenId }}
      </el-descriptions-item>
      <el-descriptions-item label="支付宝账号">
        {{ form.alipayAccount }}
      </el-descriptions-item>
      <el-descriptions-item label="身份证号">
        {{ form.idCardNo }}
        <span
          v-if="
            form.repeatApplyDistributionCount &&
            form.repeatApplyDistributionCount > 0
          "
          style="color: red"
        >
          已存在{{ form.repeatApplyDistributionCount }}个当前身份ID的分销商
        </span>
      </el-descriptions-item>
      <el-descriptions-item label="身份证姓名">
        {{ form.idCardName }}
      </el-descriptions-item>
      <el-descriptions-item label="银行卡号">
        {{ form.bankCardAccount }}
      </el-descriptions-item>
      <el-descriptions-item label="分销状态">
        <span v-if="form.distributionFlag == 1">是</span>
        <span v-if="form.distributionFlag == -1">申请中</span>
        <span v-if="form.distributionFlag == 0">否</span>
        <el-button
          v-if="form.distributionFlag == -1"
          type="danger"
          @click="disagree()"
          plain
          size="mini"
          style="margin-left: 15px"
        >
          不同意
        </el-button>
        <el-button
          v-if="form.distributionFlag == -1"
          type="primary"
          @click="agree()"
          plain
          size="mini"
          style="margin-left: 15px"
        >
          同意
        </el-button>
      </el-descriptions-item>
    </el-descriptions>
    <div
      style="
        font-size: 16px;
        font-weight: 700;
        color: black;
        margin: 20px 0 20px 0;
      "
    >
      收货地址信息
    </div>
    <el-table :data="form.zeroAddressList" style="width: 98%" :border="true">
      <el-table-column prop="name" label="收货人" align="center" />
      <el-table-column prop="phone" label="手机号" align="center" />
      <el-table-column prop="province" label="省" align="center" />
      <el-table-column prop="city" label="市" align="center" />
      <el-table-column prop="district" label="区" align="center" />
      <el-table-column prop="street" label="街道" align="center" />
      <el-table-column prop="detail" label="详细地址" align="center" />
    </el-table>
    <el-descriptions
      title="分销信息"
      :column="2"
      border
      style="margin-right: 20px; margin-top: 20px"
      v-if="form.distributionFlag && form.distributionFlag == 1"
    >
      <el-descriptions-item
        label="一级分销佣金比例"
        :contentStyle="{ 'text-align': 'right' }"
      >
        {{ form.directReferrerRate * 100 }}%
      </el-descriptions-item>
      <el-descriptions-item
        label="二级分销佣金比例"
        :contentStyle="{ 'text-align': 'right' }"
      >
        {{ form.indirectReferrerRate * 100 }}%
      </el-descriptions-item>
      <el-descriptions-item
        label="余额"
        :contentStyle="{ 'text-align': 'right' }"
      >
        {{ form.balance }}元
      </el-descriptions-item>
      <el-descriptions-item
        label="待结算金额"
        :contentStyle="{ 'text-align': 'right' }"
      >
        {{ form.todoBalance }}元
      </el-descriptions-item>
      <el-descriptions-item
        label="团队人数"
        :contentStyle="{ 'text-align': 'right' }"
      >
        {{ form.referrerUserCount }}人
      </el-descriptions-item>
      <el-descriptions-item
        label="分销订单数量"
        :contentStyle="{ 'text-align': 'right' }"
      >
        {{ form.referrerOrderCount }}单
      </el-descriptions-item>
    </el-descriptions>
    <div
      style="
        font-size: 16px;
        font-weight: 700;
        color: black;
        margin: 20px 0 20px 0;
      "
    >
      零钱明细
    </div>
    <el-table
      :data="form.zeroBalanceRecordList"
      style="width: 98%"
      :border="true"
    >
      <el-table-column prop="type" label="类型" align="center">
        <template slot-scope="scope">
          {{ scope.row.type == 0 ? "入账" : "提现" }}
        </template>
      </el-table-column>
      <el-table-column prop="amount" label="发生金额" align="center" />
      <el-table-column prop="balance" label="余额" align="center" />
      <el-table-column prop="dealTime" label="处理时间" align="center" />
      <el-table-column prop="state" label="处理状态" align="center">
        <template slot-scope="scope">
          <el-tag type="warning" v-if="scope.row.state == -1"> 申请中 </el-tag>
          <el-tag type="primary" v-if="scope.row.state == 1"> 已完成 </el-tag>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
export default {
  name: "ZeroMemberEdit",
  props: {
    form: {
      type: Object,
      required: true,
    },
    editDialogVisible: {
      type: Boolean,
      required: true,
    },
  },
  mounted() {},
  computed: {},
  data() {
    return {};
  },
  methods: {
    agree() {
      let params = {
        id: this.form.id,
        distributionFlag: 1,
      };
      this.$store
        .dispatch("zero_member/update", params)
        .then((response) => {
          this.$store
            .dispatch("zero_member/getById", { id: this.form.id })
            .then((member) => {
              this.form = member;
            });
          this.$message.success(response);
        })
        .catch(() => {});
    },
    disagree() {
      let params = {
        id: this.form.id,
        distributionFlag: 0,
      };
      this.$store
        .dispatch("zero_member/update", params)
        .then((response) => {
          this.$store
            .dispatch("zero_member/getById", { id: this.form.id })
            .then((member) => {
              this.form = member;
            });
          this.$message.success(response);
        })
        .catch(() => {});
    },
  },
};
</script>

<style>
.dialog-footer {
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
