<template>
  <div class="app-container">
    <div style="margin: 0px 0px 15px 0px">
      <el-button size="small" @click="add">创建</el-button>
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
    <el-dialog class="snail_dialog" title="创建" :visible.sync="addDialogVisible" width="40%">
      <v-add v-if="addDialogVisible" v-bind:add-dialog-visible.sync="addDialogVisible" />
    </el-dialog>
    <el-dialog class="snail_dialog" title="编辑" :visible.sync="editDialogVisible" width="40%">
      <v-edit
        v-if="editDialogVisible"
        :form.sync="editForm"
        v-bind:edit-dialog-visible.sync="editDialogVisible"
      />
    </el-dialog>
  </div>
</template>

<script>
import Table from '@/components/Table/index'
import Add from '@/views/system/permission/add'
import Edit from '@/views/system/permission/edit'
export default {
  name: 'Permission',
  components: {
    'v-table': Table,
    'v-edit': Edit,
    'v-add': Add
  },
  data() {
    return {
      tableProperty: [
        {
          prop: 'id',
          label: '编号',
          width: '200px'
        },
        {
          prop: 'name',
          label: '名称',
          width: '200px'
        },
        {
          prop: 'url',
          label: '路径',
          width: '300px'
        },
        {
          prop: 'description',
          label: '描述',
          width: '800px'
        }
      ],
      tableData: {},
      addDialogVisible: false,
      // 编辑页面标识
      editDialogVisible: false,
      // 编辑表单
      editForm: null
    }
  },
  created() {
    this.fetchData(1, 15)
  },
  watch: {
    addDialogVisible(newVal, oldVal) {
      if (!newVal) {
        this.fetchData(this.tableData.current, this.tableData.size)
      }
    },
    editDialogVisible(newVal, oldVal) {
      if (!newVal) {
        this.fetchData(this.tableData.current, this.tableData.size)
      }
    }
  },
  methods: {
    fetchData(current, size) {
      this.$store
        .dispatch('permission/page', { current: current, size: size })
        .then(response => {
          this.tableData = response
        })
        .catch(() => {})
    },
    add() {
      this.addDialogVisible = true
    },
    edit(row) {
      this.editForm = row
      this.editDialogVisible = true
    },
    remove(row) {
      this.$store
        .dispatch('permission/remove', { id: row.id })
        .then(response => {
          this.fetchData()
        })
        .catch(() => {})
    }
  }
}
</script>
