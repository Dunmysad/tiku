<template>
  <el-dialog
    append-to-body
    :close-on-click-modal="false"
    :before-close="crud.cancelCU"
    :visible="crud.status.cu > 0"
    :title="crud.status.title"
    width="720px"
  >
    <el-form
      ref="form"
      :model="form"
      size="small"
      label-width="80px"
      :inline="true"
      :rules="rules"
    >
      <el-form-item label="分类名称" prop="name">
        <el-input v-model="form.name" style="width: 240px" />
      </el-form-item>
      <el-form-item label="分类简称" prop="forShort">
        <el-input v-model="form.forShort" style="width: 240px" />
      </el-form-item>
      <el-form-item label="分类代码" prop="rooterPath">
        <el-input v-model="form.rooterPath" style="width: 240px" />
      </el-form-item>
      <el-form-item label="分类排序" prop="seqNum">
        <el-input-number
          v-model.number="form.seqNum"
          :min="0"
          :max="999"
          controls-position="right"
          style="width: 240px"
        />
      </el-form-item>
      <el-form-item label="分类图片" prop="picPath" style="width: 320px">
        <el-upload
          ref="upload"
          v-model="form.picPath"
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
     <!-- {{$store.state.settings.imageUrl}} -->
      <el-form-item label="题库图标" prop="repositoryPath" style="width: 290px">   
        <el-upload
          ref="upload"
          v-model="form.repositoryPath"
          class="avatar-uploader"
          action="/api/EToolFile"
          :show-file-list="false"
          :http-request="uploadFileE"
          :on-change="handleAvatarChange"
          :before-upload="beforeAvatarUpload"
        >   
          <img
            v-if="form.repositoryPath"
            :src="$store.state.settings.imageUrl + form.repositoryPath"
            class="avatar"
          />
          <i v-else class="el-icon-plus avatar-uploader-icon" />
          <div class="uploadPicture">点击上传</div>
        </el-upload>
      </el-form-item>
      <el-form-item label="显示状态" prop="isShow">
        <el-switch
          v-model="form.isShow"
          active-color="#409EFF"
          inactive-color="#F56C6C"
        />
      </el-form-item>
    </el-form>
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
  id: '',
  name: "",
  forShort: "",
  rooterPath: "",
  repositoryPath: "",
  seqNum: 5,
  picPath: "",
  isShow: true,
};
export default {
  mixins: [form(defaultForm)],

  data() {
    return {
      picPath: "",
      repositoryPath: "",
      file: null,
      hideUpload: false,
      dialogImageUrl: "",
      dialogVisible: false,
      disabled: false,
      rules: {
        name: [{ required: true, message: "请输入分类名称", trigger: "blur" }],
        forShort: [
          { required: true, message: "请输入分类简称", trigger: "blur" },
        ],
        rooterPath: [
          { required: true, message: "请输入分类代码", trigger: "blur" },
          {
            required: true,
            pattern: /^[a-z]*$/,
            message: "分类代码只能是小写英文字母",
            trigger: "blur",
          },
        ],
        seqNum: [
          { required: true, message: "请规定分类位序", trigger: "blur" },
        ],
        picPath: [
          { required: true, message: "请上传分类图片", trigger: "blur" },
        ],
        repositoryPath: [
          { required: true, message: "请上传题库图标", trigger: "blur" },
        ],
      },
    };
  },
  methods: {
    // 分类图片
    uploadFile(file) {
      console.log("上传");
      this.form.picPath = URL.createObjectURL(file.file);
      this.$message({
        message: "正在上传图片，请勿离开",
        type: "warning",
      });
      upload("/api/EToolFile", file.file).then((res) => {
        if (res.status === 200) {
          this.form.picPath = res.data.data.name;
          this.$message({
            message: "恭喜你，图片上传成功",
            type: "success",
          });
        }else {
          this.$message.error("文件上传失败");
        }
      });
    },
    // 题库图标
    uploadFileE(file) {
      console.log("上传");
      this.form.repositoryPath = URL.createObjectURL(file.file);
      this.$message({
        message: "正在上传图片，请勿离开",
        type: "warning",
      });
      upload("/api/EToolFile", file.file).then((res) => {
        if (res.status === 200) {
          this.form.repositoryPath = res.data.data.name;
          this.$message({
            message: "恭喜你，图片上传成功",
            type: "success",
          });
        }else {
          this.$message.error("文件上传失败");
        }
      });
    },
    handleAvatarChange(file){
      console.log("改变");
      this.$refs.upload.clearFiles();
    },
    // 上传图片之前的判断
    beforeAvatarUpload(file) {
      const isLt2M = file.size / 1024 / 1024 < 1;
      if (!isLt2M) {
        this.$message.error(
          "图片尺寸限制最小为200 x 200,大小不能超过 1MB,比例为1：1!"
        );
        return false;
      } else {
        const isSize = new Promise((resolve, reject) => {
          const width = 200;
          const height = 200;
          const _URL = window.URL || window.webkitURL;
          const img = new Image();
          img.onload = () => {
            const valid =
              img.width >= width &&
              img.height >= height &&
              img.width === img.height;
            valid ? resolve() : reject();
          };
          img.src = _URL.createObjectURL(file);
        }).then(
          () => {
            return file;
          },
          () => {
            this.$message.error(
              "图片尺寸限制最小为200 x 200,大小不能超过 2MB,比例为1：1!"
            );
            return Promise.reject();
          }
        );
        return isSize;
      }
    },
  },
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
::v-deep .el-input-number .el-input__inner {
  text-align: left;
}
</style>

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
