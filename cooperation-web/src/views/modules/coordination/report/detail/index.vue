<!--  -->
<template>
  <div>
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input
          v-model="dataForm.search"
          placeholder="请输入指标名称"
          prefix-icon="el-icon-search"
          clearable
        ></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
      </el-form-item>
      <el-form-item>
        <el-button
          v-if="isAuth('detail:saveReportUsers')"
          type="primary"
          @click="reportUsers()"
        >成员管理</el-button>
      </el-form-item>
    </el-form>

    <el-table
      :data="dataList"
      tooltip-effect="dark"
      border
      v-loading="dataListLoading"
      style="width: 100%;"
    >
      <el-table-column
        prop="id"
        label="ID"
        width="65px"
        header-align="center"
        align="center"
        sortable
      ></el-table-column>
      <el-table-column
        prop="name"
        label="指标名称"
        width="auto"
        header-align="center"
        align="center"
        show-overflow-tooltip
      ></el-table-column>
      <el-table-column prop="level" label="优先级" width="auto" header-align="center" align="center"></el-table-column>
      <el-table-column
        prop="executorsStr"
        label="执行者"
        width="auto"
        header-align="center"
        align="center"
      ></el-table-column>
      <el-table-column
        prop="finishTime"
        header-align="center"
        align="center"
        width="120"
        label="完成时间"
      ></el-table-column>
      <el-table-column prop="status" label="状态" header-align="center" align="center">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status === '2'" size="small" type="success">完成</el-tag>
          <el-tag v-else-if="scope.row.status === '1'" size="small" type="info">进行中</el-tag>
          <el-tag v-else size="small" type="danger">未分配</el-tag>
        </template>
      </el-table-column>
      <el-table-column fixed="right" header-align="center" align="center" width="250" label="操作">
        <template slot-scope="scope">
          <el-button
            v-if="isAuth('detail:saveSentUsers')"
            type="primary"
            size="small"
            @click="sendToUser(scope.row.id)"
          >分配</el-button>
          <el-button
            v-if="isAuth('detail:statusChange')"
            type="primary"
            size="small"
            @click="statusChange(scope.row.id)"
          >完成</el-button>
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
    <UserAddOrRemove ref="userAddOrRemove"></UserAddOrRemove>
    <IndxSendToUser ref="indxSendToUser" @refreshDataList="getDataList"></IndxSendToUser>
  </div>
</template>

<script>
import UserAddOrRemove from "./components/user-add-or-remove";
import IndxSendToUser from "./components/indx-send-to-user";

export default {
  components: { UserAddOrRemove, IndxSendToUser },
  data() {
    return {
      reportId: 0,
      dataForm: {
        search: ""
      },
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataList: [],
      dataListLoading: false
    };
  },

  mounted() {
    if (!this.$route.params.reportId) {
      this.$message({
        message: "reportId不存在",
        type: "error",
        duration: 1500
      });
      // return false下面的代码就不执行
      return false;
    }
    this.reportId = this.$route.params.reportId;
    this.getDataList();
  },

  methods: {
    // 获取数据
    getDataList() {
      this.dataListLoading = true;
      this.$http({
        url: this.$http.adornUrl("/detail/list"),
        method: "get",
        params: this.$http.adornParams({
          page: this.pageIndex,
          limit: this.pageSize,
          search: this.dataForm.search,
          reportId: this.reportId
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

    // 报告成员管理
    reportUsers() {
      this.$refs.userAddOrRemove.dialogOpen(this.reportId);
    },

    // 分配指标
    sendToUser(id) {
      this.$refs.indxSendToUser.dialogOpen(this.reportId, id);
    },

    // 状态管理，改变状态为完成
    statusChange(id) {
      this.$http({
        url: this.$http.adornUrl("/detail/statusChange"),
        method: "post",
        data: this.$http.adornData({
          reportId: this.reportId,
          indxId: id
        })
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
    }
  }
};
</script>

<style lang='scss' scoped>
//@import url(); 引入公共css类
</style>