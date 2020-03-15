<template>
  <el-dialog :title="!reportId ? '新增' : '修改'" :close-on-click-modal="false" :visible.sync="visible">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input
          v-model="dataForm.search"
          placeholder="请输入指标名称"
          prefix-icon="el-icon-search"
          clearable
        ></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
      </el-form-item>
    </el-form>

    <el-table
      ref="multipleTable"
      :data="tableData"
      tooltip-effect="dark"
      style="width: 100%;"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="name" label="指标名称" width="auto" header-align="center"></el-table-column>
    </el-table>
    <el-pagination
      @size-change="sizeChangeHandle"
      @current-change="currentChangeHandle"
      :current-page="pageIndex"
      :page-sizes="[10, 20, 50, 100]"
      :page-size="pageSize"
      :total="totalPage"
      layout="total, sizes, prev, pager, next, jumper"
    ></el-pagination>

    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
export default {
  data() {
    return {
      dataForm: {
        search: ""
      },
      visible: false,
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      tableData: [],
      multipleSelection: [],
      reportId: 0
    };
  },

  methods: {
    init(id) {
      this.reportId = id || 0;
      this.visible = true;

      this.getDataList();
    },

    // 获取报告下的指标
    getDataList() {
      this.$http({
        url: this.$http.adornUrl("/report/indx"),
        method: "get",
        params: this.$http.adornParams({
          page: this.pageIndex,
          limit: this.pageSize,
          search: this.dataForm.search,
          reportId: this.reportId
        })
      }).then(({ data }) => {
        if (data && data.code === 0) {
          this.tableData = data.page.list;
          this.totalPage = data.page.totalCount;
          if (data.hasOwnProperty("indxIds")) {
            // 第一个参数必须是this.tableData，因为这个是和表格绑定的数据
            this.toggleSelection(this.tableData, data.indxIds);
          }
        } else {
          this.tableData = [];
          this.totalPage = 0;
        }
      });
    },

    // 勾选 target 对应的数据，第一个参数必须是this.tableData，因为这个是和表格绑定的数据，不然显示不了
    toggleSelection(source, target) {
      this.$nextTick(() => {
        source.forEach((temp1, index) => {
          target.forEach(temp2 => {
            if (temp1.id === temp2) {
              this.$refs.multipleTable.toggleRowSelection(source[index], true);
            }
          });
        });
      });
    },

    handleSelectionChange(val) {
      this.multipleSelection = val;
    },

    // 每页数
    sizeChangeHandle(val) {
      this.pageSize = val;
      this.pageIndex = 1;
      this.getDataList();
    },

    // 当前页
    currentChangeHandle(val) {
      this.pageIndex = val;
      this.getDataList();
    },

    // 表单提交
    dataFormSubmit() {
      this.$refs["dataForm"].validate(valid => {
        if (valid) {
          this.$http({
            url: this.$http.adornUrl(
              `/${!this.dataForm.id ? "save" : "update"}`
            ),
            method: "post",
            data: this.$http.adornData({
              userId: this.dataForm.id || undefined,
              username: this.dataForm.userName,
              password: this.dataForm.password,
              salt: this.dataForm.salt,
              email: this.dataForm.email,
              mobile: this.dataForm.mobile,
              status: this.dataForm.status,
              roleIdList: this.dataForm.roleIdList
            })
          }).then(({ data }) => {
            if (data && data.code === 0) {
              this.$message({
                message: "操作成功",
                type: "success",
                duration: 1500,
                onClose: () => {
                  this.visible = false;
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
</style>
