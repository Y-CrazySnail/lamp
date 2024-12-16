<template>
  <div class="app-container">
    <div style="display: flex">
      <div style="width: 23%; margin-right: 2%">
        商品信息：
        <el-select
          v-model="spuId"
          placeholder="商品信息"
          filterable
          @change="onChangeSpu"
        >
          <el-option
            v-for="item in spuList"
            :key="item.id"
            :label="item.spuName"
            :value="item.id"
          >
          </el-option>
        </el-select>
        <el-descriptions
          :column="1"
          border
          v-if="spu.id"
          style="margin-top: 20px"
        >
          <el-descriptions-item>
            <template slot="label"> 编号 </template>
            {{ spu.id }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label"> 店铺名称 </template>
            {{ spu.storeName }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label"> 分类名称 </template>
            {{ spu.categoryName }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label"> 名称 </template>
            {{ spu.spuName }}
          </el-descriptions-item>
          <el-descriptions-item
            v-show="spu.spuAttribute"
            v-for="(item, index) in JSON.parse(spu.spuAttribute)"
            :key="index"
          >
            <template slot="label"> {{ item.attributeKey }} </template>
            {{ item.attributeValue }}
          </el-descriptions-item>
        </el-descriptions>
      </div>
      <div style="width: 75%">
        <div>
          <div style="margin: 0px 0px 15px 0px">
            <el-row>
              <el-button
                size="mini"
                type="primary"
                icon="el-icon-plus"
                @click="add"
                v-if="this.spu.id"
              >
                新增
              </el-button>
            </el-row>
          </div>
        </div>
        <v-table
          :table-property.sync="tableProperty"
          :table-data.sync="tableData"
          @fetchData="fetchData"
        >
          <template v-slot:skuStatus="data">
            <el-tag type="success" v-if="data.scope == true">上架</el-tag>
            <el-tag type="danger" v-if="data.scope == false">下架</el-tag>
          </template>
          <template v-slot:operation="scope">
            <el-button size="mini" @click="edit(scope.scope.row)">
              编辑
            </el-button>
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
      </div>
    </div>
    <el-drawer
      title="创建商品库存"
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
      title="维护商品库存"
      :visible.sync="editDialogVisible"
      direction="rtl"
      :destroy-on-close="true"
      :wrapperClosable="false"
      size="50%"
    >
      <v-edit
        v-if="editDialogVisible"
        :editForm.sync="editForm"
        :edit-dialog-visible.sync="editDialogVisible"
      />
    </el-drawer>
  </div>
</template>

<script>
import Table from "@/components/Table/index";
import Add from "@/views/one/sku/add";
import Edit from "@/views/one/sku/edit";
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
          prop: "skuName",
          label: "SKU名称",
          width: "150px",
        },
        {
          prop: "skuStatus",
          label: "SKU状态",
          width: "100px",
          slot: true,
        },
        {
          prop: "skuAttribute",
          label: "SKU属性",
          width: "300px",
        },
        {
          prop: "skuPrice",
          label: "价格",
          width: "100px",
        },
        {
          prop: "skuStock",
          label: "库存",
          width: "100px",
        },
        {
          prop: "skuSales",
          label: "销量",
          width: "100px",
        },
        {
          prop: "skuSort",
          label: "排序",
          width: "100px",
        },
      ],
      tableData: {},
      addDialogVisible: false,
      // 编辑页面标识
      editDialogVisible: false,
      // 编辑表单
      editForm: null,
      spuId: null,
    };
  },
  mounted() {
    if (this.spu && this.spu.id) {
      this.spuId = this.spu.id;
    }
    this.$store.dispatch("one_spu/all", null);
    this.fetchData(1, 15);
  },
  computed: {
    spuList() {
      let spuList = this.$store.state.one_spu.spuList;
      return spuList || [];
    },
    spu() {
      return this.$store.state.one_sku.spu;
    },
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
    },
  },
  methods: {
    fetchData(current, size) {
      if (!this.spuId) {
        return;
      }
      this.$store
        .dispatch("one_sku/page", {
          current: current,
          size: size,
          spuId: this.spuId,
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
        .dispatch("one_sku/get", { id: row.id })
        .then((response) => {
          this.editForm = response;
          this.editDialogVisible = true;
        })
        .catch(() => {});
    },
    remove(row) {
      this.$store
        .dispatch("one_sku/remove", { id: row.id })
        .then((response) => {
          this.fetchData(this.tableData.current, this.tableData.size);
        })
        .catch(() => {});
    },
    onChangeSpu() {
      this.$store
        .dispatch("one_spu/get", {
          id: this.spuId,
        })
        .then((response) => {
          this.$store.state.one_sku.spu = response;
        });
      this.fetchData(1, 15);
    },
    onChangeStore() {
      this.queryParam.categoryId = null;
    },
  },
};
</script>
