package hengai.com.shishuo.bean;

import java.util.List;

/**
 * Created by yu on 2017/8/15.
 */

public class HomeBean {


    /**
     * message : 成功获取主页json！
     * result : 1
     * data : {"practice":{"personNum":5,"practiceList":[{"commentNum":0,"author":"","authorType":"学生","title":"测试2","videoId":"3E00EEE78E73F9B39C33DC5901307461","classType":"JGH","code":"1661626938","coverImage":"http://3-img.bokecc.com/comimage/1F95D50A9DE128D5/2017-10-31/3E00EEE78E73F9B39C33DC5901307461-0.jpg"},{"commentNum":0,"author":"","authorType":"学生","title":"测试","videoId":"9C2BA1E25FB5894D9C33DC5901307461","classType":"SK","code":"8300671582","coverImage":"http://2-img.bokecc.com/comimage/1F95D50A9DE128D5/2017-10-31/9C2BA1E25FB5894D9C33DC5901307461-0.jpg"},{"commentNum":0,"author":"","authorType":"学生","title":"课时name","videoId":"4701AD2D166C2A319C33DC5901307461","classType":"KD","code":"3715107318","coverImage":"http://4-img.bokecc.com/comimage/1F95D50A9DE128D5/2017-10-27/4701AD2D166C2A319C33DC5901307461-0.jpg"},{"commentNum":0,"author":"张3a","authorType":"学生","title":"刘老师的教宗课程","videoId":"1A20514C6F742A0B9C33DC5901307461","classType":"SK","code":"7958357496","coverImage":"http://4-img.bokecc.com/comimage/1F95D50A9DE128D5/2017-10-22/1A20514C6F742A0B9C33DC5901307461-0.jpg"},{"commentNum":0,"author":"李四","authorType":"学生","title":"刘老师的教宗课程，很不错哦","videoId":"1A20514C6F742A0B9C33DC5901307461","classType":"SK","code":"9383150598","coverImage":"http://4-img.bokecc.com/comimage/1F95D50A9DE128D5/2017-10-22/1A20514C6F742A0B9C33DC5901307461-0.jpg"}]},"live":[{"startDate":1507824000,"memo":"直播面试1111","personNum":1,"isPackage":true,"endDate":1511280000,"code":"5628726408119345","endTime":1511280000,"teachers":[{"teacherIcon":"http://59.110.42.139:9393/images/IDCards/101dd68c-27e4-4cd7-9ce9-cb0412a4b7c8.jpg","teacherName":"李四"}],"startTime":1507824000,"title":"直播面试1","courseIntroduction":{"introduce":"直播面试1111","introduceUrl":""},"isRecommend":true,"mybuy":false},{"startDate":1506873600,"memo":"直播笔试2","personNum":1,"isPackage":true,"endDate":1510329600,"code":"2836948335212401","endTime":1510329600,"teachers":[{"teacherIcon":"http://59.110.42.139:9393/images/IDCards/101dd68c-27e4-4cd7-9ce9-cb0412a4b7c8.jpg","teacherName":"李四"}],"startTime":1506873600,"title":"直播笔试2","courseIntroduction":{"introduce":"直播笔试2","introduceUrl":""},"isRecommend":true,"mybuy":false},{"startDate":1506873600,"memo":"111","personNum":200,"isPackage":false,"endDate":1510329600,"code":"8691119320315140","endTime":1510329600,"teachers":[{"teacherIcon":"http://59.110.42.139:9393/images/IDCards/dcd3e960-71f7-43be-ae28-ecaf8ca4d740.jpg","teacherName":"张3a"}],"startTime":1506873600,"title":"单课程直播测试","courseIntroduction":{"introduce":"111","introduceUrl":""},"isRecommend":true,"mybuy":false},{"startDate":1506787200,"memo":"","personNum":23,"isPackage":false,"endDate":1510243200,"code":"7928984893135670","endTime":1510243200,"teachers":[],"startTime":1506787200,"title":"测试数据1111","courseIntroduction":{"introduce":"","introduceUrl":""},"isRecommend":true,"mybuy":false},{"startDate":1506787200,"memo":"1111111111111<div>11111111111<\/div><div>1111111111<\/div>","personNum":111,"isPackage":true,"endDate":1510243200,"code":"5812107505031200","endTime":1510243200,"teachers":[{"teacherIcon":"http://59.110.42.139:9393/images/IDCards/101dd68c-27e4-4cd7-9ce9-cb0412a4b7c8.jpg","teacherName":"李四"}],"startTime":1506787200,"title":"新的测试","courseIntroduction":{"introduce":"1111111111111<div>11111111111<\/div><div>1111111111<\/div>","introduceUrl":""},"isRecommend":true,"mybuy":false},{"startDate":1506873600,"memo":"","personNum":21,"isPackage":false,"endDate":1510329600,"code":"5847901151139949","endTime":1510329600,"teachers":[{"teacherIcon":"http://59.110.42.139:9393/images/IDCards/dcd3e960-71f7-43be-ae28-ecaf8ca4d740.jpg","teacherName":"张3a"}],"startTime":1506873600,"title":"单课程测试直播系列1","courseIntroduction":{"introduce":"","introduceUrl":""},"isRecommend":true,"mybuy":false},{"startDate":1506873600,"memo":"","personNum":11,"isPackage":false,"endDate":1510329600,"code":"4964234647835591","endTime":1510329600,"teachers":[{"teacherIcon":"http://59.110.42.139:9393/images/IDCards/101dd68c-27e4-4cd7-9ce9-cb0412a4b7c8.jpg","teacherName":"李四"}],"startTime":1506873600,"title":"多课程测试","courseIntroduction":{"introduce":"","introduceUrl":""},"isRecommend":true,"mybuy":false},{"startDate":1507996800,"memo":"111111111","personNum":20,"isPackage":false,"endDate":1511452800,"code":"3555591947725799","endTime":1511452800,"teachers":[],"startTime":1507996800,"title":"多课程直播测试","courseIntroduction":{"introduce":"111111111","introduceUrl":""},"isRecommend":true,"mybuy":false}]}
     * code : 200
     */

