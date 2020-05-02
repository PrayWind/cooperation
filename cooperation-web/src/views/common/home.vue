<template>
  <div style="margin: -20px">
    <div style="background: rgba(38, 50, 56, 0.6);">
      <img :src="mainBg" width="100%" style="opacity: 0.6; filter: alpha(opacity=60);" />
    </div>
    <div class="brand-info">
      <h2 class="brand-info__text">报告协作系统</h2>
      <p class="brand-info__intro">报告写作是一个用于解决XXX公司内文档协作、管理的系统。</p>
      <p class="brand-info__intro">本系统致力于为公司报告组提供文档的分工、协作等与管理相关的功能，</p>
      <p class="brand-info__intro">通过微软提供的OneDrive实现成员与成员之间文档的共享，实现在线编辑功能；</p>
      <p class="brand-info__intro">在这基础上二次开发一个Office插件实现文档与系统交互，</p>
      <p class="brand-info__intro">辅助报告组进行有效的报告管理与协作，提高工作效率。</p>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      mainBg: require("@/assets/img/main-bg2.jpg")
    };
  }
};
</script>

<style lang="scss" scope>
.brand-info {
  position: absolute;
  top: -60px;
  right: 0;
  bottom: 0;
  left: 60px;
  padding: 0;
  margin: 220px 100px 0 90px;
  color: #fff;
}
.brand-info__text {
  margin: 0 0 22px 0;
  font-size: 48px;
  font-weight: 400;
  text-transform: uppercase;
}
.brand-info__intro {
  margin: 10px 0;
  font-size: 16px;
  line-height: 1.58;
  opacity: 0.7;
}
</style>

