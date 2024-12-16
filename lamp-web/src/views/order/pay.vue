<template>
  <div>
    <el-card style="width: 350px; margin: 20px">
      <el-descriptions
        :title="packages.title"
        :column="1"
        size="mini"
        labelClassName="discription-lable"
        border
      >
        <template slot="extra">
          <el-button type="primary" size="mini" @click="pay(packages.id)">
            支付
          </el-button>
        </template>
        <el-descriptions-item>
          <template slot="label">
            <span style="">套餐适用</span>
          </template>
          <div style="margin: 5px">{{ packages.introduce }}</div>
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <span style="">套餐流量</span>
          </template>
          <div style="margin: 5px">
            <el-tag type="primary" effect="plain" size="small">
              {{ packages.dataTraffic }}GB/月
            </el-tag>
          </div>
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <span style="">套餐时长</span>
          </template>
          <div style="margin: 5px">
            <el-tag type="success" effect="plain" size="small">
              {{ packages.period }}个月
            </el-tag>
          </div>
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <span style="">套餐价格</span>
          </template>
          <div style="margin: 5px">
            <el-tag type="danger" effect="plain" size="small">
              {{ packages.price }}元
            </el-tag>
          </div>
        </el-descriptions-item>
      </el-descriptions>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "Pay",
  computed: {
    packageList() {
      return this.$store.state.packages.packageList;
    },
  },
  data() {
    return {
      packages: {},
    };
  },
  created() {
    this.packages = this.packageList.find(
      (e) => e.id == this.$route.query.packageId
    );
  },
  methods: {
    pay() {
      console.log(this.$route.query.packageId)
    },
  },
};
</script>

<style lang="scss">
</style>
