<template>
  <div class="table-div">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input
          v-model="dataForm.search"
          placeholder="请输入报告名称"
          prefix-icon="el-icon-search"
          clearable
        ></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
        <el-button type="primary" @click="addOrUpdateHandle()">新增</el-button>
        <el-button
          type="danger"
          @click="deleteHandle()"
          :disabled="dataListSelections.length <= 0"
        >批量删除</el-button>
      </el-form-item>
    </el-form>

    <el-table
      :data="dataList"
      border
      v-loading="dataListLoading"
      @selection-change="selectionChangeHandle"
      style="width: 100%;"
    >
      <el-table-column type="selection" header-align="center" align="center" width="50"></el-table-column>
      <el-table-column prop="id" header-align="center" align="center" width="80" label="ID"></el-table-column>
      <el-table-column prop="name" header-align="center" align="center" label="报告名称"></el-table-column>
      <el-table-column prop="reportInfo" header-align="center" align="center" label="简介"></el-table-column>
      <el-table-column prop="leader" header-align="center" align="center" label="负责人"></el-table-column>
      <el-table-column
        prop="createTime"
        header-align="center"
        align="center"
        width="180"
        label="创建时间"
      ></el-table-column>
      <el-table-column prop="status" header-align="center" align="center" label="状态">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status === '0'" size="small" type="danger">进行中</el-tag>
          <el-tag v-else size="small">完成</el-tag>
        </template>
      </el-table-column>
      <el-table-column fixed="right" header-align="center" align="center" width="250" label="操作">
        <template slot-scope="scope">
          <el-button type="primary" size="small">查看</el-button>
          <el-button
            v-if="isAuth('sys:user:update')"
            type="primary"
            size="small"
            @click="addOrUpdateHandle(scope.row.id)"
          >修改</el-button>
          <el-button
            v-if="isAuth('sys:user:delete')"
            type="primary"
            size="small"
            @click="deleteHandle(scope.row.userId)"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="sizeChangeHandle"
      @current-change="currentChangeHandle"
      :current-page="pageIndex"
      :page-sizes="[10, 20, 50, 100]"
      :page-size="pageSize"
      :total="totalPage"
      layout="total, sizes, prev, pager, next, jumper"
    ></el-pagination>

    <!-- 弹窗, 新增 / 修改 -->
    <AddOrUpdate v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></AddOrUpdate>
  </div>
</template>

<script>
import * as reports from "@/api/coordination/reports";
import AddOrUpdate from "./components/report-add-or-update";

export default {
  components: {
    AddOrUpdate
  },
  data() {
    return {
      dataForm: {
        search: ""
      },
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListLoading: false,
      dataListSelections: [],
      addOrUpdateVisible: false,
      items: [],
      loading: false
    };
  },
  activated() {
    this.getDataList();
  },
  methods: {
    // 获取数据列表
    getDataList() {
      this.dataListLoading = true;
      this.$http({
        url: this.$http.adornUrl("/report/list"),
        method: "get",
        params: this.$http.adornParams({
          page: this.pageIndex,
          limit: this.pageSize,
          search: this.dataForm.search
        })
      }).then(({ data }) => {
        if (data && data.code === 0) {
          this.dataList = data.page.list;
          this.totalPage = data.page.totalCount;
        } else {
          this.dataList = [];
          this.totalPage = 0;
        }
        this.dataListLoading = false;
      });
    },
    // 每页数
    sizeChangeHandle(val) {
      this.pageSize = val;
      this.pageIndex = 1;
      this.getDataList();
    },
    // 当前页
    currentChangeHandle(val) {
      this.pageIndex = val;
      this.getDataList();
    },
    // 多选
    selectionChangeHandle(val) {
      this.dataListSelections = val;
    },
    // 新增 / 修改
    addOrUpdateHandle(id) {
      this.addOrUpdateVisible = true;
      this.$nextTick(() => {
        this.$refs.addOrUpdate.init(id);
      });
    },
    // 删除
    deleteHandle(id) {
      var userIds = id
        ? [id]
        : this.dataListSelections.map(item => {
            return item.userId;
          });
      this.$confirm(
        `确定对[id=${userIds.join(",")}]进行[${id ? "删除" : "批量删除"}]操作?`,
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }
      )
        .then(() => {
          this.$http({
            url: this.$http.adornUrl("/sys/user/delete"),
            method: "post",
            data: this.$http.adornData(userIds, false)
          }).then(({ data }) => {
            if (data && data.code === 0) {
              this.$message({
                message: "操作成功",
                type: "success",
                duration: 1500,
                onClose: () => {
                  this.getDataList();
                }
              });
            } else {
              this.$message.error(data.msg);
            }
          });
        })
        .catch(() => {});
    }
    // //展示所有项目信息
    // list() {
    //   this.loading = true;
    //   reports
    //     .getProjectList(this.search)
    //     .then(response => {
    //       this.loading = false;
    //       this.items = response.data.data;
    //     })
    //     .catch();
    // },
    // //添加项目
    // addReport() {
    //   // 清空对象属性
    //   this.$refs.addReportDialog.dialogOpen();
    // },
    // //添加项目成员
    // addPerson(projectId) {
    //   this.$refs.addPersonDialog.dialogOpen(projectId);
    // },
    // //删除项目
    // deleteReports(projectId) {
    //   this.$confirm("此操作将永久删除该数据, 是否继续?", "提示", {
    //     confirmButtonText: "确定",
    //     cancelButtonText: "取消",
    //     type: "warning"
    //   })
    //     .then(() =>
    //       reports
    //         .deleteReports(projectId)
    //         .then(res => {
    //           this.$message({
    //             type: "success",
    //             message: "删除成功!"
    //           });
    //           this.list();
    //         })
    //         .catch(err => {})
    //     )
    //     .catch(() => {
    //       this.$message({
    //         type: "info",
    //         message: "已取消删除"
    //       });
    //     });
    // }
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