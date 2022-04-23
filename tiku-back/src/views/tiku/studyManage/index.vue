<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <div v-if="crud.props.searchToggle">
        <el-form :inline="true" :model="form" class="demo-form-inline">
          <!-- 搜索 -->
          <el-form-item label="关键字">
            <el-input
              v-model="query.keywords"
              clearable
              size="small"
              placeholder="请输入关键字"
              style="width: 200px"
              class="filter-item"
              @keyup.enter.native="crud.toQuery"
            />
          </el-form-item>
          <el-form-item label="标题">
            <el-input
              v-model="query.title"
              clearable
              size="small"
              placeholder="请输入标题"
              style="width: 200px"
              class="filter-item"
              @keyup.enter.native="crud.toQuery"
            />
          </el-form-item>
          <el-form-item label="案例类别">
            <el-select
              v-model="query.safeTypeId"
              placeholder="请选择案例类别"
              clearable
              @keyup.enter.native="crud.toQuery"
            >
              <el-option
                v-for="item in selectSafeType"
                :key="item.id"
                :value="item.id"
                :label="item.name"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="资源类型">
            <el-select
              v-model="query.dataType"
              placeholder="请选择资料类型"
              clearable
              @keyup.enter.native="crud.toQuery"
            >
              <el-option
                v-for="item in option"
                :key="item.value"
                :value="item.value"
                :label="item.text"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="是否推荐">
            <el-select
              v-model="query.isIntroduce"
              placeholder="请选择资料类型"
              clearable
              @keyup.enter.native="crud.toQuery"
            >
              <el-option
                v-for="item in options"
                :key="item.value"
                :value="item.value"
                :label="item.text"
              />
            </el-select>
          </el-form-item>
          <rrOperation />
        </el-form>
      </div>
      <crudOperation :permission="permission" />
    </div>
    <!--表单组件-->
    <el-dialog
      append-to-body
      :close-on-click-modal="false"
      :before-close="crud.cancelCU"
      :visible.sync="crud.status.cu > 0"
      :title="crud.status.title"
      width="1000px"
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="106px">
        <el-form-item label="标题" prop="title">
          <el-input
            v-model="form.title"
            style="width: 200px"
            placeholder="请输入标题"
          />
        </el-form-item>
        <el-form-item label="关键词" prop="keywords">
          <el-input
            v-model="form.keywords"
            style="width: 200px"
            placeholder="请输入关键词,需用分号隔开"
          />
        </el-form-item>
        <el-form-item label="类型" prop="safeTypeId">
          <el-select
            v-model="form.safeTypeId"
            style="width: 200px"
            placeholder="请选择类型"
          >
            <el-option
              v-for="item in selectSafeType"
              :key="item.id"
              :value="item.id"
              :label="item.name"
            />
            <el-option key="0" :value="0" label="其他类型" />
          </el-select>
        </el-form-item>
        <el-form-item label="资源类型" prop="dataType">
          <el-select
            v-model="form.dataType"
            style="width: 200px"
            placeholder="请选择资源类型"
            @change="selectChanged"
          >
            <el-option
              v-for="item in option"
              :key="item.value"
              :value="item.value"
              :label="item.text"
            />
          </el-select>
        </el-form-item>
        <el-form-item
          label="学习资料图片"
          prop="coverPicLink"
          style="width: 306px"
          :required="imageTo"
        >
          <el-upload
            ref="upload"
            v-model="form.coverPicLink"
            class="avatar-uploader"
            action="/api/EToolFile"
            :show-file-list="false"
            :http-request="UploadFile"
            :on-change="handleAvatarChange"
            :before-upload="beforeAvatarUpload"
          >
            <img
              v-if="form.coverPicLink"
              referrerPolicy="no-referrer"
              :src="$store.state.settings.imageUrl + form.coverPicLink"
              class="avatar"
            >
            <i v-else class="el-icon-plus avatar-uploader-icon" />
            <div class="uploadPicture">点击上传</div>
          </el-upload>
        </el-form-item>
        <el-form-item label="上传视频" prop="videoPath" :required="videoTo">
          <el-upload
            ref="video"
            v-model="form.videoPath"
            class="upload-demo"
            drag
            action="/api/EToolFile"
            :multiple="false"
            :before-upload="beforeUploadVideo"
            :http-request="UploadVideo"
          >
            <i class="el-icon-upload" />
            <div class="el-upload__text">
              将文件拖到此处，或<em>点击上传</em>
            </div>
          </el-upload>
          <el-progress
            v-show="progressFlag"
            :percentage="loadProgress"
          />
        </el-form-item>
        <el-form-item label="学习资料内容" prop="content" :required="isHaveTo">
          <template>
            <div id="app">
              <VueEditor
                v-model="form.content"
                use-custom-image-handler
                @image-added="handleImageAdded"
              />
            </div>
          </template>
        </el-form-item>
        <el-form-item label="是否推荐" prop="isIntroduce">
          <el-switch
            v-model="form.isIntroduce"
            active-color="#409EFF"
            inactive-color="#F56C6C"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="text" @click="crud.cancelCU">取消</el-button>
        <el-button
          :loading="crud.status.cu === 2"
          type="primary"
          @click="crud.submitCU"
        >确认</el-button>
      </div>
    </el-dialog>
    <!--表格渲染-->
    <el-table
      ref="table"
      v-loading="crud.loading"
      :data="crud.data"
      :cell-style="rowStyle"
      border
      lazy
      :load="getStudyDatas"
      :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
      row-key="id"
      @row-click="handle"
      @select="crud.selectChange"
      @select-all="crud.selectAllChange"
      @selection-change="crud.selectionChangeHandler"
    >
      <el-table-column type="selection" width="55" />
	  <el-table-column
			label="序号"
	        type="index"
	        width="50">
	      </el-table-column>
      <el-table-column label="学习资料id" prop="id" />
      <el-table-column label="标题" prop="title">
        <template slot-scope="scope">
          {{ scope.row.title || "--" }}
        </template>
      </el-table-column>
      <el-table-column label="关键词" prop="keywords">
        <template slot-scope="scope">
          {{ scope.row.keywords || "--" }}
        </template>
      </el-table-column>
      <el-table-column label="类型" prop="safeTypeId">
        <template slot-scope="scope">
          {{ scope.row.safeTypeId || "其他类型" }}
        </template>
      </el-table-column>
      <el-table-column label="学习资源封面" prop="coverPicLink">
        <template slot-scope="scope">
          <img
            v-if="scope.row.coverPicLink"
            referrerPolicy="no-referrer"
            style="width: 100px; height: 100px"
            :src="$store.state.settings.imageUrl + scope.row.coverPicLink"
          >
          <span v-else>无</span>
        </template>
      </el-table-column>
      <el-table-column label="点赞数" prop="tags">
        <template slot-scope="scope">
          {{ scope.row.tags || "0" }}
        </template>
      </el-table-column>
      <el-table-column prop="isIntroduce" label="是否推荐" align="center">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.isIntroduce"
            active-color="#409EFF"
            inactive-color="#F56C6C"
            @change="change($event, scope.row.id)"
          />
        </template>
      </el-table-column>
      <el-table-column
        v-permission="['admin', 'studyData:edit', 'studyData:del']"
        label="操作"
        align="right"
        width="190px"
        fixed="right"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            fixed="right"
            type="text"
            style="
              width: 44px;
              display: inline-block;
              position: relative;
              left: 15px;
            "
            @click="view(scope.row)"
          >查看</el-button>
          <el-button
            v-if="crud.optShow.edit"
            v-permission="permission.edit"
            size="mini"
            type="text"
            :loading="crud.status.cu === 2"
            @click="crud.toEdit(scope.row)"
          >
            编辑
          </el-button>
          <el-button
            v-if="crud.optShow.del"
            v-permission="permission.del"
            type="text"
            style="color: red; margin-left: 3px"
            @click="open(scope.row)"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!--分页组件-->
    <pagination />
  </div>
