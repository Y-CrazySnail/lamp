<template>
  <div v-loading="loading">
    <el-form
      ref="server"
      :model="server"
      label-width="80px"
      size="mini"
      :inline="true"
      style="margin-left: 10px"
    >
      <el-col :span="24">
        <el-form-item label="服务器ID:" prop="id">
          <el-input
            v-model="server.id"
            placeholder="ID"
            style="width: 300px"
            disabled
          />
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="服务器:" prop="API地址">
          <el-input
            v-model="server.apiIp"
            placeholder="API地址"
            style="width: 200px"
            disabled
          />
          <el-input
            v-model="server.apiPort"
            placeholder="API端口"
            style="width: 100px"
            disabled
          />
        </el-form-item>
      </el-col>
    </el-form>

    <div v-for="(inbound, index) in server.inboundList" :key="index">
      <div style="margin: 0 0 10px 30px; font-weight: bold">
        入站（Vmess）{{ index + 1 }}
      </div>
      <el-form
        ref="inbound"
        :model="inbound"
        label-width="80px"
        size="mini"
        :inline="true"
        style="margin-left: 10px"
      >
        <el-col :span="24">
          <el-form-item label="入站端口" prop="inboundPort">
            <el-input
              v-model="inbound.xuiPort"
              placeholder="入站端口"
              style="width: 300px"
            />
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="入站备注" prop="remark">
            <el-input
              v-model="inbound.xuiRemark"
              placeholder="入站备注"
              style="width: 300px"
            />
          </el-form-item>
        </el-col>
      </el-form>
      <div style="margin-left: 30px; margin-bottom: 20px">
        <el-button
          size="small"
          @click="onDeleteInbound(inbound)"
          style="margin-right: 10px"
        >
          删除
        </el-button>
        <el-button
          type="primary"
          size="small"
          @click="onSubmit(inbound)"
          v-if="!inbound.id"
        >
          提交
        </el-button>
      </div>
    </div>

    <div style="margin-left: 30px; margin-bottom: 20px">
      <el-button type="primary" size="small" @click="onAddInbound">
        新增入站
      </el-button>
    </div>
  </div>
</template>

<script>
export default {
  name: "AladdinserverEdit",
  props: {
    id: {
      type: Number,
      require: true,
    },
    inboundDialogVisible: {
      type: Boolean,
      required: true,
    },
  },
  mounted() {
    this.$store
      .dispatch("aladdin_server/getById", { id: this.id })
      .then((server) => {
        this.server = server;
        this.loading = false;
      });
  },
  data() {
    return {
      loading: true,
      server: {},
    };
  },
  methods: {
    onSubmit(inbound) {
      this.loading = true;
      inbound.serverId = this.server.id;
      this.$store.dispatch("aladdin_inbound/save", inbound).then((response) => {
        this.$message.success("新增入站成功");
        this.$store
          .dispatch("aladdin_server/getById", { id: this.id })
          .then((server) => {
            this.server = server;
            this.loading = false;
          });
      });
    },
    onCancle() {
      this.$emit("update:inboundDialogVisible", false);
    },
    onAddInbound() {
      let inbound = {
        xuiPort: 20000,
        xuiRemark: "备注",
      };
      this.server.inboundList.push(inbound);
    },
    onDeleteInbound(inbound) {
      this.loading = true;
      this.$store
        .dispatch("aladdin_inbound/remove", inbound)
        .then((response) => {
          this.$message.success("删除入站成功");
          this.$store
            .dispatch("aladdin_server/getById", { id: this.id })
            .then((server) => {
              this.server = server;
              this.loading = false;
            });
        });
    },
  },
  destroyed() {},
};
</script>

<style></style>