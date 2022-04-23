<template>
  <el-dialog
    append-to-body
    :close-on-click-modal="false"
    :before-close="crud.cancelCU"
    :visible="crud.status.cu > 0"
    :title="crud.status.title"
    width="670px"
  >
    <el-form
      ref="form"
      :inline="true"
      :model="form"
      :rules="rules"
      size="small"
      label-width="86px"
    >
      <el-form-item label="姓名" prop="name">
        <el-input v-model="form.name" style="width: 188px" />
      </el-form-item>
      <el-form-item label="准考证号" prop="admissionNum" label-width="86px">
        <el-input v-model.number="form.admissionNum" style="width: 188px" />
      </el-form-item>
      <el-form-item label="性别" prop="sex">
        <el-select
          v-model="form.sex"
          placeholder=" "
          clearable
          style="width: 188px"
        >
          <el-option
            v-for="item in selectSex"
            :key="item.value"
            :value="item.value"
            :label="item.name"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="身份证号" prop="idCard" label-width="86px">
        <el-input v-model="form.idCard" style="width: 188px" />
      </el-form-item>
      <el-form-item label="学号" prop="studentNum">
        <el-input v-model.number="form.studentNum" style="width: 188px" />
      </el-form-item>
      <el-form-item label="学校名称" prop="universityId" label-width="86px">
        <!-- <el-input v-model="form.universityName" style="width: 170px" /> -->
        <treeselect
          :options="dept"
          :normalizer="normalizer"
          @select="uniChange"
          v-model="form.universityId"
          style="width: 188px"
          placeholder=" "
        />
      </el-form-item>
      <el-form-item label="专业" prop="major">
        <el-input v-model="form.major" style="width: 188px" />
      </el-form-item>
      
      <el-form-item label="入学年份" prop="enrollmentYear" label-width="86px">
        <el-date-picker
          v-model="form.enrollmentYear"
          type="year"
          style="width: 188px"
          :picker-options="pickerOptions"
          @change="getSTime"
          value-format="yyyy"
        />
      </el-form-item>
      <el-form-item label="学院名称" prop="college" label-width="86px">
        <el-input v-model="form.college" style="width: 188px" />
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
import crudStudents from "@/api/tiku/students";
import CRUD, { form, crud} from "@crud/crud";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

const defaultForm = {
  admissionNum: "",
  name: null,
  idCard: null,
  studentNum: '',
  // universityName: "",
  sex:"",
  universityId: null,
  major: null,
  college: null,
  enrollmentYear: "",
};
export default {
  mixins: [form(defaultForm)],
  components: { Treeselect },
  data() {
    return {
      // 性别选择
      selectSex: [
        { value: false, name: "男" },
        { value: true, name: "女" },
      ],
      // 入学年份选择
      pickerOptions: {
        //disabled为函数，返回值为布尔值，
        disabledDate: (time) => {
          let minYear = new Date().getFullYear() - 4;
          let maxYear = new Date().getFullYear() + 4;
          return (
            time.getTime() > new Date(JSON.stringify(maxYear)) ||
            time.getTime() < new Date(JSON.stringify(minYear))
          );
        },
      },
      // 学校名称
      dept: [],
      universityId: "",
      deptName: "",
      // 表单正则及规则
      rules: {
        name: [{ required: true, message: "请输入姓名",trigger: "blur"}],
        major: [{required: true,message: "请输入专业名称",trigger: "blur"}],
        universityId: [{required: true,message: "请选择学校名称",trigger: "change"}],
        enrollmentYear: [{required: true, message: "请选择入学年份", trigger: "blur"}],
        sex: [{ required: true, message: "请选择性别", trigger: "change" },],
        college: [{required: true, message: "请输入学院名称",trigger: "blur"}],
        studentNum: [{required: true, message: "请输入学号"},
                      { type: 'number', message: '学号必须为数字'}],
        admissionNum: [{required: true, message: "请输入准考证号"},
                        {type: 'number', message: '准考证号必须为数字'}],
        idCard: [
          { required: true,message: "请输入身份证号", trigger: "blur"},
          { min: 18, max: 18, message: '请如实填写18位号码', trigger: 'blur' },
          {
            required: true,
            pattern: /(^\d{18}$)|(^\d{17}(\d|X|x)$)/,
            message: '请输入正确的身份证号码',
            trigger: 'blur'
          }],
      },
    };
  },
  created() {
    this.deptsSelect();
  },
  methods: {
    // 编辑之前
    [CRUD.HOOK.beforeToEdit](crud) {
      crud.form.admissionNum=parseInt(crud.form.admissionNum);
      crud.form.studentNum=parseInt(crud.form.studentNum);
      // console.log(typeof(crud.form.admissionNum));
    }, 
    // 提交之前
    [CRUD.HOOK.afterSubmit](crud) {
      crud.form.admissionNum=String(crud.form.admissionNum);
      crud.form.studentNum=String(crud.form.studentNum);
      // console.log(typeof(crud.form.admissionNum));
    },

    //查询部门
    deptsSelect() {
      crudStudents.getDepts().then((response) => {
        // console.log(response.data.records);
        // this.dept = response.data.records;
        let newArr = JSON.parse(
          JSON.stringify(response.data).replace(/name/g, 'label')
        )
        this.dept = newArr
      });
    },
    uniChange(value) {
      this.deptName = value.label;
      this.universityId = value.id;
    },
    normalizer(node) {
      //去掉children=[]的children属性 respectively
      if (node.children && !node.children.length) {
        delete node.children;
      }
    },
    //入学年份只传年份
    getSTime(val) {
      console.log("改变年份了");
      this.enrollmentyear = val;
    },
  },
};
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

</style>
