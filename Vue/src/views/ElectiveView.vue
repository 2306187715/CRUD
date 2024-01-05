<template>
  <div>
    <!--SearchForm 搜索表单-->
    <el-form :inline="true" :model="searchElective" class="demo-form-inline">
      <!-- 学期选择器 -->
      <el-form-item label="学期：">
        <el-select v-model="searchElective.semesterId" placeholder="请选择" @change="page" style="width: 150px;">
          <el-option v-for="item in semesterList" :key="item.value" :label="item.name" :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
      <!-- 课程编号选择器 -->
      <el-form-item label="课程编号：">
        <el-input v-model="searchElective.courseId" placeholder="请输入课程编号" @change="page" style="width: 150px;"></el-input>
      </el-form-item>
      <!-- 学号选择器 -->
      <el-form-item label="学号">
        <el-input v-model="searchElective.stuId" placeholder="请输入学生学号" @change="page" style="width: 150px;"></el-input>
      </el-form-item>
      <!-- 分数范围选择器 -->
      <el-form-item label="分数范围：">
        <el-slider v-model="searchElective.score" range :marks="marks" style="width: 300px;">
        </el-slider>
      </el-form-item>
      <el-button type="primary" @click="doSearch" style="margin-left: 50px;">查询</el-button>
      <el-button type="info" @click="clear">清空</el-button>

      <el-row>

        <el-button type="success" @click="dialogFormVisible = true">- 添加记录</el-button>
      </el-row>
    </el-form>


    <!-- dialog 表单 -->
    <el-dialog ref="form" title="编辑选课记录" :visible.sync="dialogFormVisible" width="30%" :before-close='vanish'>
      <el-form ref="form" :model="elective" label-width="80px" size="mini" :rules="rules">
        <el-form-item label="课程编号" prop="courseId">
          <el-input v-model="elective.courseId"></el-input>
        </el-form-item>
        <el-form-item label="学生号" prop="stuId">
          <el-input v-model="elective.stuId"></el-input>
        </el-form-item>
        <el-form-item label="分数">
          <el-input v-model="elective.score"></el-input>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="elective.status" placeholder="请选择" style="width:100%">
            <el-option v-for="item in statusList" :key="item.value" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>
        <h5 style="margin-right: 30px; text-align:right;">默认为选课状态</h5>
        <el-form-item>
          <el-button type="primary" @click="add()">提交</el-button>
          <el-button @click="vanish">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>



    <!--Table 表格-->
    <el-table :data="tableData" stripe style="width: 100%">
      <el-table-column prop="courseId" label="课程编号" width="auto" align="center">
      </el-table-column>
      <el-table-column prop="courseName" label="课程名" width="auto" align="center" :formatter="formatCourse">
      </el-table-column>
      <el-table-column prop="stuId" label="学号" width="auto" align="center">
      </el-table-column>
      <el-table-column prop="stuName" label="学生名" width="auto" align="center" :formatter="formatStu">
      </el-table-column>
      <el-table-column prop="score" label="成绩" width="auto" align="center">
      </el-table-column>
      <el-table-column prop="status" label="状态" width="auto" align="center">
        <template slot-scope="scope">
          <span style="margin-right: 10px" v-if="scope.row.status == 0">选课</span>
          <span style="margin-right: 10px" v-if="scope.row.status == 1">已退课</span>
          <span style="margin-right: 10px" v-if="scope.row.status == 2">挂科</span>
        </template>
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
    <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :background="background"
      :current-page="currentPage" :page-sizes="[5, 10, 15, 20]" :page-size="5"
      layout="total, sizes, prev, pager, next, jumper" :total="totalCount">
    </el-pagination>
  </div>
</template>

