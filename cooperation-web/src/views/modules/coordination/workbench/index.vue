<!-- 工作台 -->
<template>
  <div class>
    <el-row :gutter="24">
      <el-col :span="12">
        <el-card>
          <div slot="header" class="clearfix">
            <span class="title">进行中的报告</span>
          </div>
          <el-table
            :data="currentReport"
            v-loading="dataListLoading"
            class="card-margin-top"
            height="240"
          >
            <el-table-column prop="reportName" label="报告名称"></el-table-column>
            <el-table-column prop="reportInfo" label="报告简介"></el-table-column>
          </el-table>
        </el-card>
      </el-col>

      <el-col :span="12">
        <el-card>
          <div slot="header" class="clearfix">
            <span class="title">进行中的指标</span>
          </div>
          <el-table
            :data="currentReportIndx"
            v-loading="dataListLoading"
            class="card-margin-top"
            height="240"
          >
            <el-table-column prop="reportName" label="报告名称"></el-table-column>
            <el-table-column prop="indxName" label="指标名称"></el-table-column>
          </el-table>
        </el-card>
      </el-col>
    </el-row>
    <br />

    <el-row :gutter="24">
      <el-col :span="12">
        <el-card>
          <div slot="header" class="clearfix">
            <span class="title">参与过的报告</span>
          </div>
          <el-table
            :data="finishReport"
            v-loading="dataListLoading"
            class="card-margin-top"
            height="240"
          >
            <el-table-column prop="reportName" label="报告名称"></el-table-column>
            <el-table-column prop="reportInfo" label="报告简介"></el-table-column>
          </el-table>
        </el-card>
      </el-col>

      <el-col :span="12">
        <el-card>
          <div slot="header" class="clearfix">
            <span class="title">完成过的指标</span>
          </div>
          <el-table
            :data="finishReportIndx"
            v-loading="dataListLoading"
            class="card-margin-top"
            height="240"
          >
            <el-table-column prop="reportName" label="报告名称"></el-table-column>
            <el-table-column prop="indxName" label="指标名称"></el-table-column>
          </el-table>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  data() {
    return {
      currentReport: [],
      currentReportIndx: [],
      finishReport: [],
      finishReportIndx: [],
      dataListLoading: false
    };
  },
  activated() {
    this.getDataList();
  },
  methods: {
    // 获取数据列表
    getDataList() {
      this.dataListLoading = true;
      this.$http({
        url: this.$http.adornUrl("/workbench/list"),
        method: "get",
        params: this.$http.adornParams({})
      }).then(({ data }) => {
        if (data && data.code === 0) {
          this.currentReport = data.currentReport;
          this.currentReportIndx = data.currentReportIndx;
          this.finishReport = data.finishReport;
          this.finishReportIndx = data.finishReportIndx;
        } else {
          this.currentReport = [];
          this.currentReportIndx = [];
          this.finishReport = [];
          this.finishReportIndx = [];
        }
        this.dataListLoading = false;
      });
    }
  }
};
</script>

<style lang='scss' scoped>
.card-margin-top {
  margin-top: -15px;
}

.title {
  font-size: 18px;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both;
}
</style>