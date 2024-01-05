<template>
  <div>
    <!--SearchForm 搜索表单-->
    <el-form :inline="true" :model="searchStu" class="demo-form-inline">
      <el-form-item label="学号">
        <el-input v-model="searchStu.id" placeholder="请输入学生学号" @change="page"></el-input>
      </el-form-item>
      <el-form-item label="学院">
        <el-select v-model="searchStu.college" placeholder="请选择" @change="page">
          <el-option v-for="item in collegeList" :key="item.value" :label="item.name" :value="item.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="性别">
        <el-select v-model="searchStu.gender" placeholder="请选择" @change="page">
          <el-option label="男" value="1"></el-option>
          <el-option label="女" value="0"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="生日">
        <el-date-picker v-model="birthday" type="daterange" range-separator="至" start-placeholder="开始日期"
          end-placeholder="结束日期" format="yyyy-MM-dd" value-format="yyyy-MM-dd" @change="page">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="doSearch">查询</el-button>
        <el-button type="info" @click="clear()">清空</el-button>
      </el-form-item>
    </el-form>


    <!-- button -->
    <el-row style="max-height:;">
      <!-- 新增 -->
      <el-button type="success" @click="dialogFormVisible = true" style="margin-left: 10px;">- 添加学生</el-button>



      <!-- 导入dialog -->
      <el-button type="primary" style="margin-left: 10px;" icon="el-icon-upload" @click="dialogUploadVisible = true">-
        导入excel</el-button>

      <el-dialog title="导入excel" :visible.sync="dialogUploadVisible">
        <div id="upload">
          <el-upload action="http://localhost:8080/students/upload" :on-success="successUpload" accept=".xls, .xlsx" drag>
            <i class="el-icon-upload"></i>
            <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
          </el-upload>
        </div>
      </el-dialog>



      <!-- 导入导出 -->
      <!-- <el-upload action="http://localhost:8080/students/upload" :on-success="successUpload" accept=".xls, .xlsx"
        style="display: inline-block;">
        <el-button type="primary" style="margin-left: 10px;" icon="el-icon-upload">-
          导入excel</el-button>
      </el-upload> -->
      <el-button type="primary" @click="exp()" style="margin-left: 10px;" icon="el-icon-download">- 导出excel</el-button>

      <!-- 批量删除 -->
      <el-button type="danger" @click="deleteByIds" v-if="multipleSelection.length > 1">- 批量删除</el-button>
      <h6>Tips:选择多条可使用批量删除</h6>
    </el-row>


    <!--dialog 表单-->
    <el-dialog ref="form" title="编辑学生" :visible.sync="dialogFormVisible" width="30%" :before-close='vanish'>
      <el-form ref="form" :model="stu" label-width="80px" size="mini" :rules="rules">
        <el-form-item label="学号" prop="id">
          <el-input v-model="stu.id" :disabled="disable"></el-input>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="stu.name"></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="stu.password"></el-input>
        </el-form-item>

        <el-form-item label="性别">
          <el-select v-model="stu.gender" placeholder="请选择" style="width:100%">
            <el-option v-for="item in genderList" :key="item.value" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>

        <el-form-item label="头像">
          <!-- <el-upload class="avatar-uploader" action="/api/upload" :headers="token" name="image" :show-file-list="false"
            :on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload">
            <img v-if="stu.image" :src="stu.image" class="avatar" />
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload> -->
        </el-form-item>

        <el-form-item label="生日">
          <el-date-picker v-model="stu.birthday" clearable type="date" placeholder="选择日期" size="small"
            style="width:100%"></el-date-picker>
        </el-form-item>

        <el-form-item label="学院" prop="collegeId">
          <el-select v-model="stu.collegeId" placeholder="请选择" style="width:100%">
            <el-option v-for="item in collegeList" :key="item.value" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="add()">提交</el-button>
          <el-button @click="vanish">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>





    <!-- Table 表格 -->
    <el-table :data="tableData" ref="table" stripe style="width: 100%" @selection-change="handleSelectionChange"
      :row-key="getRowKey">
      <el-table-column type="selection" width="55" align="center" :reserve-selection="true"></el-table-column>
      <el-table-column prop="id" label="学号" width="auto">
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
      <el-table-column prop="birthday" label="生日" width="auto">
      </el-table-column>
      <el-table-column prop="college" label="学院" :formatter="formatCollege" width="auto">
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
      :current-page="currentPage" :page-sizes="[5, 10, 20, 50, 100]" :page-size="5"
      layout="total, sizes, prev, pager, next, jumper" :total="totalCount">
    </el-pagination>
  </div>
