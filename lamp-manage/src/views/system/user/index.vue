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
      <template v-slot:accountNonExpired="data">{{data.scope?'是':'否'}}</template>
      <template v-slot:accountNonLocked="data">{{data.scope?'是':'否'}}</template>
      <template v-slot:credentialsNonExpired="data">{{data.scope?'是':'否'}}</template>
      <template v-slot:enabled="data">{{data.scope?'是':'否'}}</template>
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
    <el-dialog title="创建" :visible.sync="addDialogVisible" width="800px">
      <v-add v-if="addDialogVisible" v-bind:add-dialog-visible.sync="addDialogVisible" />
    </el-dialog>
    <el-dialog title="编辑" :visible.sync="editDialogVisible" width="800px">
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
import Edit from '@/views/system/user/edit'
import Add from '@/views/system/user/add'
export default {
  name: 'User',
  components: {
    'v-table': Table,
    'v-edit': Edit,
    'v-add': Add
  },
  data() {
    return {
      // 表格列信息
      tableProperty: [
        {
          prop: 'id',
          label: '编号',
          width: '100px'
        },
        {
          prop: 'username',
          label: '账号',
          width: '200px'
        },
        {
          prop: 'password',
          label: '密码',
          width: '550px'
        },
        {
          prop: 'accountNonExpired',
          label: '账号未过期',
          width: '200px',
          slot: true
        },
        {
          prop: 'accountNonLocked',
          label: '账号未锁定',
          width: '200px',
          slot: true
        },
        {
          prop: 'credentialsNonExpired',
          label: '认证信息未过期',
          width: '200px',
          slot: true
        },
        {
          prop: 'enabled',
          label: '账号可用',
          width: '200px',
          slot: true
        }
      ],
      // 表格内容
      tableData: {},
      // 编辑页面标识
      editDialogVisible: false,
      // 编辑表单
      editForm: null,
      // 创建页面表示
      addDialogVisible: false
    }
  },
  created() {
    this.$store.dispatch('role/all')
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
    // 获取用户信息列表
    fetchData(current, size) {
      this.loading = true
      this.$store
        .dispatch('user/page', { current: current, size: size })
        .then(response => {
          this.tableData = response
          this.loading = false
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
        .dispatch('user/remove', { id: row.id })
        .then(response => {
          this.fetchData()
        })
        .catch(() => {})
    }
  }
}
</script>
