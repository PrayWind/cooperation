<template>
  <div>
    <el-row :gutter="24">
      <el-col :span="8">
        <el-card style="height: 270px">
          <div slot="header" class="clearfix header-style">
            <span class="header-title-style">报告总数</span>
            <span class="header-num-style">{{ totalReportNum }}</span>
          </div>
          <ve-histogram
            :data="reportData.chartData"
            :settings="reportData.chartSettings"
            :extend="chartExtend"
            height="270px"
            ref="reportChart"
            style="margin: -50px 0 0 -10px"
          ></ve-histogram>
          <div class="footer-style">
            <span class="footer-title-style">本月报告</span>
            <span class="footer-num-style">{{ currentReportNum }}</span>
          </div>
        </el-card>
      </el-col>

      <el-col :span="8">
        <el-card style="height: 270px">
          <div slot="header" class="clearfix header-style">
            <span class="header-title-style">指标总数</span>
            <span class="header-num-style">{{ totalIndxNum }}</span>
          </div>
          <ve-line
            :data="indxData.chartData"
            :settings="indxData.chartSettings"
            :extend="chartExtend"
            height="270px"
            ref="indxChart"
            style="margin: -50px 0 0 -10px"
          ></ve-line>
          <div class="footer-style">
            <span class="footer-title-style">本月指标</span>
            <span class="footer-num-style">{{ currentIndxNum }}</span>
          </div>
        </el-card>
      </el-col>

      <el-col :span="8">
        <el-card style="height: 155px">
          <div slot="header" class="clearfix header-style">
            <span class="header-title-style">总进度</span>
            <span class="header-num-style">{{ totalProcess }}%</span>
          </div>
          <el-progress :text-inside="true" :stroke-width="26" :percentage="totalProcess"></el-progress>
          <div style="margin-top: 15px">
            <span class="footer-title-style">本月进度</span>
            <span class="footer-num-style">{{ currentProcess }}%</span>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row>
      <el-col :span="24">
        <el-card style="margin-top: 20px; height: 470px">
          <center style="margin-top: 25px;">
            <span class="header-title-style">各月份报告数和指标数</span>
            <ve-histogram
              :data="totalData.chartData"
              :settings="totalData.chartSettings"
              height="420px"
              width="100%"
              style="margin-top:10px"
              ref="totalChart"
            ></ve-histogram>
          </center>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  data() {
    return {
      chartExtend: {
        legend: {
          show: false
        },
        xAxis: {
          show: false
        },
        yAxis: {
          show: false
        }
      },
      reportData: {
        chartData: {
          columns: ["month", "reportNum"],
          rows: [
            { month: "1月", reportNum: "0" },
            { month: "2月", reportNum: "0" },
            { month: "3月", reportNum: "0" },
            { month: "4月", reportNum: "0" },
            { month: "5月", reportNum: "0" },
            { month: "6月", reportNum: "0" },
            { month: "7月", reportNum: 0 },
            { month: "8月", reportNum: 0 },
            { month: "9月", reportNum: 0 },
            { month: "10月", reportNum: 0 },
            { month: "11月", reportNum: 0 },
            { month: "12月", reportNum: 0 }
          ]
        },
        chartSettings: {
          labelMap: {
            reportNum: "报告数"
          }
        }
      },
      indxData: {
        chartData: {
          columns: ["month", "indxNum"],
          rows: [
            { month: "1月", indxNum: "0" },
            { month: "2月", indxNum: "0" },
            { month: "3月", indxNum: "0" },
            { month: "4月", indxNum: "0" },
            { month: "5月", indxNum: "0" },
            { month: "6月", indxNum: "0" },
            { month: "7月", indxNum: 0 },
            { month: "8月", indxNum: 0 },
            { month: "9月", indxNum: 0 },
            { month: "10月", indxNum: 0 },
            { month: "11月", indxNum: 0 },
            { month: "12月", indxNum: 0 }
          ]
        },
        chartSettings: {
          area: true,
          itemStyle: {
            color: "rgb(90, 177, 239)"
          },
          areaStyle: {
            color: "rgb(90, 177, 239)"
          },
          labelMap: {
            indxNum: "指标数"
          }
        }
      },
      totalData: {
        chartData: {
          columns: ["month", "reportNum", "indxNum"],
          rows: [
            { month: "1月", reportNum: "0", indxNum: "0" },
            { month: "2月", reportNum: "0", indxNum: "0" },
            { month: "3月", reportNum: "0", indxNum: "0" },
            { month: "4月", reportNum: "0", indxNum: "0" },
            { month: "5月", reportNum: "0", indxNum: "0" },
            { month: "6月", reportNum: "0", indxNum: "0" },
            { month: "7月", reportNum: 0, indxNum: 0 },
            { month: "8月", reportNum: 0, indxNum: 0 },
            { month: "9月", reportNum: 0, indxNum: 0 },
            { month: "10月", reportNum: 0, indxNum: 0 },
            { month: "11月", reportNum: 0, indxNum: 0 },
            { month: "12月", reportNum: 0, indxNum: 0 }
          ]
        },
        chartSettings: {
          yAxisName: ["数量"],
          legendName: {
            month: "月份"
          },
          labelMap: {
            reportNum: "报告数",
            indxNum: "指标数"
          }
        }
      },
      totalReportNum: 0,
      currentReportNum: 0,
      totalIndxNum: 0,
      currentIndxNum: 0,
      totalProcess: 0,
      currentProcess: 0
    };
  },

  activated() {
    this.getDataList();
  },

  methods: {
    // 获取数据列表
    getDataList() {
      this.$http({
        url: this.$http.adornUrl("/reportAnalysis/list"),
        method: "get",
        params: this.$http.adornParams({})
      }).then(({ data }) => {
        if (data && data.code === 0) {
          this.reportData.chartData.rows = data.reportData.rows;
          this.totalReportNum = data.reportData.totalReportNum;
          this.currentReportNum = data.reportData.currentReportNum;
          this.indxData.chartData.rows = data.indxData.rows;
          this.totalIndxNum = data.indxData.totalIndxNum;
          this.currentIndxNum = data.indxData.currentIndxNum;
          this.totalProcess = data.processData.totalProcess;
          this.currentProcess = data.processData.currentProcess;
          this.totalData.chartData.rows = data.totalData.rows;
        } else {
          this.reportData.chartData.rows = [];
          this.totalReportNum = 0;
          this.currentReportNum = 0;
          this.indxData.chartData.rows = [];
          this.totalIndxNum = 0;
          this.currentIndxNum = 0;
          this.totalProcess = 0;
          this.currentProcess = 0;
          this.totalData.chartData.rows = [];
        }
      });
    },

    // 重新加载图表
    reload() {
      console.log("报告分析重新加载");
      this.$nextTick(_ => {
        this.$refs[`reportChart`].echarts.resize();
        this.$refs[`indxChart`].echarts.resize();
        // this.$refs[`progressChart`].echarts.resize();
        this.$refs[`totalChart`].echarts.resize();
      });
    }
  }
};
</script>

<style>
.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both;
}

.header-style {
  line-height: 16px;
}

.header-title-style {
  font-size: 16px;
}

.header-num-style {
  font-size: 25px;
  margin-left: 5px;
  color: rgba(90, 177, 239);
}

.footer-style {
  margin-top: -65px;
}

.footer-title-style {
  color: rgba(0, 0, 0, 0.45);
  font-size: 16px;
}

.footer-num-style {
  font-size: 20px;
  color: rgba(90, 177, 239);
}
</style>