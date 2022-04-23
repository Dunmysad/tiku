<template>
  <div class="container">
    <div class="content">
      <el-form ref="form" label-width="110px">
        <el-form-item label="标题:">{{ this.title }}</el-form-item>
        <el-form-item label="关键字:">{{ this.keywords }}</el-form-item>
        <el-form-item label="类型:">
          {{ this.safeTypeId || "其他类型" }}
        </el-form-item>
        <el-form-item label="资源类型:">
          <span v-if="this.dataSource == 0">文字类型</span>
          <span v-if="this.dataSource == 1">图片类型</span>
          <span v-if="this.dataSource == 2">视频类型</span>
        </el-form-item>
        <el-form-item
          label="学习资料封面:"
        ><img
           v-if="this.coverPicLink"
           :src="$store.state.settings.imageUrl + this.coverPicLink"
         >
          <span v-else>无</span>
        </el-form-item>
        <el-form-item
          label="学习资料视频:"
        ><video
           v-if="this.videoPath"
           controls
           :src="$store.state.settings.imageUrl + this.videoPath"
         />
          <span v-else>无</span>
        </el-form-item>
        <el-form-item label="学习资料内容:">
          <span class="text" v-html="this.content">&#12288;&#12288;</span>
        </el-form-item>
        <el-form-item label="显示状态:" style="margin-bottom: 32px">
          <el-switch
            v-model="this.introduce"
            active-color="#409EFF"
            inactive-color="#F56C6C"
          />
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
export default {
  name: 'StudyView',
  data() {
    return {
      title: null,
      keywords: null,
      safeName: null,
      coverPicLink: null,
      content: null,
      introduce: null,
      dataSource: null,
      videoPath: null
    }
  },
  created() {
    console.log(1)
    if (this.$route.query) {
      console.log(this.$route.query.studyID)
      this.title = JSON.parse(this.$route.query.basicForm).title
      this.keywords = JSON.parse(this.$route.query.basicForm).keywords
      this.safeName = JSON.parse(this.$route.query.basicForm).safeName
      this.coverPicLink = JSON.parse(this.$route.query.basicForm).coverPicLink
      this.content = JSON.parse(this.$route.query.basicForm).content
      this.introduce = JSON.parse(this.$route.query.basicForm).isIntroduce
      this.dataSource = JSON.parse(this.$route.query.basicForm).dataType
      this.videoPath = JSON.parse(this.$route.query.basicForm).videoPath
      console.log(this.data)
    }
  },
  methods: {}
}
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
video {
  width: 600px;
  height: 400px;
}
.text {
  text-indent: 2em;
}
</style>
