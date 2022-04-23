<template>
  <div v-if="crud.props.searchToggle">
    <!-- :model="form" -->
    <el-form
      ref="form"
      :inline="true"
      size="small"
      label-position="right"
      label-width="68px"
    >
      <el-form-item label="姓名">
        <el-input
          v-model="query.name"
          clearable
          size="small"
          placeholder="输入姓名搜索"
          style="width: 200px"
          class="filter-item"
          @keyup.enter.native="crud.toQuery"
        />
      </el-form-item>
      <el-form-item label="准考证号">
        <el-input
          v-model="query.admissionNum"
          clearable
          size="small"
          placeholder="输入准考证号搜索"
          style="width: 200px"
          class="filter-item"
          @keyup.enter.native="crud.toQuery"
        />
      </el-form-item>
      <el-form-item label="身份证号">
        <el-input
          v-model="query.idCard"
          clearable
          size="small"
          placeholder="输入身份证号搜索"
          style="width: 200px"
          class="filter-item"
          @keyup.enter.native="crud.toQuery"
        />
      </el-form-item>
      <el-form-item label="学号">
        <el-input
          v-model="query.studentNum"
          clearable
          size="small"
          placeholder="输入学号搜索"
          style="width: 200px"
          class="filter-item"
          @keyup.enter.native="crud.toQuery"
        />
      </el-form-item>

      <el-form-item label=" 学校名称">
        <treeselect
          :options="dept"
          :normalizer="normalizer"
          @select="uniChange"
          v-model="query.universityId"
          style="width: 200px"
          placeholder="选择机构名称搜索"
          @keyup.enter.native="crud.toQuery"
        />
      </el-form-item>
      <el-form-item label="学院名称">
        <el-input
          v-model="query.college"
          clearable
          size="small"
          placeholder="输入学院名称搜索"
          style="width: 200px"
          class="filter-item"
          @keyup.enter.native="crud.toQuery"
        />
      </el-form-item>
      <el-form-item label="入学年度">
        <el-date-picker
          v-model="query.enrollmentYear"
          clearable
          size="small"
          type="year"
          placeholder="选择入学年份搜索"
          style="width: 200px"
          :picker-options="pickerOptions"
          @change="getSTime"
          value-format="yyyy"
          @keyup.enter.native="crud.toQuery"
          :editable="false"
        />
      </el-form-item>
      <rrOperation/>
    </el-form>
  </div>
</template>

<script>
import crudStudents from "@/api/tiku/students";
import { header } from "@crud/crud";
import rrOperation from "@crud/RR.operation";
import DateRangePicker from "@/components/DateRangePicker";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
export default {
  components: { rrOperation, DateRangePicker, Treeselect },
  mixins: [header()],
  props: {
    permission: {
      type: Object,
      required: true,
    },
  },
  data() {
    return {
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
    
    };
  },
  created() {
    this.deptsSelect();
  },
  methods: {
    //入学年份只传年份
    getSTime(val) {
      this.enrollmentYear = val; //这个sTime是在data中声明的，也就是v-model绑定的值
    },
    //查询部门
    deptsSelect() {
      crudStudents.getDepts().then((response) => {
        console.log(response.data);
        // this.dept = response.data;
        let newArr = JSON.parse(
          JSON.stringify(response.data).replace(/name/g, 'label')
        )
        this.dept = newArr
      });
    },
    uniChange(value) {
      console.log(value);
      this.deptName = value.lable;
      this.universityId = value.id;
    },
    normalizer(node) {
      console.log(node,"node");
      //去掉children=[]的children属性 respectively
      if (node.children && !node.children.length) {
        delete node.children;
      }
    },
  },
};
</script>
<style rel="stylesheet/scss" lang="scss" scoped>

</style>
