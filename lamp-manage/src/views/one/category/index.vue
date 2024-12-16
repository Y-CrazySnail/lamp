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
              <el-form-item label="店铺:">
                <el-select
                  v-model="queryParam.storeId"
                  clearable
                  placeholder="店铺"
                >
                  <el-option
                    v-for="item in storeList"
                    :key="item.id"
                    :label="item.storeName"
                    :value="item.id"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="分类名称:">
                <el-input
                  v-model="queryParam.categoryName"
                  placeholder="分类名称"
                  clearable
                />
              </el-form-item>
            </el-col>
          </el-form>
        </el-row>
        <el-row>
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
            @click="fetchData(1, 15)"
          >
            查询
          </el-button>
          <el-button
            size="mini"
            type="info"
            icon="el-icon-refresh"
            @click="reset"
          >
            重置
          </el-button>
        </el-row>
      </div>
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
    <el-drawer
      title="创建"
      :visible.sync="addDialogVisible"
      direction="rtl"
      :destroy-on-close="true"
      :wrapperClosable="false"
      size="50%"
    >
      <v-add
        v-if="addDialogVisible"
        :add-dialog-visible.sync="addDialogVisible"
      />
    </el-drawer>
    <el-drawer
      title="编辑"
      :visible.sync="editDialogVisible"
      :destroy-on-close="true"
      :wrapperClosable="false"
      size="50%"
    >
      <v-edit
        v-if="editDialogVisible"
        :form.sync="editForm"
        :edit-dialog-visible.sync="editDialogVisible"
      />
    </el-drawer>
  </div>
</template>

<script>
import Table from "@/components/Table/index";
import Add from "@/views/one/category/add";
import Edit from "@/views/one/category/edit";
export default {
  components: {
    "v-table": Table,
    "v-edit": Edit,
    "v-add": Add
  },
  data() {
    return {
      tableProperty: [
        {
          prop: "id",
          label: "编号",
          width: "200px"
        },
        {
          prop: "categoryName",
          label: "名称",
          width: "300px"
        },
        {
          prop: "categorySort",
          label: "排序",
          width: "300px"
        },
        {
          prop: "storeName",
          label: "店铺名称",
          width: "300px"
        }
      ],
      tableData: {},
      addDialogVisible: false,
      // 编辑页面标识
      editDialogVisible: false,
      // 编辑表单
      editForm: null,
      queryParam: {
        storeId: null,
        categoryName: ""
      }
    };
  },
  created() {
    this.fetchData(1, 15);
    this.$store.dispatch("one_store/all", null);
    this.$store.dispatch("one_tenant/all", null);
  },
  computed: {
    storeList() {
      return this.$store.state.one_store.storeList || [];
    }
  },
  watch: {
    addDialogVisible(newVal, oldVal) {
      if (!newVal) {
        this.fetchData(1, 15);
      }
    },
    editDialogVisible(newVal, oldVal) {
      if (!newVal) {
        this.fetchData(1, 15);
      }
    }
  },
  methods: {
    fetchData(current, size) {
      this.$store
        .dispatch("one_category/page", {
          current: current,
          size: size,
          storeId: this.queryParam.storeId,
          categoryName: this.queryParam.categoryName
        })
        .then(response => {
          this.tableData = response;
        })
        .catch(() => {});
    },
    add() {
      this.addDialogVisible = true;
    },
    edit(row) {
      this.$store
        .dispatch("one_category/get", { id: row.id })
        .then(response => {
          this.editForm = response;
          this.editDialogVisible = true;
        })
        .catch(() => {});
    },
    remove(row) {
      this.$store
        .dispatch("one_category/remove", { id: row.id })
        .then(response => {
          this.fetchData(this.tableData.current, this.tableData.size);
        })
        .catch(() => {});
    },
    reset() {
      this.queryParam = {};
      this.fetchData(1, 15);
    }
  }
};
</script>
