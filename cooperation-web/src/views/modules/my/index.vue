<template>
  <div class="app-container">
    <el-row :gutter="25">
      <el-col :span="7" :xs="24">
        <MyCard :userInfo="userInfo"></MyCard>
      </el-col>

      <el-col :span="17" :xs="24">
        <MyAccount :myAccount="myAccount" @updateUserInfo="updateUserInfo"></MyAccount>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import * as my from "@/api/my";
import MyCard from "./components/MyCard";
import MyAccount from "./components/MyAccount";

export default {
  components: { MyCard, MyAccount },

  data() {
    return {
      isReload: true,
      userInfo: {
        username: "",
        name: "",
        email: "",
        mobile: ""
      },
      myAccount: {
        username: "",
        name: "",
        email: "",
        mobile: ""
      }
    };
  },

  methods: {
    // 刷新 DOM
    reload() {
      this.isReload = false;
      // nextTick，用于异步执行 DOM 更新
      this.$nextTick(function() {
        this.isReload = true;
      });
    },

    updateUserInfo(newUserInfo) {
      // 如果这里直接用 this.userInfo = this.myAccount 会导致数据双向绑定
      this.userInfo.username = newUserInfo.username;
      this.userInfo.name = newUserInfo.name;
      this.userInfo.email = newUserInfo.email;
      this.userInfo.mobile = newUserInfo.mobile;
    }
  },

  mounted() {
    my.getMyInfo().then(({ data }) => {
      if (data && data.code === 0) {
        this.userInfo.username = data.user.username;
        this.userInfo.name = data.user.name;
        this.userInfo.email = data.user.email;
        this.userInfo.mobile = data.user.mobile;

        // 给MyAccount.vue的数据
        this.myAccount.username = data.user.username;
        this.myAccount.name = data.user.name;
        this.myAccount.email = data.user.email;
        this.myAccount.mobile = data.user.mobile;
      }
    });
  }
};
</script>