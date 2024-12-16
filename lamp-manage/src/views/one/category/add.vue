<template>
  <div style="
      height: 500px;
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
      <el-button size="default" @click="onCancle" style="margin-right: 10px;">取消</el-button>
      <el-button type="primary" size="default" @click="onSubmit">创建</el-button>
    </div>
  </div>
</template>

<script>
  export default {
    name: "OneTenantAdd",
    props: {
      addDialogVisible: {
        type: Boolean,
        required: true,
      },
    },
    mounted () { },
    data () {
      return {
        form: {
          tenantId: "",
          storeId: "",
          categoryName: "",
          categorySort: "",
        },
      };
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
    methods: {
      onSubmit () {
        this.$store
          .dispatch("one_category/save", this.form)
          .then((response) => {
            this.$message.success("创建成功");
            this.$emit("update:addDialogVisible", false);
          })
          .catch(() => { });
      },
      onCancle () {
        this.$emit("update:addDialogVisible", false);
      },
      onChangeTenant () {
        this.form.storeId = null;
      },
    },
    destroyed () {
      this.form = {};
    },
  };
</script>