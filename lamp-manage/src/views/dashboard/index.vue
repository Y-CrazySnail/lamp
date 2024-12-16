<template>
  <div class="dashboard-container" v-if="false">
    <div class="info-container">
      <div class="info-time">
        <span style="width: 110px; margin-left: 5px;">剩余时长:</span>
        <span>30天</span>
      </div>
      <div class="info-data">
        <span style="width: 110px; margin-left: 5px;">当月剩余流量:</span>
        <span>50G</span>
      </div>
    </div>
    <div class="system">
      <el-button class="system-button" type="primary" plain>
        <i class="el-icon-share"></i>
        IOS 教程
      </el-button>
    </div>
    <div class="system">
      <el-button class="system-button" type="success" plain>
        <i class="el-icon-share"></i>
        安卓 教程
      </el-button>
    </div>
    <div class="system">
      <el-button class="system-button" type="warning" plain>
        <i class="el-icon-share"></i>
        Windows 教程
      </el-button>
    </div>
    <div class="system">
      <el-button class="system-button" type="danger" plain>
        <i class="el-icon-share"></i>
        Mac OS 教程
      </el-button>
    </div>
  </div>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  name: "Dashboard",
  computed: {
    ...mapGetters(["name"]),
  },
};
</script>

<style lang="scss" scoped>
.info-container {
  margin: 10px 0 0 20px;
  width: 250px;
}

.info-time {
  display: flex;
  align-items: center;
  height: 30px;
  background-color: greenyellow;
  border-radius: 5px;
}

.info-data {
  display: flex;
  align-items: center;
  height: 30px;
  margin-top: 5px;
  background-color: greenyellow;
  border-radius: 5px;
}

.system {
  margin: 10px 0 0 20px;
}

.system-button {
  width: 150px;
}
</style>
