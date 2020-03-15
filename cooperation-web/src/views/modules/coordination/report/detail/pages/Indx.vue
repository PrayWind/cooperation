<template>
  <section>
    <el-card class="table-card">
      <div class="table-div">
        <!-- 顶部工具条 -->
        <el-col :span="24" class="toptoolbar">
          <el-input
            clearable
            class="input-style"
            v-model="search"
            placeholder="请输入模板名称"
            prefix-icon="el-icon-search"
          ></el-input>
          <el-button type="primary" size="small" icon="el-icon-edit" @click.native="add()">添加</el-button>
        </el-col>

        <!-- 列表 -->
        <el-col :span="24">
          <el-table
            v-loading="loading"
            size="small"
            :data="indxTreeList"
            highlight-current-row
            border
            row-key="rowId"
            ref="tree"
            style="margin-bottom: 0"
            :header-cell-style="{background:'#EEF1F6'}"
            :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
          >
            <el-table-column
              align="center"
              width="auto"
              prop="name"
              label="模板名称"
              show-overflow-tooltip
            ></el-table-column>
            <el-table-column
              align="center"
              width="auto"
              prop="executorName"
              label="执行者"
              show-overflow-tooltip
            ></el-table-column>
            <el-table-column
              align="center"
              width="auto"
              prop="templetStatus"
              label="状态"
              show-overflow-tooltip
            ></el-table-column>
            <el-table-column
              align="center"
              width="auto"
              prop="templetLevel"
              label="优先级"
              show-overflow-tooltip
            ></el-table-column>
            <el-table-column align="center" min-width="200" label="操作" show-overflow-tooltip>
              <template slot-scope="scope">
                <!-- <el-button type="primary" size="small" @click="edit(scope.$index, scope.row)">编辑</el-button> -->
                <el-button type="primary" size="small" @click="sendToUser(scope.row)">分配</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-col>

        <!-- 底部工具条 -->
        <el-col :span="24" class="bottomtoolbar">
          <el-pagination
            class="clearfix"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page.sync="pageNo"
            :page-sizes="pageSizesList"
            :page-size="pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="totalDataNumber"
          ></el-pagination>
        </el-col>

        <!-- 其他组件 -->
        <EditIndxDialog ref="editIndxDialog"></EditIndxDialog>
        <AddIndxDialog ref="addIndxDialog"></AddIndxDialog>
        <SendToUserDialog ref="sendToUserDialog" @handleCurrentChange="handleCurrentChange"></SendToUserDialog>
      </div>
    </el-card>
  </section>
</template>

<script>
import * as indx from "@/api/coordination/detail/indx";
import EditIndxDialog from "../components/EditIndxDialog";
import AddIndxDialog from "../components/AddIndxDialog";
import SendToUserDialog from "../components/SendToUserDialog";

export default {
  props: {
    projectId: {
      type: String
    }
  },

  components: { EditIndxDialog, AddIndxDialog, SendToUserDialog },

  data() {
    //这里存放数据
    return {
      search: "",
      indxTreeList: [],
      pageNo: 1,
      pageSize: 5,
      pageSizesList: [5, 10, 15, 20, 30, 50],
      totalDataNumber: 10
    };
  },

  watch: {
    search(val) {
      this.pageNo = 1;
      this.handleSizeChange(this.pageSize);
    }
  },

  //生命周期 - 创建完成（可以访问当前this实例）
  created() {
    this.handleSizeChange(this.pageSize);
  },

  methods: {
    // 重新加载图表
    reload() {
      console.log("指标页面图表重新加载");
      // this.$nextTick(_ => {
      //   this.$refs[`reportChart`].echarts.resize();
      //   this.$refs[`indxChart`].echarts.resize();
      //   // this.$refs[`progressChart`].echarts.resize();
      //   this.$refs[`totalChart`].echarts.resize();
      // });
    },
    handleSizeChange(pageSize) {
      this.pageSize = pageSize;
      this.handleCurrentChange(this.pageNo);
    },
    handleCurrentChange(currentPage) {
      this.pageNo = currentPage;
      this.currentChangePage(currentPage);
    },
    currentChangePage(currentPage) {
      this.loading = true;
      this.tableData = [];
      indx
        .templetList(this.projectId, this.search)
        .then(res => {
          this.totalDataNumber = res.data.data.length;
          this.indxTreeList = res.data.data;
          this.loading = false;
        })
        .catch();
    },
    add() {
      this.$refs.addIndxDialog.dialogOpen(this.projectId);
    },
    edit(index, row) {
      console.log("edit....");
      console.log(index);
      console.log(row);
    },
    sendToUser(row) {
      this.$refs.sendToUserDialog.dialogOpen(this.projectId, row);
    }
  }
};
</script>

<style>
</style>