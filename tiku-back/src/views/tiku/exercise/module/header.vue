<template>
  <div v-if="crud.props.searchToggle">
    <el-form
      ref="form"
      :inline="true"
      :model="form"
      label-position="right"
      label-width="68px"
    >
      <el-form-item label="题目类别">
        <el-select
          v-model="query.safeTypeId"
          placeholder="请选择类别搜索"
          clearable
          style="width: 150px"
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
      <el-form-item label="题型">
        <el-select
          v-model="query.quesType"
          placeholder="请选择题型"
          clearable
          style="width: 150px"
          @keyup.enter.native="crud.toQuery"
        >
          <el-option
            v-for="item in selectExerType"
            :key="item.value"
            :value="item.value"
            :label="item.name"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="题干">
        <el-input
          v-model="query.quesContent"
          clearable
          size="small"
          placeholder="输入题干搜索"
          style="width: 200px"
          class="filter-item"
          @keyup.enter.native="crud.toQuery"
        />
      </el-form-item>

      <el-form-item label="出题时间">
        <date-range-picker v-model="query.createTime" class="date-item" />
      </el-form-item>
      <rrOperation />
    </el-form>
  </div>
</template>

<script>
import crudExercise from '@/api/tiku/exercise'
import { header } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import DateRangePicker from '@/components/DateRangePicker'

export default {
  components: { rrOperation, DateRangePicker },
  mixins: [header()],
  props: {
    permission: {
      type: Object,
      required: true
    }
  },
  cruds() {
    return CRUD({
      crudMethod: { ...crudExercise }
    })
  },
  data() {
    return {
      // 题型选择
      selectExerType: [
        { value: 1, name: "单选题" },
        { value: 2, name: "多选题" },
        { value: 3, name: "判断题" },
      ],
      form: {},
      selectSafeType: []
    }
  },
  created() {
    this.safeTypeSelect()
    // console.log(this.crud, "222");
  },
  methods: {
    // 获取所有分类
    safeTypeSelect() {
      crudExercise.getSafeType().then((response) => {
        this.selectSafeType = response.data;
      })
    }
  }
}
</script>
