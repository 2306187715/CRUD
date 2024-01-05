<template>
  <div class="login-wrap">
    <div class="ms-login">
      <div class="ms-title">后台管理系统</div>
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="0px" class="ms-content">
        <el-form-item prop="userId" inputmode="number">
          <el-input v-model="ruleForm.userId" placeholder="userId">
            <el-button slot="prepend" icon="el-icon-lx-people"></el-button>
          </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input type="password" placeholder="password" v-model="ruleForm.password"
            @keyup.enter.native="submitForm('ruleForm')">
            <el-button slot="prepend" icon="el-icon-lx-lock"></el-button>
          </el-input>
        </el-form-item>
        <div class="login-btn">
          <el-button type="primary" @click="submitForm('ruleForm')">登录</el-button>
        </div>
        <p class="login-tips">登录方式：
          <el-radio-group v-model="ruleForm.radio">
            <el-radio :label="3" :change="changeRole()" style="color: rgb(188, 164, 120);">学生</el-radio>
            <el-radio :label="6" style="color: rgb(161, 98, 163);">教师</el-radio>
            <el-radio :label="9" style="color: rgb(122, 71, 71);">管理员</el-radio>
          </el-radio-group>
        </p>
      </el-form>
    </div>
  </div>
</template>

<script>
import { doLogin } from '../api/login';
import { setRole, setRoleList, setToken, setUserId } from '../utils/auth'
import router from '../router';

export default {
  data() {
    return {
      ruleForm: {
        userId: '123123',
        password: '123123',
        radio: 3
      },
      loginInfo: {},
      rules: {
        userId: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          // { type: 'integer', message: '请输入正确格式的学号/职工号', trigger: 'blur' },
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 6, max: 16, message: '长度在 6 到 16 个字符', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          doLogin(this.ruleForm).then(res => {
            if (res.data.code == 1) {
              this.loginInfo = res.data.data
              setUserId(this.ruleForm.userId)
              setToken(this.loginInfo.token)
              setRoleList(this.loginInfo.permissionList)
              router.push('/dashboard');
            } else {
              this.$notify.error(res.data.msg)
            }

          }).catch(err => {
            console.log(err);
          });

        } else {
          console.log('error submit!!');
        }
      });
    },
    changeRole() {
      switch (this.ruleForm.radio) {
        case 3:
          setRole("stu")
          // console.log(this.ruleForm.radio)
          break;
        case 6:
          setRole("teacher")
          // console.log("6")
          break;
        case 9:
          setRole("admin")
          // console.log("9")
          break;

        default:
          break;
      }
    },
  }
}
</script>

<style scoped>
.login-wrap {
  position: relative;
  width: 100%;
  height: 100%;
  background-size: 100%;
}

.ms-title {
  width: 100%;
  line-height: 50px;
  text-align: center;
  font-size: 20px;
  color: #fff;
  border-bottom: 1px solid #ddd;
}

.ms-login {
  position: absolute;
  left: 50%;
  top: 50%;
  width: 350px;
  margin: -190px 0 0 -175px;
  border-radius: 5px;
  background: rgba(255, 255, 255, 0.3);
  overflow: hidden;
}

.ms-content {
  padding: 30px 30px;
}

.login-btn {
  text-align: center;
}

.login-btn button {
  width: 100%;
  height: 36px;
  margin-bottom: 10px;
}

.login-tips {
  font-size: 12px;
  line-height: 30px;
  color: #fff;
}
</style>