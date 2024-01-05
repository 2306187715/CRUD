<template>
  <div>
    <el-row style="font-size: 50px; text-align: center;">
      学期课表
      <el-button type="success" @click="refreshPage" size="big" style="text-align: center;" v-if="this.events.length != 0"
        icon="el-icon-refresh">更换学期</el-button>
    </el-row>
    <el-form :inline="true" :model="searchInfo" class="demo-form-inline">
      <!-- 学期选择器 -->
      <el-form-item label="学期：" v-if="this.events.length == 0">
        <el-select v-model="searchInfo.semesterId" placeholder="请选择" @change="doSearch()" style="width: 150px;">
          <el-option v-for="item in semesterList" :key="item.value" :label="item.name" :value="item.id">
          </el-option>
        </el-select>
        <span style="font-size: small; padding-left: 10px; color: brown;" v-if="this.events.length == 0">
          Tips:选择学期来查看对应课表
        </span>

      </el-form-item>
    </el-form>
    <!--  -->
    <el-table :data="timetable" :span-method="objectSpanMethod" border
      :header-cell-style="{ background: '#d9e5fd', color: 'black', fontWeight: 1000 }" :cell-style="tableCellStyle">
      <el-table-column prop="sjd" label="时间段" width="80" align="center">
      </el-table-column>
      <el-table-column prop="jc" label="节次" width="80" align="center">
      </el-table-column>
      <el-table-column prop="mon" label="星期一" align="center">
        <template slot-scope="scope">
          <h4>{{ scope.row.mon.title }}</h4>
          <div v-html="scope.row.mon.teacherId"></div>
        </template>
      </el-table-column>
      <el-table-column prop="tue" label="星期二" align="center">
        <template slot-scope="scope">
          <h4>{{ scope.row.tue.title }}</h4>
          <div v-html="scope.row.tue.content"></div>
        </template>
      </el-table-column>
      <el-table-column prop="wed" label="星期三" align="center">
        <template slot-scope="scope">
          <h4>{{ scope.row.wed.title }}</h4>
          <div v-html="scope.row.wed.teacherId"></div>
        </template>
      </el-table-column>
      <el-table-column prop="thu" label="星期四" align="center">
        <template slot-scope="scope">
          <h4>{{ scope.row.thu.title }}</h4>
          <div v-html="scope.row.thu.content"></div>
        </template>
      </el-table-column>
      <el-table-column prop="fri" label="星期五" align="center">
        <template slot-scope="scope">
          <h4>{{ scope.row.fri.title }}</h4>
          <div v-html="scope.row.fri.content"></div>
        </template>
      </el-table-column>
      <el-table-column prop="sat" label="星期六" align="center">
        <template slot-scope="scope">
          <h4>{{ scope.row.sat.title }}</h4>
          <div v-html="scope.row.sat.content"></div>
        </template>
      </el-table-column>
      <el-table-column prop="sun" label="星期日" align="center">
        <template slot-scope="scope">
          <h4>{{ scope.row.sun.title }}</h4>
          <div v-html="scope.row.sun.content"></div>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>


<script>
import { selectAllSemesters, getInfo } from "../api/semester";
import { selectAllTeachers } from "../api/teacher";
import { getRole, getUserId } from '../utils/auth';
import { getScheduleByStuId, getScheduleByTeacherId } from '../api/elective';


