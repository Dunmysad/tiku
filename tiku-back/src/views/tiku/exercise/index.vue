<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <eHeader :permission="permission" />
      <el-button
        size="mini"
        icon="el-icon-upload"
        type="warning"
        style="float: left; margin: 3px 6px 0px 0px"
        @click="dialogExport = true"
        >题目批量导入</el-button
      >
      <crudOperation :permission="permission" />
      <el-dialog
        title="批量导入题目"
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
          action="/api/eRepository/import"
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
        <span
          >Excel文件中数据填写有误，请下载错误报告根据提示修改后再次导入!</span
        >
        <el-button
          type="danger"
          size="mini"
          plain
          @click="errorFile"
          icon="el-icon-upload"
          style="margin: 10px 150px"
          >点击下载错误报告</el-button
        >
      </el-dialog>
    </div>
    <!--表格渲染-->
    <el-table
      ref="table"
      v-loading="crud.loading"
      border
      :data="crud.data"
      style="width: 100%"
      @selection-change="crud.selectionChangeHandler"
    >
      <!-- {{crud.page.page}} -->
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" width="55">
        <template slot-scope="scope">
          {{ (crud.page.page - 1) * 10 + scope.$index + 1 || "--" }}
          <!-- {{scope.$index+1}}  -->
        </template>
      </el-table-column>
      <el-table-column prop="quesContent" label="题干" align="center">
        <template slot-scope="scope">
          <span v-html="scope.row.quesContent"></span>
        </template>
      </el-table-column>
      <el-table-column prop="quesType" label="题型" align="center">
        <template slot-scope="scope">
          <span v-if="scope.row.quesType === 1">单选题</span>
          <span v-else-if="scope.row.quesType === 2">多选题</span>
          <span v-else-if="scope.row.quesType === 3">判断题</span>
          <span v-else>--</span>
        </template>
      </el-table-column>
      <el-table-column prop="options" label="选项" align="center">
        <template slot-scope="scope">
          {{ scope.row.options || "--" }}
        </template>
      </el-table-column>
      <el-table-column prop="answer" label="参考答案" align="center">
        <template slot-scope="scope">
          {{ scope.row.answer || "--" }}
        </template>
      </el-table-column>
      <el-table-column prop="name" label="题目类别" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.name || "--" }}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="createTime"
        label="出题时间"
        align="center"
        width="150px"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) || "--" }}</span>
        </template>
      </el-table-column>
      <!--   编辑与删除   -->
      <el-table-column
        v-permission="['admin', 'repository:edit', 'repository:del']"
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
            @click="
              ccEdit((crud.page.page - 1) * 10 + scope.$index + 1, scope.row)
            "
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
// import { getToken } from "@/utils/auth";
import crudExercise from "@/api/tiku/exercise";
import eHeader from "./module/header";
import eForm from "./module/form";
import CRUD, { presenter } from "@crud/crud";
import crudOperation from "@crud/CRUD.operation";
import pagination from "@crud/Pagination";
import udOperation from "@crud/UD.operation";
import { upload } from "@/utils/upload";
import { downloadFile } from "@/utils/index";
import { download } from "@/api/data";

export default {
  name: "Exercise",
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
      title: "题目",
      url: "/api/eRepository",
      // sort: [
      //   { column: "eSafeType_sort", asc: "true" },
      //   { column: "id", asc: "false" },
      // ],
      crudMethod: { ...crudExercise },
    });
  },
  mixins: [presenter()],
  data() {
    return {
      exerciseList: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10],
      // 批量导入弹窗
      dialogExport: false,
      // 错误报告弹窗
      dialogImport: false,
      // 下载批量导入模板连接
      uploadUrl: "",
      // 下载错误报告连接
      errorUrl: "",
      // 拼接token
      headToken: { Authorization: "" },
      permission: {
        add: ["admin", "repository:add"],
        edit: ["admin", "repository:edit"],
        del: ["admin", "repository:del"],
      },
      index: "",
    };
  },

  mounted() {
    // 批量导入处拼接taken
    // var token = getToken();
    // this.headToken.Authorization = token;
  },
  methods: {
    // 刷新之后的操作
    [CRUD.HOOK.afterRefresh]() {
      console.log(this.crud.data, "当前页数据");
      this.crud.data.forEach((element) => {
        // console.log(element,"222")
        let temOptions = element.options;
        let temAnswer = element.answer;
        const tem = [];
        // 分割字符串
        temOptions = element.options.split("|");

        // 主页---options
        // console.log(element.quesType, '1111')
        const tempStr = ["A：", "B：", "C：", "D：", "E：", "F：", "G："];
        const tmp = [];
        if (element.quesType == 1 || element.quesType == 2) {
          for (let i = 0; i < temOptions.length; i++) {
            tmp[i] = tempStr[i] + temOptions[i];
            // tmp+=tempStr[i].toString();
          }
          element.options = tmp.toString();
        } else {
          element.options = "A：正确,B：错误";
          // 页面--判断题答案显示
          if (element.answer == "Y") {
            element.answer = "A";
          } else {
            element.answer = "B";
          }
        }

        // 设置答案的长度等于选项的长度
        tem.length = temOptions.length;
        // 判断每一个选项为true还是false（存在即为true，不存在即为false）
        if (element.quesType !== 3) {
          temAnswer = element.answer.split("|");
          for (const item of temAnswer) {
            switch (item) {
              case "A":
                tem[0] = true;
                break;
              case "B":
                tem[1] = true;
                break;
              case "C":
                tem[2] = true;
                break;
              case "D":
                tem[3] = true;
                break;
              case "E":
                tem[4] = true;
                break;
              case "F":
                tem[5] = true;
                break;
              case "G":
                tem[6] = true;
                break;
            }
          }
        } else {
          for (const item of temAnswer) {
            // tem.length=2
            switch (item) {
              case "Y":
                tem[0] = true;
                break;
              case "N":
                tem[1] = true;
                break;
            }
          }
        }
        // console.log(element.options,"3333")
        element["temOptions"] = temOptions;
        element["temAnswer"] = tem;
        // console.log(element,"1111111")
      });
    },
    // 删除
    ccEdit(index, row) {
      // console.log(index);
      let that = this;
      let params = [row.id];
      this.$confirm(`确认删除序号为 ` + index + " 的相关数据吗?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          crudExercise.del(params).then((res) => {
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
      // 下载批量导入模板连接
      this.uploadUrl = "/api/eRepository/downloadTemplate";
      download(this.uploadUrl, null).then((res) => {
        downloadFile(res, "exercise", "xlsx");
      });
    },
    // 错误报告下载
    errorFile() {
      this.dialogImport = false;
      // console.log(this.errorUrl);
      download(this.errorUrl, null).then((res) => {
        downloadFile(res, "exerciseError", "xlsx");
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
      const url = "/api/eRepository/import"; // 上传地址
      console.log(param.file, "param.file");
      // this.$alert("导入用户需要时间，请耐心等待!");
      upload(url, param.file).then((res) => {
        // console.log(res, "接口调用res---------------");
        let data = res.data;
        if (data.code === 0) {
          let that = this;
          this.$alert("导入题目需要时间，请耐心等待!", "提示", {
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
          this.$alert("导入题目需要时间，请耐心等待!", "提示", {
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
    },
  },
};
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
::v-deep .el-input-number .el-input__inner {
  text-align: left;
}
::v-deep img {
  width: 50px;
  height: 50px;
}
.upload-demo {
  display: inline-block;
}
</style>
