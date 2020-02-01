<template>
  <div>
    <el-dialog title="添加模板" :visible.sync="dialogVisible" width="45%" :before-close="handleClose">
      <div class="border-head-style"></div>

      <el-table
        :data="tableData"
        v-loading="loading"
        style="width: 100%;margin-bottom: 20px;"
        row-key="id"
        :expand-row-keys="expandsRowKeys"
        ref="tree"
        border
        highlight-current-row
        :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
      >
        <el-table-column
          show-overflow-tooltip
          prop="name"
          label="指标名称"
          sortable
          width="auto"
          header-align="center"
        ></el-table-column>
        <el-table-column label="操作" width="auto" align="center">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="primary"
              @click="getTemplet(scope.row, projectId)"
            >查看模板</el-button>
          </template>
        </el-table-column>
      </el-table>

      <div slot="footer" class="dialog-footer">
        <div class="border-style" style="margin-top: 0%"></div>
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
      </div>
    </el-dialog>

    <ViewTemplet ref="viewTemplet"></ViewTemplet>
  </div>
</template>

<script>
import * as indx from "@/api/Coordination/ReportDetail/indx";
import ViewTemplet from "./ViewTemplet";

export default {
  components: { ViewTemplet },

  data() {
    return {
      dialogVisible: false,
      loading: true,
      tableData: [],
      expandsRowKeys: [],
      projectId: ""
    };
  },

  methods: {
    dialogOpen(projectId) {
      this.dialogVisible = true;
      if (projectId) {
        // projectId不为空，说明是报告详情中点击添加指标
        this.projectId = projectId;
        this.getIndxTree();
      } else {
        // projectId为空，说明是创建报告时点击添加指标
        this.getIndxTree();
      }
    },
    handleClose(done) {
      this.$confirm("确认关闭？")
        .then(_ => {
          done();
        })
        .catch(_ => {});
    },
    getIndxTree() {
      //获取树表格数据
      indx
        .indxTree()
        .then(res => {
          this.tableData = res.data.data;
          this.loading = false;
        })
        .catch();
    },
    getTemplet(row, projectId) {
      if (projectId) {
        // projectId不为空，说明是在报告详情中添加模板
        this.$refs.viewTemplet.dialogOpen(row, projectId);
      } else {
        // projectId为空，说明是创建报告时添加模板
        this.$refs.viewTemplet.dialogOpen(row);
      }
    }
  }
};
</script>

<style>
</style>