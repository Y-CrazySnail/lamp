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
                  @change="onChangeStore"
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
              <el-form-item label="分类:">
                <el-select
                  v-model="queryParam.categoryId"
                  clearable
                  placeholder="分类"
                >
                  <el-option
                    v-for="item in categoryList"
                    :key="item.id"
                    :label="item.categoryName"
                    :value="item.id"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="SPU名称:">
                <el-input
                  v-model="queryParam.spuName"
                  placeholder="SPU名称"
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
      :operate-width="'250px'"
    >
      <template v-slot:spuStatus="data">
        <el-tag type="success" v-if="data.scope == true">上架</el-tag>
        <el-tag type="danger" v-if="data.scope == false">下架</el-tag>
      </template>
      <template v-slot:operation="scope">
        <el-button size="mini" @click="edit(scope.scope.row)">编辑</el-button>
        <el-button size="mini" @click="sku(scope.scope.row)">
          库存维护
        </el-button>
        <el-popconfirm
          confirm-button-text="确认"
          cancel-button-text="取消"
          icon="el-icon-info"
          icon-color="red"
          title="确认删除？"
          @confirm="remove(scope.scope.row)"
          style="margin-left: 10px"
        >
          <el-button size="mini" slot="reference">删除</el-button>
        </el-popconfirm>
      </template>
    </v-table>
    <el-drawer
      title="创建标准产品单元"
      :visible.sync="addDrawerVisible"
      direction="rtl"
      :destroy-on-close="true"
      :wrapperClosable="false"
      size="60%"
    >
      <v-add
        v-if="addDrawerVisible"
        :add-dialog-visible.sync="addDrawerVisible"
      />
    </el-drawer>
    <el-drawer
      title="编辑标准产品单元"
      :visible.sync="editDrawerVisible"
      direction="rtl"
      :destroy-on-close="true"
      :wrapperClosable="false"
      size="60%"
    >
      <v-edit
        v-if="editDrawerVisible"
        :edit-form.sync="form"
        :edit-dialog-visible.sync="editDrawerVisible"
      />
    </el-drawer>
  </div>
</template>

<script>
import Table from "@/components/Table/index";
import Add from "@/views/one/spu/add";
import Edit from "@/views/one/spu/edit";
export default {
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
          prop: "storeName",
          label: "店铺名称",
          width: "150px",
        },
        {
          prop: "categoryName",
          label: "分类名称",
          width: "150px",
        },
        {
          prop: "spuName",
          label: "SPU名称",
          width: "200px",
        },
        {
          prop: "spuSort",
          label: "SPU排序",
          width: "100px",
        },
        {
          prop: "spuStatus",
          label: "SPU状态",
          width: "100px",
          slot: true,
        },
        {
          prop: "spuAttribute",
          label: "SPU属性",
          width: "500px",
        },
      ],
      tableData: {},
      addDrawerVisible: false,
      editDrawerVisible: false,
      skuDrawerVisible: false,
      form: null,
      queryParam: {
        storeId: null,
        categoryId: null,
        spuName: "",
      },
    };
  },
  created() {
    this.fetchData(1, 15);
    this.$store.dispatch("one_tenant/all", null);
    this.$store.dispatch("one_store/all", null);
    this.$store.dispatch("one_category/all", null);
  },
  computed: {
    storeList() {
      let storeList = this.$store.state.one_store.storeList;
      return storeList || [];
    },
    categoryList() {
      if (this.queryParam.storeId) {
        return (
          this.$store.state.one_category.categoryList.filter(
            (e) => e.storeId == this.queryParam.storeId
          ) || []
        );
      } else {
        return this.$store.state.one_category.categoryList || [];
      }
    },
  },
  watch: {
    addDrawerVisible(newVal, oldVal) {
      if (!newVal) {
        this.fetchData(1, 15);
      }
    },
    editDrawerVisible(newVal, oldVal) {
      if (!newVal) {
        this.fetchData(1, 15);
      }
    },
    skuDrawerVisible(newVal, oldVal) {
      if (!newVal) {
        this.fetchData(1, 15);
      }
    },
  },
  methods: {
    fetchData(current, size) {
      this.$store
        .dispatch("one_spu/page", {
          current: current,
          size: size,
          storeId: this.queryParam.storeId,
          categoryId: this.queryParam.categoryId,
          spuName: this.queryParam.spuName,
        })
        .then((response) => {
          this.tableData = response;
        })
        .catch(() => {});
    },
    add() {
      this.addDrawerVisible = true;
    },
    edit(row) {
      this.$store
        .dispatch("one_spu/get", { id: row.id })
        .then((response) => {
          this.form = response;
          this.editDrawerVisible = true;
        })
        .catch(() => {});
    },
    remove(row) {
      this.$store
        .dispatch("one_spu/remove", { id: row.id })
        .then((response) => {
          this.fetchData(this.tableData.current, this.tableData.size);
        })
        .catch(() => {});
    },
    reset() {
      this.queryParam = {};
      this.fetchData(1, 15);
    },
    onChangeStore() {
      this.queryParam.categoryId = null;
    },
    sku(row) {
      this.$store.state.one_sku.spu = row;
      this.$router.push({ path: "/one/sku" });
    },
  },
};
</script>
