<template>
  <div class="app-container">
    <div style="margin: 0px 0px 15px 0px">
      <el-row>
        <el-form
          ref="queryParams"
          :model="queryParams"
          :inline="true"
          size="mini"
        >
          <el-form-item label="微信:" prop="wechat">
            <el-input
              v-model="queryParams.wechat"
              clearable
              placeholder="微信"
            />
          </el-form-item>
          <el-form-item label="邮箱:" prop="email">
            <el-input
              v-model="queryParams.email"
              clearable
              placeholder="邮箱"
            />
          </el-form-item>
          <el-form-item>
            <el-button
              size="mini"
              type="primary"
              icon="el-icon-plus"
              @click="add"
            >
              创建
            </el-button>
            <el-button
              size="mini"
              type="primary"
              icon="el-icon-search"
              @click="handleQuery"
            >
              查询
            </el-button>
            <el-button
              size="mini"
              type="info"
              icon="el-icon-refresh"
              @click="handleReset"
            >
              重置
            </el-button>
          </el-form-item>
        </el-form>
      </el-row>
    </div>
    <v-table
      :table-property.sync="tableProperty"
      :table-data.sync="tableData"
      @fetchData="fetchData"
    >
      <template v-slot:operation="scope">
        <el-button size="mini" @click="edit(scope.scope.row)">编辑</el-button>
        <el-popconfirm
          confirm-button-text="确认"
          cancel-button-text="取消"
          icon="el-icon-info"
          icon-color="red"
          title="确认删除？"
          @confirm="remove(scope.scope.row)"
        >
          <el-button size="mini" slot="reference">删除</el-button>
        </el-popconfirm>
      </template>
    </v-table>
    <el-drawer title="创建" :visible.sync="addDrawerVisible" size="400">
      <v-add
        v-if="addDrawerVisible"
        :add-drawer-visible.sync="addDrawerVisible"
      />
    </el-drawer>
    <el-drawer title="编辑" :visible.sync="editDrawerVisible" size="400px">
      <v-edit
        v-if="editDrawerVisible"
        :id.sync="editId"
        :edit-drawer-visible.sync="editDrawerVisible"
      />
    </el-drawer>
  </div>
</template>

<script>
import Table from "@/components/Table/index";
import Add from "@/views/aladdin/member/add";
import Edit from "@/views/aladdin/member/edit";
export default {
  name: "AladdinMember",
  components: {
    "v-table": Table,
    "v-edit": Edit,
    "v-add": Add,
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
          prop: "wechat",
          label: "微信",
          width: "150px",
        },
        {
          prop: "email",
          label: "邮箱",
          width: "150px",
        },
        {
          prop: "password",
          label: "密码",
          width: "150px",
        },
        {
          prop: "endDate",
          label: "结束时间",
          width: "100px",
        },
        {
          prop: "bandwidth",
          label: "流量",
          width: "60px",
        },
        {
          prop: "period",
          label: "周期",
          width: "60px",
        },
        {
          prop: "price",
          label: "价格",
          width: "60px",
        },
        {
          prop: "uuid",
          label: "UUID",
          width: "270px",
        },
        {
          prop: "referralCode",
          label: "推荐码",
          width: "70px",
        },
        {
          prop: "referrerCode",
          label: "推荐人",
          width: "70px",
        },
        {
          prop: "level",
          label: "会员等级",
          width: "50px",
        },
        {
          prop: "remark",
          label: "备注",
          width: "100px",
        },
      ],
      queryParams: {
        wechat: "",
        email: "",
      },
      tableData: {},
      addDrawerVisible: false,
      editDrawerVisible: false,
      editForm: null,
      subscribeDrawerVisible: false,
      editId: null,
    };
  },
  created() {
    this.fetchData(1, 15);
  },
  watch: {
    addDrawerVisible(newVal, oldVal) {
      if (!newVal) {
        this.fetchData(this.tableData.current, this.tableData.size);
      }
    },
    editDrawerVisible(newVal, oldVal) {
      if (!newVal) {
        this.fetchData(this.tableData.current, this.tableData.size);
      }
    },
  },
  methods: {
    fetchData(current, size) {
      this.$store
        .dispatch("aladdin_member/page", {
          current: current,
          size: size,
          wechat: this.queryParams.wechat,
          email: this.queryParams.email,
        })
        .then((response) => {
          this.tableData = response;
        });
    },
    handleQuery() {
      this.fetchData(this.tableData.current, this.tableData.size);
    },
    handleReset() {
      this.$refs.queryParams.resetFields();
      this.fetchData(this.tableData.current, this.tableData.size);
    },
    add() {
      this.addDrawerVisible = true;
    },
    edit(row) {
      this.editId = row.id;
      this.editDrawerVisible = true;
    },
    remove(row) {
      this.$store
        .dispatch("aladdin_member/remove", { id: row.id })
        .then((response) => {
          this.fetchData(this.tableData.current, this.tableData.size);
        })
        .catch(() => {});
    },
    service(row) {},
  },
};
</script>
