package hengai.com.shishuo.bean;

import java.util.List;

/**
 * Created by yu on 2017/10/23.
 */

public class VideoCouseInfo {

    /**
     * message : 获得课时信息成功!
     * result : 1
     * data : {"startDate":1508924786,"desc":"教师招聘初中数学结构化面试","viewtime":10,"vtype":"V","endDate":1508924786,"code":"9402999125","endTime":1508924786,"cfgId":4,"commentNum":3,"startTime":1508924786,"ctag2":"","myzan":false,"title":"练课","ctag1":"","ctag3":"","teacherHeadUrl":"http://59.110.42.139:9393/images/IDCards/dcd3e960-71f7-43be-ae28-ecaf8ca4d740.jpg","videoId":"DB468EBE9EE4EAD59C33DC5901307461","zannum":1,"teacher":"张3a","comments":[{"id":55,"mainId":0,"mainType":"","content":"123","rate":"G","flag":"Y","fromUserId":14,"fromUserName":"同学","fromUserType":"U","fromUserHeadUrl":"","fromIp":"101.39.215.88","totalZanNum":0,"niminZanNum":0,"childNum":0,"createTime":"2017-10-23 12:21:14","replys":[],"fromUser":14},{"id":56,"mainId":0,"mainType":"","content":"123","rate":"G","flag":"Y","fromUserId":14,"fromUserName":"同学","fromUserType":"U","fromUserHeadUrl":"","fromIp":"101.39.215.88","totalZanNum":0,"niminZanNum":0,"childNum":1,"createTime":"2017-10-23 12:21:15","replys":[{"id":63,"mainId":0,"mainType":"","content":"abc56","rate":"G","flag":"Y","fromUserId":14,"fromUserName":"同学","fromUserType":"U","fromUserHeadUrl":"","fromIp":"101.39.215.88","totalZanNum":0,"niminZanNum":0,"childNum":0,"createTime":"2017-10-25 01:02:03","replys":[],"fromUser":14}],"fromUser":14},{"id":57,"mainId":0,"mainType":"","content":"123","rate":"G","flag":"Y","fromUserId":14,"fromUserName":"同学","fromUserType":"U","fromUserHeadUrl":"","fromIp":"101.39.215.88","totalZanNum":0,"niminZanNum":0,"childNum":1,"createTime":"2017-10-23 12:21:15","replys":[{"id":62,"mainId":0,"mainType":"","content":"abc57","rate":"G","flag":"Y","fromUserId":14,"fromUserName":"同学","fromUserType":"U","fromUserHeadUrl":"","fromIp":"101.39.215.88","totalZanNum":0,"niminZanNum":0,"childNum":0,"createTime":"2017-10-25 00:46:03","replys":[],"fromUser":14}],"fromUser":14}],"isRefered":"","teacherId":38,"videoHeadUrl":"http://img.bokecc.com/comimage/1F95D50A9DE128D5/2017-07-26/DB468EBE9EE4EAD59C33DC5901307461-0.jpg"}
     * code : 200
     */

    private String message;
    private int result;
    /**
     * startDate : 1508924786
     * desc : 教师招聘初中数学结构化面试
     * viewtime : 10
     * vtype : V
     * endDate : 1508924786
     * code : 9402999125
     * endTime : 1508924786
     * cfgId : 4
     * commentNum : 3
     * startTime : 1508924786
     * ctag2 :
     * myzan : false
     * title : 练课
     * ctag1 :
     * ctag3 :
     * teacherHeadUrl : http://59.110.42.139:9393/images/IDCards/dcd3e960-71f7-43be-ae28-ecaf8ca4d740.jpg
     * videoId : DB468EBE9EE4EAD59C33DC5901307461
     * zannum : 1
     * teacher : 张3a
     * comments : [{"id":55,"mainId":0,"mainType":"","content":"123","rate":"G","flag":"Y","fromUserId":14,"fromUserName":"同学","fromUserType":"U","fromUserHeadUrl":"","fromIp":"101.39.215.88","totalZanNum":0,"niminZanNum":0,"childNum":0,"createTime":"2017-10-23 12:21:14","replys":[],"fromUser":14},{"id":56,"mainId":0,"mainType":"","content":"123","rate":"G","flag":"Y","fromUserId":14,"fromUserName":"同学","fromUserType":"U","fromUserHeadUrl":"","fromIp":"101.39.215.88","totalZanNum":0,"niminZanNum":0,"childNum":1,"createTime":"2017-10-23 12:21:15","replys":[{"id":63,"mainId":0,"mainType":"","content":"abc56","rate":"G","flag":"Y","fromUserId":14,"fromUserName":"同学","fromUserType":"U","fromUserHeadUrl":"","fromIp":"101.39.215.88","totalZanNum":0,"niminZanNum":0,"childNum":0,"createTime":"2017-10-25 01:02:03","replys":[],"fromUser":14}],"fromUser":14},{"id":57,"mainId":0,"mainType":"","content":"123","rate":"G","flag":"Y","fromUserId":14,"fromUserName":"同学","fromUserType":"U","fromUserHeadUrl":"","fromIp":"101.39.215.88","totalZanNum":0,"niminZanNum":0,"childNum":1,"createTime":"2017-10-23 12:21:15","replys":[{"id":62,"mainId":0,"mainType":"","content":"abc57","rate":"G","flag":"Y","fromUserId":14,"fromUserName":"同学","fromUserType":"U","fromUserHeadUrl":"","fromIp":"101.39.215.88","totalZanNum":0,"niminZanNum":0,"childNum":0,"createTime":"2017-10-25 00:46:03","replys":[],"fromUser":14}],"fromUser":14}]
     * isRefered :
     * teacherId : 38
     * videoHeadUrl : http://img.bokecc.com/comimage/1F95D50A9DE128D5/2017-07-26/DB468EBE9EE4EAD59C33DC5901307461-0.jpg
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
        private int startDate;
        private String desc;
        private int viewtime;
        private String vtype;
        private int endDate;
        private String code;
        private int endTime;
        private int cfgId;
        private int commentNum;
        private int startTime;
        private String ctag2;
        private boolean myzan;
        private String title;
        private String ctag1;
        private String ctag3;
        private String teacherHeadUrl;
        private String videoId;
        private int zannum;
        private String teacher;
        private String isRefered;
        private int teacherId;
        private String videoHeadUrl;
        /**
         * id : 55
         * mainId : 0
         * mainType :
         * content : 123
         * rate : G
         * flag : Y
         * fromUserId : 14
         * fromUserName : 同学
         * fromUserType : U
         * fromUserHeadUrl :
         * fromIp : 101.39.215.88
         * totalZanNum : 0
         * niminZanNum : 0
         * childNum : 0
         * createTime : 2017-10-23 12:21:14
         * replys : []
         * fromUser : 14
         */