export default {

  data() {
    return {
      searchInfo: {
        userId: '',
        semesterId: '',
      },
      semesterList: [],
      teacherList: [],
      semesterMap: {},
      teacherMap: {},

      // userId: null,

      // 课程表数据
      length: 11,
      afternoonLength: 4,
      timetable: [],
      events: [],
      hoverOrderArr: [],
      weeks: ['mon', 'tue', 'wed', 'thu', 'fri', 'sat', 'sun'],
      initTable: [],
      temp: [null, null, null, null, null, null, null],

    }
  },
  methods: {
    doSearch() {
      // 清空课程表数据
      // this.events = [];
      this.searchInfo.userId = getUserId();

      if (getRole() == 'stu') {
        this.searchInfo = {
          stuId: this.searchInfo.userId,
          semesterId: this.searchInfo.semesterId
        };
        getScheduleByStuId(this.searchInfo).then((res) => {
          if (res.data.code == 1) {
            this.events = res.data.data;
            this.$forceUpdate();
            if (this.events.length == 0) {
              this.$notify.success('真好，一节课都没有')
            } else {
              this.mergeData();
              this.$notify({
                title: '已显示课表',
                type: 'success'
              });
            }
          } else {
            this.$notify({
              title: res.data.msg,
              type: 'error'
            });
          }
        });
      } else {
        this.searchInfo = {
          teacherId: this.searchInfo.userId,
          semesterId: this.searchInfo.semesterId
        };
        getScheduleByTeacherId(this.searchInfo).then((res) => {
          if (res.data.code == 1) {
            this.events = res.data.data;
            this.$forceUpdate();
            if (this.events.length == 0) {
              this.$notify.success('真好，一节课都没有')
            } else {
              this.mergeData();
              this.$notify({
                title: '已显示课表',
                type: 'success'
              });
            }
          } else {
            this.$notify({
              title: res.data.msg,
              type: 'error'
            });
          }
        });
      }


    },

    // 刷新页面
    refreshPage() {
      this.$router.go(0)
    },

    // 单元格添加背景色
    tableCellStyle(row) {
      if (row.row[row.column.property].title !== undefined) {
        return 'background-color:rgb(24 144 255 / 80%);color: #fff; border-radius:10px'
      }
    },
    // 构造课程表完整数据
    makeTimetable() {
      this.timetable = []
      for (let i = 0; i < this.length; i++) {
        let one = {
          sjd: '',
          jc: i + 1,
          mon: {},
          tue: {},
          wed: {},
          thu: {},
          fri: {},
          sat: {},
          sun: {}
        }
        if (i < 4) {
          one.sjd = '上午'
        } else if (i > 3 && i < (this.afternoonLength + 4)) {
          one.sjd = '下午'
        } else {
          one.sjd = '晚上'
        }
        this.timetable.push(one)
      }
    },

    mergeData() {
      // 合并数据
      if (this.events.length > 0) {
        for (let i = 0; i < this.events.length; i++) {
          // 获取星期几
          // console.log("mergeData", this.events)
          let week = this.weeks[this.events[i].dayOfWeek - 1]
          this.timetable[this.events[i].startTime - 1][week] = this.events[i]
        }
      }
    },
    objectSpanMethod({ row, column, rowIndex, columnIndex }) {
      if (columnIndex === 0) {
        if (rowIndex < 4) {
          if (rowIndex === 0) {
            return {
              rowspan: 4,
              colspan: 1
            }
          } else {
            return {
              rowspan: 0,
              colspan: 0
            }
          }
        } else if (rowIndex > 3 && rowIndex < (4 + this.afternoonLength)) {
          if (rowIndex === 4) {
            return {
              rowspan: this.afternoonLength,
              colspan: 1
            }
          } else {
            return {
              rowspan: 0,
              colspan: 0
            }
          }
        } else {
          if (rowIndex === (4 + this.afternoonLength)) {
            return {
              rowspan: this.length - 4 - this.afternoonLength,
              colspan: 1
            }
          } else {
            return {
              rowspan: 0,
              colspan: 0
            }
          }
        }
      }
      if (columnIndex === 2) {
        if (row.mon.title !== undefined) {
          this.temp[0] = row.mon;
          return {
            rowspan: row.mon.endTime - row.mon.startTime + 1,
            colspan: 1
          }
        } else {
          if (this.temp[0] == null) {
            return {
              rowspan: 1,
              colspan: 1,
            }
          }
          if (rowIndex >= this.temp[0].startTime && rowIndex < this.temp[0].endTime) {
            if (rowIndex == this.temp[0].endTime - 1) {
              this.temp[0] = null;
            }
            return {
              rowspan: 0,
              colspan: 0,
            }
          } else {
            this.temp[0] = null;
            return {
              rowspan: 1,
              colspan: 1,
            }
          }

        }
      }
      if (columnIndex === 3) {

        if (row.tue.title !== undefined) {
          this.temp[1] = row.tue;
          return {
            rowspan: row.tue.endTime - row.tue.startTime + 1,
            colspan: 1
          }
        } else {


          if (this.temp[1] == null) {
            return {
              rowspan: 1,
              colspan: 1,
            }
          }
          if (rowIndex >= this.temp[1].startTime && rowIndex < this.temp[1].endTime) {
            if (rowIndex == this.temp[1].endTime - 1) {
              this.temp[1] = null;
            }
            return {
              rowspan: 0,
              colspan: 0,
            }
          } else {
            this.temp[1] = null;
            return {
              rowspan: 1,
              colspan: 1,
            }
          }

        }
      }
      if (columnIndex === 4) {
        if (row.wed.title !== undefined) {
          this.temp[2] = row.wed;
          return {
            rowspan: row.wed.endTime - row.wed.startTime + 1,
            colspan: 1
          }
        } else {
          if (this.temp[2] == null) {
            return {
              rowspan: 1,
              colspan: 1,
            }
          }
          if (rowIndex >= this.temp[2].startTime && rowIndex < this.temp[2].endTime) {
            if (rowIndex == this.temp[2].endTime - 1) {
              this.temp[2] = null;
            }
            return {
              rowspan: 0,
              colspan: 0,
            }
          } else {
            this.temp[2] = null;
            return {
              rowspan: 1,
              colspan: 1,
            }
          }

        }
      }
      if (columnIndex === 5) {
        if (row.thu.title !== undefined) {
          this.temp[3] = row.thu;
          return {
            rowspan: row.thu.endTime - row.thu.startTime + 1,
            colspan: 1
          }
        } else {
          if (this.temp[3] == null) {
            return {
              rowspan: 1,
              colspan: 1,
            }
          }
          if (rowIndex >= this.temp[3].startTime && rowIndex < this.temp[3].endTime) {
            if (rowIndex == this.temp[3].endTime - 1) {
              this.temp[3] = null;
            }
            return {
              rowspan: 0,
              colspan: 0,
            }
          } else {
            this.temp[3] = null;
            return {
              rowspan: 1,
              colspan: 1,
            }
          }

        }
      }
      if (columnIndex === 6) {
        if (row.fri.title !== undefined) {
          this.temp[4] = row.fri;
          return {
            rowspan: row.fri.endTime - row.fri.startTime + 1,
            colspan: 1
          }
        } else {
          if (this.temp[4] == null) {
            return {
              rowspan: 1,
              colspan: 1,
            }
          }
          if (rowIndex >= this.temp[4].startTime && rowIndex < this.temp[4].endTime) {
            if (rowIndex == this.temp[4].endTime - 1) {
              this.temp[4] = null;
            }
            return {
              rowspan: 0,
              colspan: 0,
            }
          } else {
            this.temp[4] = null;
            return {
              rowspan: 1,
              colspan: 1,
            }
          }

        }
      }
      if (columnIndex === 7) {
        if (row.sat.title !== undefined) {
          this.temp[5] = row.sat;
          return {
            rowspan: row.sat.endTime - row.sat.startTime + 1,
            colspan: 1
          }
        } else {
          if (this.temp[5] == null) {
            return {
              rowspan: 1,
              colspan: 1,
            }
          }
          if (rowIndex >= this.temp[5].startTime && rowIndex < this.temp[5].endTime) {
            if (rowIndex == this.temp[5].endTime - 1) {
              this.temp[5] = null;
            }
            return {
              rowspan: 0,
              colspan: 0,
            }
          } else {
            this.temp[5] = null;
            return {
              rowspan: 1,
              colspan: 1,
            }
          }

        }
      }
      if (columnIndex === 8) {
        if (row.sun.title !== undefined) {
          this.temp[6] = row.sun;
          return {
            rowspan: row.sun.endTime - row.sun.startTime + 1,
            colspan: 1
          }
        } else {
          if (this.temp[6] == null) {
            return {
              rowspan: 1,
              colspan: 1,
            }
          }
          if (rowIndex >= this.temp[6].startTime && rowIndex < this.temp[6].endTime) {

            if (rowIndex == this.temp[6].endTime - 1) {
              this.temp[6] = null;
            }
            return {
              rowspan: 0,
              colspan: 0,
            }
          } else {
            this.temp[6] = null;
            return {
              rowspan: 1,
              colspan: 1,
            }
          }

        }
      }
    }

  },




  mounted() {
    // 学期map
    selectAllSemesters().then((res) => {
      this.semesterList = res.data.data

      this.semesterMap = this.semesterList.reduce((map, semester) => {
        map[semester.id] = semester.name
        return map;
      }, {});
    });
    // 教师map
    selectAllTeachers().then((res) => {
      this.teacherList = res.data.data

      this.teacherMap = this.teacherList.reduce((map, teacher) => {
        map[teacher.id] = teacher.name
        return map;
      }, {});
    });

    this.searchInfo.userId = getUserId()
    // console.log(this.events)
    // this.mergeData()

  },




  watch: {
    events: {
      handler(newVal, oldVal) {
        console.log("watch", this.events)
        this.mergeData()
      },
      deep: true
    }
  },

  created() {
    getInfo().then((res) => {
      if (res == null) {
        this.$notify.error("请登录或尝试登陆其他账号")
        this.$router.push('/403')
      } else {
        this.makeTimetable()
      }
    })

  },
}
</script>

<style></style>