    private String message;
    private int result;
    /**
     * practice : {"personNum":5,"practiceList":[{"commentNum":0,"author":"","authorType":"学生","title":"测试2","videoId":"3E00EEE78E73F9B39C33DC5901307461","classType":"JGH","code":"1661626938","coverImage":"http://3-img.bokecc.com/comimage/1F95D50A9DE128D5/2017-10-31/3E00EEE78E73F9B39C33DC5901307461-0.jpg"},{"commentNum":0,"author":"","authorType":"学生","title":"测试","videoId":"9C2BA1E25FB5894D9C33DC5901307461","classType":"SK","code":"8300671582","coverImage":"http://2-img.bokecc.com/comimage/1F95D50A9DE128D5/2017-10-31/9C2BA1E25FB5894D9C33DC5901307461-0.jpg"},{"commentNum":0,"author":"","authorType":"学生","title":"课时name","videoId":"4701AD2D166C2A319C33DC5901307461","classType":"KD","code":"3715107318","coverImage":"http://4-img.bokecc.com/comimage/1F95D50A9DE128D5/2017-10-27/4701AD2D166C2A319C33DC5901307461-0.jpg"},{"commentNum":0,"author":"张3a","authorType":"学生","title":"刘老师的教宗课程","videoId":"1A20514C6F742A0B9C33DC5901307461","classType":"SK","code":"7958357496","coverImage":"http://4-img.bokecc.com/comimage/1F95D50A9DE128D5/2017-10-22/1A20514C6F742A0B9C33DC5901307461-0.jpg"},{"commentNum":0,"author":"李四","authorType":"学生","title":"刘老师的教宗课程，很不错哦","videoId":"1A20514C6F742A0B9C33DC5901307461","classType":"SK","code":"9383150598","coverImage":"http://4-img.bokecc.com/comimage/1F95D50A9DE128D5/2017-10-22/1A20514C6F742A0B9C33DC5901307461-0.jpg"}]}
     * live : [{"startDate":1507824000,"memo":"直播面试1111","personNum":1,"isPackage":true,"endDate":1511280000,"code":"5628726408119345","endTime":1511280000,"teachers":[{"teacherIcon":"http://59.110.42.139:9393/images/IDCards/101dd68c-27e4-4cd7-9ce9-cb0412a4b7c8.jpg","teacherName":"李四"}],"startTime":1507824000,"title":"直播面试1","courseIntroduction":{"introduce":"直播面试1111","introduceUrl":""},"isRecommend":true,"mybuy":false},{"startDate":1506873600,"memo":"直播笔试2","personNum":1,"isPackage":true,"endDate":1510329600,"code":"2836948335212401","endTime":1510329600,"teachers":[{"teacherIcon":"http://59.110.42.139:9393/images/IDCards/101dd68c-27e4-4cd7-9ce9-cb0412a4b7c8.jpg","teacherName":"李四"}],"startTime":1506873600,"title":"直播笔试2","courseIntroduction":{"introduce":"直播笔试2","introduceUrl":""},"isRecommend":true,"mybuy":false},{"startDate":1506873600,"memo":"111","personNum":200,"isPackage":false,"endDate":1510329600,"code":"8691119320315140","endTime":1510329600,"teachers":[{"teacherIcon":"http://59.110.42.139:9393/images/IDCards/dcd3e960-71f7-43be-ae28-ecaf8ca4d740.jpg","teacherName":"张3a"}],"startTime":1506873600,"title":"单课程直播测试","courseIntroduction":{"introduce":"111","introduceUrl":""},"isRecommend":true,"mybuy":false},{"startDate":1506787200,"memo":"","personNum":23,"isPackage":false,"endDate":1510243200,"code":"7928984893135670","endTime":1510243200,"teachers":[],"startTime":1506787200,"title":"测试数据1111","courseIntroduction":{"introduce":"","introduceUrl":""},"isRecommend":true,"mybuy":false},{"startDate":1506787200,"memo":"1111111111111<div>11111111111<\/div><div>1111111111<\/div>","personNum":111,"isPackage":true,"endDate":1510243200,"code":"5812107505031200","endTime":1510243200,"teachers":[{"teacherIcon":"http://59.110.42.139:9393/images/IDCards/101dd68c-27e4-4cd7-9ce9-cb0412a4b7c8.jpg","teacherName":"李四"}],"startTime":1506787200,"title":"新的测试","courseIntroduction":{"introduce":"1111111111111<div>11111111111<\/div><div>1111111111<\/div>","introduceUrl":""},"isRecommend":true,"mybuy":false},{"startDate":1506873600,"memo":"","personNum":21,"isPackage":false,"endDate":1510329600,"code":"5847901151139949","endTime":1510329600,"teachers":[{"teacherIcon":"http://59.110.42.139:9393/images/IDCards/dcd3e960-71f7-43be-ae28-ecaf8ca4d740.jpg","teacherName":"张3a"}],"startTime":1506873600,"title":"单课程测试直播系列1","courseIntroduction":{"introduce":"","introduceUrl":""},"isRecommend":true,"mybuy":false},{"startDate":1506873600,"memo":"","personNum":11,"isPackage":false,"endDate":1510329600,"code":"4964234647835591","endTime":1510329600,"teachers":[{"teacherIcon":"http://59.110.42.139:9393/images/IDCards/101dd68c-27e4-4cd7-9ce9-cb0412a4b7c8.jpg","teacherName":"李四"}],"startTime":1506873600,"title":"多课程测试","courseIntroduction":{"introduce":"","introduceUrl":""},"isRecommend":true,"mybuy":false},{"startDate":1507996800,"memo":"111111111","personNum":20,"isPackage":false,"endDate":1511452800,"code":"3555591947725799","endTime":1511452800,"teachers":[],"startTime":1507996800,"title":"多课程直播测试","courseIntroduction":{"introduce":"111111111","introduceUrl":""},"isRecommend":true,"mybuy":false}]
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
         * practiceList : [{"commentNum":0,"author":"","authorType":"学生","title":"测试2","videoId":"3E00EEE78E73F9B39C33DC5901307461","classType":"JGH","code":"1661626938","coverImage":"http://3-img.bokecc.com/comimage/1F95D50A9DE128D5/2017-10-31/3E00EEE78E73F9B39C33DC5901307461-0.jpg"},{"commentNum":0,"author":"","authorType":"学生","title":"测试","videoId":"9C2BA1E25FB5894D9C33DC5901307461","classType":"SK","code":"8300671582","coverImage":"http://2-img.bokecc.com/comimage/1F95D50A9DE128D5/2017-10-31/9C2BA1E25FB5894D9C33DC5901307461-0.jpg"},{"commentNum":0,"author":"","authorType":"学生","title":"课时name","videoId":"4701AD2D166C2A319C33DC5901307461","classType":"KD","code":"3715107318","coverImage":"http://4-img.bokecc.com/comimage/1F95D50A9DE128D5/2017-10-27/4701AD2D166C2A319C33DC5901307461-0.jpg"},{"commentNum":0,"author":"张3a","authorType":"学生","title":"刘老师的教宗课程","videoId":"1A20514C6F742A0B9C33DC5901307461","classType":"SK","code":"7958357496","coverImage":"http://4-img.bokecc.com/comimage/1F95D50A9DE128D5/2017-10-22/1A20514C6F742A0B9C33DC5901307461-0.jpg"},{"commentNum":0,"author":"李四","authorType":"学生","title":"刘老师的教宗课程，很不错哦","videoId":"1A20514C6F742A0B9C33DC5901307461","classType":"SK","code":"9383150598","coverImage":"http://4-img.bokecc.com/comimage/1F95D50A9DE128D5/2017-10-22/1A20514C6F742A0B9C33DC5901307461-0.jpg"}]
         */

