package hengai.com.shishuo.bean;

import java.util.List;

/**
 * Created by yu on 2017/9/28.
 */

public class LessonBean {

    /**
     * code : 200
     * data : [{"code":"4626478814144269","courseIntroduction":{"introduce":"学生视频测试课程","introduceUrl":""},"endDate":1504886400,"endTime":1504886400,"isPackage":false,"isRecommend":true,"memo":"学生视频测试课程","personNum":1,"startDate":1504454400,"startTime":1504454400,"teachers":[{"teacherIcon":"http://59.110.42.139:9393/images/IDCards/dcd3e960-71f7-43be-ae28-ecaf8ca4d740.jpg","teacherName":"张3a"}],"title":"学生视频测试课程"}]
     * message : 操作成功!
     * result : 1
     * totalcourse : 1
     */

    private int code;
    private String message;
    private int result;
    private int totalcourse;
    /**
     * code : 4626478814144269
     * courseIntroduction : {"introduce":"学生视频测试课程","introduceUrl":""}
     * endDate : 1504886400
     * endTime : 1504886400
     * isPackage : false
     * isRecommend : true
     * memo : 学生视频测试课程
     * personNum : 1
     * startDate : 1504454400
     * startTime : 1504454400
     * teachers : [{"teacherIcon":"http://59.110.42.139:9393/images/IDCards/dcd3e960-71f7-43be-ae28-ecaf8ca4d740.jpg","teacherName":"张3a"}]
     * title : 学生视频测试课程
     */

    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getTotalcourse() {
        return totalcourse;
    }

    public void setTotalcourse(int totalcourse) {
        this.totalcourse = totalcourse;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String code;
        /**
         * introduce : 学生视频测试课程
         * introduceUrl :
         */

        private CourseIntroductionBean courseIntroduction;
        private int endDate;
        private int endTime;
        private boolean isPackage;
        private boolean isRecommend;
        private String memo;
        private int personNum;
        private int startDate;
        private int startTime;
        private String title;
        /**
         * teacherIcon : http://59.110.42.139:9393/images/IDCards/dcd3e960-71f7-43be-ae28-ecaf8ca4d740.jpg
         * teacherName : 张3a
         */

        private List<TeachersBean> teachers;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public CourseIntroductionBean getCourseIntroduction() {
            return courseIntroduction;
        }

        public void setCourseIntroduction(CourseIntroductionBean courseIntroduction) {
            this.courseIntroduction = courseIntroduction;
        }

        public int getEndDate() {
            return endDate;
        }

        public void setEndDate(int endDate) {
            this.endDate = endDate;
        }

        public int getEndTime() {
            return endTime;
        }

        public void setEndTime(int endTime) {
            this.endTime = endTime;
        }

        public boolean isIsPackage() {
            return isPackage;
        }

        public void setIsPackage(boolean isPackage) {
            this.isPackage = isPackage;
        }

        public boolean isIsRecommend() {
            return isRecommend;
        }

        public void setIsRecommend(boolean isRecommend) {
            this.isRecommend = isRecommend;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }

        public int getPersonNum() {
            return personNum;
        }

        public void setPersonNum(int personNum) {
            this.personNum = personNum;
        }

        public int getStartDate() {
            return startDate;
        }

        public void setStartDate(int startDate) {
            this.startDate = startDate;
        }

        public int getStartTime() {
            return startTime;
        }

        public void setStartTime(int startTime) {
            this.startTime = startTime;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<TeachersBean> getTeachers() {
            return teachers;
        }

        public void setTeachers(List<TeachersBean> teachers) {
            this.teachers = teachers;
        }

        public static class CourseIntroductionBean {
            private String introduce;
            private String introduceUrl;

            public String getIntroduce() {
                return introduce;
            }

            public void setIntroduce(String introduce) {
                this.introduce = introduce;
            }

            public String getIntroduceUrl() {
                return introduceUrl;
            }

            public void setIntroduceUrl(String introduceUrl) {
                this.introduceUrl = introduceUrl;
            }
        }

        public static class TeachersBean {
            private String teacherIcon;
            private String teacherName;

            public String getTeacherIcon() {
                return teacherIcon;
            }

            public void setTeacherIcon(String teacherIcon) {
                this.teacherIcon = teacherIcon;
            }

            public String getTeacherName() {
                return teacherName;
            }

            public void setTeacherName(String teacherName) {
                this.teacherName = teacherName;
            }
        }
    }
}