        private List<CommentsBean> comments;

        public int getStartDate() {
            return startDate;
        }

        public void setStartDate(int startDate) {
            this.startDate = startDate;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public int getViewtime() {
            return viewtime;
        }

        public void setViewtime(int viewtime) {
            this.viewtime = viewtime;
        }

        public String getVtype() {
            return vtype;
        }

        public void setVtype(String vtype) {
            this.vtype = vtype;
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

        public int getCfgId() {
            return cfgId;
        }

        public void setCfgId(int cfgId) {
            this.cfgId = cfgId;
        }

        public int getCommentNum() {
            return commentNum;
        }

        public void setCommentNum(int commentNum) {
            this.commentNum = commentNum;
        }

        public int getStartTime() {
            return startTime;
        }

        public void setStartTime(int startTime) {
            this.startTime = startTime;
        }

        public String getCtag2() {
            return ctag2;
        }

        public void setCtag2(String ctag2) {
            this.ctag2 = ctag2;
        }

        public boolean isMyzan() {
            return myzan;
        }

        public void setMyzan(boolean myzan) {
            this.myzan = myzan;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCtag1() {
            return ctag1;
        }

        public void setCtag1(String ctag1) {
            this.ctag1 = ctag1;
        }

        public String getCtag3() {
            return ctag3;
        }

        public void setCtag3(String ctag3) {
            this.ctag3 = ctag3;
        }

        public String getTeacherHeadUrl() {
            return teacherHeadUrl;
        }

        public void setTeacherHeadUrl(String teacherHeadUrl) {
            this.teacherHeadUrl = teacherHeadUrl;
        }

        public String getVideoId() {
            return videoId;
        }

        public void setVideoId(String videoId) {
            this.videoId = videoId;
        }

        public int getZannum() {
            return zannum;
        }

        public void setZannum(int zannum) {
            this.zannum = zannum;
        }

        public String getTeacher() {
            return teacher;
        }

        public void setTeacher(String teacher) {
            this.teacher = teacher;
        }

        public String getIsRefered() {
            return isRefered;
        }

        public void setIsRefered(String isRefered) {
            this.isRefered = isRefered;
        }

        public int getTeacherId() {
            return teacherId;
        }

        public void setTeacherId(int teacherId) {
            this.teacherId = teacherId;
        }

        public String getVideoHeadUrl() {
            return videoHeadUrl;
        }

        public void setVideoHeadUrl(String videoHeadUrl) {
            this.videoHeadUrl = videoHeadUrl;
        }

        public List<CommentsBean> getComments() {
            return comments;
        }

        public void setComments(List<CommentsBean> comments) {
            this.comments = comments;
        }

        public static class CommentsBean {
            private int id;
            private int mainId;
            private String mainType;
            private String content;
            private String rate;
            private String flag;
            private int fromUserId;
            private String fromUserName;
            private String fromUserType;
            private String fromUserHeadUrl;
            private String fromIp;
            private boolean mycomment;
            private int totalZanNum;
            private int niminZanNum;
            private int childNum;
            private String createTime;
            private int fromUser;
            private List<ReplaysBean> replys;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getMainId() {
                return mainId;
            }

            public void setMainId(int mainId) {
                this.mainId = mainId;
            }

            public String getMainType() {
                return mainType;
            }

            public void setMainType(String mainType) {
                this.mainType = mainType;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getRate() {
                return rate;
            }

            public void setRate(String rate) {
                this.rate = rate;
            }

            public String getFlag() {
                return flag;
            }

            public void setFlag(String flag) {
                this.flag = flag;
            }

            public int getFromUserId() {
                return fromUserId;
            }

            public void setFromUserId(int fromUserId) {
                this.fromUserId = fromUserId;
            }

            public String getFromUserName() {
                return fromUserName;
            }

            public void setFromUserName(String fromUserName) {
                this.fromUserName = fromUserName;
            }

            public String getFromUserType() {
                return fromUserType;
            }

            public void setFromUserType(String fromUserType) {
                this.fromUserType = fromUserType;
            }

            public String getFromUserHeadUrl() {
                return fromUserHeadUrl;
            }

            public void setFromUserHeadUrl(String fromUserHeadUrl) {
                this.fromUserHeadUrl = fromUserHeadUrl;
            }

            public String getFromIp() {
                return fromIp;
            }

            public void setFromIp(String fromIp) {
                this.fromIp = fromIp;
            }

            public int getTotalZanNum() {
                return totalZanNum;
            }

            public void setTotalZanNum(int totalZanNum) {
                this.totalZanNum = totalZanNum;
            }

            public int getNiminZanNum() {
                return niminZanNum;
            }

            public void setNiminZanNum(int niminZanNum) {
                this.niminZanNum = niminZanNum;
            }

            public int getChildNum() {
                return childNum;
            }

            public void setChildNum(int childNum) {
                this.childNum = childNum;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public int getFromUser() {
                return fromUser;
            }

            public void setFromUser(int fromUser) {
                this.fromUser = fromUser;
            }
            public boolean getMycomment(){
                return mycomment;
            }
            public void setId(boolean mycomment) {
                this.mycomment = mycomment;
            }
            public List<ReplaysBean> getReplys() {
                return replys;
            }

            public void setReplys(List<ReplaysBean> replys) {
                this.replys = replys;
            }
            public static class ReplaysBean {
                private int id;
                private int mainId;
                private String mainType;
                private String content;
                private String rate;
                private String flag;
                private int fromUserId;
                private String fromUserName;
                private String fromUserType;
                private String fromUserHeadUrl;
                private String fromIp;
                private boolean mycomment;
                private int totalZanNum;
                private int niminZanNum;
                private int childNum;
                private String createTime;
                private int fromUser;
                private List<?> replys;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }
                public boolean getMycomment(){
                    return mycomment;
                }
                public void setId(boolean mycomment) {
                    this.mycomment = mycomment;
                }
                public int getMainId() {
                    return mainId;
                }

                public void setMainId(int mainId) {
                    this.mainId = mainId;
                }

                public String getMainType() {
                    return mainType;
                }

                public void setMainType(String mainType) {
                    this.mainType = mainType;
                }

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
                }

                public String getRate() {
                    return rate;
                }

                public void setRate(String rate) {
                    this.rate = rate;
                }

                public String getFlag() {
                    return flag;
                }

                public void setFlag(String flag) {
                    this.flag = flag;
                }

                public int getFromUserId() {
                    return fromUserId;
                }

                public void setFromUserId(int fromUserId) {
                    this.fromUserId = fromUserId;
                }

                public String getFromUserName() {
                    return fromUserName;
                }

                public void setFromUserName(String fromUserName) {
                    this.fromUserName = fromUserName;
                }

                public String getFromUserType() {
                    return fromUserType;
                }

                public void setFromUserType(String fromUserType) {
                    this.fromUserType = fromUserType;
                }

                public String getFromUserHeadUrl() {
                    return fromUserHeadUrl;
                }

                public void setFromUserHeadUrl(String fromUserHeadUrl) {
                    this.fromUserHeadUrl = fromUserHeadUrl;
                }

                public String getFromIp() {
                    return fromIp;
                }

                public void setFromIp(String fromIp) {
                    this.fromIp = fromIp;
                }

                public int getTotalZanNum() {
                    return totalZanNum;
                }

                public void setTotalZanNum(int totalZanNum) {
                    this.totalZanNum = totalZanNum;
                }

                public int getNiminZanNum() {
                    return niminZanNum;
                }

                public void setNiminZanNum(int niminZanNum) {
                    this.niminZanNum = niminZanNum;
                }

                public int getChildNum() {
                    return childNum;
                }

                public void setChildNum(int childNum) {
                    this.childNum = childNum;
                }

                public String getCreateTime() {
                    return createTime;
                }

                public void setCreateTime(String createTime) {
                    this.createTime = createTime;
                }

                public int getFromUser() {
                    return fromUser;
                }

                public void setFromUser(int fromUser) {
                    this.fromUser = fromUser;
                }

                public List<?> getReplys() {
                    return replys;
                }

                public void setReplys(List<?> replys) {
                    this.replys = replys;
                }
            }
        }
    }
}
