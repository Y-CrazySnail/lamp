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
              <el-form-item label="店铺名称:">
                <el-input
                  v-model="queryParam.storeName"
                  clearable
                  placeholder="客户名称"
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
      direction="rtl"
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
import Add from "@/views/one/store/add";
import Edit from "@/views/one/store/edit";
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
          width: "100px"
        },
        {
          prop: "storeName",
          label: "名称",
          width: "150px"
        },
        {
          prop: "storeLogo",
          label: "头像",
          width: "150px"
        },
        {
          prop: "storeWechatQrcode",
          label: "二维码",
          width: "150px"
        },
        {
          prop: "storeWechat",
          label: "用户微信",
          width: "150px"
        },
        {
          prop: "storeDescription",
          label: "店铺介绍",
          width: "150px"
        },
        {
          prop: "storePhone",
          label: "手机",
          width: "150px"
        },
        {
          prop: "storeLongitude",
          label: "店铺经度",
          width: "150px"
        },
        {
          prop: "storeLatitude",
          label: "店铺纬度",
          width: "150px"
        },
        {
          prop: "storeAddress",
          label: "店铺地址",
          width: "500px"
        }
      ],
      tableData: {},
      addDialogVisible: false,
      // 编辑页面标识
      editDialogVisible: false,
      // 编辑表单
      editForm: null,
      queryParam: {
        storeName: ""
      }
    };
  },
  created() {
    this.fetchData(1, 15);
    this.$store.dispatch("one_tenant/all", null);
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
        .dispatch("one_store/page", {
          current: current,
          size: size,
          storeName: this.queryParam.storeName,
          storePhone: this.queryParam.storePhone,
          storeEmail: this.queryParam.storeEmail
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
        .dispatch("one_store/get", { id: row.id })
        .then(response => {
          this.editForm = response;
          this.editDialogVisible = true;
        })
        .catch(() => {});
    },
    remove(row) {
      this.$store
        .dispatch("one_store/remove", { id: row.id })
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
