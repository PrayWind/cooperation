<template>
  <div class="app-container">
    <el-row :gutter="25">
      <el-col :span="7" :xs="24">
        <MyCard :userInfo="userInfo" v-if="isReload"></MyCard>
      </el-col>

      <el-col :span="17" :xs="24">
        <MyAccount :myAccount="myAccount" @updateUserInfo="updateUserInfo" v-if="isReload"></MyAccount>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import * as my from "@/api/my";
import MyCard from "./components/MyCard";
import MyAccount from "./components/MyAccount";
import { mapGetters } from "vuex";

export default {
  components: { MyCard, MyAccount },

  computed: {
    ...mapGetters(["avatar"])
  },

  data() {
    return {
      isReload: true,
      userInfo: {
        id: "",
        loginName: "",
        name: "",
        tele: "",
        email: "",
        avatar: "",
        roleName: "",
        deptName: ""
      },
      myAccount: {
        loginName: "",
        name: "",
        tele: "",
        email: ""
      }
    };
  },

  provide() {
    return {
      reload: this.reload
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
      this.userInfo.loginName = newUserInfo.loginName;
      this.userInfo.name = newUserInfo.name;
      this.userInfo.tele = newUserInfo.tele;
      this.userInfo.email = newUserInfo.email;
    }
  },

  created() {
    my.getUserInfo()
      .then(res => {
        this.userInfo.id = res.data.data.id;
        this.userInfo.loginName = res.data.data.loginName;
        this.userInfo.name = res.data.data.name;
        this.userInfo.tele = res.data.data.tele;
        this.userInfo.email = res.data.data.email;
        this.userInfo.avatar = this.$store.getters.avatar;
        this.userInfo.roleName = res.data.data.roleName;
        this.userInfo.deptName = res.data.data.deptName;

        // 给MyAccount.vue的数据
        this.myAccount.loginName = res.data.data.loginName;
        this.myAccount.name = res.data.data.name;
        this.myAccount.tele = res.data.data.tele;
        this.myAccount.email = res.data.data.email;
      })
      .catch(err => {});
  }
};
</script>