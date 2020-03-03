<template>
  <el-dialog title="上传头像" :visible.sync="dialogFormVisible" :before-close="handleClose">
    <!-- :accept属性的作用，在弹出的选择文件框会默认筛选出图片格式的文件 -->
    <el-upload
      class="avatar-uploader"
      action
      :http-request="uploadAvatar"
      :accept="'image/*'"
      :show-file-list="false"
      :before-upload="beforeUploadAvatar"
    >
      <img v-if="preview" :src="preview" class="avatar" />
      <i v-else class="el-icon-plus avatar-uploader-icon"></i>
    </el-upload>
  </el-dialog>
</template>

<script>
import * as my from "@/api/my";

export default {
  data() {
    return {
      dialogFormVisible: false,
      preview: "",
      headers: {
        "Content-Type": "multipart/form-data"
      },
      url: process.env.OPEN_PROXY ? "/proxyApi/" : window.SITE_CONFIG.baseUrl
    };
  },

  props: {
    myCard: {
      type: Object
    }
  },

  methods: {
    // 上传头像
    uploadAvatar(data) {
      console.log("@@@@", data);
      console.log("$$$$", this.url);
      let file = new FormData();
      file.append("data", data.file, data.file.name);
      console.log("#####", file.get("data"));
      // this.$http({
      //   url: this.url + "/my/uploadAvatar",
      //   method: "post",
      //   data: file,
      //   headers: this.headers
      // }).then(({ data }) => {
      //   if (data && data.code === 0) {
      //     this.$message({
      //       message: "操作成功",
      //       type: "success",
      //       duration: 1500,
      //       onClose: () => {}
      //     });
      //   } else {
      //     this.$message.error(data.msg);
      //   }
      // });
      my.uploadAvatar(file).then(({ data }) => {
        
        if (data && data.code === 0) {
          this.myCard.avatar =
            url + data;

          // this.$store.commit("SET_AVATAR", this.myCard.avatar);

          this.$message.success(data.msg);
          this.dialogFormVisible = false;
        }
      });
    },

    // 关闭窗口提示
    handleClose(done) {
      this.$confirm("确认关闭？")
        .then(_ => {
          done();
        })
        .catch(_ => {});
    },

    // 打开上传头像对话框
    dialogOpen() {
      this.preview = "";
      this.dialogFormVisible = true;
    },

    // 图片上传失败钩子函数
    // uploadAvatarError(err, file, fileList) {
    //   this.$message.error(err.message);
    // },

    // 图片上传成功钩子函数
    // uploadAvatarSuccess(res, file) {
    //   if (res.errno == 0) {
    //     this.myCard.avatar =
    //       process.env.BASE_API + "/my/loadAvatar?filename=" + res.data;
    //     this.$message.success(res.errmsg);
    //     // 生成预览图
    //     this.preview = URL.createObjectURL(file.raw);
    //     this.$store.commit("SET_AVATAR", this.myCard.avatar);
    //     this.dialogFormVisible = false;
    //   } else {
    //     this.$message.error(res.errmsg);
    //   }
    // },

    // 图片上传之前钩子函数
    beforeUploadAvatar(file) {
      const isImage =
        file.type === "image/png" ||
        file.type === "image/jpg" ||
        file.type === "image/jpeg";
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isImage) {
        this.$message.error("上传头像图片只能是 png/jpg/jpeg 格式!");
      }
      if (!isLt2M) {
        this.$message.error("上传头像图片大小不能超过 2MB!");
      }
      return isImage && isLt2M;
    }
  }
};
</script>

<style>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  left: 35%;
}
.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>