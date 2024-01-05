<template>
  <div class="big-box">
    <!--SearchForm 搜索表单-->
    <el-form :inline="true" :model="searchCourse" class="demo-form-inline">
      <!-- 学期选择器 -->
      <el-form-item label="学期：">
        <el-select v-model="searchCourse.semester" placeholder="请选择" @change="page">
          <el-option v-for="item in semesterList" :key="item.value" :label="item.name" :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
      <!-- 教师名称选择器 -->
      <el-form-item label="教师名称：">
        <el-select v-model="searchCourse.teacher" placeholder="请选择" @change="page">
          <el-option v-for="item in teacherList" :key="item.value" :label="item.name" :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
      <!-- 课程名选择器 -->
      <el-form-item label="课程名：">
        <el-input v-model="searchCourse.name" placeholder="请输入课程名" @change="page"></el-input>
      </el-form-item>
      <!-- 日期monday选择器 -->
      <el-form-item label="星期：">
        <el-select v-model="searchCourse.dayOfWeek" placeholder="请选择" style="width:100%" @change="page">
          <el-option v-for="item in dayOfWeekList" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
      </el-form-item>
      <br>
      <!-- 周week选择器 -->
      <el-form-item label="周次： ">
        <el-slider v-model="searchCourse.week" range show-stops :min="1" :max="18" :marks="marks" style="width: 400px;"
          @input="page">
        </el-slider>
      </el-form-item>
      <!-- 时间hour选择器 -->
      <el-form-item label="节次：">
        <el-time-select placeholder="起始时间" v-model="startTime" :picker-options="{
          start: '08:00',
          step: '01:00',
          end: '19:00'
        }" @change="page">
        </el-time-select>
        至
        <el-time-select placeholder="结束时间" v-model="endTime" :picker-options="{
          start: '10:00',
          step: '01:00',
          end: '22:00',
          minTime: startTime
        }" @change="page">
        </el-time-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="doSearch">查询</el-button>
        <el-button type="info" @click="clear">清空</el-button>
      </el-form-item>
    </el-form>

    <!-- button -->
    <el-row>
      <el-button type="success" @click="dialogFormVisible = true"
        v-if="roleList.includes('admin') || roleList.includes('teacher.update')">- 添加课程</el-button>
      <!-- <el-button type="danger" @click="deleteByIds">- 批量删除</el-button> -->
    </el-row>


    <!-- dialog 表单 -->
    <el-dialog ref="form" title="编辑课程" :visible.sync="dialogFormVisible" width="30%" :before-close='vanish'>
      <el-form ref="form" :model="course" label-width="80px" size="mini" :rules="rules">
        <el-form-item label="课程编号" prop="id">
          <el-input v-model="course.id" :disabled="disable"></el-input>
        </el-form-item>
        <el-form-item label="课程名" prop="name">
          <el-input v-model="course.name"></el-input>
        </el-form-item>
        <el-form-item label="学分">
          <el-input v-model="course.credit"></el-input>
        </el-form-item>
        <el-form-item label="学时">
          <el-input v-model="course.hours"></el-input>
        </el-form-item>
        <el-form-item label="讲课教师" prop="teacherId">
          <el-select v-model="course.teacherId" placeholder="请选择" style="width:100%">
            <el-option v-for="item in teacherList" :key="item.value" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="上课学期" prop="semesterId">
          <el-select v-model="course.semesterId" placeholder="请选择" style="width:100%">
            <el-option v-for="item in semesterList" :key="item.value" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="周次： ">
          <el-slider v-model="weekRange" range show-stops :min="1" :max="18" :marks="marks" style="width: 300px;"
            @input="handleWeekRangeChange">
          </el-slider>
        </el-form-item>
        <el-form-item label="节次：">
          <el-time-select placeholder="起始时间" v-model="course.startTime" :picker-options="{
            start: '08:00',
            step: '01:00',
            end: '19:00'
          }">
          </el-time-select>
          至
          <el-time-select placeholder="结束时间" v-model="course.endTime" :picker-options="{
            start: '10:00',
            step: '01:00',
            end: '22:00',
            minTime: startTime
          }">
          </el-time-select>
        </el-form-item>
        <el-form-item label="星期：">
          <el-select v-model="course.dayOfWeek" placeholder="请选择" style="width:100%">
            <el-option v-for="item in dayOfWeekList" :key="item.value" :label="item.label" :value="item.value" />
          </el-select>
        </el-form-item>


        <el-form-item>
          <el-button type="primary" @click="add()">提交</el-button>
          <el-button @click="vanish">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <!-- table 表格 -->
    <el-table :data="tableData" stripe style="width: 100%">
      <el-table-column prop="id" label="课程编号" width="auto" align="center">
      </el-table-column>
      <el-table-column prop="name" label="课程名" width="auto" align="center">
      </el-table-column>
      <el-table-column prop="teacher" label="教师名称" width="auto" align="center" :formatter="formatTeacher">
      </el-table-column>
      <el-table-column prop="credit" label="学分" width="auto" align="center">
      </el-table-column>
      <el-table-column prop="hours" label="学时" width="auto" align="center">
      </el-table-column>
      <!-- 1-9周或者10-18周 -->
      <el-table-column prop="week" label="上课周" width="auto" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.startWeek }}-{{ scope.row.endWeek }}</span>
        </template>
      </el-table-column>
      <!-- 时间点，几点到几点 -->
      <el-table-column prop="time" label="上课时间" width="auto" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.startTime }}-{{ scope.row.endTime }}</span>
        </template>
      </el-table-column>
      <!-- 周一还是周二………… -->
      <el-table-column prop="dayOfWeek" label="上课日" width="auto" align="center">
        <template slot-scope="scope">
          <span style="margin-right: 10px" v-if="scope.row.dayOfWeek == 1">星期一</span>
          <span style="margin-right: 10px" v-if="scope.row.dayOfWeek == 2">星期二</span>
          <span style="margin-right: 10px" v-if="scope.row.dayOfWeek == 3">星期三</span>
          <span style="margin-right: 10px" v-if="scope.row.dayOfWeek == 4">星期四</span>
          <span style="margin-right: 10px" v-if="scope.row.dayOfWeek == 5">星期五</span>
          <span style="margin-right: 10px" v-if="scope.row.dayOfWeek == 6">星期六</span>
          <span style="margin-right: 10px" v-if="scope.row.dayOfWeek == 7">星期日</span>
        </template>
      </el-table-column>

      <el-table-column prop="createTime" label="创建时间" width="auto" v-if="roleList.includes('admin')">
      </el-table-column>
      <el-table-column prop="updateTime" label="最后修改时间" width="auto" v-if="roleList.includes('admin')">
      </el-table-column>
      <el-table-column align="center" label="操作" width="150">
        <template slot-scope="scope">
          <el-button type="primary" size="small" @click="submit(scope.row.id)" v-if="roles == 'stu'">选课</el-button>
          <el-button type="danger" size="small" @click="cancel(scope.row.id)" v-if="roles == 'stu'">退课</el-button>
          <br>
          <el-button type="primary" size="small" @click="echo(scope.row.id)" v-if="roles !== 'stu'">编辑</el-button>
          <el-button type="danger" size="small" @click="deleteById(scope.row.id)" v-if="roles !== 'stu'">删除</el-button>
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
import { page, add, update, deleteById, selectById, getInfo } from "@/api/course.js";
import { add as addElective, update as updateElective } from "../api/elective";
import { selectAllSemesters } from "../api/semester";
import { selectAllTeachers } from "../api/teacher";
import { getRole, getRoleList, getUserId } from "../utils/auth";
export default {
  data() {
    return {
      // tag
      disable: false,
      addUpdateTags: '',
      background: true,
      dialogFormVisible: false,
      // 页数/总量/页码
      pageSize: 5,
      totalCount: 100,
      currentPage: 1,

      // 搜索表单
      searchCourse: {
        name: '',
        teacher: '',
        semester: '',
        week: [1, 18],
        dayOfWeek: '',
      },
      roleList: [],
      tableData: [],
      startTime: '',
      endTime: '',
      // 周选择器标记点
      marks: {
        1: '1',
        9: '9',
        10: '10',
        18: '18'
      },

      // 学期列表
      semesterList: [],
      semesterMap: {},

      // 教师列表
      teacherList: [],
      teacherMap: {},

      // 星期列表
      dayOfWeekList: [{
        value: 1,
        label: '星期一'
      }, {
        value: 2,
        label: '星期二'
      }, {
        value: 3,
        label: '星期三'
      }, {
        value: 4,
        label: '星期四'
      }, {
        value: 5,
        label: '星期五'
      }, {
        value: 6,
        label: '星期六'
      }, {
        value: 7,
        label: '星期日'
      }],
      // 课程实体类
      course: {
        startWeek: 1,
        endWeek: 18
      },
      // weekRange: [],

      roles: '',
      userId: '',
      electiveSubmit: {},



      rules: {
        id: [
          { required: true, message: 'id不能为空', trigger: 'change' }
        ],
        name: [
          { required: true, message: '课程名不能为空', trigger: 'change' },
          { min: 1, max: 10, message: '长度不得超过10位', trigger: 'change' },
        ],
        teacherId: [
          { required: true, message: '教师不能为空', trigger: 'blur' }
        ],
        semesterId: [
          { required: true, message: '学期不能为空', trigger: 'blur' }
        ],
      }

    }
  },
  methods: {
    doSearch() {
      // console.log(this.searchCourse.week.join(',').split(',')[0])
      // console.log(this.tableData)
      // console.log(this.searchCourse)
      this.page()
    },
    clear() {
      this.searchCourse = {
        name: '',
        teacher: '',
        semester: '',
        week: [1, 18],
        dayOfWeek: '',
      }
      this.startTime = ''
      this.endTime = ''
      this.page()
    },
    // 分页查询
    page() {
      page(
        this.currentPage,
        this.pageSize,
        this.searchCourse.name,
        this.searchCourse.teacher,
        this.searchCourse.semester,
        this.searchCourse.week.join(',').split(','),
        this.searchCourse.dayOfWeek,
        this.startTime,
        this.endTime
      ).then((res) => {
        this.totalCount = res.data.data.total
        this.tableData = res.data.data.rows
      })
    },

    echo(id) {
      selectById(id).then((res) => {
        this.course = res.data.data
        this.disable=true
        this.dialogFormVisible = true
        this.addUpdateTags = 'update'
        this.weekRange = [this.course.startWeek, this.course.endWeek]
      })
    },

    add() {

      if (this.course.id == null || this.course.name == null || this.course.teacherId == null || this.course.semesterId == null || this.course.id == '' || this.course.name == '' || this.course.name == '' || this.course.teacherId == '') {
        this.$notify.error('请输入必填项')
      } else {

        let tempRes;
        if (this.addUpdateTags == 'update') {
          console.log(this.course)
          tempRes = update(this.course)
        } else {
          tempRes = add(this.course)
        }
        tempRes.then((res) => {
          if (res.data.code == 1) {
            this.dialogFormVisible = false;
            this.page();
            this.course = {
              startWeek: 1,
              endWeek: 18
            },
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
      this.addUpdateTags='',
      this.disable=false
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

    submit(row_id) {
      let semesterId = this.tableData.find(c => c.id === row_id).semesterId
      this.electiveSubmit = {
        courseId: row_id,
        semesterId,
        stuId: this.userId
      }
      // console.log(row_id)
      // console.log(this.tableData.find(c => c.id === row_id).semesterId)
      addElective(this.electiveSubmit).then((res) => {
        if (res.data.code == 1) {
          this.page()
          this.$notify({
            title: '选课成功',
            type: 'success'
          });
        } else {
          this.$notify({
            title: res.data.msg,
            type: 'error'
          });
        }
      })
    },
    cancel(row_id) {
      let status = 1
      let semesterId = this.tableData.find(c => c.id === row_id).semesterId
      this.electiveSubmit = {
        courseId: row_id,
        semesterId,
        stuId: this.userId,
        status
      }
      updateElective(this.electiveSubmit).then((res) => {
        if (res.data.code == 1) {
          this.$notify({
            title: '退课成功',
            type: 'success'
          });
        } else {
          this.$notify({
            title: res.data.msg,
            type: 'error'
          });
        }
      })
    },



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
    formatTeacher(row) {
      return this.teacherMap[row.teacherId];
    },
    vanish() {
      this.dialogFormVisible = false,
      this.disable=false
        this.course = {
          startWeek: 1,
          endWeek: 18
        },
        this.addUpdateTags = ''
    },
    handleWeekRangeChange(value) {
      this.weekRange = value
      this.course.startWeek = value[0];
      this.course.endWeek = value[1];
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
    //教师id
    selectAllTeachers().then((res) => {
      this.teacherList = res.data.data

      this.teacherMap = this.teacherList.reduce((map, teacher) => {
        map[teacher.id] = teacher.name
        return map;
      }, {});
    });
    this.roles = getRole()
    this.userId = getUserId()
    this.roleList = getRoleList()



  },

  created() {
    getInfo().then((res) => {
      if (res == null) {
        this.$notify.error("请登录或尝试登陆其他账号")
        this.$router.push('/403')
      }
    })

  },


  computed: {
    weekRange: {
      get() {
        return this.course.weekRange;
      },
      set(newWeekRange) {
        this.course.weekRange = newWeekRange;
      },
    },
  },

}
</script>

<style></style>