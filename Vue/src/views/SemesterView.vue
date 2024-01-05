<template>
  <div>
    <!-- button -->
    <el-row>
      <el-button type="success" @click="dialogFormVisible = true" v-if="roleList.includes('admin')">- 添加学期</el-button>
    </el-row>

    <!-- dialog 表单 -->
    <el-dialog title="编辑学期" :visible.sync="dialogFormVisible" :before-close='vanish'>
      <el-form :model="semester" :rules="rules">
        <el-form-item label="学期名称" label-width="80" prop="name">
          <el-input v-model="semester.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="开始时间" label-width="80">
          <el-date-picker v-model="semester.startDate" type="date" placeholder="选择日期" format="yyyy-MM-dd"
            value-format="yyyy-MM-dd">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间" label-width="80">
          <el-date-picker v-model="semester.endDate" type="date" placeholder="选择日期" format="yyyy-MM-dd"
            value-format="yyyy-MM-dd">
          </el-date-picker>
        </el-form-item>


      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="vanish">取 消</el-button>
        <el-button type="primary" @click=add()>确 定</el-button>
      </div>
    </el-dialog>

    <!-- table 表格 -->
    <el-table :data="tableData" stripe style="width: 100%">
      <el-table-column prop="name" label="学期名称" width="auto" align="center">
      </el-table-column>
      <el-table-column prop="startDate" label="开始时间" width="auto" align="center">
      </el-table-column>
      <el-table-column prop="endDate" label="结束时间" width="auto" align="center">
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间" width="auto" v-if="roleList.includes('admin')">
      </el-table-column>
      <el-table-column prop="updateTime" label="最后修改时间" width="auto" v-if="roleList.includes('admin')">
      </el-table-column>
      <el-table-column align="center" label="操作" width="150">
        <template slot-scope="scope">
          <el-button type="primary" size="small" @click="echo(scope.row.id)" v-if="roleList.includes('admin')">编辑</el-button>
          <el-button type="danger" size="small" @click="deleteById(scope.row.id)" v-if="roleList.includes('admin')">删除</el-button>
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
import { add, page, selectById, update, deleteById, getInfo } from '../api/semester';
import { getRoleList } from '../utils/auth';


export default {
  data() {
    return {
      pageSize: 5,
      totalCount: 100,
      currentPage: 1,
      dialogFormVisible: false,
      tableData: [],
      semester: {
        id: '',
        name: null,
        startDate: '',
        endDate: '',
      },
      roleList: [],
      rules: {
        name: [
          { required: true, message: '名称不能为空', trigger: 'blur' }
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
          this.semester = res.data.data
        }
      })
    },
    add() {
      if (this.semester.name == null || this.semester.name == '') {
        this.$notify.error('请输入必填项')
      } else {
        let tempRes;
        if (this.semester.id) {
          tempRes = update(this.semester)
        } else {
          tempRes = add(this.semester)
        }

        tempRes.then((res) => {
          if (res.data.code == 1) {
            this.dialogFormVisible = false;
            this.page()
            this.semester = {
              id: '',
              name: "",
              startDate: '',
              endDate: '',
            },
              this.$notify.success("保存成功")
          } else {
            this.$notify.error("操作失败")
          }
        })
      }
    },
    deleteById(id) {
      this.$confirm("是否删除所选学期？", {
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
        this.semester = {
          id: '',
          name: "",
          startDate: '',
          endDate: '',
        }
    }


  },
  mounted() {
    this.page()
    this.roleList = getRoleList()
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