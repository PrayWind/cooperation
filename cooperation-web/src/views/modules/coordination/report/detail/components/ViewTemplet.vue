<template>
  <el-dialog title="选择模板" :visible.sync="dialogVisible" width="45%" :before-close="handleClose">
    <el-table
      size="small"
      :data="templetList"
      highlight-current-row
      border
      row-key="id"
      style="margin-bottom: 0"
      :header-cell-style="{background:'#EEF1F6'}"
      @selection-change="handleSelectionChange"
    >
      <el-table-column align="center" type="selection" width="55"></el-table-column>
      <el-table-column align="center" width="auto" prop="href" label="模板路径" show-overflow-tooltip></el-table-column>
      <el-table-column align="center" width="auto" prop="name" label="模板名称" show-overflow-tooltip></el-table-column>
    </el-table>

    <span slot="footer" class="dialog-footer">
      <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="saveSelectedTemplet(projectId)">添加模板</el-button>
    </span>
  </el-dialog>
</template>

<script>
import * as indx from "@/api/coordination/detail/indx";
import * as reports from "@/api/coordination/reports";

export default {
  data() {
    return {
      dialogVisible: false,
      templetList: [],
      templet: {},
      projectId: "",
      selectedTempletList: []
    };
  },
  methods: {
    dialogOpen(row, projectId) {
      this.dialogVisible = true;
      this.templet = row;
      if (projectId) {
        // projectId不为空，说明是报告详情中点击查看模板
        this.projectId = projectId;
        this.selectTemplet(row.Zid, projectId);
      } else {
        // projectId为空，说明是创建报告中点击查看模板
        this.selectTemplet(row.Zid);
      }
    },
    handleClose(done) {
      this.$confirm("确认关闭？")
        .then(_ => {
          done();
        })
        .catch(_ => {});
    },
    // 获取可选择的模板
    selectTemplet(indxTreeId, projectId) {
      if (projectId) {
        // projectId不为空，说明在报告详情中
        indx
          .selectTemplet(indxTreeId, projectId)
          .then(res => {
            this.templetList = res.data.data;
          })
          .catch();
      } else {
        // projectId为空，说明在创建报告中
        reports
          .allIndxTemplet(indxTreeId)
          .then(res => {
            this.templetList = res.data.data;
          })
          .catch();
      }
    },
    // 提交选择的模板
    saveSelectedTemplet(projectId) {
      if (projectId) {
        // projectId不为空，说明是在报告详情中添加模板
        indx
          .saveSelectedTemplet(projectId, this.selectedTempletList)
          .then(res => {
            this.$message.success(res.data.errmsg);
            this.dialogVisible = false;
            this.$parent.dialogVisible = false;
            this.$parent.$parent.$parent.handleCurrentChange(1);
          })
          .catch();
      } else {
        // projectId为空，说明是创建报告时添加模板
        this.dialogVisible = false;
        this.$parent.dialogVisible = false;
        this.$parent.$parent.selectedTempletList = this.selectedTempletList;
      }
    },
    handleSelectionChange(val) {
      this.selectedTempletList = val;
    }
  }
};
</script>

<style>
</style>