</template>

<script>
import crudStudy from '@/api/tiku/study'
import crudCase from '@/api/tiku/case'
import Treeselect from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
import { LOAD_CHILDREN_OPTIONS } from '@riophae/vue-treeselect'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import pagination from '@crud/Pagination'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import DateRangePicker from '@/components/DateRangePicker'
import { upload } from '@/utils/upload'
import { VueEditor } from 'vue2-editor'
import defaultSettings from '@/settings'
const defaultForm = {
  id: null,
  title: null,
  keywords: null,
  safeTypeId: null,
  coverPicLink: null,
  dataType: null,
  content: null,
  tags: null,
  isIntroduce: true,
  videoPath: null
}
export default {
  name: 'StudyManage',
  components: {
    VueEditor,
    pagination,
    Treeselect,
    crudOperation,
    rrOperation,
    udOperation,
    DateRangePicker
  },
  cruds() {
    return CRUD({
      title: '学习资料',
      sort: [{ column: 'create_time', asc: 'true' }],
      url: 'api/eStudyData',
      crudMethod: { ...crudStudy }
    })
  },
  mixins: [presenter(), header(), form(defaultForm), crud()],
  data() {
    const validateContent = (rule, value, callback) => {
      if (this.form.content == null && this.isHaveTo == true) {
        callback(new Error('请输入资料内容'))
      } else {
        callback()
      }
    }
    const validatePic = (rule, value, callback) => {
      if (this.form.coverPicLink == null && this.imageTo == true) {
        callback(new Error('请上传图片'))
      } else {
        callback()
      }
    }
    const validateVideo = (rule, value, callback) => {
      if (
        this.form.videoPath == null &&
        this.videoTo == true &&
        this.imageTo == true
      ) {
        callback(new Error('请上传视频'))
      } else {
        callback()
      }
    }
    return {
      progressFlag: false,
      loadProgress: 0,
      imageTo: false,
      isHaveTo: false,
      videoFlag: false,
      videoTo: false,
      empty: '--',
      option: [
        { value: 0, text: '文字类型' },
        { value: 1, text: '图片类型' },
        { value: 2, text: '视频类型' }
      ],
      options: [
        { value: 0, text: '否' },
        { value: 1, text: '是' }
      ],
      optionss: [
        { value: 0, text: '题库导入' },
        { value: 1, text: '手动添加' }
      ],
      picUrl: null,
      editorImg: null,
      editorUrl: null,
      videoUrl: null,
      selectSafeType: [],
      textarea: '',
      rules: {
        title: [{ required: true, message: '请输入标题名称', trigger: 'blur' }],
        keywords: [
          {
            required: true,
            message: '请输入关键词名称',
            trigger: 'blur'
          }
        ],
        safeTypeId: [
          { required: true, message: '请选择类型', trigger: 'change' }
        ],
        dataType: [
          { required: true, message: '请选择资源类型', trigger: 'change' }
        ],
        content: [{ validator: validateContent }],
        coverPicLink: [{ validator: validatePic }],
        videoPath: [{ validator: validateVideo }]
      },
      permission: {
        add: ['admin', 'studyData:add'],
        edit: ['admin', 'studyData:edit'],
        del: ['admin', 'studyData:del']
      },
      inject: ['reload']
    }
  },
  created() {
    this.safeSelect()
    console.log(this.crud.form, '111')
    console.log(this.crud, '222')
  },
  methods: {
    rowStyle() {
      return 'text-align:center'
    },
    // 富文本图片路径修改
    handleImageAdded(file, Editor, cursorLocation, resetUploader) {
      upload('/api/EToolFile', file).then((res) => {
        if (res.status === 200) {
          this.editorUrl = defaultSettings.imageUrl + res.data.data.name
          Editor.insertEmbed(cursorLocation, 'image', this.editorUrl)
          resetUploader()
        }
      })
    },
    selectChanged(value) {
      if (value == 0) {
        this.isHaveTo = true
        this.imageTo = false
        this.videoTo = false
      } else if (value == 1) {
        this.imageTo = true
        this.isHaveTo = false
        this.videoTo = false
      } else if (value == 2) {
        this.videoTo = true
        this.imageTo = true
        this.isHaveTo = false
      }
    },
    change($event, num) {
      const a = {
        id: num,
        isIntroduce: $event
      }
      crudStudy.changeIntro(a).then((res) => {})
      if ($event == true) {
        this.$message({
          message: '推荐成功',
          type: 'success'
        })
      } else {
        this.$message('取消推荐')
      }
    },
    handle(row) {
      console.log(row)
      if (row.dataType == 0) {
        this.isHaveTo = true
        this.imageTo = false
        this.videoTo = false
      } else if (row.dataType == 1) {
        this.imageTo = true
        this.isHaveTo = false
        this.videoTo = false
      } else if (row.dataType == 2) {
        this.videoTo = true
        this.imageTo = true
        this.isHaveTo = false
      }
    },
    handleAvatarChange(file, fileList) {
      this.$refs.upload.clearFiles()
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg'
      const isJPG2 = file.type === 'image/jpg'
      const isPNG = file.type === 'image/png'
      const isLt5M = file.size / 1024 / 1024 < 5
      if (!isJPG && !isJPG2 && !isPNG) {
        this.$message.warning('只支持jpg或png格式图片')
      }
      if (!isLt5M) {
        this.$message.warning('请上传5MB以内的图片!')
      }
      return (isJPG || isJPG2 || isPNG) && isLt5M
    },
    UploadFile(params) {
      this.form.coverPicLink = URL.createObjectURL(params.file)
      this.$message({
        message: '请勿离开，正在上传',
        type: 'warning'
      })
      upload('/api/EToolFile', params.file).then((res) => {
        this.form.coverPicLink = res.data.data.name
        if (res.data.code == 0) {
          this.$message({
            message: '恭喜你，上传成功',
            type: 'success'
          })
        } else {
          this.$message.error('文件上传失败')
        }
      })
    },
    // 视频上传
    UploadVideo(params) {
      this.form.videoPath = URL.createObjectURL(params.file)
      this.progressFlag = true
      this.loadProgress = 0
      const interval = setInterval(() => {
        if (this.loadProgress >= 99) {
          clearInterval(interval)
          return
        }
        this.loadProgress += 1
      }, 20)
      upload('/api/EToolFile', params.file).then((res) => {
        console.log(res)
        this.form.videoPath = res.data.data.name
        this.progressFlag = false
        this.loadProgress = 100
        this.$message({
          type: 'success',
          message: '上传成功!'
        })
        // 清空上传列表
        this.$refs.video.clearFiles()
      })
    },
    beforeUploadVideo(file) {
      const isLt30M = file.size / 1024 / 1024 < 30
      if (
        [
          'video/mp4',
          'video/ogg',
          'video/flv',
          'video/avi',
          'video/wmv',
          'video/rmvb'
        ].indexOf(file.type) == -1
      ) {
        this.$message.error('请上传正确的视频格式')
        return false
      }
      if (!isLt30M) {
        this.$message.error('上传视频大小不能超过30MB哦!')
        return false
      }
    },
    getStudyDatas(tree, treeNode, resolve) {
      const params = { pid: tree.id }
      setTimeout(() => {
        crudStudy.getStudys(params).then((res) => {
          resolve(res.data.pages === undefined ? res.data : res.data.records)
        })
      }, 100)
    },
    // 选择案例
    safeSelect() {
      crudCase.getSafeType().then((res) => {
        this.selectSafeType = res.data
      })
    },
    // 编辑
    toEdit(row) {},
    // 点击查看按钮跳转
    view(row) {
      this.$router.push({
        name: 'studyView',
        query: {
          studyID: row.id,
          basicForm: JSON.stringify(row)
        }
      })
    },
    // 删除
    open(row) {
      // let id = null,
      const that = this
      const params = [row.id]
      this.$confirm(`确认删除选中数据?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          crudStudy.del(params).then((res) => {
            if (res.code === 0) {
              this.$message({
                type: 'success',
                message: '删除成功!'
              })
              // 删除成功后，刷新表格数据
              that.crud.refresh()
            } else {
              this.$message.error('删除失败，请重试')
            }
          })
        })
        .catch(() => {})
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
::v-deep .vue-treeselect__control,
::v-deep .vue-treeselect__placeholder,
::v-deep .vue-treeselect__single-value {
  height: 30px;
  line-height: 30px;
}
</style>

<style rel="stylesheet/scss" lang="scss" scoped>
::v-deep .el-input-number .el-input__inner {
  text-align: left;
}
</style>

<style rel="stylesheet/scss" lang="scss" scoped>
::v-deep .el-table th.is-leaf,
.el-table td {
  text-align: center;
}
</style>

<style scoped>
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
  height: 70px;
  line-height: 80px;
  text-align: center;
}
.avatar {
  width: 110px;
  height: 110px;
}
</style>
