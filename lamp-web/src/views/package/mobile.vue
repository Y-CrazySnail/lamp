<template>
  <div>
    <windows v-show="device !== 'mobile'"></windows>
    <mobile v-show="device === 'mobile'"></mobile>
    <div>
      <h4>会员商店</h4>
    </div>
    <div style="display: flex; flex-wrap: wrap; justify-content: center">
      <el-card
        style="width: 350px; margin: 20px"
        v-for="packages in packageList"
        :key="packages.id"
      >
        <el-descriptions
          :title="packages.title"
          :column="1"
          size="mini"
          labelClassName="discription-lable"
          border
        >
          <template slot="extra">
            <el-button
              type="info"
              size="mini"
              style="background-color: #000000; border-color: #000000"
              @click="placeOrder(packages.id)"
            >
              购买
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
              <el-tag
                type="info"
                effect="plain"
                size="small"
                style="color: #606266"
              >
                {{
                  parseFloat(packages.bandwidth / 1024 / 1024 / 1024).toFixed(
                    0
                  )
                }}GB/月
              </el-tag>
            </div>
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <span style="">套餐时长</span>
            </template>
            <div style="margin: 5px">
              <el-tag
                type="info"
                effect="plain"
                size="small"
                style="color: #606266"
              >
                {{ packages.period }}个月
              </el-tag>
            </div>
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <span style="">套餐价格</span>
            </template>
            <div style="margin: 5px">
              <el-tag
                type="info"
                effect="plain"
                size="small"
                style="color: #606266"
              >
                {{ packages.price }}元
              </el-tag>
              <el-tag
                type="info"
                effect="plain"
                size="small"
                style="color: #606266; margin-left: 10px"
                v-show="packages.beforePrice"
              >
                <span style="text-decoration: line-through">
                  {{ packages.beforePrice }}元
                </span>
              </el-tag>
            </div>
          </el-descriptions-item>
        </el-descriptions>
      </el-card>
    </div>
  </div>
</template>

<script>
export default {
  name: "Package",
  mounted() {
    this.$store.dispatch("packages/list");
  },
  data() {
    return {};
  },
  computed: {
    packageList() {
      return this.$store.state.packages.packageList;
    },
    spuList() {
      return this.$store.state.packages.spuList;
    },
  },
  methods: {
    placeOrder(packageId) {
      // 请求后端 新增订单
      let param = {};
      param.packageId = packageId;
      this.$store.dispatch("order/place", param).then((res) => {
        this.$router.push("/order/index");
      });
    },
  },
};
</script>

<style lang="scss">
.discription-lable {
  width: 80px;
}
</style>
