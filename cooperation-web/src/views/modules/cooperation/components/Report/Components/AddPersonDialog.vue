<!--  -->
<template>
  <el-dialog title="添加用户" :visible.sync="dialogVisible" width="30%" :before-close="handleClose">
    <center>
      <el-select v-model="selectUserIds" placeholder="请选择" multiple>
        <el-option v-for="item in userList" :key="item.id" :label="item.name" :value="item.id"></el-option>
      </el-select>
    </center>
    <div slot="footer" class="dialog-footer">
      <el-button size="small" @click="dialogVisible = false">取 消</el-button>
      <el-button type="primary" size="small" @click="save">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
import * as reports from "@/api/Coordination/reports";

export default {
  //import引入的组件需要注入到对象中才能使用
  components: {},
  data() {
    //这里存放数据
    return {
      dialogVisible: false,
      projectId: "",
      userList: [],
      selectUserIds: []
    };
  },

  watch: {
    dialogVisible(val) {
      this.selectUserIds = [];
    }
  },

  //方法集合
  methods: {
    handleClose(done) {
      this.$confirm("确认关闭？")
        .then(_ => {
          done();
        })
        .catch(_ => {});
    },
    dialogOpen(projectId) {
      this.getUserList(projectId);
      this.projectId = projectId;
      this.dialogVisible = true;
    },
    getUserList(projectId) {
      reports
        .userList(projectId)
        .then(res => {
          this.loading = false;
          this.userList = res.data.data.userList;
          this.selectUserIds = res.data.data.userIds;
        })
        .catch();
    },
    save() {
      reports
        .addUser(this.projectId, this.selectUserIds)
        .then(res => {
          this.dialogVisible = false;
          this.$message.success("成功");
          this.$emit("list");
        })
        .catch();
    }
  }
};
</script>

<style lang='scss' scoped>
//@import url(); 引入公共css类
</style>