package hengai.com.shishuo.bean;

import java.util.List;

/**
 * Created by yu on 2017/8/15.
 */

public class HomeBean {

    /**
     * message : 成功获取主页json！
     * result : 1
     * data : {"practice":{"personNum":5,"practiceList":[{"author":"马老六","authorType":"学生","title":"中学语文试讲","commnetNum":100,"classType":"说课","coverImage":"http://img.liangshiba.com/uploads/img/2017/05/24/149561596132.jpg"}]},"live":[{"startDate":1504022400,"memo":"1","personNum":1,"isPackage":false,"endDate":1507478400,"code":"9387804063658849","endTime":1507478400,"teachers":[{"teacherIcon":"","teacherName":"贾秀涛"}],"id":134,"startTime":1504022400,"title":"测试笔试直播1","courseIntroduction":{"introduce":"1","introduceUrl":""},"isRecommend":true}]}
     * code : 200
     */

    private String message;
    private int result;
    /**
     * practice : {"personNum":5,"practiceList":[{"author":"马老六","authorType":"学生","title":"中学语文试讲","commnetNum":100,"classType":"说课","coverImage":"http://img.liangshiba.com/uploads/img/2017/05/24/149561596132.jpg"}]}
     * live : [{"startDate":1504022400,"memo":"1","personNum":1,"isPackage":false,"endDate":1507478400,"code":"9387804063658849","endTime":1507478400,"teachers":[{"teacherIcon":"","teacherName":"贾秀涛"}],"id":134,"startTime":1504022400,"title":"测试笔试直播1","courseIntroduction":{"introduce":"1","introduceUrl":""},"isRecommend":true}]
     */

    private DataBean data;
    private int code;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static class DataBean {
        /**
         * personNum : 5
         * practiceList : [{"author":"马老六","authorType":"学生","title":"中学语文试讲","commnetNum":100,"classType":"说课","coverImage":"http://img.liangshiba.com/uploads/img/2017/05/24/149561596132.jpg"}]
         */

        private PracticeBean practice;
        /**
         * startDate : 1504022400
         * memo : 1
         * personNum : 1
         * isPackage : false
         * endDate : 1507478400
         * code : 9387804063658849
         * endTime : 1507478400
         * teachers : [{"teacherIcon":"","teacherName":"贾秀涛"}]
         * id : 134
         * startTime : 1504022400
         * title : 测试笔试直播1
         * courseIntroduction : {"introduce":"1","introduceUrl":""}
         * isRecommend : true
         */

        private List<LiveBean> live;

        public PracticeBean getPractice() {
            return practice;
        }

        public void setPractice(PracticeBean practice) {
            this.practice = practice;
        }

        public List<LiveBean> getLive() {
            return live;
        }

        public void setLive(List<LiveBean> live) {
            this.live = live;
        }

        public static class PracticeBean {
            private int personNum;
            /**
             * author : 马老六
             * authorType : 学生
             * title : 中学语文试讲
             * commnetNum : 100
             * classType : 说课
             * coverImage : http://img.liangshiba.com/uploads/img/2017/05/24/149561596132.jpg
             */

            private List<PracticeListBean> practiceList;

            public int getPersonNum() {
                return personNum;
            }

            public void setPersonNum(int personNum) {
                this.personNum = personNum;
            }

            public List<PracticeListBean> getPracticeList() {
                return practiceList;
            }

            public void setPracticeList(List<PracticeListBean> practiceList) {
                this.practiceList = practiceList;
            }

            public static class PracticeListBean {
                private String author;
                private String authorType;
                private String title;
                private int commnetNum;
                private String classType;
                private String coverImage;

                public String getAuthor() {
                    return author;
                }

                public void setAuthor(String author) {
                    this.author = author;
                }

                public String getAuthorType() {
                    return authorType;
                }

                public void setAuthorType(String authorType) {
                    this.authorType = authorType;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public int getCommnetNum() {
                    return commnetNum;
                }

                public void setCommnetNum(int commnetNum) {
                    this.commnetNum = commnetNum;
                }

                public String getClassType() {
                    return classType;
                }

                public void setClassType(String classType) {
                    this.classType = classType;
                }

                public String getCoverImage() {
                    return coverImage;
                }

                public void setCoverImage(String coverImage) {
                    this.coverImage = coverImage;
                }
            }
        }

        public static class LiveBean {
            private int startDate;
            private String memo;
            private int personNum;
            private boolean isPackage;
            private int endDate;
            private String code;
            private int endTime;
            private int id;
            private int startTime;
            private String title;
            /**
             * introduce : 1
             * introduceUrl :
             */

            private CourseIntroductionBean courseIntroduction;
            private boolean isRecommend;
            /**
             * teacherIcon :
             * teacherName : 贾秀涛
             */

            private List<TeachersBean> teachers;

            public int getStartDate() {
                return startDate;
            }

            public void setStartDate(int startDate) {
                this.startDate = startDate;
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

            public boolean isIsPackage() {
                return isPackage;
            }

            public void setIsPackage(boolean isPackage) {
                this.isPackage = isPackage;
            }

            public int getEndDate() {
                return endDate;
            }

            public void setEndDate(int endDate) {
                this.endDate = endDate;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public int getEndTime() {
                return endTime;
            }

            public void setEndTime(int endTime) {
                this.endTime = endTime;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
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

            public CourseIntroductionBean getCourseIntroduction() {
                return courseIntroduction;
            }

            public void setCourseIntroduction(CourseIntroductionBean courseIntroduction) {
                this.courseIntroduction = courseIntroduction;
            }

            public boolean isIsRecommend() {
                return isRecommend;
            }

            public void setIsRecommend(boolean isRecommend) {
                this.isRecommend = isRecommend;
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
}
