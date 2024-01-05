<template>
  <div>
    <!--SearchForm 搜索表单-->
    <el-form :inline="true" :model="searchTeacher" class="demo-form-inline">
      <el-form-item label="职工号">
        <el-input v-model="searchTeacher.id" placeholder="请输入教师职工号" @change="page"></el-input>
      </el-form-item>
      <el-form-item label="学院">
        <el-select v-model="searchTeacher.college" placeholder="请选择" @change="page">
          <el-option v-for="item in collegeList" :key="item.value" :label="item.name" :value="item.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="部门">
        <el-select v-model="searchTeacher.dept" placeholder="请选择" @change="page">
          <el-option v-for="item in deptList" :key="item.value" :label="item.name" :value="item.id"></el-option>
        </el-select>
      </el-form-item>


      <el-form-item>
        <el-button type="primary" @click="doSearch">查询</el-button>
        <el-button type="info" @click="clear()">清空</el-button>
      </el-form-item>
    </el-form>


    <!-- button -->
    <el-row>
      <el-button type="success" @click="dialogFormVisible = true">- 添加教师</el-button>
      <el-button type="danger" @click="deleteByIds" v-if="multipleSelection.length > 1">- 批量删除</el-button>
      <h6>Tips:选择多条可使用批量删除</h6>

    </el-row>


    <!--dialog 表单-->
    <el-dialog ref="form" title="编辑教师" :visible.sync="dialogFormVisible" width="30%" :before-close='vanish'>
      <el-form ref="form" :model="teacher" label-width="80px" size="mini" :rules="rules">
        <el-form-item label="职工号" prop="id">
          <el-input v-model="teacher.id" :disabled="disable"></el-input>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="teacher.name"></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="teacher.password"></el-input>
        </el-form-item>

        <el-form-item label="性别">
          <el-select v-model="teacher.gender" placeholder="请选择" style="width:100%">
            <el-option v-for="item in genderList" :key="item.value" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>

        <el-form-item label="头像">
          <!-- <el-upload class="avatar-uploader" action="/api/upload" :headers="token" name="image" :show-file-list="false"
              :on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload">
              <img v-if="teacher.image" :src="teacher.image" class="avatar" />
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload> -->
        </el-form-item>
        <el-form-item label="职称">
          <el-select v-model="teacher.title" placeholder="请选择" style="width:100%">
            <el-option v-for="item in titleList" :key="item.value" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>

        <el-form-item label="学院" prop="collegeId">
          <el-select v-model="teacher.collegeId" placeholder="请选择" style="width:100%">
            <el-option v-for="item in collegeList" :key="item.value" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="部门" prop="deptId">
          <el-select v-model="teacher.deptId" placeholder="请选择" style="width:100%">
            <el-option v-for="item in deptList" :key="item.value" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="add()">提交</el-button>
          <el-button @click="vanish">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>





    <!-- Table 表格 -->
    <el-table :data="tableData" stripe style="width: 100%" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"></el-table-column>
      <el-table-column prop="id" label="职工号" width="auto">
      </el-table-column>
      <el-table-column prop="name" label="姓名" width="auto">
      </el-table-column>
      <el-table-column prop="password" label="密码" width="auto">
      </el-table-column>
      <el-table-column prop="gender" label="性别" width="50">
        <template slot-scope="scope">
          <span style="margin-right: 10px">
            {{ scope.row.gender == "1" ? "男" : "女" }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="image" label="照片" width="auto">
        <template slot-scope="{ row }">
          <el-image style="width: auto; height: 40px; border: none; cursor: pointer" :src="row.image"></el-image>
        </template>
      </el-table-column>
      <el-table-column prop="title" label="职称" width="80">
        <template slot-scope="scope">
          <span style="margin-right: 10px" v-if="scope.row.title == 1">班主任</span>
          <span style="margin-right: 10px" v-if="scope.row.title == 2">讲师</span>
          <span style="margin-right: 10px" v-if="scope.row.title == 3">学工主管</span>
          <span style="margin-right: 10px" v-if="scope.row.title == 4">教研主管</span>
        </template>
      </el-table-column>
      <el-table-column prop="college" label="学院" :formatter="formatCollege" width="auto">
      </el-table-column>
      <el-table-column prop="dept" label="部门" :formatter="formatDept" width="auto">
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间" width="170">
      </el-table-column>
      <el-table-column prop="updateTime" label="最后修改时间" width="170">
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
import { page, add, update, deleteById, selectById, getInfo } from "@/api/teacher.js";
import { selectAllColleges } from "../api/college";
import { selectAllDepts } from "../api/dept";


export default {
  data() {
    return {
      // tag
      disable: false,
      addUpdateTags: '',
      background: true,
      // 页数/总量/页码
      pageSize: 5,
      totalCount: 100,
      currentPage: 1,

      //搜索表单
      searchTeacher: {
        id: '',
        college: '',
        dept: ''
      },
      // 数据
      tableData: [],
      // 学院列表
      collegeList: [],
      // 部门列表
      deptList: [],
      // 职称列表
      titleList: [{
        id: 1,
        name: "班主任",
      },
      {
        id: 2,
        name: "讲师",
      },
      {
        id: 3,
        name: "学工主管",
      },
      {
        id: 4,
        name: "教研主管",
      }],

      // 存储部门ID到部门名称的映射
      deptMap: {},
      // 存储学院ID到学院名称的映射
      collegeMap: {},
      // 被选中的id数组
      selectedIds: [],
      // 复选框选中数据集合
      multipleSelection: [],

      fileList: [],
      // 临时实体类对象列表
      teacher: {
        id: null,
        name: '',
        image: "",
        collegeId: null,
        deptId: null
      },
      genderList: [{ id: 1, name: "男" }, { id: 2, name: "女" }],

      dialogFormVisible: false,

      rules: {
        id: [
          { required: true, message: 'id不能为空', trigger: 'change' }
        ],
        name: [
          { required: true, message: '姓名不能为空', trigger: 'change' },
          { min: 1, max: 8, message: '姓名长度不得超过8位', trigger: 'change' },
        ],
        collegeId: [
          { required: true, message: '学院不能为空', trigger: 'blur' }
        ],
        deptId: [
          { required: true, message: '学院不能为空', trigger: 'blur' }
        ],
      }

    }
  },
  mounted() {
    this.page()
    selectAllColleges().then((res) => {
      this.collegeList = res.data.data
      // 构建学院ID到学院名称的映射
      this.collegeMap = this.collegeList.reduce((map, college) => {
        map[college.id] = college.name;
        return map;
      }, {});
    });
    selectAllDepts().then((res) => {
      this.deptList = res.data.data
      // 构建部门ID到部门名称的映射
      this.deptMap = this.deptList.reduce((map, dept) => {
        map[dept.id] = dept.name;
        return map;
      }, {});
    });
  },
  methods: {




    // 分页查询
    page() {
      page(
        this.currentPage,
        this.pageSize,
        this.searchTeacher.id,
        this.searchTeacher.college,
        this.searchTeacher.dept,
      ).then((res) => {
        this.totalCount = res.data.data.total;
        this.tableData = res.data.data.rows;
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
    // 执行搜索
    doSearch() {
      this.page();
    },
    // 清空搜索栏
    clear() {
      this.searchTeacher = {
        id: '',
        college: '',
        dept: ''
      },
        this.page()
    },
    // 复选框调用
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    echo(id) {
      this.disable = true
      this.dialogFormVisible = true;
      this.addUpdateTags = "update";
      //查询回显
      selectById(id).then((res) => {
        if (res.data.code == 1) {
          this.teacher = res.data.data;
        }
      })
    },
    deleteById(id) {
      this.$confirm("是否删除所选教师？", {
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
    // 批量删除
    deleteByIds() {
      this.$confirm("此操作将删除该数据, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        // 单独复制一份id数组,multipleSelection的每一项都是对象
        for (let i = 0; i < this.multipleSelection.length; i++) {
          this.selectedIds[i] = this.multipleSelection[i].id;
        }
        console.log(this.multipleSelection)
        console.log(this.selectedIds)
        deleteById(this.selectedIds).then((res) => {
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

    add() {

      if (this.teacher.id == null || this.teacher.name == null || this.teacher.collegeId == null || this.teacher.deptId == null || this.teacher.id == '' || this.teacher.name == '' || this.teacher.collegeId == '' || this.teacher.deptId == ''
      ) {
        this.$notify.error('请输入必填项')
      } else {

        let tempRes;
        if (this.addUpdateTags == "update") {
          tempRes = update(this.teacher)
        } else {
          tempRes = add(this.teacher)
        }

        tempRes.then((res) => {
          if (res.data.code == 1) {
            this.dialogFormVisible = false;
            this.page();
            this.teacher = { image: "", },
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
        this.disable = false
        this.addUpdateTags = '';
      }
    },

    formatCollege(row) {
      // 使用映射将学院ID转换为学院名称
      return this.collegeMap[row.collegeId];
    },
    formatDept(row) {
      // 使用映射将学院ID转换为学院名称
      return this.deptMap[row.deptId];
    },

    vanish() {
      this.disable = false
      this.dialogFormVisible = false,
        this.teacher = {
          image: "",
        },
        this.addUpdateTags = ''
    },
  },

  created() {
    getInfo().then((res) => {
      if (res == null) {
        this.$notify.error("请登录或尝试登陆其他账号")
        this.$router.push('/403')
      }
    })

  },

}

</script>
  
<style></style>