package hengai.com.shishuo.bean;

import java.util.List;

/**
 * Created by yu on 2017/9/28.
 */

public class LessonVideoBean {

    /**
     * result : 1
     * data : [{"startTime":1506599752,"startDate":1506599752,"title":"幼儿园-中班美术-五彩手掌鸟-说课","testUrl":"","videoId":"EDD73E730865D40B9C33DC5901307461","vtype":"V","endDate":1506599752,"endTime":1506599752,"testPaperTitle":"幼儿园-中班美术-五彩手掌鸟-说课","cfgId":4},{"startTime":1506599752,"startDate":1506599752,"title":"初中美术-创造美的纹样-说课","testUrl":"","videoId":"EA9A53CC09A23D279C33DC5901307461","vtype":"V","endDate":1506599752,"endTime":1506599752,"testPaperTitle":"初中美术-创造美的纹样-说课","cfgId":4},{"startTime":1506599752,"startDate":1506599752,"title":"课时1","testUrl":"","videoId":"0527E3B28DDECB909C33DC5901307461","vtype":"V","endDate":1506599752,"endTime":1506599752,"testPaperTitle":"标签","cfgId":4},{"startTime":1506599752,"startDate":1506599752,"title":"可是可是可是","testUrl":"","videoId":"0527E3B28DDECB909C33DC5901307461","vtype":"V","endDate":1506599752,"endTime":1506599752,"testPaperTitle":"标签","cfgId":4}]
     * code : 200
     * totalTable : 4
     */

    private int result;
    private int code;
    private int totalTable;
    /**
     * startTime : 1506599752
     * startDate : 1506599752
     * title : 幼儿园-中班美术-五彩手掌鸟-说课
     * testUrl :
     * videoId : EDD73E730865D40B9C33DC5901307461
     * vtype : V
     * endDate : 1506599752
     * endTime : 1506599752
     * testPaperTitle : 幼儿园-中班美术-五彩手掌鸟-说课
     * cfgId : 4
     */

    private List<DataBean> data;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getTotalTable() {
        return totalTable;
    }

    public void setTotalTable(int totalTable) {
        this.totalTable = totalTable;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private int startTime;
        private int startDate;
        private String title;
        private String testUrl;
        private String videoId;
        private String vtype;
        private int endDate;
        private int endTime;
        private String testPaperTitle;
        private int cfgId;

        public int getStartTime() {
            return startTime;
        }

        public void setStartTime(int startTime) {
            this.startTime = startTime;
        }

        public int getStartDate() {
            return startDate;
        }

        public void setStartDate(int startDate) {
            this.startDate = startDate;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTestUrl() {
            return testUrl;
        }

        public void setTestUrl(String testUrl) {
            this.testUrl = testUrl;
        }

        public String getVideoId() {
            return videoId;
        }

        public void setVideoId(String videoId) {
            this.videoId = videoId;
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

        public int getEndTime() {
            return endTime;
        }

        public void setEndTime(int endTime) {
            this.endTime = endTime;
        }

        public String getTestPaperTitle() {
            return testPaperTitle;
        }

        public void setTestPaperTitle(String testPaperTitle) {
            this.testPaperTitle = testPaperTitle;
        }

        public int getCfgId() {
            return cfgId;
        }

        public void setCfgId(int cfgId) {
            this.cfgId = cfgId;
        }
    }
}
