<template>
  <div class="sidebar">
    <el-menu class="sidebar-el-menu" :default-active="onRoutes" :collapse="collapse" background-color="#324157"
      text-color="#bfcbd9" active-text-color="#20a0ff" unique-opened router>
      <template v-for="item in items">
        <!-- 一级-有子节点 -->
        <template v-if="item.subs && item.roles.includes(role)">
          <el-submenu :index="item.index" :key="item.index">
            <template slot="title">
              <i :class="item.icon"></i><span slot="title">{{ item.title }}</span>
            </template>
            <!-- 二级 -->
            <template v-for="subItem in item.subs">
              <el-submenu v-if="subItem.subs && subItem.roles.includes(role)" :index="subItem.index"
                :key="subItem.index">
                <template slot="title">{{ subItem.title }}</template>
              </el-submenu>
              <el-menu-item v-else-if="subItem.roles.includes(role)" :index="subItem.index" :key="subItem.index">
                {{ subItem.title }}
              </el-menu-item>
            </template>
          </el-submenu>
        </template>
        <!-- 一级-无子节点 -->
        <el-menu-item v-else-if="item.roles.includes(role)" :index="item.index" :key="item.index">
          <i :class="item.icon"></i><span slot="title">{{ item.title }}</span>
        </el-menu-item>
      </template>
    </el-menu>
  </div>
</template>


<script>
import { getRole } from '../../utils/auth';
import bus from '../common/bus';
export default {
  data() {
    return {
      role: '',
      collapse: false,
      items: [
        {
          icon: 'el-icon-lx-home',
          index: 'dashboard',
          title: '系统首页',
          roles: ['admin', 'teacher', 'stu'],
        },
        {
          icon: 'el-icon-lx-group',
          index: '2',
          title: '学生信息管理',
          roles: ['admin', 'teacher', 'stu'],
          subs: [
            {
              index: 'stu',
              title: '学生管理',
              roles: ['admin']
            },
            {
              index: 'college',
              title: '学院管理',
              roles: ['admin', 'teacher', 'stu'],
            },
          ]
        },
        {
          icon: 'el-icon-lx-service',
          index: '4',
          title: '教师信息管理',
          roles: ['admin'],
          subs: [
            {
              index: 'teacher',
              title: '教师管理',
              roles: ['admin'],
            },
            {
              index: 'dept',
              title: '部门管理',
              roles: ['admin', 'teacher'],
            },
          ]
        },
        {
          icon: 'el-icon-lx-addressbook',
          index: 'elective',
          title: '选课信息管理',
          roles: ['admin', 'teacher', 'stu'],
          subs: [
            {
              index: 'elective',
              title: '选课信息',
              roles: ['admin', 'teacher'],
            },
            {
              index: 'course',
              title: '课程管理',
              roles: ['admin', 'teacher', 'stu'],
            },
          ]
        },
        {
          icon: 'el-icon-lx-global',
          index: 'semester',
          title: '学期信息管理',
          roles: ['admin', 'teacher'],
        },
        {
          icon: 'el-icon-lx-calendar',
          index: 'schedule',
          title: '课表信息管理',
          roles: ['stu', 'teacher'],
        }
      ]
    }
  },
  computed: {
    onRoutes() {
      return this.$route.path.replace('/', '');
    }
  },
  created() {
    // 通过 Event Bus 进行组件间通信，来折叠侧边栏
    bus.$on('collapse', msg => {
      this.collapse = msg;
    })
  },
  methods: {

  },
  mounted() {
    this.role = getRole()
    }
}
</script>

<style scoped>
.sidebar {
  display: block;
  position: absolute;
  left: 0;
  top: 70px;
  bottom: 0;
  overflow-y: scroll;
}

.sidebar::-webkit-scrollbar {
  width: 0;
}

.sidebar-el-menu:not(.el-menu--collapse) {
  width: 250px;
}

.sidebar>ul {
  height: 100%;
}
</style>
