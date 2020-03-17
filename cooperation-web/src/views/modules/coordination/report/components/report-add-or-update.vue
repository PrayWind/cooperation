<template>
  <div>
    <el-dialog
      :title="!dataForm.id ? '新增' : '修改'"
      :close-on-click-modal="false"
      :visible.sync="visible"
    >
      <el-form
        :model="dataForm"
        :rules="dataRule"
        :inline="true"
        label-position="right"
        ref="dataForm"
        @keyup.enter.native="dataFormSubmit()"
      >
        <el-form-item label="报告名称" prop="name" class="label-style">
          <el-input
            v-model="dataForm.name"
            type="text"
            placeholder="请输入报告名称"
            maxlength="20"
            class="input-style"
          ></el-input>
        </el-form-item>
        <el-form-item label="负责人" prop="leader" class="label-style">
          <el-select v-model="dataForm.leader" placeholder="请选择负责人">
            <el-option
              v-for="item in users"
              :key="item.userId"
              :label="item.username"
              :value="item.userId"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="报告简介" prop="reportInfo" class="label-style">
          <el-input
            v-model="dataForm.reportInfo"
            type="textarea"
            placeholder="请输入报告简介"
            maxlength="50"
            class="input-style"
          ></el-input>
        </el-form-item>
        <el-form-item label="开始日期" prop="createTime" class="label-style">
          <el-date-picker
            v-model="dataForm.createTime"
            type="date"
            placeholder="请选择日期"
            format="yyyy - MM - dd"
            value-format="yyyy-MM-dd"
            :picker-options="pickerOptions"
            class="input-style"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="添加指标" prop="indx" class="label-style">
          <el-button @click="indxAddOrUpdate(dataForm.id)" class="input-style">添加指标</el-button>
        </el-form-item>
        <el-form-item label="预计完成" prop="predictTime" class="label-style">
          <el-date-picker
            v-model="dataForm.predictTime"
            type="date"
            placeholder="请选择日期"
            format="yyyy - MM - dd"
            value-format="yyyy-MM-dd"
            :picker-options="pickerOptions"
            class="input-style"
          ></el-date-picker>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="visible = false">取消</el-button>
        <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
      </span>
    </el-dialog>
    <!-- 弹窗, 新增 / 修改 -->
    <IndxAddOrUpdate
      v-if="indxAddOrUpdateVisible"
      ref="indxAddOrUpdate"
      @updateIndxList="updateIndxList"
    ></IndxAddOrUpdate>
  </div>
</template>

<script>
import IndxAddOrUpdate from "./indx-add-or-update";

export default {
  components: {
    IndxAddOrUpdate
  },
  data() {
    return {
      visible: false,
      indxAddOrUpdateVisible: false,
      users: [],
      dataForm: {
        id: 0,
        name: "",
        reportInfo: "",
        leader: "",
        type: "",
        createTime: "",
        predictTime: "",
        status: ""
      },
      selectedIndxList: [],
      dataRule: {
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
  methods: {
    init(id) {
      this.dataForm.id = id || 0;
      this.visible = true;

      // 清除上次数据
      this.$nextTick(() => {
        this.$refs["dataForm"].resetFields();
      });

      // 获取系统所有用户
      this.getUsers();

      if (this.dataForm.id != 0) {
        // 获取报告下的所有指标，只有修改报告时才获取
        this.getIndxs();
      }

      if (this.dataForm.id) {
        this.$http({
          url: this.$http.adornUrl(`/report/info/${this.dataForm.id}`),
          method: "get",
          params: this.$http.adornParams()
        }).then(({ data }) => {
          if (data && data.code === 0) {
            this.dataForm.name = data.report.name;
            this.dataForm.reportInfo = data.report.reportInfo;
            // 必须转成整型，el-select标签才能识别
            this.dataForm.leader = parseInt(data.report.leader);
            this.dataForm.type = data.report.type;
            this.dataForm.createTime = data.report.createTime;
            this.dataForm.predictTime = data.report.predictTime;
            this.dataForm.status = data.report.status;
          }
        });
      }
    },

    getUsers() {
      this.$http({
        url: this.$http.adornUrl("/report/users"),
        method: "get",
        params: this.$http.adornParams()
      }).then(({ data }) => {
        if (data && data.code === 0) {
          this.users = data.users;
        }
      });
    },

    getIndxs() {
      this.$http({
        url: this.$http.adornUrl("/report/indxs"),
        method: "get",
        params: this.$http.adornParams({
          reportId: this.dataForm.id
        })
      }).then(({ data }) => {
        if (data && data.code === 0) {
          if (data.hasOwnProperty("indxs")) {
            this.selectedIndxList = data.indxs;
          }
        }
      });
    },

    indxAddOrUpdate(id) {
      this.indxAddOrUpdateVisible = true;
      this.$nextTick(() => {
        this.$refs.indxAddOrUpdate.init(id);
      });
    },

    // 接收子组件传来的 指标数组
    updateIndxList(val) {
      this.selectedIndxList = val;
    },

    // 表单提交
    dataFormSubmit() {
      this.$refs["dataForm"].validate(valid => {
        if (valid) {
          this.$http({
            url: this.$http.adornUrl(
              `/report/${!this.dataForm.id ? "save" : "update"}`
            ),
            method: "post",
            data: this.$http.adornData({
              dataForm: this.dataForm,
              selectedIndxList: this.selectedIndxList
            })
          }).then(({ data }) => {
            if (data && data.code === 0) {
              this.$message({
                message: "操作成功",
                type: "success",
                duration: 1500,
                onClose: () => {
                  this.visible = false;
                  this.selectedIndxList = [];
                  this.$emit("refreshDataList");
                }
              });
            } else {
              this.$message.error(data.msg);
            }
          });
        }
      });
    }
  }
};
</script>

<style>
.label-style {
  width: 44%;
  height: auto;
  text-align: right;
  min-width: 200px;
}
.input-style {
  width: 200px;
}
</style>
