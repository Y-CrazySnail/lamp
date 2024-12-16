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
            <el-form-item label="产品代码:" prop="productNo">
              <el-select v-model="queryParams.productNo" clearable>
                <el-option
                  v-for="item in tenantList"
                  :key="item.productNo"
                  :label="item.productNo + '_' + item.productName"
                  :value="item.productNo"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="手机号:" prop="phone">
              <el-input
                v-model="queryParams.phone"
                clearable
                placeholder="手机号"
              />
            </el-form-item>
          </el-col>
        </el-form>
      </el-row>
      <el-row>
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
          >重置</el-button
        >
      </el-row>
    </div>
    <v-table
      :table-property.sync="tableProperty"
      :table-data.sync="tableData"
      @fetchData="fetchData"
    >
      <template v-slot:qualityPermission="data">
        <el-tag type="success" v-show="data.scope == 1"> 已授权 </el-tag>
        <el-tag type="danger" v-show="data.scope == 0"> 未授权 </el-tag>
        <el-tag type="warning" v-show="data.scope == -1"> 申请授权 </el-tag>
      </template>
      <template v-slot:operation="scope">
        <el-button
          size="mini"
          icon="el-icon-edit"
          @click="edit(scope.scope.row)"
        >
          编辑
        </el-button>
      </template>
    </v-table>
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
import Edit from "@/views/car-film/user/edit";
export default {
  name: "CarFilmUserIndex",
  components: {
    "v-table": Table,
    "v-edit": Edit,
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
          prop: "productNo",
          label: "产品代码",
          width: "200px",
        },
        {
          prop: "openId",
          label: "Open ID",
          width: "300px",
        },
        {
          prop: "nickName",
          label: "名称",
          width: "200px",
        },
        {
          prop: "phone",
          label: "手机",
          width: "200px",
        },
        {
          prop: "qualityPermission",
          label: "质保录入权限",
          width: "200px",
          slot: true,
        },
      ],
      tableData: {},
      queryParams: {
        productNo: "",
        phone: "",
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
    this.$store.dispatch("car_film_tenant/list", null);
  },
  computed: {
    tenantList() {
      return this.$store.state.car_film_tenant.tenantList || [];
    },
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
        .dispatch("car_film_user/pages", {
          current: current,
          size: size,
          productNo: this.queryParams.productNo,
          phone: this.queryParams.phone,
        })
        .then((response) => {
          this.tableData = response;
        })
        .catch(() => {});
    },
    handleQuery() {
      this.fetchData(1, this.tableData.size);
    },
    handleReset() {
      this.$refs.queryParams.resetFields();
      this.fetchData(1, this.tableData.size);
    },
    edit(row) {
      this.editForm = row;
      this.editDialogVisible = true;
    },
  },
};
</script>