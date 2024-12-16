<template>
  <div>
    <el-table :data="tableData.records" :show-summary="showSummary" border fit stripe highlight-current-row size="mini">
      <el-table-column
        v-for="(column, index) in tableProperty"
        :key="index"
        :prop="column.prop"
        :label="column.label"
        :width="column.width"
        :show-overflow-tooltip="true"
      >
        <template slot-scope="scope">
          <template v-if="column.slot">
            <slot :name="column.prop" :scope="scope.row[column.prop]"></slot>
          </template>
          <span v-else>{{ scope.row[column.prop] }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" fixed="right" :width="operateWidth">
        <template slot-scope="scope">
          <slot name="operation" :scope="scope" />
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="tableData.current"
      :page-sizes="[15, 50, 100, 500]"
      :page-size="tableData.size"
      layout="total, sizes, prev, pager, next, jumper"
      :total="tableData.total"
    />
  </div>
</template>

<script>
export default {
  name: "Table",
  props: {
    tableProperty: {
      type: Array,
      required: true,
    },
    tableData: {
      type: Object,
      required: true,
    },
    showSummary: {
      type: Boolean,
      required: false,
    },
    operateWidth:{
      type: String,
      required: false,
      default: '200px',
    }
  },
  data() {
    return {};
  },
  created() {},
  methods: {
    handleSizeChange(val) {
      this.tableData.size = val;
      this.$emit("fetchData", this.tableData.current, this.tableData.size);
    },
    handleCurrentChange(val) {
      this.tableData.current = val;
      this.$emit("fetchData", this.tableData.current, this.tableData.size);
    },
  },
};
</script>
