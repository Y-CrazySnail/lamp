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
          <el-form-item label="微信|邮箱:" prop="wechat">
            <el-input
              v-model="queryParams.keywords"
              clearable
              placeholder="微信|邮箱"
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
    <el-drawer title="创建" :visible.sync="addDrawerVisible" size="420px">
      <v-add
        v-if="addDrawerVisible"
        :add-drawer-visible.sync="addDrawerVisible"
      />
    </el-drawer>
    <el-drawer title="编辑" :visible.sync="editDrawerVisible" size="420px">
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
          width: "60px",
        },
        {
          prop: "wechat",
          label: "微信",
          width: "100px",
        },
        {
          prop: "email",
          label: "邮箱",
          width: "200px",
        },
        {
          prop: "password",
          label: "密码",
          width: "150px",
        },
        {
          prop: "bandwidth",
          label: "套餐流量",
          width: "100px",
        },
        {
          prop: "monthBandwidth",
          label: "本月剩余",
          width: "200px",
        },
        {
          prop: "monthBandwidthUp",
          label: "本月上行",
          width: "200px",
        },
        {
          prop: "monthBandwidthDown",
          label: "本月下行",
          width: "200px",
        },
        {
          prop: "expiryDate",
          label: "到期时间",
          width: "100px",
        },
        {
          prop: "lastSyncTime",
          label: "最后同步时间",
          width: "120px",
        },
        {
          prop: "uuid",
          label: "uuid",
          width: "200px",
        },
        {
          prop: "referralCode",
          label: "推荐码",
          width: "100px",
        },
        {
          prop: "referrerCode",
          label: "推荐人",
          width: "100px",
        },
        {
          prop: "level",
          label: "会员等级",
          width: "100px",
        },
        {
          prop: "remark",
          label: "备注",
          width: "150px",
        },
      ],
      queryParams: {
        keywords: "",
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
          keywords: this.queryParams.keywords,
        })
        .then((response) => {
          this.tableData = response;
        });
    },
    handleQuery() {
      this.fetchData(this.tableData.current, this.tableData.size);
    },
    handleReset() {
      this.queryParams = {
        keywords: "",
      };
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
