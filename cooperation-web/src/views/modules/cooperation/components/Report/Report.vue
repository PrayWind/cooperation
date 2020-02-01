<template>
  <el-card class="table-card">
    <div class="table-div">
      <!-- 顶部工具条 -->
      <el-row class="toptoolbar" style="margin-bottom:25px">
        <el-col :span="24">
          <el-input
            clearable
            class="input-style"
            v-model="search"
            placeholder="请输入报告名称"
            prefix-icon="el-icon-search"
          ></el-input>
          <el-button type="primary" size="small" icon="el-icon-edit" @click="addReport">添加</el-button>
        </el-col>
      </el-row>

      <div v-for="item in items" :key="item.id" v-loading="loading">
        <!-- 内容 -->
        <div style="margin-top:-15px;">
          <div class="left-part">
            <div class="left-1">
              <router-link :to="'/report/ReportDetail?id=' + item.id" class="above">{{item.name}}</router-link>
              <br />
              <span class="down">{{item.projectInfo}}</span>
              <br />
            </div>
          </div>
          <div class="middle-part">
            <div class="middle-1">
              <span class="above">创建时间</span>
              <br />
              <span class="down">{{item.createTime}}</span>
            </div>
            <div class="middle-2">
              <span class="above">创建人</span>
              <br />
              <span class="down">{{item.leader}}</span>
            </div>
            <div class="middle-2">
              <span class="above">进度</span>
              <br />
              <span class="down">{{item.status}}</span>
            </div>
            <br />
          </div>

          <div class="right-part">
            <div class="right-1" @click="addPerson(item.id)">
              <el-tooltip content="添加成员">
                <svg-icon icon-class="icon_addperson" class="icon" />
              </el-tooltip>
            </div>
            <div class="divider">
              <el-divider direction="vertical"></el-divider>
            </div>
            <div class="right-1" @click="deleteReports(item.id)">
              <el-tooltip content="移动到回收站">
                <svg-icon icon-class="icon_delete" class="icon" />
              </el-tooltip>
            </div>
            <div class="divider">
              <el-divider direction="vertical"></el-divider>
            </div>

            <div class="right-1" @click="setProject(item)">
              <el-tooltip content="项目设置">
                <svg-icon icon-class="icon_setting" class="icon" />
              </el-tooltip>
            </div>
            <div class="divider">
              <el-divider direction="vertical"></el-divider>
            </div>

            <div class="right-1" @click="collection(item)">
              <el-tooltip content="收藏">
                <svg-icon icon-class="icon_star" class="icon" />
              </el-tooltip>
            </div>
          </div>

          <div class="clear">
            <!--如何你上面用到float,下面布局开始前最好清除一下。-->
          </div>
        </div>
        <el-divider></el-divider>
      </div>
      <AddPersonDialog ref="addPersonDialog" @list="list"></AddPersonDialog>
      <AddReportDialog ref="addReportDialog" @list="list"></AddReportDialog>
    </div>
  </el-card>
</template>

<script>
import * as reports from "@/api/Coordination/reports";
import AddPersonDialog from "./Components/AddPersonDialog";
import AddReportDialog from "./Components/AddReportDialog";

export default {
  components: {
    AddPersonDialog,
    AddReportDialog
  },
  data() {
    return {
      search: "",
      items: [],
      loading: false
    };
  },
  methods: {
    // 重新加载图表
    reload() {
      console.log("报告重新加载");
      // this.$nextTick(_ => {
      //   this.$refs[`reportChart`].echarts.resize();
      //   this.$refs[`totalChart`].echarts.resize();
      // });
    },
    //展示所有项目信息
    list() {
      this.loading = true;
      reports
        .getProjectList(this.search)
        .then(response => {
          this.loading = false;
          this.items = response.data.data;
        })
        .catch();
    },
    //添加项目
    addReport() {
      // 清空对象属性
      this.$refs.addReportDialog.dialogOpen();
    },
    //添加项目成员
    addPerson(projectId) {
      this.$refs.addPersonDialog.dialogOpen(projectId);
    },
    //删除项目
    deleteReports(projectId) {
      this.$confirm("此操作将永久删除该数据, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() =>
          reports
            .deleteReports(projectId)
            .then(res => {
              this.$message({
                type: "success",
                message: "删除成功!"
              });
              this.list();
            })
            .catch(err => {})
        )
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除"
          });
        });
    }
  },
  watch: {
    search() {
      this.list();
    }
  },
  mounted() {
    this.list();
  }
};
</script>

<style lang='scss' scoped>
//@import url(); 引入公共css类
//左部分样式
.left-part {
  padding-top: 10px;
  padding-bottom: 10px;
  padding-left: 20px;
  line-height: 25px; //行间距
  font-size: 15px;
  height: 50px;
  width: 40%;
  float: left;
  .above {
    color: #1890ff !important;
  }
}
//中间部分样式
.middle-part {
  padding-top: 10px;
  padding-bottom: 10px;
  padding-left: 20px;
  line-height: 25px; //行间距
  font-size: 15px;
  height: 50px;
  width: 40%;
  margin-left: 20px;
  float: left;
  .middle-1 {
    width: 80px;
    float: left;
  }
  .middle-2 {
    width: 90px;
    float: left;
    margin-left: 30px;
  }
}
//右部分样式
.right-part {
  padding-top: 20px;
  padding-bottom: 10px;
  padding-left: 20px;
  font-size: 15px;
  height: 50px;
  width: 15%;
  float: right;
  .right-1 {
    float: left;
    .icon {
      float: left;
      vertical-align: middle;
      cursor: pointer;
      &:hover {
        color: #1890ff;
      }
    }
  }
  .divider {
    float: left;
  }
}
//清除浮动
.clear {
  clear: both;
}
.above {
  color: rgb(140, 140, 140);
}
.down {
  color: rgb(140, 140, 140);
}
</style>