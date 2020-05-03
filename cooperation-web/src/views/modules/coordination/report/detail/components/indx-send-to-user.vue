<template>
  <el-dialog title="指标分配" :visible.sync="dialogVisible" :close-on-click-modal="false">
    <center>
      <el-select v-model="sentUserIds" placeholder="请选择" multiple>
        <el-option
          v-for="item in userList"
          :key="item.userId"
          :label="item.username"
          :value="item.userId"
        ></el-option>
      </el-select>
    </center>
    <div slot="footer" class="dialog-footer">
      <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="saveSentUsers()">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
export default {
  data() {
    return {
      dialogVisible: false,
      reportId: 0,
      indxId: 0,
      userList: [],
      sentUserIds: []
    };
  },

  //方法集合
  methods: {
    dialogOpen(id1, id2) {
      this.reportId = id1;
      this.indxId = id2;
      this.userList = [];
      this.sentUserIds = [];
      this.dialogVisible = true;

      this.getUserList();
    },

    getUserList() {
      this.$http({
        url: this.$http.adornUrl("/detail/sentUsers"),
        method: "get",
        params: this.$http.adornParams({
          reportId: this.reportId,
          indxId: this.indxId
        })
      }).then(({ data }) => {
        if (data && data.code === 0) {
          this.userList = data.userList;
          if (data.hasOwnProperty("sentUserIds")) {
            this.sentUserIds = data.sentUserIds;
          }
        }
      });
    },

    saveSentUsers() {
      this.$http({
        url: this.$http.adornUrl("/detail/saveSentUsers"),
        method: "post",
        data: this.$http.adornData({
          sentUserIds: this.sentUserIds,
          reportId: this.reportId,
          indxId: this.indxId
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
              this.sentUserIds = [];
              this.$emit("refreshDataList");
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