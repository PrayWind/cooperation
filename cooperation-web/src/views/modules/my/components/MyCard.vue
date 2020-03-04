<template>
  <el-card style="margin-bottom:20px">
    <div slot="header" class="clearfix">
      <span>My Card</span>
    </div>

    <div class="user-profile">
      <div class="box-center">
        <pan-thumb :image="avatar" :height="'100px'" :width="'100px'" :hoverable="false">
          <div>Hello</div>
          {{ myCard.username }}
          <el-button type="mini" icon="el-icon-edit" circle @click="uploadAvatar(myCard.id)"></el-button>
        </pan-thumb>
      </div>
      <div class="box-center">
        <div class="user-name text-center">{{ myCard.username }}</div>
        <div class="user-role text-center text-muted">{{ myCard.name }}</div>
      </div>
    </div>

    <div class="user-bio">
      <div class="user-role user-bio-section">
        <div class="user-bio-section-header">
          <icon-svg name="role"></icon-svg>
          <span>Role</span>
        </div>
        <div class="user-bio-section-body">
          <div class="text-muted">{{myCard.username}}</div>
        </div>
      </div>

      <div class="user-phone user-bio-section">
        <div class="user-bio-section-header">
          <icon-svg name="mobile"></icon-svg>
          <span>Mobile</span>
        </div>
        <div class="user-bio-section-body">
          <div class="text-muted">{{myCard.mobile}}</div>
        </div>
      </div>

      <div class="user-email user-bio-section">
        <div class="user-bio-section-header">
          <icon-svg name="email"></icon-svg>
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

  computed: {
    avatar: {
      get() {
        return this.$store.state.user.avatar;
      }
    }
  },

  props: {
    userInfo: {
      type: Object
    }
  },

  data() {
    return {
      myCard: this.userInfo
    };
  },

  methods: {
    uploadAvatar() {
      this.$refs.uploadAvatar.dialogOpen();
    }
  }
};
</script>

<style lang="scss" scoped>
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
