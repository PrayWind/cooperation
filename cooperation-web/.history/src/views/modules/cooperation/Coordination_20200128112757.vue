<!-- 报告协同 -->
<template>
  <el-tabs type="border-card" v-model="activeName" style="margin:20px 0px 0px 0px;">
    <el-tab-pane label="工作台" name="workbench">
      <Workbench ref="workbench"></Workbench>
    </el-tab-pane>
    <el-tab-pane label="报告" name="report" style="margin: -10px -38px -25px -35px;">
      <Report ref="report"></Report>
    </el-tab-pane>
    <el-tab-pane label="报告分析" name="reportAnalysis">
      <ReportAnalysis ref="reportAnalysis"></ReportAnalysis>
    </el-tab-pane>
  </el-tabs>
</template>

<script>
//这里可以导入其他文件（比如：组件，工具js，第三方插件js，json文件，图片文件等等）
//例如：import 《组件名称》 from '《组件路径》';
import Report from "./Coordination/Report/Report";
import Workbench from "./Coordination/Workbench/Workbench";
import ReportAnalysis from "./Coordination/ReportAnalysis/ReportAnalysis";

export default {
  //import引入的组件需要注入到对象中才能使用
  components: {
    Report: Report,
    Workbench: Workbench,
    ReportAnalysis: ReportAnalysis
  },
  data() {
    //这里存放数据
    return {
      activeName: ""
    };
  },

  watch: {
    activeName(v) {
      this.$refs[`${v}`].reload();
    }
  },

  //监听属性 类似于data概念
  computed: {},
  //监控data中的数据变化

  //方法集合
  methods: {},
  //生命周期 - 创建完成（可以访问当前this实例）
  created() {
    //this.currentRow={"ss":"sds"}
  },
  //生命周期 - 挂载完成（可以访问DOM元素）
  mounted() {
    this.activeName = "workbench";
  },
  beforeCreate() {}, //生命周期 - 创建之前
  beforeMount() {}, //生命周期 - 挂载之前
  beforeUpdate() {}, //生命周期 - 更新之前
  updated() {}, //生命周期 - 更新之后
  beforeDestroy() {}, //生命周期 - 销毁之前
  destroyed() {}, //生命周期 - 销毁完成
  activated() {} //如果页面有keep-alive缓存功能，这个函数会触发
};
</script>

<style lang='scss' scoped>
//@import url(); 引入公共css类
</style>