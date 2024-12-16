<template>
  <div class="app-container">
    <div style="margin: 0px 0px 15px 0px">
      <el-row>
        <el-form
          ref="queryParams"
          :model="queryParams"
          :inline="true"
          size="mini"
          label-width="90px"
        >
          <el-col :span="6">
            <el-form-item label="管理人姓名:" prop="managerName">
              <el-input
                v-model="queryParams.managerName"
                clearable
                placeholder="输入管理人姓名"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="产品编号:" prop="productNo">
              <el-input
                v-model="queryParams.productNo"
                clearable
                placeholder="输入产品编号"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="产品名称:" prop="productName">
              <el-input
                v-model="queryParams.productName"
                clearable
                placeholder="输入产品名称"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="企业名称:" prop="companyName">
              <el-input
                v-model="queryParams.companyName"
                clearable
                placeholder="输入企业名称"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="管理人电话:" prop="managerPhone">
              <el-input
                v-model="queryParams.managerPhone"
                clearable
                placeholder="输入管理人电话"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="信用代码:" prop="companyNo">
              <el-input
                v-model="queryParams.companyNo"
                clearable
                placeholder="输入统一社会信用代码"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="开通小程序:" prop="miniProgramFlag">
              <el-select v-model="queryParams.miniProgramFlag" clearable>
                <el-option label="未开通" value="0" />
                <el-option label="已开通" value="1" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="官方网站:" prop="officialWebsiteFlag">
              <el-select v-model="queryParams.officialWebsiteFlag" clearable>
                <el-option label="无官网" value="0" />
                <el-option label="有官网" value="1" />
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
          @click="handleQuery"
          >查询</el-button
        >
        <el-button
          size="mini"
          type="info"
          icon="el-icon-refresh"
          @click="handleReset"
          >重置</el-button
        >
      </el-row>
    </div>
    <v-table
      :table-property.sync="tableProperty"
      :table-data.sync="tableData"
      @fetchData="fetchData"
    >
      <template v-slot:miniProgramFlag="data">{{
        data.scope === "1" ? "是" : "否"
      }}</template>
      <template v-slot:officialWebsiteFlag="data">{{
        data.scope === "1" ? "是" : "否"
      }}</template>
      <template v-slot:operation="scope">
        <el-button
          size="mini"
          icon="el-icon-edit"
          @click="edit(scope.scope.row)"
          >编辑</el-button
        >
        <el-popconfirm
          confirm-button-text="确认"
          cancel-button-text="取消"
          icon="el-icon-info"
          icon-color="red"
          title="确认删除？"
          @confirm="remove(scope.scope.row)"
        >
          <el-button size="mini" icon="el-icon-delete" slot="reference"
            >删除</el-button
          >
        </el-popconfirm>
      </template>
    </v-table>
    <el-dialog
      class="snail_dialog"
      title="创建"
      :visible.sync="addDialogVisible"
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
    >
      <v-edit
        v-if="editDialogVisible"
        :form.sync="editForm"
        :edit-dialog-visible.sync="editDialogVisible"
      />
    </el-dialog>
  </div>
</template>

<script>
import Table from "@/components/Table/index.vue";
import Add from "@/views/car-film/tenant/add";
import Edit from "@/views/car-film/tenant/edit";
export default {
  name: "CFTenantIndex",
  components: {
    "v-table": Table,
    "v-edit": Edit,
    "v-add": Add,
  },
  data() {
    return {
      tableProperty: [
        {
          prop: "tenantNo",
          label: "客户编号",
          width: "100px",
        },
        {
          prop: "tenantName",
          label: "客户名称",
          width: "150px",
        },
        {
          prop: "publishDate",
          label: "产品成立日期",
          width: "150px",
        },
        {
          prop: "operationPrice",
          label: "产品运营维护价格",
          width: "150px",
        },
        {
          prop: "companyName",
          label: "企业名称",
          width: "300px",
        },
        {
          prop: "companyNo",
          label: "统一社会信用代码",
          width: "200px",
        },
        {
          prop: "managerName",
          label: "管理人姓名",
          width: "100px",
        },
        {
          prop: "managerPhone",
          label: "管理人手机",
          width: "150px",
        },
        {
          prop: "managerEmail",
          label: "管理人邮箱",
          width: "200px",
        },
        {
          prop: "miniProgramFlag",
          label: "开通小程序",
          width: "100px",
          slot: true,
        },
        {
          prop: "miniProgramName",
          label: "小程序名称",
          width: "150px",
        },
        {
          prop: "officialWebsiteFlag",
          label: "官方网站",
          width: "100px",
          slot: true,
        },
        {
          prop: "officialWebsiteDomain",
          label: "官方网站域名",
          width: "300px",
        },
      ],
      tableData: {},
      queryParams: {
        productNo: "",
        productName: "",
        companyName: "",
        companyNo: "",
        managerName: "",
        managerPhone: "",
        miniProgramFlag: "",
        officialWebsiteFlag: "",
      },
      addDialogVisible: false,
      // 编辑页面标识
      editDialogVisible: false,
      // 编辑表单
      editForm: null,
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
        .dispatch("cf_tenant/page", {
          current: current,
          size: size,
          productNo: this.queryParams.productNo,
          productName: this.queryParams.productName,
          companyName: this.queryParams.companyName,
          companyNo: this.queryParams.companyNo,
          managerName: this.queryParams.managerName,
          managerPhone: this.queryParams.managerPhone,
          miniProgramFlag: this.queryParams.miniProgramFlag,
          officialWebsiteFlag: this.queryParams.officialWebsiteFlag,
        })
        .then((response) => {
          this.tableData = response;
        })
        .catch(() => {});
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
        .dispatch("car_film_tenant/remove", { id: row.id })
        .then((response) => {
          this.fetchData(this.tableData.current, this.tableData.size);
        })
        .catch(() => {});
    },
  },
};
</script>