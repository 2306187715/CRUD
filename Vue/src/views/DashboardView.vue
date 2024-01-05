<template>
  <div>
    <el-row>
      <el-col :span="24">
        <h1>你的身份：{{ roles }}</h1>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { getRole, getUserId } from '../utils/auth';
import { deleteRequest, getRequest, postRequest, putRequest } from '@/utils/request'

export default {
  data() {
    return {
      roles: '',
      userId: '',
    }
  },
  methods: {


    

  },
  mounted() {
    this.roles = getRole()
    this.userId = getUserId()
  },

  created() {
    getRequest('/dashboard/getInfo').then((res) => {
      if (res == null) {
        this.$notify.error("请登录或尝试登陆其他账号")
        this.$router.push('/403')
      }
    })

  }


}
</script>

<style></style>