<template>
  <el-dialog title="成员管理" :visible.sync="dialogVisible" :close-on-click-modal="false">
    <center>
      <el-select v-model="selectedUserIds" placeholder="请选择" multiple>
        <el-option
          v-for="item in userList"
          :key="item.userId"
          :label="item.name"
          :value="item.userId"
        ></el-option>
      </el-select>
    </center>
    <div slot="footer" class="dialog-footer">
      <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="saveReportUsers()">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
import * as reports from "@/api/coordination/reports";

export default {
  data() {
    return {
      dialogVisible: false,
      reportId: "",
      userList: [],
      selectedUserIds: []
    };
  },

  //方法集合
  methods: {
    dialogOpen(id) {
      this.reportId = id;
      this.dialogVisible = true;

      this.getUserList();
    },

    getUserList() {
      this.$http({
        url: this.$http.adornUrl(`/detail/reportUsers/${this.reportId}`),
        method: "get",
        params: this.$http.adornParams()
      }).then(({ data }) => {
        if (data && data.code === 0) {
          this.userList = data.userList;
          if (data.hasOwnProperty("selectedUserIds")) {
            this.selectedUserIds = data.selectedUserIds;
          }
        }
      });
    },

    saveReportUsers() {
      this.$http({
        url: this.$http.adornUrl("/detail/saveReportUsers"),
        method: "post",
        data: this.$http.adornData({
          selectedUserIds: this.selectedUserIds,
          reportId: this.reportId
        })
      }).then(({ data }) => {
        if (data && data.code === 0) {
          this.$message({
            message: "操作成功",
            type: "success",
            duration: 1500,
            onClose: () => {
              this.dialogVisible = false;
              this.userList = [];
              this.selectedUserIds = [];
            }
          });
        } else {
          this.$message.error(data.msg);
        }
      });
    }
  }
};
</script>

<style lang='scss' scoped>
//@import url(); 引入公共css类
</style>