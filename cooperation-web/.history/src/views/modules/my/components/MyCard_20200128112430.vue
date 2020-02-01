<template>
  <el-card style="margin-bottom:20px">
    <div slot="header" class="clearfix">
      <span>My Card</span>
      <el-link type="danger" class="savePassword-link" @click="savePassword(myCard.id)">修改密码</el-link>
    </div>

    <div class="user-profile">
      <div class="box-center">
        <pan-thumb :image="myCard.avatar" :height="'100px'" :width="'100px'" :hoverable="false">
          <div>Hello</div>
          {{ myCard.loginName }}
          <el-button type="mini" icon="el-icon-edit" circle @click="changeAvatar(myCard.id)"></el-button>
        </pan-thumb>
      </div>
      <div class="box-center">
        <div class="user-name text-center">{{ myCard.roleName }}</div>
        <div class="user-role text-center text-muted">{{ myCard.name | uppercaseFirst }}</div>
      </div>
    </div>

    <div class="user-bio">
      <div class="user-dept user-bio-section">
        <div class="user-bio-section-header">
          <svg-icon icon-class="dept" />
          <span>Dept</span>
        </div>
        <div class="user-bio-section-body">
          <div class="text-muted">{{myCard.deptName}}</div>
        </div>
      </div>

      <div class="user-role user-bio-section">
        <div class="user-bio-section-header">
          <svg-icon icon-class="role" />
          <span>Role</span>
        </div>
        <div class="user-bio-section-body">
          <div class="text-muted">{{myCard.roleName}}</div>
        </div>
      </div>

      <div class="user-phone user-bio-section">
        <div class="user-bio-section-header">
          <svg-icon icon-class="tele" />
          <span>Tele</span>
        </div>
        <div class="user-bio-section-body">
          <div class="text-muted">{{myCard.tele}}</div>
        </div>
      </div>

      <div class="user-email user-bio-section">
        <div class="user-bio-section-header">
          <svg-icon icon-class="email" />
          <span>Email</span>
        </div>
        <div class="user-bio-section-body">
          <div class="text-muted">{{myCard.email}}</div>
        </div>
      </div>
    </div>

    <!-- 上传头像框 -->
    <UploadAvatarDialog ref="uploadAvatar" :myCard="myCard"></UploadAvatarDialog>
  </el-card>
</template>

<script>
// 图片组件
import PanThumb from "@/components/PanThumb";
import * as my from "@/api/my";
import UploadAvatarDialog from "./UploadAvatarDialog";

export default {
  components: { PanThumb, UploadAvatarDialog },

  props: {
    userInfo: {
      type: Object
    }
  },

  inject: ["reload"],

  data() {
    return {
      myCard: this.userInfo
    };
  },

  methods: {
    savePassword(id) {
      this.$refs.savePassword.dialogOpen(id);
    },
    changeAvatar() {
      this.$refs.uploadAvatar.dialogOpen();
    }
  },

  filters: {
    uppercaseFirst(value) {
      if (!value) return "";
      value = value.toString();
      return value.charAt(0).toUpperCase() + value.slice(1);
    }
  }
};
</script>

<style lang="scss" scoped>
.savePassword-link {
  float: right;
}
.clearfix {
  color: #606266;
}

.box-center {
  margin: 0 auto;
  display: table;
}

.text-muted {
  color: #777;
}

.user-profile {
  .user-name {
    font-weight: bold;
  }

  .box-center {
    padding-top: 10px;
  }

  .user-role {
    padding-top: 10px;
    font-weight: 400;
    font-size: 14px;
  }

  .box-social {
    padding-top: 30px;

    .el-table {
      border-top: 1px solid #dfe6ec;
    }
  }

  .user-follow {
    padding-top: 20px;
  }
}

.user-bio {
  margin-top: 20px;
  color: #606266;

  span {
    padding-left: 4px;
  }

  .user-bio-section {
    font-size: 14px;
    padding: 15px 0;

    .user-bio-section-header {
      border-bottom: 1px solid #dfe6ec;
      padding-bottom: 10px;
      margin-bottom: 10px;
      font-weight: bold;
    }
  }
}
</style>
