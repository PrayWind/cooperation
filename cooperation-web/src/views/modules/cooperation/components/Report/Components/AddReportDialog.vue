<!-- 添加项目 -->
<template>
  <div>
    <el-dialog
      v-loading="loading"
      :title="dialogTitle"
      :visible.sync="dialogVisible"
      customClass="dialog-style"
      :fullscreen="false"
      :before-close="handleClose"
    >
      <el-form
        ref="formData"
        :inline="true"
        :model="formData"
        :rules="rules"
        label-position="right"
        status-icon
      >
        <el-form-item label="报告名称:" class="label-style" prop="name">
          <el-input
            type="text"
            v-model="formData.name"
            class="input-style"
            placeholder="请输入报告名称"
            maxlength="20"
          ></el-input>
        </el-form-item>
        <el-form-item label="负责人:" class="label-style" prop="leader">
          <!-- <el-input type="text" v-model="formData.leader" placeholder="请选择负责人" class="input-style"></el-input> -->
          <el-select v-model="formData.leader" placeholder="请选择负责人" class="input-style">
            <el-option v-for="item in leaders" :key="item.id" :label="item.name" :value="item.id"></el-option>
          </el-select>
        </el-form-item>
        <!-- <el-form-item label="报告类型:" class="label-style" prop="type">
          <el-input type="text" v-model="formData.type" placeholder="请选择报告类型" class="input-style"></el-input>
        </el-form-item>-->
        <el-form-item label="报告简介:" class="label-style" prop="projectInfo">
          <el-input
            v-model="formData.projectInfo"
            class="input-style"
            type="textarea"
            placeholder="请输入报告简介"
            maxlength="50"
          ></el-input>
        </el-form-item>
        <el-form-item label="开始日期:" class="label-style" prop="createTime">
          <el-date-picker
            v-model="formData.createTime"
            type="date"
            placeholder="选择日期"
            format="yyyy - MM - dd"
            value-format="yyyy-MM-dd"
            class="input-style"
            :picker-options="pickerOptions"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="添加指标:" class="label-style" prop="indx">
          <el-button @click="addIndx" class="input-style">添加指标</el-button>
        </el-form-item>
        <el-form-item label="预计完成:" class="label-style" style="margin-top:-20px" prop="predictTime">
          <el-date-picker
            v-model="formData.predictTime"
            type="date"
            placeholder="选择日期"
            format="yyyy - MM - dd"
            value-format="yyyy-MM-dd"
            class="input-style"
            :picker-options="pickerOptions"
          ></el-date-picker>
        </el-form-item>
      </el-form>
      <div slot="footer" class="button-style">
        <div class="border-style"></div>
        <el-button size="small" @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" size="small" @click="submitForm('formData')">确 定</el-button>
      </div>
    </el-dialog>
    <!-- 其他组件 -->
    <AddIndxDialog ref="addIndxDialog"></AddIndxDialog>
  </div>
</template>

<script>
import * as reports from "@/api/Coordination/reports";
import AddIndxDialog from "../ReportDetail/Components/AddIndxDialog";

export default {
  //import引入的组件需要注入到对象中才能使用
  components: { AddIndxDialog },
  data() {
    //这里存放数据
    return {
      dialogTitle: "添加项目",
      dialogVisible: false,
      fullscreen: false,
      loading: false,
      leaders: [],
      formData: {
        name: "",
        leader: "",
        projectInfo: "",
        createTime: "",
        predictTime: "",
        indx: "1",
        type: ""
      },
      selectedTempletList: [],
      rules: {
        name: [
          { required: true, message: "请输入报告名称", trigger: "blur" },
          { min: 1, max: 15, message: "长度在 1 到 15 个字符", trigger: "blur" }
        ],
        leader: [
          { required: true, message: "请选择负责人", trigger: "change" }
        ],
        createTime: [
          {
            required: true,
            message: "请选择日期",
            trigger: "change"
          }
        ],
        predictTime: [
          {
            required: true,
            message: "请选择日期",
            trigger: "change"
          }
        ],
        indx: [
          {
            required: true,
            message: "请添加指标",
            trigger: "change"
          }
        ]
      },
      pickerOptions: {
        // disabledDate(time) {
        //   return time.getTime() > Date.now();
        // },
        shortcuts: [
          {
            text: "今天",
            onClick(picker) {
              picker.$emit("pick", new Date());
            }
          },
          {
            text: "昨天",
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() - 3600 * 1000 * 24);
              picker.$emit("pick", date);
            }
          },
          {
            text: "一周前",
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit("pick", date);
            }
          }
        ]
      }
    };
  },

  //监控data中的数据变化
  watch: {
    dialogVisible(val) {
      if (val == false) {
        this.$refs.formData.resetFields();
        this.selectedTempletList = [];
      }
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
    dialogOpen() {
      this.getUserList();
      this.dialogVisible = true;
    },
    // 获取所有用户
    getUserList() {
      reports
        .userList()
        .then(res => {
          this.leaders = res.data.data;
        })
        .catch();
    },
    /**
     * 提交表单
     */
    submitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          if (this.selectedTempletList.length == 0) {
            this.$message({
              showClose: true,
              message: "请添加指标",
              type: "error"
            });
            return false;
          }
          reports
            .saveReport(this.formData, this.selectedTempletList)
            .then(res => {
              this.$message.success(res.data.errmsg);
              this.dialogVisible = false;
              this.$emit("list");
            })
            .catch();
        }
      });
    },
    addIndx() {
      this.$refs.addIndxDialog.dialogOpen();
    }
  }
};
</script>

<style lang='scss' scoped>
//@import url(); 引入公共css类
</style>