        private PracticeBean practice;
        /**
         * startDate : 1507824000
         * memo : 直播面试1111
         * personNum : 1
         * isPackage : true
         * endDate : 1511280000
         * code : 5628726408119345
         * endTime : 1511280000
         * teachers : [{"teacherIcon":"http://59.110.42.139:9393/images/IDCards/101dd68c-27e4-4cd7-9ce9-cb0412a4b7c8.jpg","teacherName":"李四"}]
         * startTime : 1507824000
         * title : 直播面试1
         * courseIntroduction : {"introduce":"直播面试1111","introduceUrl":""}
         * isRecommend : true
         * mybuy : false
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
             * commentNum : 0
             * author :
             * authorType : 学生
             * title : 测试2
             * videoId : 3E00EEE78E73F9B39C33DC5901307461
             * classType : JGH
             * code : 1661626938
             * coverImage : http://3-img.bokecc.com/comimage/1F95D50A9DE128D5/2017-10-31/3E00EEE78E73F9B39C33DC5901307461-0.jpg
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
                private int commentNum;
                private String author;
                private String authorType;
                private String title;
                private String videoId;
                private String classType;
                private String code;
                private String coverImage;

                public int getCommentNum() {
                    return commentNum;
                }

                public void setCommentNum(int commentNum) {
                    this.commentNum = commentNum;
                }

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

                public String getVideoId() {
                    return videoId;
                }

                public void setVideoId(String videoId) {
                    this.videoId = videoId;
                }

                public String getClassType() {
                    return classType;
                }

                public void setClassType(String classType) {
                    this.classType = classType;
                }

                public String getCode() {
                    return code;
                }

                public void setCode(String code) {
                    this.code = code;
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
            private int startTime;
            private String title;
            /**
             * introduce : 直播面试1111
             * introduceUrl :
             */

            private CourseIntroductionBean courseIntroduction;
            private boolean isRecommend;
            private boolean mybuy;
            /**
             * teacherIcon : http://59.110.42.139:9393/images/IDCards/101dd68c-27e4-4cd7-9ce9-cb0412a4b7c8.jpg
             * teacherName : 李四
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

            public boolean isMybuy() {
                return mybuy;
            }

            public void setMybuy(boolean mybuy) {
                this.mybuy = mybuy;
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
