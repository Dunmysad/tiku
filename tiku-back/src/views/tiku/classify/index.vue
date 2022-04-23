<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <eHeader :permission="permission" />
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
      <!-- <el-table-column prop="id" label="分类ID" align="center"/> -->
      <el-table-column prop="name" label="分类名称" align="center">
        <template slot-scope="scope">
          {{ scope.row.name || "--" }}
        </template>
      </el-table-column>
      <el-table-column prop="forShort" label="分类简称" align="center">
        <template slot-scope="scope">
          {{ scope.row.forShort || "--" }}
        </template>
      </el-table-column>
      <el-table-column prop="picPath" label="分类图片" align="center">
        <template slot-scope="scope">
          <img :src="$store.state.settings.imageUrl+scope.row.picPath" class="securityImage"/>
        </template>
      </el-table-column>
      <el-table-column prop="rooterPath" label="分类代码" align="center">
        <template slot-scope="scope">
          {{ scope.row.rooterPath || "--" }}
        </template>
      </el-table-column>
      <el-table-column prop="repositoryPath" label="题库图标" align="center" >
        <template slot-scope="scope">
          <img :src="$store.state.settings.imageUrl+scope.row.repositoryPath" class="securityImage myColor"/>
        </template>
      </el-table-column>
      <el-table-column prop="seqNum" label="分类排序" align="center">
        <template slot-scope="scope">
          {{ scope.row.seqNum || "--" }}
        </template>
      </el-table-column>
      <el-table-column prop="isShow" label="显示状态" align="center">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.isShow"
            active-color="#409EFF"
            inactive-color="#F56C6C"
            @change="changeEnabled(scope.row.id, scope.row.isShow)"
          />
          <!-- @change="changeShow(scope.row, scope.row.isShow)" -->
        </template>
      </el-table-column>
      <el-table-column
        prop="createTime"
        label="创建日期"
        align="center"
        width="150px"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <!--   编辑与删除   -->
      <el-table-column
        v-permission="['admin', 'safeType:edit', 'safeType:del']"
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
import crudSecurity from "@/api/tiku/classify";
import eHeader from "./module/header";
import eForm from "./module/form";
import CRUD, { presenter } from "@crud/crud";
import crudOperation from "@crud/CRUD.operation";
import pagination from "@crud/Pagination";
import udOperation from "@crud/UD.operation";

export default {
  name: "Classify",
  components: { eHeader, eForm, crudOperation, pagination, udOperation },
  cruds() {
    return CRUD({
      title: "分类",
      url: "/api/eSafeType",
      // sort: [
      //   { column: "eSafeType_sort", asc: "true" },
      //   { column: "id", asc: "false" },
      // ],
      crudMethod: { ...crudSecurity },
    });
  },
  mixins: [presenter()],
  data() {
    return {
      permission: {
        add: ["admin", "safeType:add"],
        edit: ["admin", "safeType:edit"],
        del: ["admin", "safeType:del"],
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
      this.$confirm('确认删除分类 < '+row.name+' >，以及相关数据吗？', "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          crudSecurity.del(params).then((res) => {
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
    // 改变状态
    changeEnabled(typeId, typeIshow) {
      let changeType = {
        safeTypeId: typeId,
        isShow: typeIshow,
      };
      if (typeIshow == false) {
        crudSecurity.updateType(changeType).then(() => {
          // this.$alert("禁用成功！！！");
          this.$message.success("禁用成功！");
        });
      } else {
        crudSecurity.updateType(changeType).then(() => {
          this.$message.info("已取消禁用！");
        });
      }
    },
  },
};
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
::v-deep .el-input-number .el-input__inner {
  text-align: left;
}
::v-deep .securityImage{
  width: 50px;
  height: 50px;
}
.myColor{
  background-color: gainsboro;
}

</style>
