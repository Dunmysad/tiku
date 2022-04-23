<template>
  <el-dialog
    append-to-body
    :close-on-click-modal="false"
    :before-close="crud.cancelCU"
    :visible="crud.status.cu > 0"
    :title="crud.status.title"
    width="530px"
  >
    <el-form
      ref="form"
      :model="form"
      size="small"
      label-width="99px"
      :rules="rules"
    >
      <el-form-item label="轮播图片" prop="picPath">
        <el-upload
          v-model="form.picPath"
          ref="upload"
          class="avatar-uploader"
          action="/api/EToolFile"
          :show-file-list="false"
          :http-request="uploadFile"
          :on-change="handleAvatarChange"
          :before-upload="beforeAvatarUpload"
        >
          <img
            v-if="form.picPath"
            :src="$store.state.settings.imageUrl + form.picPath"
            class="avatar"
          />
          <i v-else class="el-icon-plus avatar-uploader-icon" />
          <div class="uploadPicture">点击上传</div>
        </el-upload>
      </el-form-item>
      <el-form-item label="轮播图标题" prop="title">
        <el-input v-model="form.title" style="width: 370px" />
      </el-form-item>
      <el-form-item label="学习资料ID" prop="studyDataId">
        <el-input v-model.number="form.studyDataId" style="width: 370px" />
      </el-form-item>
      <el-form-item label="图片排序" prop="sort">
        <el-input-number
          v-model.number="form.sort"
          :min="0"
          :max="999"
          controls-position="right"
          style="width: 370px"
        />
      </el-form-item>
    </el-form>
    <!-- 底部按钮 -->
    <div slot="footer" class="dialog-footer">
      <el-button type="text" @click="crud.cancelCU"> 取消 </el-button>
      <el-button
        :loading="crud.status.cu === 2"
        type="primary"
        @click="crud.submitCU"
      >
        确认
      </el-button>
    </div>
  </el-dialog>
</template>

<script>
import { form } from "@crud/crud";
import { upload } from "@/utils/upload";

const defaultForm = {
  picPath: "",
  title: "",
  studyDataId: "",
  sort: 5,
};
export default {
  mixins: [form(defaultForm)],
  data() {
    return {
      // 初始值：hideUpload: false,
      picPath: "",
      hideUpload: false,
      dialogImageUrl: "",
      dialogVisible: true,
      disabled: false,
      imageUrl: "",
      // 表单校验
      rules: {
        picPath: [
          { required: true, message: "请上传轮播图片", trigger: "blur" },
        ],
        title: [
          { required: true, message: "请输入轮播图标题", trigger: "blur" },
        ],
        studyDataId: [{ type: "number", message: "学习资料ID必须为数字" }],
        sort: [{ required: true, message: "请规定图片位序", trigger: "blur" }],
      },
    };
  },
  mounted() {
    // console.log(this, "bnm");
    // console.log(this.dialogImageUrl, "qwe");
    // console.log(JSON.stringify(this.crud.data), "111");
  },
  methods: {
    uploadFile(params) {
      this.form.picPath = URL.createObjectURL(params.file);
      this.$message({
        message: "正在上传图片，请勿离开",
        type: "warning",
      });
      upload("/api/EToolFile", params.file).then((res) => {
        if (res.status === 200) {
          this.form.picPath = res.data.data.name;
          this.$message({
            message: "恭喜你，图片上传成功",
            type: "success",
          });
        } else {
          this.$message.error("文件上传失败");
        }
      });
    },
    handleAvatarChange(file) {
      this.$refs.upload.clearFiles();
    },
    beforeAvatarUpload(file) {
      const isLt2M = file.size / 1024 / 1024 < 1;
      if (!isLt2M) {
        this.$message.error("图片大小不能超过1MB!");
        return false;
      }
      // else {
      //   const isSize = new Promise((resolve, reject) => {
      //     const width = 320;
      //     const height = 180;
      //     const _URL = window.URL || window.webkitURL;
      //     const img = new Image();
      //     img.onload = () => {
      //       const valid =
      //         img.width >= width &&
      //         img.height >= height &&
      //         img.width === img.height;
      //       valid ? resolve() : reject();
      //     };
      //     img.src = _URL.createObjectURL(file);
      //   }).then(
      //     () => {
      //       return file;
      //     },
      //     () => {
      //       this.$message.error(
      //         "图片尺寸限制最小为355 x 150,比例为16：9!"
      //       );
      //       return Promise.reject();
      //     }
      //   );
      //   return isSize;
      // }
    },
  },
};
</script>

<style scoped>
.el-dialog__header {
  text-align: center;
}
.avatar-uploader {
  border-radius: 6px;
  text-align: center;
  cursor: pointer;
  width: 110px;
  height: 110px;
  border: 1px dashed #d9d9d9;
  cursor: pointer;
}
.avatar-uploader:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 24px;
  color: #8c939d;
  width: 70px;
  height:70px;
  line-height: 80px;
  text-align: center;

}
.avatar {
  width: 110px;
  height: 110px;
}
</style>