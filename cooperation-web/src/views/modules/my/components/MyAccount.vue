<template>
  <el-card style="width: 98%">
    <div slot="header" class="clearfix">
      <span>My Account</span>
    </div>

    <el-form :model="myAccount" :rules="rules" ref="myAccount" status-icon label-width="10%">
      <el-form-item label="账号:">
        <el-input type="text" v-model="myAccount.loginName" class="edit-input" disabled></el-input>
      </el-form-item>
      <el-form-item label="昵称:">
        <el-input type="text" v-model="myAccount.name" class="edit-input" maxlength="20"></el-input>
      </el-form-item>
      <el-form-item label="手机:" prop="tele">
        <el-input type="text" v-model="myAccount.tele" class="edit-input" maxlength="11"></el-input>
      </el-form-item>
      <el-form-item label="邮箱:" prop="email">
        <el-input type="text" v-model="myAccount.email" class="edit-input" maxlength="30"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" size="small" @click="saveUserInfo(myAccount)">修改</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>

<script>
import * as my from "@/api/my";
export default {
  props: {
    myAccount: {
      type: Object
    }
  },

  inject: ["reload"],

  data() {
    var validateEmail = (rule, value, callback) => {
      var reg = /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
      if (this.myAccount.email === "") {
        callback(new Error("请输入邮箱"));
      } else {
        if (!reg.test(this.myAccount.email)) {
          callback(new Error("请输入正确的邮箱格式"));
        }
        callback();
      }
    };

    var validateTele = (rule, value, callback) => {
      var reg = /^1[3456789]\d{9}$/;
      if (this.myAccount.tele === "") {
        callback(new Error("请输入手机号码"));
      } else {
        if (!reg.test(this.myAccount.tele)) {
          callback(new Error("请输入有效的手机号码"));
        }
        callback();
      }
    };

    return {
      isReload: true,

      rules: {
        email: [
          {
            required: true,
            validator: validateEmail,
            trigger: "blur"
          }
        ],
        tele: [
          {
            required: true,
            validator: validateTele,
            trigger: "blur"
          }
        ]
      }
    };
  },

  methods: {
    saveUserInfo(newUserInfo) {
      this.$refs.myAccount.validate(valid => {
        if (valid) {
          my.saveUserInfo(newUserInfo)
            .then(res => {
              this.$message.success(res.data.errmsg);
              this.$emit("updateUserInfo", this.myAccount);
              this.reload();
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
.clearfix {
  color: #606266;
}

.edit-input {
  width: 93%;
}
</style>

