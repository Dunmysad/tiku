<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <crudOperation :permission="permission" />
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
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column prop="title" label="轮播图标题" align="center">
        <template slot-scope="scope">
          {{ scope.row.title || "--" }}
        </template>
      </el-table-column>
      <el-table-column prop="picPath" label="轮播图片" align="center">
        <template slot-scope="scope">
          <img :src="$store.state.settings.imageUrl+ scope.row.picPath" referrerPolicy="no-referrer" class="lunboImage"/>
        </template>
      </el-table-column>
      <el-table-column prop="studyDataId" label="学习资料ID" align="center" >
        <template slot-scope="scope">
          {{ scope.row.studyDataId || "--" }}
        </template>
      </el-table-column>
      <el-table-column prop="sort" label="图片排序" align="center">
        <template slot-scope="scope">
          {{ scope.row.sort || "--"}}
        </template>
      </el-table-column>

      <!--   编辑与删除   -->
      <el-table-column
        v-permission="['admin', 'lunbo:edit', 'lunbo:del']"
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
import crudLunbo from "@/api/tiku/lunbo";
import eForm from "./module/form";
import CRUD, { presenter } from "@crud/crud";
import crudOperation from "@crud/CRUD.operation";
import pagination from "@crud/Pagination";
import udOperation from "@crud/UD.operation";

export default {
  name: "Lunbo",
  components: { eForm, crudOperation, pagination, udOperation },
  cruds() {
    return CRUD({
      title: "轮播图",
      url: "/api/eSlideshow/all",
      // sort: [{ 'column': 'job_sort', 'asc': 'true' }, { 'column': 'id', 'asc': 'false' }],
      crudMethod: { ...crudLunbo },
    });
  },
  // 混入，提高代码的重用性，使你的代码保持干净和易于维护。
  mixins: [presenter()],
  data() {
    return {
      permission: {
        add: ["admin", "lunbo:add"],
        edit: ["admin", "lunbo:edit"],
        del: ["admin", "lunbo:del"],
      },
    };
  },
  created() {
    // console.log(this.crud, '222')
  },
  methods: {
    // 删除
    ccEdit(row) {
      // let id = null,
      let that = this;
      let params = [row.id];
      this.$confirm('确认删除轮播图 < '+row.title+' >，以及相关数据吗？', "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          crudLunbo.del(params).then((res) => {
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
  },
};
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
::v-deep .el-input-number .el-input__inner {
  text-align: left;
}
::v-deep .lunboImage{
  width: 71px;
  height: 30px;
}
</style>
