<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <eHeader :permission="permission" />
      <el-button
        v-permission="permission.imp"
        size="mini"
        icon="el-icon-upload"
        type="warning"
        style="  
          float:left;
          margin: 3px 6px 0px 0px;
        "
        @click="dialogExport = true"
        >入学新生信息批量导入</el-button
      >
      <crudOperation :permission="permission" /> 
      <el-dialog
        title="批量导入新生信息"
        :visible.sync="dialogExport"
        width="670px"
        center
      >
        <i class="el-icon-position" style="margin-left: 30px" />
        <span style="color: red"
          >批量导入功能需严格按照模版格式录入，否则系统无法识别。</span
        >
        <el-button
          type="warning"
          size="mini"
          plain
          @click="getFile"
          icon="el-icon-upload"
          >下载批量导入模板</el-button
        >
        <el-upload
          ref="upload"
          class="upload-demo"
          drag
          action="/api/eStudent/import"
          :before-upload="beforeUpload"
          :http-request="uploadHttpRequest"
          style="margin: 20px 120px"
          :multiple="false"
        >
          <i class="el-icon-upload"></i>
          <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
          <div class="el-upload__tip" slot="tip" style="margin-left: 25px">
            上传文件只能是xls/xlsx格式！且文件大小不超过10M！
          </div>
        </el-upload>
      </el-dialog>
      <!-- 错误模板弹窗 -->
      <el-dialog
        title="提示"
        :visible.sync="dialogImport"
        width="500px"
        center
        top="200px"
      >
        <span >Excel文件中数据填写有误，请下载错误报告根据提示修改后再次导入!</span>
        <el-button
          type="danger"
          size="mini"
          plain
          @click="errorFile"
          icon="el-icon-upload"
          style="margin:10px 150px"
          >点击下载错误报告</el-button
        >
      </el-dialog>
    </div>
    <!--表格渲染-->
    <el-table
      ref="table"
      border
      v-loading="crud.loading"
      :data="crud.data"
      style="width: 100%"
      @selection-change="crud.selectionChangeHandler"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column prop="name" label="姓名" align="center" >
        <template slot-scope="scope">
          {{ scope.row.name || "--" }}
        </template>
      </el-table-column>
      <el-table-column prop="sex" label="性别" width="55" align="center">
        <template slot-scope="scope">
          <span v-if="scope.row.sex === false">男</span>
          <span v-else-if="scope.row.sex === true">女</span>
          <span v-else>--</span>
        </template>
      </el-table-column>
      <el-table-column prop="admissionNum" label="准考证号" width="130" align="center">
      <template slot-scope="scope">
          {{ scope.row.admissionNum || "--" }}
        </template>
      </el-table-column>
      <el-table-column prop="idCard" label="身份证号" align="center" width="190">
        <template slot-scope="scope">
          {{ scope.row.idCard || "--" }}
        </template>
      </el-table-column>
      <el-table-column prop="studentNum" label="学号" align="center" width="140">
        <template slot-scope="scope">
          {{ scope.row.studentNum || "--" }}
        </template>
      </el-table-column>
      <el-table-column prop="universityId" label="学校名称" align="center" width="170">
        <template slot-scope="scope">
          <span>{{ scope.row.universityName || "--" }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="college" label="学院名称" align="center" width="160">
        <template slot-scope="scope">
          {{ scope.row.college || "--" }}
        </template>
      </el-table-column>
      <el-table-column prop="major" label="专业" align="center" width="180">
        <template slot-scope="scope">
          {{ scope.row.major || "--" }}
        </template>
      </el-table-column>
      <el-table-column prop="enrollmentYear" label="入学年份" align="center" width="120">
        <template slot-scope="scope">
          {{ scope.row.enrollmentYear || "--" }}
        </template>
      </el-table-column>

      <!--   编辑与删除   -->
      <el-table-column
        v-permission="['admin', 'student:edit', 'student:del']"
        label="操作"
        width="130px"
        align="center"
        fixed="right"
      >
        <!-- <template slot-scope="scope">
          <udOperation :data="scope.row" :permission="permission" />
        </template> -->
        <template slot-scope="scope">
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
            @click="ccEdit(scope.row)"
            style="color: red; margin-left: 3px"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <!--分页组件-->
    <pagination />
    <!--表单渲染-->
    <eForm />
  </div>
</template>

<script>
import { getToken } from "@/utils/auth";
import { downloadFile } from "@/utils/index";
import { initData, download } from "@/api/data";
import crudStudents from "@/api/tiku/students";
import eHeader from "./module/header";
import eForm from "./module/form";
import CRUD, { presenter, refresh } from "@crud/crud";
import crudOperation from "@crud/CRUD.operation";
import pagination from "@crud/Pagination";
import udOperation from "@crud/UD.operation";
import { upload } from "@/utils/upload";


export default {
  name: "Students",
  components: {
    eHeader,
    eForm,
    crudOperation,
    pagination,
    udOperation,
    upload,
  },
  cruds() {
    return CRUD({
      title: "学生信息",
      url: "/api/eStudent",
      // sort: [{ 'column': 'job_sort', 'asc': 'true' }, { 'column': 'id', 'asc': 'false' }],
      crudMethod: { ...crudStudents },
    });
  },
  mixins: [presenter()],
  data() {
    return {
      // 批量导入弹窗
      dialogExport: false,
      dialogImport:false,
      // 下载批量导入模板连接
      uploadUrl: "",
      errorUrl:'',
      // 拼接token
      headToken: { Authorization: "" },
      permission: {
        add: ["admin", "student:add"],
        edit: ["admin", "student:edit"],
        del: ["admin", "student:del"],
        imp:["admin","student:imp"]
      },
    };
  },
  mounted() {
    // 下载批量导入模板连接
    this.uploadUrl = "/api/eStudent/downloadTemplate";
    // 批量导入处拼接taken
    // var token = getToken();
    // this.headToken.Authorization = token;
  },
  methods: {
    // 删除
    ccEdit(row) { 
      // let id = null,
      let that = this;
      let params = [row.id];
      this.$confirm('确认删除学生 < '+row.name+' >，以及相关数据吗？', "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          crudStudents.del(params).then((res) => {
            if (res.code === 0) {
              this.$message({
                type: "success",
                message: "删除成功!",
              });
              // 删除成功后，刷新表格数据
              that.crud.refresh();
            } else {
              this.$message.error("删除失败，请重试");
            }
          });
        })
        .catch(() => {});
    },
    // 下载导入模板
    getFile() {
      download(this.uploadUrl, null).then((res) => {
        downloadFile(res, "students ", "xlsx");
      });
    },
    // 错误报告下载
    errorFile() {
      this.dialogImport = false
      download(this.errorUrl, null).then((res) => {
        downloadFile(res, "studentsError", "xlsx");
      });
    },

    // 上传文件之前的钩子，参数为上传的文件，若返回 false 或者返回 Promise 且被 reject，则停止上传。
    beforeUpload(file) {
      // 文件类型
      const isType = file.type === "application/vnd.ms-excel";
      const isTypeComputer =
        file.type ===
        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
      const fileType = isType || isTypeComputer;
      if (!fileType) {
        this.$message.error("上传文件只能是xls/xlsx格式！");
      }
      // 文件大小限制为10M
      const fileLimit = file.size / 1024 / 1024 < 10;
      if (!fileLimit) {
        this.$message.error("上传文件大小不超过10M！");
      }
      return fileType && fileLimit;
    },
    // 自定义上传方法,覆盖默认的上传行为，可以自定义上传的实现
    uploadHttpRequest(param) {
      const formData = new FormData(); // FormData对象，添加参数只能通过append('key', value)的形式添加
      formData.append("file", param.file); // 添加文件对象
      const url = "/api/eStudent/import"; // 上传地址
      console.log(param.file,"param.file")
      upload(url, param.file).then((res) => {
        // console.log(res, "接口调用res---------------");
        let data = res.data;
        if (data.code === 0) {
          let that = this;
          this.$alert("导入用户需要时间，请耐心等待!", "提示", {
            confirmButtonText: "确定",
            callback: () => {
              this.$message({
                message: "上传成功!",
                type: "success",
              });
              // 清空上传列表
              this.$refs.upload.clearFiles();
              // 关闭弹窗
              this.dialogExport = false;
              // 刷新页面
              that.crud.refresh();
            },
          });
        } else {
          this.$alert("导入用户需要时间，请耐心等待!", "提示", {
            confirmButtonText: "确定",
            callback: () => {
              // 关闭下载模板弹窗
              this.dialogExport = false;
              // 打开错误报告弹窗
              this.dialogImport = true;
              // 下载错误模板链接
              this.errorUrl = "/api/file/download/" + data.message;
              // 清空上传列表
              this.$refs.upload.clearFiles();
            },
          });
        }
      });
      // this.$alert("导入用户需要时间，请耐心等待!");
      // upload(url, param.file).then((res) => {
      //   this.$alert("导入用户需要时间，请耐心等待!");
      //   console.log(res,"接口调用res---------------")
      //   let data = res.data;
      //   if (data.code === 0) {
      //     console.log("data.code");
      //     let that = this
      //     this.$message({
      //       message:"上传成功！",
      //       type: "success"
      //     })
      //     // 清空上传列表
      //     this.$refs.upload.clearFiles();
      //     this.dialogExport = false;  
      //     that.crud.refresh();
      //   } else {
      //     this.dialogImport = true,
      //     // console.log(data.message,"data.message")
      //     // 下载错误模板链接
      //     this.errorUrl ="/api/file/download/" + data.message;
      //     // 清空上传列表
      //     this.$refs.upload.clearFiles();
      //     this.dialogExport = false;
      //   }
      // });
    },
  },
};
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
::v-deep .el-input-number .el-input__inner {
  text-align: left;
}
.upload-demo {
  display: inline-block;
}
</style>
