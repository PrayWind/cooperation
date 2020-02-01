<template>
  <el-dialog title="选择用户" :visible.sync="dialogVisible" width="30%" :before-close="handleClose">
    <center>
      <el-select v-model="selectUserIds" placeholder="请选择" multiple>
        <el-option
          v-for="item in projectUserList"
          :key="item.id"
          :label="item.name"
          :value="item.id"
        ></el-option>
      </el-select>
    </center>
    <div slot="footer" class="dialog-footer">
      <el-button size="small" @click="dialogVisible = false">取 消</el-button>
      <el-button type="primary" size="small" @click="save">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
import * as indx from "@/api/Coordination/ReportDetail/indx";

export default {
  data() {
    return {
      dialogVisible: false,
      projectId: "",
      templet: {},
      projectUserList: [],
      selectUserIds: []
    };
  },

  watch: {
    dialogVisible(val) {
      this.selectUserIds = [];
    }
  },

  methods: {
    dialogOpen(id, row) {
      this.getProjectUserList(id, row);
      this.projectId = id;
      this.templet = row;
      this.dialogVisible = true;
    },
    handleClose(done) {
      this.$confirm("确认关闭？")
        .then(_ => {
          done();
        })
        .catch(_ => {});
    },
    // 获取项目用户
    getProjectUserList(id, templet) {
      indx
        .projectUserList(id, templet)
        .then(res => {
          this.projectUserList = res.data.data.userList;
          this.selectUserIds = res.data.data.userIds;
        })
        .catch();
    },
    save() {
      indx
        .sendToUser(this.projectId, this.selectUserIds, this.templet)
        .then(res => {
          this.dialogVisible = false;
          this.$message.success("成功");
          this.$emit('handleCurrentChange', 1);
        })
        .catch();
    }
  }
};
</script>

<style>
</style>