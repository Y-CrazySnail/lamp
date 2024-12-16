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
          <el-form-item label="状态:" prop="status">
            <el-select
              v-model="queryParams.status"
              clearable
              placeholder="状态"
            >
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </el-form-item>
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
      <template v-slot:uuid="data">
        <el-tag @click="subscribe(data.scope)"> {{ data.scope }} </el-tag>
      </template>
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
    <el-dialog
      class="snail_dialog"
      title="创建"
      :visible.sync="addDialogVisible"
      width="80%"
    >
      <v-add
        v-if="addDialogVisible"
        :add-dialog-visible.sync="addDialogVisible"
      />
    </el-dialog>
    <el-dialog
      class="snail_dialog"
      title="编辑"
      :visible.sync="editDialogVisible"
      width="80%"
    >
      <v-edit
        v-if="editDialogVisible"
        :form.sync="editForm"
        :edit-dialog-visible.sync="editDialogVisible"
      />
    </el-dialog>
    <el-dialog
      title="订阅"
      :visible.sync="subscribeDialogVisible"
      width="80%"
    >
      <v-subscribe
        v-if="subscribeDialogVisible"
        :uuid.sync="uuid"
        :subscribe-dialog-visible.sync="subscribeDialogVisible"
      />
    </el-dialog>
  </div>
</template>

<script>
import Table from "@/components/Table/index";
import Add from "@/views/aladdin/service/add";
import Edit from "@/views/aladdin/service/edit";
import Subscribe from "@/views/aladdin/service/subscribe";
export default {
  name: "AladdinService",
  components: {
    "v-table": Table,
    "v-edit": Edit,
    "v-add": Add,
    "v-subscribe": Subscribe,
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
          prop: "uuid",
          label: "UUID",
          width: "300px",
          slot: true,
        },
        {
          prop: "wechat",
          label: "微信",
          width: "200px",
        },
        {
          prop: "email",
          label: "邮箱",
          width: "200px",
        },
        {
          prop: "beginDate",
          label: "开始时间",
          width: "200px",
        },
        {
          prop: "endDate",
          label: "结束时间",
          width: "200px",
        },
        {
          prop: "dataTraffic",
          label: "流量(GB/月)",
          width: "100px",
        },
        {
          prop: "period",
          label: "周期(月)",
          width: "100px",
        },
        {
          prop: "price",
          label: "价格(元)",
          width: "100px",
        },
      ],
      options: [
        {
          value: "1",
          label: "正常",
        },
        {
          value: "0",
          label: "过期",
        },
      ],
      queryParams: {
        wechat: "",
        email: "",
        status: "",
      },
      tableData: {},
      addDialogVisible: false,
      editDialogVisible: false,
      editForm: null,
      subscribeDialogVisible: false,
      uuid: null,
    };
  },
  created() {
    this.fetchData(1, 15);
    this.$store.dispatch("aladdin_member/all", null);
    this.$store.dispatch("aladdin_package/all", null);
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
        .dispatch("aladdin_service/page", {
          current: current,
          size: size,
          wechat: this.queryParams.wechat,
          email: this.queryParams.email,
          status: this.queryParams.status,
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
      this.addDialogVisible = true;
    },
    edit(row) {
      this.editForm = row;
      this.editDialogVisible = true;
    },
    remove(row) {
      this.$store
        .dispatch("aladdin_service/remove", { id: row.id })
        .then((response) => {
          this.fetchData(this.tableData.current, this.tableData.size);
        })
        .catch(() => {});
    },
    subscribe(uuid) {
      this.uuid = uuid;
      this.subscribeDialogVisible = true;
    },
  },
};
</script>
