<template>
  <el-dialog title="修改密码" :visible.sync="dialogFormVisible" width="30%" :before-close="handleClose">
    <div class="border-head-style"></div>

    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" status-icon label-width="35%">
      <el-form-item label="原密码" prop="origin">
        <el-input
          type="password"
          v-model="ruleForm.origin"
          autocomplete="off"
          placeholder="请输入原密码"
          style="max-width:200px;"
          maxlength="15"
          show-password
        ></el-input>
      </el-form-item>
      <el-form-item label="新密码" prop="pass">
        <el-input
          type="password"
          v-model="ruleForm.pass"
          autocomplete="off"
          placeholder="请输入新密码"
          style="max-width:200px;"
          maxlength="15"
          show-password
        ></el-input>
      </el-form-item>
      <el-form-item label="确认密码" prop="checkPass">
        <el-input
          type="password"
          v-model="ruleForm.checkPass"
          autocomplete="off"
          placeholder="再次输入新密码"
          style="max-width:200px;"
          show-password
        ></el-input>
      </el-form-item>
    </el-form>

    <div slot="footer" class="dialog-footer">
      <el-button size="small" @click="dialogFormVisible = false">取 消</el-button>
      <el-button type="primary" size="small" @click="submitForm()">确 定</el-button>
    </div>

    <div class="border-style"></div>
  </el-dialog>
</template>

<script>
import * as my from "@/api/my";

export default {
  data() {
    var validateOrigin = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入原密码"));
      } else {
        callback();
      }
    };

    var validatePass = (rule, value, callback) => {
      if (value.length > 30) {
        callback(new Error("密码长度不得大于30"));
      } else if (value === "") {
        callback(new Error("请输入新密码"));
      } else {
        if (this.ruleForm.pass !== "" && this.ruleForm.pass.length >= 6) {
          callback();
          this.$refs.ruleForm.validateField("checkPass");
        }
        callback(new Error("密码长度至少为6"));
      }
    };

    var validatePass2 = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请再次输入新密码"));
      } else if (value !== this.ruleForm.pass) {
        callback(new Error("两次输入的新密码不一致!"));
      } else {
        callback();
      }
    };

    return {
      dialogFormVisible: false,
      ruleForm: {
        id: "",
        origin: "",
        pass: "",
        checkPass: ""
      },
      rules: {
        origin: [
          {
            required: true,
            validator: validateOrigin,
            trigger: "blur"
          }
        ],
        pass: [
          {
            required: true,
            validator: validatePass,
            trigger: "blur"
          }
        ],
        checkPass: [
          {
            required: true,
            validator: validatePass2,
            trigger: "blur"
          }
        ]
      }
    };
  },
  watch: {
    dialogFormVisible(val) {
      if (val == false) {
        this.$refs.ruleForm.resetFields();
      }
    }
  },
  methods: {
    // 关闭窗口提示
    handleClose(done) {
      this.$confirm("确认关闭？")
        .then(_ => {
          done();
        })
        .catch(_ => {});
    },

    // 打开修改密码对话框
    dialogOpen(id) {
      this.dialogFormVisible = true;
      this.ruleForm.id = id;
    },

    // 提交表单
    submitForm() {
      this.$refs.ruleForm.validate(valid => {
        if (valid) {
          my.savePassword(this.ruleForm)
            .then(res => {
              this.dialogFormVisible = false;
              this.$message.success(res.data.errmsg);
            })
            .catch(err => {});
        } else {
          return false;
        }
      });
    }
  }
};
</script>

<style>
.edit-input {
  width: 80%;
}
</style>
