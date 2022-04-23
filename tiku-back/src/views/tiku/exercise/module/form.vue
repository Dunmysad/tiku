<template>
  <el-dialog
    append-to-body
    :close-on-click-modal="false"
    :before-close="crud.cancelCU"
    :visible="crud.status.cu > 0"
    :title="crud.status.title"
    width="890px"
  >
    <!-- {{ form }} -->
    <el-form
      ref="form"
      :model="form"
      size="small"
      label-width="79px"
      :rules="rules"
    >
      <el-form-item label="题目类别" prop="safeTypeId">
        <el-select
          v-model="form.safeTypeId"
          placeholder=" "
          clearable
          style="width: 230px"
        >
          <el-option
            v-for="item in selectSafeType"
            :key="item.id"
            :value="item.id"
            :label="item.name"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="题型" prop="quesType">
        <el-select
          v-model="form.quesType"
          placeholder=" "
          clearable
          @change="quesTypeChange"
          style="width: 230px"
        >
          <el-option
            v-for="item in selectExerType"
            :key="item.value"
            :value="item.value"
            :label="item.name"
          />
        </el-select>
      </el-form-item>
      <!-- {{form.quesType}} -->
      <el-form-item label="题干" prop="quesContent">
        <!-- <el-input v-model="form.quesContent" style="width: 709px" /> -->
        <template>
          <div id="app">
            <vue-editor
              useCustomImageHandler
              @image-added="handleImageAdded"
              v-model="form.quesContent"
              style="width: 709px"
            ></vue-editor>
          </div>
        </template>
      </el-form-item>

      <!-- 选项和答案的表单 -->
      <el-form
        ref="dynamicValidateForm"
        :model="form"
        label-width="79px"
        class="demo-dynamic"
      >
        <!-- 选项 -->
        <el-form label-width="79px">
          <!-- 选择题 -->
          <el-form-item label="选项" v-if="isShowOption">
            <el-form-item
              v-for="(domain, index) in form.temOptions"
              :key="index"
              :label="options[index]"
              :prop="'domains.' + index + '.value'"
            >
              <!-- {{domain}} -->
              <el-input
                v-if="isShowOption"
                v-model="form.temOptions[index]"
                style="width: 480px"
                type="textarea"
                autosize
              />
              <el-button
                type="text"
                size="mini"
                @click.prevent="removeDomain(domain)"
                >X</el-button
              >
            </el-form-item>
          </el-form-item>
          <!-- 判断题 -->
          <el-form-item label="选项" v-if="isShowJudge">
            <el-form-item label="A">正确</el-form-item>
            <el-form-item label="B">错误</el-form-item>
          </el-form-item>
        </el-form>

        <!-- 新增按钮 -->
        <el-form-item>
          <el-button type="text" v-if="isShowOption" @click="addDomain"
            >新增选项</el-button
          >
        </el-form-item>
        <!-- 答案复选框 -->

        <el-form-item label="答案" v-if="isShowAnswer">
          <!-- {{ form.temAnswer }} -->
          <el-form-item
            v-for="(domain, index) in form.temAnswer"
            :key="index"
            :label="options[index]"
            style="float: left"
          >
            <el-checkbox
              v-model="form.temAnswer[index]"
              @change="
                form.quesType == 1 || form.quesType == 3
                  ? answerChange(index)
                  : ''
              "
            />
            <!-- <el-input type="checkbox" :value="domain"></el-input> -->
          </el-form-item>
        </el-form-item>
      </el-form>
    </el-form>
    <!-- 底部 -->
    <div slot="footer" class="dialog-footer">
      <el-button type="text" @click="crud.cancelCU"> 取消 </el-button>
      <el-button
        :loading="crud.status.cu === 2"
        type="primary"
        @click="submit(crud)"
      >
        确认
      </el-button>
    </div>
  </el-dialog>
</template>

