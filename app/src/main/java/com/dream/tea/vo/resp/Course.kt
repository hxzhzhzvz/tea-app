package com.dream.tea.vo.resp

class Course {

    /**
     * id
     */
    var courseId: Long = 0

    /**
     * 这一节课对应的科目的id
     */
    var courseSubjectId: Long = 0

    /**
     * 课程的名字
     */
    var courseName: String = ""

    /**
     * 课程封面
     */
    var coverUrl: String = ""

    /**
     * 视频链接
     */
    var videoUrl: String = ""

}