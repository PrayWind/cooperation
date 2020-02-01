<!-- 工作台 -->
<template>
  <div class>
    <el-row :gutter="20">
      <el-col :span="12">
        <el-card class="box-card-1">
          <div slot="header" class="clearfix">
            <span class="title">进行中的项目</span>
            <el-button style="float: right; padding: 3px 0" type="text">全部项目</el-button>
          </div>
          <div v-for="o in 6" :key="o">
            <el-card shadow="hover" class="cards-1">
              <div class="cards-1-1">项目名称</div>
              <br />
              <div class="cards-1-2">项目简介</div>
              <br />
              <div class="cards-1-3">
                创建人
                <div class="cards-1-4">创建时间</div>
              </div>
              <br />
            </el-card>
          </div>
        </el-card>
      </el-col>

      <el-col :span="12">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span class="title">我的指标任务</span>
          </div>
          <el-table :data="tableData" style="width: 100%" height="250">
            <el-table-column fixed prop="date" label="日期" width="150"></el-table-column>
            <el-table-column prop="name" label="姓名" width="120"></el-table-column>
            <el-table-column prop="province" label="省份" width="120"></el-table-column>
          </el-table>
        </el-card>
      </el-col>
    </el-row>
    <br />
    <el-row :gutter="20">
      <el-col :span="12">
        <el-card class="box-card-3">
          <div slot="header" class="clearfix">
            <span class="title">动态</span>
          </div>
          <div v-for="o in 4" :key="o" class="cards-2">
            <div class="left-part">
              <el-avatar icon="el-icon-user-solid"></el-avatar>
            </div>
            <div class="middle-part">
              <span>动态消息</span>
              <br />
              <span>时间-项目名称</span>
            </div>
            <div class="right-part">
              <span>指标名称</span>
            </div>
            <div class="clear">
              <!--如何你上面用到float,下面布局开始前最好清除一下。-->
            </div>
            <el-divider></el-divider>
          </div>
        </el-card>
      </el-col>

      <el-col :span="12">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span class="title">我的里程碑</span>
          </div>
          <div v-for="o in 4" :key="o" class="text item">{{'列表内容 ' + o }}</div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
//这里可以导入其他文件（比如：组件，工具js，第三方插件js，json文件，图片文件等等）
//例如：import 《组件名称》 from '《组件路径》';

export default {
  //import引入的组件需要注入到对象中才能使用
  components: {},
  data() {
    //这里存放数据
    return {
      tableData: [
        {
          date: "2016-05-03",
          name: "王小虎",
          province: "上海"
        },
        {
          date: "2016-05-02",
          name: "王小虎",
          province: "上海"
        },
        {
          date: "2016-05-04",
          name: "王小虎",
          province: "上海"
        },
        {
          date: "2016-05-01",
          name: "王小虎",
          province: "上海"
        },
        {
          date: "2016-05-08",
          name: "王小虎",
          province: "上海"
        },
        {
          date: "2016-05-06",
          name: "王小虎",
          province: "上海"
        },
        {
          date: "2016-05-07",
          name: "王小虎",
          province: "上海"
        }
      ]
    };
  },
  //监听属性 类似于data概念
  computed: {},
  //监控data中的数据变化
  watch: {},
  //方法集合
  methods: {
    // 重新加载图表
    reload() {
      console.log("工作台页面图表重新加载");
      // this.$nextTick(_ => {
      //   this.$refs[`reportChart`].echarts.resize();
      //   this.$refs[`totalChart`].echarts.resize();
      // });
    }
  },
  //生命周期 - 创建完成（可以访问当前this实例）
  created() {},
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
.title {
  font-size: 18px;
}
.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both;
}
.box-card-1 {
  height: 350px;
  .cards-1 {
    height: 120px;
    width: 180px;
    margin-right: 5px;
    margin-bottom: 5px;
    float: left;
    .cards-1-1 {
      float: left;
    }
    .cards-1-2 {
      margin-top: 2px;
      float: left;
    }
    .cards-1-3 {
      margin-top: 45px;
    }
    .cards-1-4 {
      float: right;
    }
  }
}

.cards-2 {
  .left-part {
    float: left;
  }
  .middle-part {
    margin-left: 20px;
    line-height: 20px; //行间距
    float: left;
  }
  .right-part {
    margin-top: 10px;
    margin-right: 20px;
    float: right;
  }
  //清除浮动
  .clear {
    clear: both;
  }
}
</style>