package com.dream.tea.client

import com.dream.tea.vo.resp.Course

/**
 * 通过http请求来访问数据
 */
class CourseClient {

    fun getCourseListByCourseSubjectId(courseSubjectId: Long): List<Course> {
        val list = ArrayList<Course>()
        for (idx in 1..100) {
            val course = Course()
            course.courseSubjectId = courseSubjectId
            course.courseId = idx.toLong()
            if (idx % 2 == 0) {
                course.courseName = "灰太狼和喜羊羊的友谊（二）"
                course.videoUrl = "http://vfx.mtime.cn/Video/2019/03/19/mp4/190319222227698228.mp4"
                course.coverUrl =
                    "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=170099600,2854157420&fm=26&gp=0.jpg"
            } else {
                course.courseName = "小灰灰成长历险记"
                course.videoUrl = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"
                course.coverUrl =
                    "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1558124247,3969933314&fm=15&gp=0.jpg"
            }
            list.add(course)
        }
        return list
    }

}