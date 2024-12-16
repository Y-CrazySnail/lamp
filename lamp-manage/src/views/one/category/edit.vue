<template>
  <div style="
      height: 300px;
      position: relative;
      display: flex;
      flex-direction: column;
      align-items: left;
      margin-right: 50px;
    ">
    <el-form ref="form" :model="form" label-width="120px" class="dialog-body">
      <div class="dialog-body-item">
        <el-form-item label="客户" class="inner">
          <el-select v-model="form.tenantId" clearable placeholder="客户" @change="onChangeTenant">
            <el-option v-for="item in tenantList" :key="item.id" :label="item.tenantName" :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="店铺" class="inner">
          <el-select v-model="form.storeId" clearable placeholder="店铺">
            <el-option v-for="item in storeList" :key="item.id" :label="item.storeName" :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
      </div>
      <div class="dialog-body-item">
        <el-form-item label="分类名称" class="inner">
          <el-input v-model="form.categoryName" />
        </el-form-item>
        <el-form-item label="排序" class="inner">
          <el-input v-model="form.categorySort" type="number" />
        </el-form-item>
      </div>
    </el-form>
    <div style="margin-left: 120px;">
      <el-button size="default" @click="onCancle" style="margin-right: 10px;">
        取消
      </el-button>
      <el-popconfirm confirm-button-text="确认" cancel-button-text="取消" icon="el-icon-info" title="确认更新？"
        @confirm="onSubmit">
        <el-button slot="reference" type="primary" size="default">修改</el-button>
      </el-popconfirm>
    </div>
  </div>
</template>

<script>
  export default {
    name: "OneTenantEdit",
    props: {
      form: {
        type: Object,
        required: true,
      },
      editDialogVisible: {
        type: Boolean,
        required: true,
      },
    },
    mounted () {
      console.log(this.form)
    },
    computed: {
      tenantList () {
        return this.$store.state.one_tenant.tenantList || [];
      },
      storeList () {
        if (this.form.tenantId) {
          return (
            this.$store.state.one_store.storeList.filter(
              (e) => e.tenantId == this.form.tenantId
            ) || []
          );
        } else {
          return this.$store.state.one_store.storeList || [];
        }
      },
    },
    data () {
      return {};
    },
    methods: {
      onSubmit () {
        this.$store
          .dispatch("one_category/update", this.form)
          .then((response) => {
            this.$message.success("更新成功");
            this.onCancle();
          })
          .catch(() => { });
      },
      onCancle () {
        this.$emit("update:editDialogVisible", false);
      },
      onChangeTenant () {
        this.form.storeId = null;
      },
    },
  };
</script>