package hengai.com.shishuo.bean;

import java.util.List;

/**
 * Created by yu on 2017/8/15.
 */

public class HomeBean {

    /**
     * code : 200
     * data : {"banner":[{"id":1120,"tag":"tag1","url":"http://img.liangshiba.com/uploads/img/2017/07/11/149977100916.jpg"},{"id":1121,"tag":"tag2","url":"http://img.liangshiba.com/uploads/img/2017/05/25/149568233919.png"}],"live":[{"endDate":1505361748,"endTime":1505361748,"id":1120,"personNum":300,"startDate":1502510548,"startTime":1502510548,"teachers":[{"teacherIcon":"http://www.liangshiba.com/images/toux_1.jpg","teacherName":"陈老师"},{"teacherIcon":"http://www.liangshiba.com/images/ms_tx.png","teacherName":"刘老师"}],"title":"七月份语文面试"}],"practice":{"personNum":5,"practiceList":[{"author":"马老六","authorType":"学生","classType":"说课","commnetNum":100,"coverImage":"http://img.liangshiba.com/uploads/img/2017/05/24/149561596132.jpg","title":"中学语文试讲"},{"author":"马老七","authorType":"学生","classType":"说课","commnetNum":200,"coverImage":"http://img.liangshiba.com/uploads/img/2017/05/24/149561596132.jpg","title":"中学语文试讲222"}]}}
     * message : 成功获取主页json！
     * result : 1
     */

    private int code;
    /**
     * banner : [{"id":1120,"tag":"tag1","url":"http://img.liangshiba.com/uploads/img/2017/07/11/149977100916.jpg"},{"id":1121,"tag":"tag2","url":"http://img.liangshiba.com/uploads/img/2017/05/25/149568233919.png"}]
     * live : [{"endDate":1505361748,"endTime":1505361748,"id":1120,"personNum":300,"startDate":1502510548,"startTime":1502510548,"teachers":[{"teacherIcon":"http://www.liangshiba.com/images/toux_1.jpg","teacherName":"陈老师"},{"teacherIcon":"http://www.liangshiba.com/images/ms_tx.png","teacherName":"刘老师"}],"title":"七月份语文面试"}]
     * practice : {"personNum":5,"practiceList":[{"author":"马老六","authorType":"学生","classType":"说课","commnetNum":100,"coverImage":"http://img.liangshiba.com/uploads/img/2017/05/24/149561596132.jpg","title":"中学语文试讲"},{"author":"马老七","authorType":"学生","classType":"说课","commnetNum":200,"coverImage":"http://img.liangshiba.com/uploads/img/2017/05/24/149561596132.jpg","title":"中学语文试讲222"}]}
     */

    private DataBean data;
    private String message;
    private int result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
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

    public static class DataBean {
        /**
         * personNum : 5
         * practiceList : [{"author":"马老六","authorType":"学生","classType":"说课","commnetNum":100,"coverImage":"http://img.liangshiba.com/uploads/img/2017/05/24/149561596132.jpg","title":"中学语文试讲"},{"author":"马老七","authorType":"学生","classType":"说课","commnetNum":200,"coverImage":"http://img.liangshiba.com/uploads/img/2017/05/24/149561596132.jpg","title":"中学语文试讲222"}]
         */

        private PracticeBean practice;
        /**
         * id : 1120
         * tag : tag1
         * url : http://img.liangshiba.com/uploads/img/2017/07/11/149977100916.jpg
         */

        private List<BannerBean> banner;
        /**
         * endDate : 1505361748
         * endTime : 1505361748
         * id : 1120
         * personNum : 300
         * startDate : 1502510548
         * startTime : 1502510548
         * teachers : [{"teacherIcon":"http://www.liangshiba.com/images/toux_1.jpg","teacherName":"陈老师"},{"teacherIcon":"http://www.liangshiba.com/images/ms_tx.png","teacherName":"刘老师"}]
         * title : 七月份语文面试
         */

        private List<LiveBean> live;

        public PracticeBean getPractice() {
            return practice;
        }

        public void setPractice(PracticeBean practice) {
            this.practice = practice;
        }

        public List<BannerBean> getBanner() {
            return banner;
        }

        public void setBanner(List<BannerBean> banner) {
            this.banner = banner;
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
             * classType : 说课
             * commnetNum : 100
             * coverImage : http://img.liangshiba.com/uploads/img/2017/05/24/149561596132.jpg
             * title : 中学语文试讲
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
                private String classType;
                private int commnetNum;
                private String coverImage;
                private String title;

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

                public String getClassType() {
                    return classType;
                }

                public void setClassType(String classType) {
                    this.classType = classType;
                }

                public int getCommnetNum() {
                    return commnetNum;
                }

                public void setCommnetNum(int commnetNum) {
                    this.commnetNum = commnetNum;
                }

                public String getCoverImage() {
                    return coverImage;
                }

                public void setCoverImage(String coverImage) {
                    this.coverImage = coverImage;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }
            }
        }

        public static class BannerBean {
            private int id;
            private String tag;
            private String url;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTag() {
                return tag;
            }

            public void setTag(String tag) {
                this.tag = tag;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

        public static class LiveBean {
            private int endDate;
            private int endTime;
            private int id;
            private int personNum;
            private int startDate;
            private int startTime;
            private String title;
            /**
             * teacherIcon : http://www.liangshiba.com/images/toux_1.jpg
             * teacherName : 陈老师
             */

            private List<TeachersBean> teachers;

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

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
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
