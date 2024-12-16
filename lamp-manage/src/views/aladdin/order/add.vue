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
        <el-form-item label="套餐:" prop="endDate">
          <el-select v-model="form.packageId" clearable placeholder="状态">
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
  name: "AladdinOrderAdd",
  props: {
    addDialogVisible: {
      type: Boolean,
      required: true,
    },
  },
  mounted() {},
  data() {
    return {
      form: {},
      loading: false,
      selectMemberList: [],
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
      if (!this.form.memberId) {
        this.$message.warning("请选择会员！");
        return;
      }
      this.$store
        .dispatch("aladdin_order/save", this.form)
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
  },
  destroyed() {
    this.form = {};
  },
};
</script>

<style>
</style>