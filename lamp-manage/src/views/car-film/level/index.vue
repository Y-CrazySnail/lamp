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
          <el-form-item label="品牌名称:" prop="name">
            <el-input
              v-model="queryParams.name"
              clearable
              placeholder="输入品牌名称"
            />
          </el-form-item>
          <el-form-item>
            <el-button
              size="mini"
              type="primary"
              icon="el-icon-plus"
              @click="add"
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
import Add from "@/views/car-film/brand/add";
import Edit from "@/views/car-film/brand/edit";
export default {
  name: "carBrand",
  components: {
    "v-table": Table,
    "v-edit": Edit,
    "v-add": Add,
  },
  data() {
    return {
      tableProperty: [
        {
          prop: "name",
          label: "品牌名称",
          width: "200px",
        },
        {
          prop: "nameEn",
          label: "英文名称",
          width: "200px",
        },
        {
          prop: "logoPath",
          label: "品牌logo",
          width: "500px",
        },
        {
          prop: "logoName",
          label: "品牌logo名称",
          width: "400px",
        },
      ],
      tableData: {},
      queryParams: {
        name: "",
      },
      addDialogVisible: false,
      // 编辑页面标识
      editDialogVisible: false,
      // 编辑表单
      editForm: null,
      // 查看车型
      searchModelDialogVisible: false,
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
        .dispatch("car_brand/pages", {
          current: current,
          size: size,
          name: this.queryParams.name,
        })
        .then((response) => {
          this.tableData = response;
        })
        .catch(() => {});
    },
    handleQuery() {
      this.getList();
    },
    handleReset() {
      this.$refs.queryParams.resetFields();
      this.getList();
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
        .dispatch("car_brand/remove", { id: row.id })
        .then((response) => {
          this.getList();
        })
        .catch(() => {});
    },
  },
};
</script>