<template>
  <el-dialog
    :visible.sync="dialogFormVisible"
    :before-close="handleClose"
    customClass="dialog-style"
  >
    <div class="border-head-style"></div>

    <el-form :inline="true" :model="form" ref="form" class="form">
      <el-form-item label="任务成员:" class="label-style" prop="userId">
        <el-select v-model="form.userId" placeholder="请选择成员">
          <el-option
            v-for="item in this.options"
            :key="item.id"
            :label="item.loginName"
            :value="item.id"
          ></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="任务名称:" class="label-style" prop="name">
        <el-input v-model="form.name" autocomplete="off" class="input-style" maxlength="30"></el-input>
      </el-form-item>
      <el-form-item label="创建时间:" class="label-style" prop="createTime">
        <!-- format是显示的格式，value-format是获取到的值的格式 -->
        <el-date-picker
          class="input-style"
          type="date"
          placeholder="选择日期"
          v-model="form.createTime"
          value-format="yyyy-MM-dd"
          format="yyyy-MM-dd"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="任务状态:" class="label-style" prop="status">
        <el-input v-model="form.status" autocomplete="off" class="input-style"></el-input>
      </el-form-item>
      <el-form-item label="完成时间:" class="label-style" prop="finishTime">
        <el-date-picker
          class="input-style"
          type="date"
          placeholder="选择日期"
          v-model="form.finishTime"
          value-format="yyyy-MM-dd"
          format="yyyy-MM-dd"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="任务优先级:" class="label-style" prop="leavel">
        <el-input v-model="form.leavel" autocomplete="off" class="input-style"></el-input>
      </el-form-item>

      <el-form-item label="备注:" prop="message">
        <el-input
          type="textarea"
          v-model="form.message"
          autocomplete="off"
          maxlength="150"
          show-word-limit
          style="width:400px;height:100px;margin-bottom:-60px"
        ></el-input>
      </el-form-item>
    </el-form>

    <span slot="title" class="dialog-title">
      <span style="font-size:20px">{{dialogTitle}}</span>
    </span>
    <div class="border-style"></div>
    <div slot="footer" class="button-style">
      <el-button size="small" @click="dialogFormVisible = false">取 消</el-button>
      <el-button type="primary" size="small" @click="submitForm">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
import * as coordination from "@/api/coordination/coordination";

export default {
  data() {
    return {
      dialogFormVisible: false,
      dialogTitle: "",
      options: [],
      username: "",
      form: {
        id: "",
        projectId: "",
        userId: "",
        name: "",
        createTime: "",
        status: "",
        finishTime: "",
        leavel: "",
        message: ""
      }
    };
  },
  watch: {
    dialogFormVisible(val) {
      if (val == false) {
        this.$refs.form.resetFields();
      }
    }
  },
  methods: {
    handleClose(done) {
      this.$confirm("确认关闭？")
        .then(_ => {
          this.dialogFormVisible = false;
          done();
        })
        .catch(_ => {});
    },
    dialogOpen(projectId, title, data) {
      this.dialogTitle = title;
      this.dialogFormVisible = true;
      coordination.getProjectUserList(projectId).then(res => {
        this.options = res.data.data;
      });
      this.form.projectId = projectId;
      if (data != null) {
        this.$nextTick(function() {
          this.form.id = data.id;
          this.form.projectId = projectId;
          this.form.userId = data.userId;
          this.form.name = data.name;
          this.form.createTime = data.createTime;
          this.form.status = data.status;
          this.form.finishTime = data.finishTime;
          this.form.leavel = data.leavel;
          this.form.message = data.message;
        });
      }
    },
    submitForm() {
      coordination
        .saveProjectTask(this.form)
        .then(res => {
          this.$message.success(res.data.errmsg);
          this.$refs.form.resetFields();
          this.dialogFormVisible = false;
        })
        .catch(err => {});
    }
  }
};
</script>

<style>
</style>