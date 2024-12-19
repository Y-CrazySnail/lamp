<template>
  <div class="app-container">
    <div style="margin: 0px 0px 15px 0px">
      <el-row>
        <el-form ref="queryParams" :model="queryParams" :inline="true" size="mini">
          <el-form-item>
            <el-button size="mini" type="primary" icon="el-icon-plus" @click="add">
              创建
            </el-button>
            <el-button size="mini" type="primary" icon="el-icon-search" @click="handleQuery">
              查询
            </el-button>
            <el-button size="mini" type="info" icon="el-icon-refresh" @click="handleReset">
              重置
            </el-button>
          </el-form-item>
        </el-form>
      </el-row>
    </div>
    <v-table :table-property.sync="tableProperty" :table-data.sync="tableData" @fetchData="fetchData">
      <template v-slot:operation="scope">
        <el-button size="mini" @click="inbound(scope.scope.row)">入站</el-button>
        <el-button size="mini" @click="edit(scope.scope.row)">编辑</el-button>
        <el-popconfirm confirm-button-text="确认" cancel-button-text="取消" icon="el-icon-info" icon-color="red"
          title="确认删除？" @confirm="remove(scope.scope.row)">
          <el-button size="mini" slot="reference">删除</el-button>
        </el-popconfirm>
      </template>
    </v-table>
    <el-drawer class="snail_dialog" title="入站" :visible.sync="inboundDialogVisible" size="420px">
      <v-inbound v-if="inboundDialogVisible" :id.sync="editId" :inbound-dialog-visible.sync="inboundDialogVisible" />
    </el-drawer>
    <el-drawer class="snail_dialog" title="创建" :visible.sync="addDialogVisible" size="420px">
      <v-add v-if="addDialogVisible" :add-dialog-visible.sync="addDialogVisible" />
    </el-drawer>
    <el-drawer class="snail_dialog" title="编辑" :visible.sync="editDialogVisible" size="420px">
      <v-edit v-if="editDialogVisible" :id.sync="editId" :edit-dialog-visible.sync="editDialogVisible" />
    </el-drawer>
  </div>
</template>

<script>
import Table from "@/components/Table/index";
import Add from "@/views/aladdin/server/add";
import Edit from "@/views/aladdin/server/edit";
import Inbound from "@/views/aladdin/server/inbound";
export default {
  name: "AladdinServer",
  components: {
    "v-table": Table,
    "v-edit": Edit,
    "v-add": Add,
    "v-inbound": Inbound
  },
  data() {
    return {
      tableProperty: [
        {
          prop: "id",
          label: "编号",
          width: "100px",
        },
        {
          prop: "apiIp",
          label: "API地址",
          width: "200px",
        },
        {
          prop: "apiPort",
          label: "API端口",
          width: "200px",
        },
        {
          prop: "apiUsername",
          label: "API用户名",
          width: "200px",
        },
        {
          prop: "apiPassword",
          label: "API密码",
          width: "200px",
        },
      ],
      queryParams: {
        wechat: "",
        email: "",
      },
      tableData: {},
      addDialogVisible: false,
      editDialogVisible: false,
      inboundDialogVisible: false,
      editId: null,
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
        .dispatch("aladdin_server/page", {
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
      this.addDialogVisible = true;
    },
    edit(row) {
      this.editId = row.id;
      this.editDialogVisible = true;
    },
    remove(row) {
      this.$store
        .dispatch("aladdin_server/remove", { id: row.id })
        .then((response) => {
          this.fetchData(this.tableData.current, this.tableData.size);
        })
        .catch(() => { });
    },
    inbound(row) {
      this.editId = row.id;
      this.inboundDialogVisible = true;
    },
  },
};
</script>
