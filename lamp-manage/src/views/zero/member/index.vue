<template>
  <div class="app-container">
    <div>
      <div style="margin: 0px 0px 15px 0px">
        <el-row>
          <el-form
            :model="queryParam"
            :inline="true"
            size="mini"
            label-width="90px"
          >
            <el-col :span="6">
              <el-form-item label="昵称:">
                <el-input
                  v-model="queryParam.nickName"
                  clearable
                  placeholder="昵称"
                />
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="手机号:">
                <el-input
                  v-model="queryParam.phoneNumber"
                  clearable
                  placeholder="手机号"
                />
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="分销状态:">
                <el-select
                  v-model="queryParam.distributionFlag"
                  placeholder="分销状态"
                  clearable
                >
                  <el-option
                    v-for="item in distributionFlagDict"
                    :key="item.key"
                    :label="item.value"
                    :value="item.key"
                  />
                </el-select>
              </el-form-item>
            </el-col>
          </el-form>
        </el-row>
        <el-row>
          <el-button size="mini" type="primary" icon="el-icon-plus" @click="add"
            >创建</el-button
          >
          <el-button
            size="mini"
            type="primary"
            icon="el-icon-search"
            @click="fetchData(1, 15)"
            >查询</el-button
          >
          <el-button
            size="mini"
            type="info"
            icon="el-icon-refresh"
            @click="reset"
            >重置</el-button
          >
        </el-row>
      </div>
    </div>
    <v-table
      :table-property.sync="tableProperty"
      :table-data.sync="tableData"
      @fetchData="fetchData"
    >
      <template v-slot:distributionFlag="data">
        <span v-if="data.scope == 1">是</span>
        <span v-if="data.scope == -1">申请中</span>
        <span v-if="data.scope == 0">否</span>
      </template>
      <template v-slot:directReferrerUserId="data">
        {{ data.scope }}
      </template>
      <template v-slot:indirectReferrerUserId="data">
        {{ data.scope }}
      </template>
      <template v-slot:directReferrerRate="data">
        {{ data.scope * 100 }}%
      </template>
      <template v-slot:indirectReferrerRate="data">
        {{ data.scope * 100 }}%
      </template>
      <template v-slot:operation="scope">
        <el-button size="mini" @click="edit(scope.scope.row)">详情</el-button>
      </template>
    </v-table>
    <el-dialog
      class="snail_dialog"
      title="编辑"
      :visible.sync="editDialogVisible"
      width="70%"
    >
      <v-detail
        v-if="editDialogVisible"
        :form.sync="editForm"
        :edit-dialog-visible.sync="editDialogVisible"
        :category-list.sync="categoryList"
      />
    </el-dialog>
  </div>
</template>

<script>
import Table from "@/components/Table/index";
import Detail from "@/views/zero/member/detail";
export default {
  name: "ZeroMember",
  components: {
    "v-table": Table,
    "v-detail": Detail,
  },
  data() {
    return {
      tableProperty: [
        {
          prop: "id",
          label: "编号",
          width: "50px",
        },
        {
          prop: "nickName",
          label: "昵称",
          width: "100px",
        },
        {
          prop: "phoneNumber",
          label: "手机号",
          width: "100px",
        },
        {
          prop: "wechatOpenId",
          label: "微信OpenId",
          width: "300px",
        },
        {
          prop: "balance",
          label: "余额(元)",
          width: "100px",
        },
        {
          prop: "todoBalance",
          label: "待结算金额(元)",
          width: "100px",
        },
        {
          prop: "distributionFlag",
          label: "分销标识",
          width: "100px",
          slot: true,
        },
        {
          prop: "directReferrerRate",
          label: "直接分销佣金比例",
          width: "150px",
          slot: true,
        },
        {
          prop: "indirectReferrerRate",
          label: "间接分销佣金比例",
          width: "150px",
          slot: true,
        },
        {
          prop: "directReferrerUserId",
          label: "直接推荐人",
          width: "100px",
          slot: true,
        },
        {
          prop: "indirectReferrerUserId",
          label: "间接推荐人",
          width: "100px",
          slot: true,
        },
      ],
      tableData: {},
      addDialogVisible: false,
      // 编辑页面标识
      editDialogVisible: false,
      // 编辑表单
      editForm: null,
      categoryList: [],
      queryParam: {},
      distributionFlagDict: [
        { key: "0", value: "否" },
        { key: "1", value: "是" },
        { key: "-1", value: "申请中" },
      ],
    };
  },
  created() {
    this.fetchData(1, 15);
  },
  watch: {
    addDialogVisible(newVal, oldVal) {
      if (!newVal) {
        this.fetchData(this.tableData.current, this.tableData.size);
      }
    },
    editDialogVisible(newVal, oldVal) {
      if (!newVal) {
        this.fetchData(this.tableData.current, this.tableData.size);
      }
    },
  },
  methods: {
    fetchData(current, size) {
      this.$store
        .dispatch("zero_member/page", {
          current: current,
          size: size,
          nickName: this.queryParam.nickName,
          phoneNumber: this.queryParam.phoneNumber,
          distributionFlag: this.queryParam.distributionFlag,
        })
        .then((response) => {
          this.tableData = response;
        })
        .catch(() => {});
    },
    add() {
      this.addDialogVisible = true;
    },
    edit(row) {
      this.$store
        .dispatch("zero_member/getById", { id: row.id })
        .then((response) => {
          this.editForm = response;
          this.editDialogVisible = true;
        })
        .catch(() => {});
    },
    reset() {
      this.queryParam = {};
    },
  },
};
</script>
