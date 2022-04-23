<template>
  <div class="container">
    <div class="content">
      <el-form ref="form" label-width="100px">
        <el-form-item label="标题:">{{ this.title }}</el-form-item>
        <el-form-item label="关键字:">{{ this.keyword }}</el-form-item>
        <el-form-item label="类型:">
          {{ this.safeName || "其他类型" }}
        </el-form-item>
        <el-form-item label="案例封面:"
          ><img :src="$store.state.settings.imageUrl + this.casePic"
        /></el-form-item>
        <el-form-item label="案例内容:">
          <span v-html="this.content"></span>
        </el-form-item>
        <el-form-item> </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
export default {
  name: "view",
  data() {
    return {
      title: null,
      keyword: null,
      safeName: null,
      casePic: null,
      content: null,
    };
  },
  created() {
    console.log(1);
    if (this.$route.query) {
      console.log(this.$route.query.caseID);
      this.title = JSON.parse(this.$route.query.basicForm).title;
      this.keyword = JSON.parse(this.$route.query.basicForm).keywords;
      this.safeName = JSON.parse(this.$route.query.basicForm).safeName;
      this.casePic = JSON.parse(this.$route.query.basicForm).picPath;
      this.content = JSON.parse(this.$route.query.basicForm).content;
      console.log(this.data);
    }
  },
  methods: {},
};
</script>
<style scoped>
.container {
  margin-top: 40px;
  margin-left: 60px;
}
.content {
}
img {
  width: 300px;
  height: 300px;
  margin-top: 9px;
}
p {
  margin-top: 0px;
  margin-right: 65px;
}
</style>