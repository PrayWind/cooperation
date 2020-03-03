<template>
  <el-card style="width: 98%">
    <div slot="header" class="clearfix">
      <span>My Account</span>
    </div>

    <el-form :model="myAccount" :rules="rules" ref="myAccount" status-icon label-width="10%">
      <el-form-item label="账号:">
        <el-input type="text" v-model="myAccount.username" class="edit-input" disabled></el-input>
      </el-form-item>
      <el-form-item label="昵称:">
        <el-input type="text" v-model="myAccount.name" class="edit-input" maxlength="20"></el-input>
      </el-form-item>
      <el-form-item label="手机:" prop="mobile">
        <el-input type="text" v-model="myAccount.mobile" class="edit-input" maxlength="11"></el-input>
      </el-form-item>
      <el-form-item label="邮箱:" prop="email">
        <el-input type="text" v-model="myAccount.email" class="edit-input" maxlength="30"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" size="small" @click="saveMyInfo(myAccount)">修改</el-button>
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

    var validateMobile = (rule, value, callback) => {
      var reg = /^1[3456789]\d{9}$/;
      if (this.myAccount.tele === "") {
        callback(new Error("请输入手机号码"));
      } else {
        if (!reg.test(this.myAccount.mobile)) {
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
        mobile: [
          {
            required: true,
            validator: validateMobile,
            trigger: "blur"
          }
        ]
      }
    };
  },

  methods: {
    saveMyInfo(newUserInfo) {
      this.$refs.myAccount.validate(valid => {
        if (valid) {
          my.saveMyInfo(newUserInfo)
            .then(({ data }) => {
              this.$message.success("成功");
              this.$emit("updateUserInfo", newUserInfo);
            })
            .catch(({ data }) => {
              this.$message.error("失败");
            });
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

