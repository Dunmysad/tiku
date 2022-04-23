<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <div v-if="crud.props.searchToggle">
        <el-form :inline="true" class="demo-form-inline">
          <!-- 搜索 -->
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
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
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
        <el-form-item label="类型" prop="safeType">
          <el-select v-model="form.safeType" placeholder="请选择类型">
            <el-option
              v-for="item in selectSafeType"
              :key="item.id"
              :value="item.id"
              :label="item.name"
            />
            <el-option key="0" :value="0" label="其他类型" />
          </el-select>
        </el-form-item>
        <el-form-item ref="image" label="案例封面" prop="picPath">
          <el-upload
            ref="upload"
            v-model="form.picPath"
            class="avatar-uploader"
            action="/api/EToolFile"
            :show-file-list="false"
            :http-request="UploadFile"
            :on-change="handleAvatarChange"
            :before-upload="beforeAvatarUpload"
          >
            <img
              v-if="form.picPath"
              :src="$store.state.settings.imageUrl + form.picPath"
              class="avatar"
            >
            <i v-else class="el-icon-plus avatar-uploader-icon" />
            <div class="uploadPicture">点击上传</div>
          </el-upload>
        </el-form-item>
        <el-form-item label="案例内容" prop="content">
          <template>
            <div id="app">
              <vue-editor
                v-model="form.content"
                use-custom-image-handler
                @image-added="handleImageAdded"
              />
            </div>
          </template>
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
    <!--表格渲染 -->
    <el-table
      ref="table"
      v-loading="crud.loading"
      :data="crud.data"
      :cell-style="rowStyle"
      border
      lazy
      :load="getCaseDatas"
      :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
      row-key="id"
      @row-click="handle"
      @select="crud.selectChange"
      @select-all="crud.selectAllChange"
      @selection-change="crud.selectionChangeHandler"
    >
      <el-table-column type="selection" width="55" />
      <el-table-column label="标题" prop="title">
        <template slot-scope="scope">
          {{ scope.row.title || "--" }}
        </template>
      </el-table-column>
      <el-table-column label="关键词" prop="keywords" empty-text="--">
        <template slot-scope="scope">
          {{ scope.row.keywords || "--" }}
        </template>
      </el-table-column>
      <el-table-column label="类型" prop="safeName">
        <template slot-scope="scope">
          {{ scope.row.safeName || "其他类型" }}
        </template>
      </el-table-column>
      <el-table-column label="案例封面" prop="picPath">
        <template slot-scope="scope">
          <img
            referrerPolicy="no-referrer"
            style="width: 100px; height: 100px"
            :src="$store.state.settings.imageUrl + scope.row.picPath"
          >
        </template>
      </el-table-column>
      <el-table-column label="点赞数" prop="tags">
        <template slot-scope="scope">
          {{ scope.row.tags || "0" }}
        </template>
      </el-table-column>
      <el-table-column
        v-permission="['admin', 'case:edit', 'case:del']"
        label="操作"
        align="right"
        width="200px"
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
            v-permission="['admin', 'case:edit', 'case:del']"
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
  safeType: null,
  picPath: null,
  content: null,
  tags: null
}
export default {
  name: 'CaseManage',
  components: {
    VueEditor,
    Treeselect,
    crudOperation,
    pagination,
    rrOperation,
    udOperation,
    DateRangePicker
  },
  cruds() {
    return CRUD({
      title: '案例',
      sort: [{ column: 'create_time', asc: 'true' }],
      url: 'api/eSafeCase',
      crudMethod: { ...crudCase }
    })
  },
  mixins: [presenter(), header(), form(defaultForm), crud()],
  data() {
    return {
      picUrl: null,
      editorImg: null,
      editorUrl: null,
      safeType: null,
      selectSafeType: [],
      dialogTableVisible: false,
      textarea: '',
      formInline: {
        user: '',
        region: ''
      },
      cases: [],
      rules: {
        title: [{ required: true, message: '请输入标题名称', trigger: 'blur' }],
        keywords: [
          {
            required: true,
            message: '请输入关键词名称',
            trigger: 'blur'
          }
        ],
        safeType: [
          { required: true, message: '请选择类型', trigger: 'change' }
        ],
        picPath: [
          { required: true, message: '请上传案例封面', trigger: 'blur' }
        ],
        content: [
          { required: true, message: '请输入案例内容', trigger: 'blur' }
        ]
      },
      permission: {
        add: ['admin', 'case:add'],
        edit: ['admin', 'case:edit'],
        del: ['admin', 'case:del']
      }
    }
  },
  created() {
    this.safeSelect()
    console.log('created被调用了', this.crud)
  },
  methods: {
    rowStyle() {
      return 'text-align:center'
    },
    handleImageAdded(file, Editor, cursorLocation, resetUploader) {
      upload('/api/EToolFile', file).then((res) => {
        if (res.status === 200) {
          this.editorUrl = defaultSettings.imageUrl + res.data.data.name
          Editor.insertEmbed(cursorLocation, 'image', this.editorUrl)
          resetUploader()
        }
      })
    },
    handleAvatarChange(file) {
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
      this.form.picPath = URL.createObjectURL(params.file)
      this.$message({
        message: '请勿离开，正在上传',
        type: 'warning'
      })
      upload('/api/EToolFile', params.file).then((res) => {
        this.form.picPath = res.data.data.name
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
    // 选择案例
    safeSelect() {
      crudCase.getSafeType().then((res) => {
        this.selectSafeType = res.data
      })
    },
    // 点击查看按钮跳转
    handle(row) {},
    view(row) {
      this.$router.push({
        name: 'view',
        query: {
          caseID: row.id,
          basicForm: JSON.stringify(row)
        }
      })
    },
    getCaseDatas(tree, treeNode, resolve) {
      const params = { pid: tree.id }
      setTimeout(() => {
        crudCase.getCases(params).then((res) => {
          console.log(res)
          resolve(res.data.pages === undefined ? res.data : res.data.records)
        })
      }, 100)
    },
    // 删除
    open(row) {
      // let id = null,
      const that = this
      const params = [row.id]
      console.log(params, '________row')
      this.$confirm(`确认删除选中数据?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          crudCase.del(params).then((res) => {
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
.el-table .cell {
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
  height: 70px;
  line-height: 80px;
  text-align: center;
}
.avatar {
  width: 110px;
  height: 110px;
}
</style>

