<template>
  <div v-if="crud.props.searchToggle">
    <el-form
      ref="form"
      :inline="true"
      :model="form"
      label-position="right"
      label-width="68px"
    >
      <el-form-item label="分类名称">
        <el-select
          filterable
          v-model="query.name"
          placeholder="请选择分类搜索"
          clearable
          @focus="safeTypeSelect()"
          style="width: 200px"
        >
          <el-option
            v-for="item in selectSafeType"
            :key="item.id"
            :value="item.name"
            :label="item.name"
          />
        </el-select>
      </el-form-item>
      <!-- <input v-model="query.username"/> -->
      <rrOperation />
    </el-form>
  </div>
</template>

<script>
import { header } from "@crud/crud";
import rrOperation from "@crud/RR.operation";
import DateRangePicker from "@/components/DateRangePicker";
import crudSecurity from "@/api/tiku/classify";

export default {
  components: { rrOperation, DateRangePicker },
  mixins: [header()],
  // cruds() {
  //   return CRUD({
  //     crudMethod: { ...crudExercise },
  //   });
  // },
  props: {
    permission: {
      type: Object,
      required: true,
    },
  },
  data() {
    return {
      // 分类选择
      selectSafeType: [],
      form: {},
    };
  },
  methods: {
    // 选择框获取焦点事件。获取所有分类
    safeTypeSelect() {
      crudSecurity.getSafeType().then((response) => {
        this.selectSafeType = response.data;
      });
    },
  },
};
</script>