<script>
import { page, add, update, deleteById, selectById, getInfo } from "@/api/elective.js";
import { selectAllSemesters } from "../api/semester";
import { selectAllCourses } from "../api/course";
import { selectAllStu } from "../api/stu";
export default {
  data() {
    return {
      // tag
      addUpdateTags: '',
      background: true,
      dialogFormVisible: false,
      // 页数/总量/页码
      pageSize: 5,
      totalCount: 100,
      currentPage: 1,

      // 搜索表单
      searchElective: {
        semesterId: '',
        courseId: '',
        stuId: '',
        score: [0, 100]
      },
      tableData: [],
      elective: {},

      // 学期列表
      semesterList: [],
      semesterMap: {},
      // 课程列表
      courseList: [],
      courseMap: {},

      // 学生列表
      stuList: [],
      stuMap: {},


      scoreRange: [0, 100],
      marks: {
        0: '0',
        60: {
          style: {
            color: '#f50',
          },
          label: <strong>60</strong>,
        },
        90: '90',
        100: '100'
      },
      statusList: [{ id: 0, name: "选课" }, { id: 1, name: "已退课" }, { id: 2, name: "挂科" }],


      rules: {
        courseId: [
          { required: true, message: '请输入课程id', trigger: 'blur' }
        ],
        stuId: [
          { required: true, message: '请输入学生id', trigger: 'blur' }
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
    formatCourse(row) {
      return this.courseMap[row.courseId];
    },
    formatStu(row) {
      return this.stuMap[row.stuId];
    },
    doSearch() {
      this.page()
    },
    clear() {
      this.searchElective = {
        semesterId: '',
        courseId: '',
        stuId: '',
        score: [0, 100]
      },
        this.page()
    },
    page() {
      page(
        this.currentPage,
        this.pageSize,
        this.searchElective.semesterId,
        this.searchElective.courseId,
        this.searchElective.stuId,
        this.searchElective.score.join(',').split(','),
      ).then((res) => {
        this.totalCount = res.data.data.total
        this.tableData = res.data.data.rows

      })
    },
    vanish() {
      this.dialogFormVisible = false,
        this.elective = {},
        this.addUpdateTags = ''
    },
    echo(id) {
      selectById(id).then((res) => {
        this.elective = res.data.data
        this.dialogFormVisible = true
        this.addUpdateTags = 'update'
      })
    },

    add() {
      if (this.elective.courseId == null || this.elective.stuId == null || this.elective.courseId == '' || this.elective.stuId == '') {
        this.$notify.error('请输入必填项')
      } else {
        let tempRes;
        if (this.addUpdateTags == 'update') {
          tempRes = update(this.elective)
        } else {
          this.elective.status ? 0 : this.elective.status
          tempRes = add(this.elective)
        }

        tempRes.then((res) => {
          if (res.data.code == 1) {
            this.dialogFormVisible = false;
            this.page();
            this.elective = {},
              this.$notify({
                title: '保存成功',
                type: 'success'
              });
          } else {
            this.$notify({
              title: res.data.msg,
              type: 'error'
            });
          }
        })
      }
    },

    deleteById(id) {
      this.$confirm('此操作将永久删除该课程, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteById(id).then((res) => {
          if (res.data.code == 1) {
            this.page();
            this.$notify({
              title: '删除成功',
              type: 'success'
            });
          } else {
            this.$notify({
              title: '删除失败',
              type: 'error'
            });
          }
        })
      }).catch(() => {
        this.$notify({
          title: '已取消删除',
          type: 'info'
        });
      });
    },

  },





  mounted() {
    this.page()
    //学期id映射
    selectAllSemesters().then((res) => {
      this.semesterList = res.data.data

      this.semesterMap = this.semesterList.reduce((map, semester) => {
        map[semester.id] = semester.name
        return map;
      }, {});
    });
    //课程id映射
    selectAllCourses().then((res) => {
      this.courseList = res.data.data

      this.courseMap = this.courseList.reduce((map, course) => {
        map[course.id] = course.name
        return map;
      }, {});
    });
    //学生id映射
    selectAllStu().then((res) => {
      this.stuList = res.data.data

      this.stuMap = this.stuList.reduce((map, stu) => {
        map[stu.id] = stu.name
        return map;
      }, {});
    });

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