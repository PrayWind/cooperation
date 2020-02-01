<!--  -->
<template>
  <el-tabs
    type="border-card"
    v-model="activeName"
    @tab-click="tabClick"
    style="margin:20px 0px 0px 0px;"
  >
    <el-tab-pane label="指标" name="indx" style="margin: -10px -38px -25px -35px;">
      <Indx :projectId="projectId" ref="indx"></Indx>
    </el-tab-pane>
    <el-tab-pane label="概览" name="overview">
      <Overview ref="overview"></Overview>
    </el-tab-pane>
  </el-tabs>
</template>

<script>
//这里可以导入其他文件（比如：组件，工具js，第三方插件js，json文件，图片文件等等）
//例如：import 《组件名称》 from '《组件路径》';
import * as coordination from "@/api/Coordination/coordination";
import Indx from "./Pages/Indx";
import Overview from "./Pages/Overview";

export default {
  //import引入的组件需要注入到对象中才能使用
  components: { Indx, Overview },
  data() {
    //这里存放数据
    return {
      activeName: "indx",
      projectId: ""
    };
  },
  //监听属性 类似于data概念
  computed: {},
  //监控data中的数据变化
  watch: {
    activeName(v) {
      this.$refs[`${v}`].reload();
    }
  },
  //方法集合
  methods: {
    tabClick(tab, event) {
      console.log("()()()()");
      console.log(tab, event);
    }
  },
  //生命周期 - 创建完成（可以访问当前this实例）
  created() {
    this.projectId = this.$route.query.id;
  },
  //生命周期 - 挂载完成（可以访问DOM元素）
  mounted() {},
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