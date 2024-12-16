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
              <el-form-item label="商品类别:">
                <el-select
                  v-model="queryParam.categoryId"
                  placeholder="商品类别"
                  clearable
                >
                  <el-option
                    v-for="item in categoryList"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id"
                  />
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="商品名称:">
                <el-input
                  v-model="queryParam.name"
                  clearable
                  placeholder="商品名称"
                />
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
      <template v-slot:recommendFlag="data">
        {{ data.scope ? "是" : "否" }}
      </template>
      <template v-slot:categoryId="data">
        {{ getCategoryName(data.scope) }}
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
      width="70%"
    >
      <v-add
        v-if="addDialogVisible"
        :add-dialog-visible.sync="addDialogVisible"
        :category-list.sync="categoryList"
      />
    </el-dialog>
    <el-dialog
      class="snail_dialog"
      title="编辑"
      :visible.sync="editDialogVisible"
      width="70%"
    >
      <v-edit
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
import Add from "@/views/zero/product/add";
import Edit from "@/views/zero/product/edit";
export default {
  name: "ZeroProduct",
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
          prop: "categoryId",
          label: "类别",
          width: "100px",
          slot: true,
        },
        {
          prop: "name",
          label: "名称",
          width: "100px",
        },
        {
          prop: "description",
          label: "描述",
          width: "200px",
        },
        {
          prop: "price",
          label: "价格(元)",
          width: "100px",
        },
        {
          prop: "stock",
          label: "库存(件)",
          width: "100px",
        },
        {
          prop: "sale",
          label: "销量(件)",
          width: "100px",
        },
        {
          prop: "sort",
          label: "权重",
          width: "50px",
        },
        {
          prop: "recommendFlag",
          label: "推荐标识",
          width: "100px",
          slot: true,
        },
        {
          prop: "directReferrerRate",
          label: "一级分销商提成",
          width: "150px",
        },
        {
          prop: "indirectReferrerRate",
          label: "二级分销商提成",
          width: "150px",
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
    };
  },
  created() {
    this.fetchData(1, 15);
    this.$store
      .dispatch("zero_category/dict")
      .then((response) => (this.categoryList = response));
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
        .dispatch("zero_product/page", {
          current: current,
          size: size,
          name: this.queryParam.name,
          categoryId: this.queryParam.categoryId,
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
        .dispatch("zero_product/get", { id: row.id })
        .then((response) => {
          this.editForm = response;
          this.editDialogVisible = true;
        })
        .catch(() => {});
    },
    remove(row) {
      this.$store
        .dispatch("zero_product/remove", { id: row.id })
        .then((response) => {
          this.fetchData(this.tableData.current, this.tableData.size);
        })
        .catch(() => {});
    },
    reset() {
      this.queryParam = {};
    },
    getCategoryName(categoryId) {
      let category = this.categoryList.find((e) => e.id == categoryId);
      if (category) {
        return category.name;
      } else {
        return "";
      }
    },
  },
};
</script>