<script>
import crudExercise from "@/api/tiku/exercise";
import CRUD, { form } from "@crud/crud";
import { VueEditor } from "vue2-editor";
import { upload } from "@/utils/upload";
import defaultSettings from "@/settings";

const defaultForm = {
  // name: "",
  safeTypeId: "",
  quesType: "",
  quesContent: "",
  // domain: "",
  options: "",
  answer: "",
  temOptions: [],
  temAnswer: [],
};
export default {
  components: { VueEditor },
  mixins: [form(defaultForm)],
  cruds() {
    return CRUD({
      crudMethod: { ...crudExercise },
    });
  },
  data() {
    return {
      // 消除答案最后的竖线
      newAnswer:'',
      // 富文本
      editorImg: null,
      editorUrl: null,
      options: ["A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K"],
      // 题型选择
      selectExerType: [
        { value: 1, name: "单选题" },
        { value: 2, name: "多选题" },
        { value: 3, name: "判断题" },
      ],
      value: "",
      // 分类选择
      selectSafeType: [],

      isShowOption: false,
      isShowJudge: false,
      isShowAnswer: false,
      // quesType:'',
      rules: {
        safeTypeId: [
          { required: true, message: "请选择题库类别", trigger: "change" },
        ],
        quesType: [
          { required: true, message: "请选择题型", trigger: "change" },
        ],
        quesContent: [
          { required: true, message: "请输入题干", trigger: "blur" },
        ],
        // studyDataId: [
        //   { required: true, message: "请输入学习资料ID", trigger: "blur" },
        // ],
      },
    };
  },
  created() {
    this.safeTypeSelect();
  },
  methods: {
    // 富文本
    handleImageAdded(file, Editor, cursorLocation, resetUploader) {
      // console.log(file,"file")
      upload("/api/EToolFile", file).then((res) => {
        if (res.status === 200) {
          this.editorUrl = defaultSettings.imageUrl + res.data.data.name;
          Editor.insertEmbed(cursorLocation, "image", this.editorUrl);
          resetUploader();
        }
      });
      // upload("/api/EToolFile", file).then((res) => {
      //   if (res.status === 200) {
      //     this.editorImg = res.data.data.name;
      //     this.editorUrl =
      //       process.env.VUE_APP_BASE_API +
      //       "/api/EToolFile/download/picname/" +
      //       this.editorImg;
      //     Editor.insertEmbed(cursorLocation, "image", this.editorUrl);
      //     resetUploader();
      //   }
      // });
    },
    // 提交事件
    submit(crud) {
      // 选项都不为空
      for (let i = 0; i < this.form.temOptions.length; i++) {
        if (this.form.temOptions[i] == "") {
          this.$alert("所有选项不能为空", "提示", {
            confirmButtonText: "确定",
          });
          return;
        }
      }
      // 单选题和判断题答案不为空
      if (this.form.quesType == 1 || this.form.quesType == 3) {
        let flag = true;
        for (let i = 0; i < this.form.temAnswer.length; i++) {
          if (this.form.temAnswer[i] == true) {
            flag = false;
          }
        }
        if (flag) {
          this.$alert("答案不能为空", "提示", {
            confirmButtonText: "确定",
          });
          return;
        }
      } else if (this.form.quesType == 2) {
        // 多选题答案不能少于两个
        let tNum = 0;
        for (let i = 0; i < this.form.temAnswer.length; i++) {
          if (this.form.temAnswer[i] == true) {
            tNum++;
          }
        }
        if (tNum < 2) {
          this.$alert("多选题至少有两个答案", "提示", {
            confirmButtonText: "确定",
          });
          return;
        }
      }
      crud.submitCU();
    },
    //绑定值变化时触发的事件
    answerChange(index) {
      let er = index;
      for (let i = 0; i < this.form.temAnswer.length; i++) {
        this.form.temAnswer[i] = false;
      }
      this.form.temAnswer[er] = true;
    },
    // 清楚已选答案选项
    clear() {
      for (let i = 0; i < this.form.temAnswer.length; i++) {
        this.form.temAnswer[i] = false;
      }
    },

    // 新增前做的操作
    [CRUD.HOOK.beforeToAdd](crud, form) {
      (this.isShowOption = false),
        (this.isShowJudge = false),
        (this.isShowAnswer = false);
      // console.log("要新增了")
    },
    // 打开编辑弹窗前做的操作
    [CRUD.HOOK.beforeToEdit](crud, form) {
      // console.log("打开编辑le");
      // console.log(form.quesType);
      if (this.form.quesType == 1 || this.form.quesType == 2) {
        this.isShowOption = true;
        this.isShowJudge = false;
        this.isShowAnswer = true;
      } else if (this.form.quesType == 3) {
        this.isShowOption = false;
        this.isShowJudge = true;
        this.isShowAnswer = true;
      }
    },
    // 提交编辑或新增前做的操作
    [CRUD.HOOK.afterValidateCU](crud) {
      // console.log(this.form.name,"要新增了");
      // 拼接选择题选项并传给后端
      if (this.form.quesType == 1 || this.form.quesType == 2) {
        this.form.options = this.form.temOptions.join("|");
      } else {
        this.form.options = "Y|N";
      }

      // 拼接答案
      // 选择题答案处理
      if (this.form.quesType == 1) {
        // console.log(this.form.temAnswer.length, "拼接答案啦");
        // 单选题
        for (let i = 0; i < this.form.temAnswer.length; i++) {
          if (this.form.temAnswer[i] == true) {
            this.form.answer = this.options[i];
          }
        }
      } else if (this.form.quesType == 2) {
        // 多选题
        for (let i = 0; i < this.form.temAnswer.length; i++) {
          if (this.form.temAnswer[i] == true) {
            this.form.temAnswer[i] = this.options[i] + "|";
          } else {
            this.form.temAnswer[i] = null;
          }
        }
        this.newAnswer = this.form.temAnswer.join("");
        this.newAnswer= this.newAnswer.slice(0,this.newAnswer.length-1)
        this.form.answer = this.newAnswer;

      } else {
        // 判断题答案的处理
        if (this.form.temAnswer[0] == true) {
          this.form.answer = "Y";
          // console.log(this.form.answer)
        } else {
          this.form.answer = "N";
        }
      }
    },
    // 问题类型的改变
    quesTypeChange() {
      this.clear();
      this.isShowAnswer = true;
      if (this.form.quesType == 1 || this.form.quesType == 2) {
        this.isShowOption = true;
        this.isShowJudge = false;
        this.form.temOptions.length = 2;
        this.form.temAnswer.length = 2;
      } else if (this.form.quesType == 3) {
        this.isShowOption = false;
        this.isShowJudge = true;
        this.form.temAnswer.length = 2;
      }
    },
    // 获取所有分类接口
    safeTypeSelect() {
      crudExercise.getSafeType().then((response) => {
        // console.log(response.data);
        this.selectSafeType = response.data;
      });
    },
    // 选择框获取焦点事件。获取所有分类
    // safeTypeChange() {
    //   crudExercise.getSafeType().then((response) => {
    //     console.log(response.data);
    //     this.selectSafeType = response.data;
    //   });
    // },

    // 移除表单
    removeDomain(item) {
      var index = this.form.temOptions.indexOf(item);
      // console.log(index);
      if (index !== -1) {
        this.form.temOptions.splice(index, 1);
        this.form.temAnswer.splice(index, 1);
      }
    },
    // 增加表单
    addDomain() {
      this.form.temOptions.push("");
      this.form.temAnswer.push(false);
    },
  },
};
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
::v-deep .el-input-number .el-input__inner {
  text-align: left;
}
.hello >>> .hide .el-upload--picture-card {
  display: none;
  // background-color: aqua;
}
</style>
<style scoped>
.el-dialog__header {
  text-align: center;
}
.ql-video {
  position: absolute;
  left: -9999px;
}
.ql-editor img {
  width: 100px;
  height: 100px;
}
</style>
