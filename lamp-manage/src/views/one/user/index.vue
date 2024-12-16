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
              <el-form-item label="手机:">
                <el-input
                  v-model="queryParam.userPhone"
                  clearable
                  placeholder="手机"
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
      <template v-slot:userAvatar="data">
        <el-image
          style="width: 50px; height: 50px"
          :src="data.scope"
          fit="fill"
        />
      </template>
      <template v-slot:operation="scope">
        <el-button size="mini" @click="info(scope.scope.row)">详情</el-button>
      </template>
    </v-table>
    <el-drawer
      title="用户详细信息"
      :visible.sync="infoDialogVisible"
      direction="rtl"
      :destroy-on-close="true"
      :wrapperClosable="false"
      size="70%"
    >
      <v-info
        v-if="infoDialogVisible"
        :form.sync="editForm"
        :edit-dialog-visible.sync="infoDialogVisible"
      />
    </el-drawer>
  </div>
</template>

<script>
import Table from "@/components/Table/index";
import Info from "@/views/one/user/info";
export default {
  components: {
    "v-table": Table,
    "v-info": Info,
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
          prop: "wechatOpenId",
          label: "OPEN ID",
          width: "250px",
        },
        {
          prop: "nickName",
          label: "用户昵称",
          width: "250px",
        },
        {
          prop: "userAvatar",
          label: "用户头像",
          width: "200px",
          slot: true,
        },
        {
          prop: "userPhone",
          label: "用户手机",
          width: "250px",
        },
        {
          prop: "userAddressCount",
          label: "地址数量",
          width: "200px",
        },
      ],
      tableData: {},
      // 编辑页面标识
      infoDialogVisible: false,
      // 编辑表单
      editForm: null,
      queryParam: {
        nickName: "",
        userPhone: "",
      },
    };
  },
  created() {
    this.fetchData(1, 15);
  },
  watch: {
    infoDialogVisible(newVal, oldVal) {
      if (!newVal) {
        this.fetchData(1, 15);
      }
    },
  },
  methods: {
    fetchData(current, size) {
      this.$store
        .dispatch("one_user/page", {
          current: current,
          size: size,
          nickName: this.queryParam.nickName,
          userPhone: this.queryParam.userPhone,
        })
        .then((response) => {
          this.tableData = response;
        })
        .catch(() => {});
    },
    info(row) {
      this.$store
        .dispatch("one_user/get", { id: row.id })
        .then((response) => {
          this.editForm = response;
          this.infoDialogVisible = true;
        })
        .catch(() => {});
    },
    reset() {
      this.queryParam = {};
      this.fetchData(1, 15);
    },
  },
};
</script>