</template>

<script>
import { page, add, update, deleteById, selectById, getInfo } from "@/api/stu.js";
import { selectAllColleges } from "../api/college";
import { upload } from '../api/stu';


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
      searchStu: {
        id: '',
        college: '',
        gender: "",
      },
      birthday: "",
      beginTime: "",
      endTime: "",
      // 数据
      tableData: [],
      // 学院列表
      collegeList: [],
      // 存储学院ID到学院名称的映射
      collegeMap: {},
      // 被选中的id数组
      selectedIds: [],
      // 复选框选中数据集合
      multipleSelection: [],

      fileList: [],
      // 临时实体类对象列表
      stu: {
        id: null,
        name: null,
        image: "",
        collegeId: null,
      },
      genderList: [{ id: 1, name: "男" }, { id: 2, name: "女" }],
      dialogFormVisible: false,
      dialogUploadVisible: false,


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
  },
  methods: {
    // test() {
    //   console.log(this.selectedIds)
    //   console.log(this.multipleSelection)
    // },
    exp() {
      location.href = "http://localhost:8080/students/export"
    },
    successUpload() {
      this.page();
      this.$notify.success("导入成功")
    },


    // 分页查询
    page() {
      page(
        this.currentPage,
        this.pageSize,
        this.searchStu.id,
        this.searchStu.gender,
        this.searchStu.college,
        this.beginTime,
        this.endTime,
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
      this.searchStu = {
        id: '',
        college: '',
        gender: "",
      },
        this.birthday = "",
        this.beginTime = "",
        this.endTime = "",
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
          this.stu = res.data.data;
        }
      })
    },
    deleteById(id) {
      this.$confirm("是否删除所选学生？", {
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
        this.$notify.info("已取消删除")
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
      if (this.stu.id == null || this.stu.collegeId == null || this.stu.name == null || this.stu.id == '' || this.stu.name == '') {
        this.$notify.error('请输入必填项')
      } else {
        let tempRes;
        console.log(this.addUpdateTags)
        if (this.addUpdateTags == "update") {
          tempRes = update(this.stu)
        } else {
          tempRes = add(this.stu)
        }

        tempRes.then((res) => {
          if (res.data.code == 1) {
            this.dialogFormVisible = false;
            this.page();
            this.stu = { image: "", },
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
        this.addUpdateTags = '';
        this.disable = false
      }
    },

    formatCollege(row) {
      // 使用映射将学院ID转换为学院名称
      return this.collegeMap[row.collegeId];
    },

    vanish() {
      this.dialogFormVisible = false,
      this.disable=false
        this.stu = {
          image: "",
        },
        this.addUpdateTags = ''
    },
    getRowKey(row) {
      return row.id
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



  watch: {
    birthday(val) {
      if (val && val.length >= 2) {
        this.beginTime = val[0];
        this.endTime = val[1];
      } else {
        this.beginTime = "";
        this.endTime = "";
      }
    },
  },
}

</script>

<style lang="scss" scoped>
.el-icon-upload:before {
  content: '\e7c3'
}

.el-icon-upload {
  font-size: 30px;
  margin: 4px 0 2px;
}
</style>