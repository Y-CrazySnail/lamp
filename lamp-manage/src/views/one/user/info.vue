<template>
  <div style="margin: 20px 20px 20px 20px">
    <!-- {{ form }} -->
    <el-descriptions title="用户基础信息" border="true" column="2">
      <el-descriptions-item label="昵称">{{
        form.nickName
      }}</el-descriptions-item>
      <el-descriptions-item label="头像">
        <img :src="form.userAvatar" style="width: 50px; height: 50px" />
      </el-descriptions-item>
      <el-descriptions-item label="OpenId">
        {{ form.wechatOpenId }}
      </el-descriptions-item>
      <el-descriptions-item label="手机号">
        {{ form.userPhone }}
      </el-descriptions-item>
    </el-descriptions>
    <div style="font-size: 16px; font-weight: 700; margin-top: 30px;">用户地址列表</div>
    <el-table
      :data="form.addressList"
      style="width: 100%; margin-top: 30px"
      border="true"
    >
      <el-table-column prop="addressName" label="姓名" width="150px;">
      </el-table-column>
      <el-table-column prop="addressPhone" label="手机" width="150px;">
      </el-table-column>
      <el-table-column prop="addressProvince" label="省" width="150px;">
      </el-table-column>
      <el-table-column prop="addressCity" label="市" width="150px;">
      </el-table-column>
      <el-table-column prop="addressDistrict" label="区" width="150px;">
      </el-table-column>
      <el-table-column prop="addressStreet" label="街道" width="150px;">
      </el-table-column>
      <el-table-column prop="addressDetail" label="详细地址" width="200px;">
      </el-table-column>
      <el-table-column prop="addressDefaultFlag" label="默认标识">
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
export default {
  name: "OneUserInfo",
  props: {
    form: {
      type: Object,
      required: true,
    },
    infoDialogVisible: {
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
    onSubmit() {
      this.$store.dispatch("one_tenant/update", this.form).then((response) => {
        this.$message.success("更新成功");
        this.onCancle();
      });
    },
    onCancle() {
      this.$emit("update:infoDialogVisible", false);
    },
  },
};
</script>