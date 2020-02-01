<template>
  <div>
    <el-row :gutter="24">
      <el-col :span="8">
        <el-card>
          <div slot="header" class="clearfix header-style">
            <span class="header-title-style">报告总数</span>
            <span class="header-num-style">20</span>
          </div>
          <div style="margin: -60px 0 25px -20px">
            <ve-histogram
              :data="reportData.chartData"
              :settings="reportData.chartSettings"
              :extend="chartExtend"
              height="300px"
              ref="reportChart"
            ></ve-histogram>
          </div>
          <div class="footer-style" style="margin-top: -90px">
            <el-divider></el-divider>
            <span class="footer-title-style">本月报告</span>
            <span class="footer-num-style">10</span>
          </div>
        </el-card>
      </el-col>

      <el-col :span="8">
        <el-card>
          <div slot="header" class="clearfix header-style">
            <span class="header-title-style">指标总数</span>
            <span class="header-num-style">20</span>
          </div>
          <div style="margin: -60px 0 25px -20px">
            <ve-line
              :data="indxData.chartData"
              :settings="indxData.chartSettings"
              :extend="chartExtend"
              height="300px"
              ref="indxChart"
            ></ve-line>
          </div>
          <div class="footer-style" style="margin-top: -90px">
            <el-divider></el-divider>
            <span class="footer-title-style">本月指标</span>
            <span class="footer-num-style">10</span>
          </div>
        </el-card>
      </el-col>

      <el-col :span="8">
        <el-card>
          <div slot="header" class="clearfix header-style">
            <span class="header-title-style">整体进度</span>
            <span class="header-num-style">70%</span>
          </div>
          <div>
            <el-progress :text-inside="true" :stroke-width="26" :percentage="70"></el-progress>
          </div>
          <div class="footer-style">
            <el-divider></el-divider>
            <span class="footer-title-style">本月进度</span>
            <span class="footer-num-style">10</span>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row>
      <el-col :span="24">
        <el-card style="margin-top: 20px;">
          <center style="margin-top:35px">
            <span class="header-title-style">各月份报告数和指标数</span>
            <ve-histogram
              :data="totalData.chartData"
              :settings="totalData.chartSettings"
              height="450px"
              width="100%"
              style="margin-top:5px"
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
            { month: "1月", reportNum: 1 },
            { month: "2月", reportNum: 2 },
            { month: "3月", reportNum: 1 },
            { month: "4月", reportNum: 5 },
            { month: "5月", reportNum: 1 },
            { month: "6月", reportNum: 2 },
            { month: "7月", reportNum: 7 },
            { month: "8月", reportNum: 5 },
            { month: "9月", reportNum: 8 },
            { month: "10月", reportNum: 6 },
            { month: "11月", reportNum: 3 },
            { month: "12月", reportNum: 4 }
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
            { month: "1月", indxNum: 1 },
            { month: "2月", indxNum: 2 },
            { month: "3月", indxNum: 1 },
            { month: "4月", indxNum: 5 },
            { month: "5月", indxNum: 1 },
            { month: "6月", indxNum: 2 },
            { month: "7月", indxNum: 7 },
            { month: "8月", indxNum: 5 },
            { month: "9月", indxNum: 8 },
            { month: "10月", indxNum: 6 },
            { month: "11月", indxNum: 3 },
            { month: "12月", indxNum: 4 }
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
            { month: "1月", reportNum: 1, indxNum: 2 },
            { month: "2月", reportNum: 1, indxNum: 2 },
            { month: "3月", reportNum: 1, indxNum: 2 },
            { month: "4月", reportNum: 1, indxNum: 2 },
            { month: "5月", reportNum: 1, indxNum: 2 },
            { month: "6月", reportNum: 1, indxNum: 2 },
            { month: "7月", reportNum: 1, indxNum: 2 },
            { month: "8月", reportNum: 1, indxNum: 2 },
            { month: "9月", reportNum: 1, indxNum: 2 },
            { month: "10月", reportNum: 1, indxNum: 2 },
            { month: "11月", reportNum: 1, indxNum: 2 },
            { month: "12月", reportNum: 1, indxNum: 2 }
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
      }
    };
  },

  methods: {
    // 重新加载图表
    reload() {
      console.log("概览页面图表重新加载");
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
</style>