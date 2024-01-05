/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80034
 Source Host           : localhost:3306
 Source Schema         : management

 Target Server Type    : MySQL
 Target Server Version : 80034
 File Encoding         : 65001

 Date: 05/01/2024 22:58:33
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for college
-- ----------------------------
DROP TABLE IF EXISTS `college`;
CREATE TABLE `college`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '学院id',
  `name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '学院名',
  `number` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '学院人数',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `college_pk`(`name` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 36 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course`  (
  `id` int UNSIGNED NOT NULL COMMENT '课程id',
  `name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '课程名',
  `teacher_id` int UNSIGNED NOT NULL COMMENT '教师id',
  `credit` tinyint UNSIGNED NULL DEFAULT NULL COMMENT '学分',
  `hours` tinyint NULL DEFAULT NULL COMMENT '学时',
  `semester_id` tinyint UNSIGNED NULL DEFAULT NULL COMMENT '对应学期',
  `start_week` tinyint UNSIGNED NULL DEFAULT NULL COMMENT '课程开始周',
  `end_week` tinyint UNSIGNED NULL DEFAULT NULL COMMENT '课程结束周',
  `start_time` time NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` time NULL DEFAULT NULL COMMENT '结束时间',
  `day_of_week` tinyint NULL DEFAULT NULL COMMENT '课程上课日期',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `course_pk2`(`name` ASC) USING BTREE,
  INDEX `course_teacher_id_fk`(`teacher_id` ASC) USING BTREE,
  INDEX `course_semester_id_fk`(`semester_id` ASC) USING BTREE,
  CONSTRAINT `course_semester_id_fk` FOREIGN KEY (`semester_id`) REFERENCES `semester` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `course_teacher_id_fk` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dept
-- ----------------------------
DROP TABLE IF EXISTS `dept`;
CREATE TABLE `dept`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '部门名称',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `dept_pk2`(`name` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for elective
-- ----------------------------
DROP TABLE IF EXISTS `elective`;
CREATE TABLE `elective`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '选课记录id',
  `stu_id` int UNSIGNED NULL DEFAULT NULL COMMENT '学生id',
  `course_id` int UNSIGNED NULL DEFAULT NULL COMMENT '课程id',
  `semester_id` tinyint UNSIGNED NULL DEFAULT NULL COMMENT '学期id',
  `score` tinyint UNSIGNED NULL DEFAULT NULL COMMENT '分数',
  `status` tinyint NOT NULL COMMENT '状态 0已选课 1已退课 2挂科',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `elective_stu_id_fk`(`stu_id` ASC) USING BTREE,
  INDEX `elective_course_id_fk`(`course_id` ASC) USING BTREE,
  INDEX `elective_semester_id_fk`(`semester_id` ASC) USING BTREE,
  CONSTRAINT `elective_course_id_fk` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `elective_semester_id_fk` FOREIGN KEY (`semester_id`) REFERENCES `semester` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `elective_stu_id_fk` FOREIGN KEY (`stu_id`) REFERENCES `stu` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 49 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for semester
-- ----------------------------
DROP TABLE IF EXISTS `semester`;
CREATE TABLE `semester`  (
  `id` tinyint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '学期标识格式',
  `start_date` date NULL DEFAULT NULL COMMENT '学期开始时间',
  `end_date` date NULL DEFAULT NULL COMMENT '学期结束时间',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for stu
-- ----------------------------
DROP TABLE IF EXISTS `stu`;
CREATE TABLE `stu`  (
  `id` int UNSIGNED NOT NULL COMMENT '学号，入学年份+学院+编号',
  `name` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '姓名',
  `password` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '123456' COMMENT '密码',
  `gender` tinyint UNSIGNED NULL DEFAULT NULL COMMENT '性别',
  `image` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '图像',
  `birthday` date NULL DEFAULT NULL COMMENT '出生年月',
  `college_id` int UNSIGNED NOT NULL COMMENT '所在院校',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `stu_college_id_fk`(`college_id` ASC) USING BTREE,
  CONSTRAINT `stu_college_id_fk` FOREIGN KEY (`college_id`) REFERENCES `college` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher`  (
  `id` int UNSIGNED NOT NULL COMMENT '教师id',
  `name` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '姓名',
  `password` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'teacher' COMMENT '密码',
  `gender` tinyint UNSIGNED NULL DEFAULT NULL COMMENT '性别',
  `image` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '图像',
  `title` tinyint UNSIGNED NULL DEFAULT NULL COMMENT '职称 1助教 2讲师 3副教授 4教授 5博士生导师',
  `college_id` int UNSIGNED NULL DEFAULT NULL COMMENT '所在院校',
  `dept_id` int UNSIGNED NULL DEFAULT NULL COMMENT '所在部门',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `teacher_college_id_fk`(`college_id` ASC) USING BTREE,
  INDEX `teacher_dept_id_fk`(`dept_id` ASC) USING BTREE,
  CONSTRAINT `teacher_college_id_fk` FOREIGN KEY (`college_id`) REFERENCES `college` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `teacher_dept_id_fk` FOREIGN KEY (`dept_id`) REFERENCES `dept` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Triggers structure for table stu
-- ----------------------------
DROP TRIGGER IF EXISTS `insert_trigger`;
delimiter ;;
CREATE TRIGGER `insert_trigger` AFTER INSERT ON `stu` FOR EACH ROW BEGIN
    UPDATE management.college AS coll
    SET coll.number = (SELECT COUNT(*) FROM management.stu WHERE stu.college_id = coll.id)
    WHERE coll.id = NEW.college_id;
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table stu
-- ----------------------------
DROP TRIGGER IF EXISTS `update_trigger`;
delimiter ;;
CREATE TRIGGER `update_trigger` AFTER UPDATE ON `stu` FOR EACH ROW BEGIN
    -- 更新旧学院的人数
    UPDATE management.college AS coll
    SET coll.number = (SELECT COUNT(*) FROM management.stu WHERE stu.college_id = coll.id)
    WHERE coll.id = OLD.college_id;

    -- 更新新学院的人数
    UPDATE management.college AS new_coll
    SET new_coll.number = (SELECT COUNT(*) FROM management.stu WHERE stu.college_id = new_coll.id)
    WHERE new_coll.id = NEW.college_id;
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table stu
-- ----------------------------
DROP TRIGGER IF EXISTS `delete_trigger`;
delimiter ;;
CREATE TRIGGER `delete_trigger` AFTER DELETE ON `stu` FOR EACH ROW BEGIN
    UPDATE management.college AS coll
    SET coll.number = (SELECT COUNT(*) FROM management.stu WHERE stu.college_id = coll.id)
    WHERE coll.id = OLD.college_id;
END
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
