<template>
  <div>
    <!-- button -->
    <el-row>
      <el-button type="success" @click="dialogFormVisible = true">- 添加部门</el-button>
    </el-row>

    <!-- dialog 表单 -->
    <el-dialog title="编辑部门" :visible.sync="dialogFormVisible" :before-close='vanish'>
      <el-form :model="dept" :rules="rules">
        <el-form-item label="部门名称" label-width="80" prop="name">
          <el-input v-model="dept.name" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="vanish">取 消</el-button>
        <el-button type="primary" @click=add()>确 定</el-button>
      </div>
    </el-dialog>

    <!-- table 表格 -->
    <el-table :data="tableData" stripe style="width: 100%">
      <el-table-column prop="name" label="部门名称" width="800" align="center">
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间" width="auto">
      </el-table-column>
      <el-table-column prop="updateTime" label="最后修改时间" width="auto">
      </el-table-column>
      <el-table-column align="center" label="操作" width="150">
        <template slot-scope="scope">
          <el-button type="primary" size="small" @click="echo(scope.row.id)">编辑</el-button>
          <el-button type="danger" size="small" @click="deleteById(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>


    <!--分页工具条-->
    <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :background=true
      :current-page="currentPage" :page-sizes="[5, 10, 15, 20]" :page-size="5"
      layout="total, sizes, prev, pager, next, jumper" :total="totalCount">
    </el-pagination>



  </div>
</template>
  
<script>
import { add, page, selectById, update, deleteById, getInfo } from '../api/dept';

export default {
  data() {
    return {
      pageSize: 5,
      totalCount: 100,
      currentPage: 1,
      dialogFormVisible: false,
      tableData: [],
      dept: {
        name: "",
      },
      rules: {
        name: [
          { required: true, message: '请输入部门名称', trigger: 'blur' }
        ]
      }

    }
  },

  methods: {
    //分页
    handleSizeChange(val) {
      // 重新设置每页显示的条数
      this.pageSize = val;
      this.page();
    },
    handleCurrentChange(val) {
      // 重新设置当前页码
      this.currentPage = val;
      this.page();
    },
    // 分页查询
    page() {
      page(
        this.currentPage,
        this.pageSize
      ).then((res) => {
        this.totalCount = res.data.data.total;
        this.tableData = res.data.data.rows;
      })
    },
    echo(id) {
      this.dialogFormVisible = true;
      selectById(id).then((res) => {
        if (res.data.code == 1) {
          this.dept = res.data.data
        }
      })
    },
    add() {
      if (this.dept.name == '' || this.dept.name == null) {
        this.$notify.error("请填写部门名")
      } else {
        let tempRes;
        if (this.dept.id) {
          tempRes = update(this.dept)
        } else {
          tempRes = add(this.dept)
        }

        tempRes.then((res) => {
          if (res.data.code == 1) {
            this.dialogFormVisible = false;
            this.page()
            this.dept = {};
            this.$notify.success("保存成功")
          } else {
            this.$notify.error("操作失败")
          }
        })
      }
    },
    deleteById(id) {
      this.$confirm("是否删除所选部门？", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        deleteById(id).then((res) => {
          if (res.data.code == 1) {
            this.page()
            this.$notify.success("删除成功")
          } else {
            this.$notify.error("删除失败\n" + res.data.msg)
          }
        })
      }).catch(() => {
        // 取消
        this.$notify.success("已取消删除")
      });

    },
    vanish() {
      this.dialogFormVisible = false,
        this.dept = {
          name: "",
        }

    }


  },
  mounted() {
    this.page()
  },
  created() {
    getInfo().then((res) => {
      if (res == null) {
        this.$notify.error("请登录或尝试登陆其他账号")
        this.$router.push('/403')
      }
    })

  }


}
</script>
  
<style></style>