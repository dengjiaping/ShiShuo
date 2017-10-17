package hengai.com.shishuo.bean;

import java.util.List;

/**
 * Created by yu on 2017/10/17.
 * 资讯列表
 */

public class InfoMationBean {

    /**
     * message : 获得信息列表成功！
     * result : 1
     * data : [{"id":1,"code":"GasnwsxYR2ar1kKKAA1ErkCR1wUcoXqo","title":"test2","catgid":1592,"catgName":"选调教师","scatgid":221,"scatgName":"综合素质","ctag1":"","ctag2":"","ctag3":"","viewTime":0,"firstView":"","lastView":"","headImgUrl":"http://59.110.42.139:9393/images//imgfile/a359e8e7-bfc3-439a-b761-ac40336571e1.jpg","creator":"张珊","flag":"Y","createTime":"2017-09-23 18:45:04","branchId":3,"branchName":"贵州分校","orgId":15,"orgName":"良师说","isRefered":""},{"id":2,"code":"Nisb8Wpm8h9XoI9oyt7vx1xDDHqQUStW","title":"aaaa","catgid":1524,"catgName":"教师招聘考试","scatgid":18,"scatgName":"语文","ctag1":"","ctag2":"","ctag3":"","viewTime":0,"firstView":"","lastView":"","headImgUrl":"http://59.110.42.139:9393/images//imgfile/78f67775-c8a2-485d-807b-4419a75b84f0.jpg","creator":"aa","flag":"Y","createTime":"2017-09-24 20:22:52","branchId":2,"branchName":"广东分校","orgId":15,"orgName":"良师说","isRefered":"Y"},{"id":3,"code":"9jPOctHQ5JWUYOEpe9uW8NQe5wrUmhYr","title":"测试","catgid":1524,"catgName":"教师招聘考试","scatgid":70,"scatgName":"体育","ctag1":"SK","ctag2":"","ctag3":"BK","viewTime":0,"firstView":"","lastView":"","headImgUrl":"","creator":"我","flag":"Y","createTime":"2017-09-25 16:38:51","branchId":2,"branchName":"广东分校","orgId":15,"orgName":"良师说","isRefered":""},{"id":4,"code":"ItRVgjWFU3T7ER0CGWQvMoLCKzi6zcHY","title":"我们的新闻","catgid":1592,"catgName":"选调教师","scatgid":226,"scatgName":"语文","ctag1":"","ctag2":"","ctag3":"","viewTime":0,"firstView":"","lastView":"","headImgUrl":"","creator":"李四","flag":"Y","createTime":"2017-09-26 11:14:23","branchId":2,"branchName":"广东分校","orgId":15,"orgName":"良师说","isRefered":""},{"id":5,"code":"Dab0El9KauBxL4JPbOs3Gnb6YYG0Qf8e","title":"111111111","catgid":1524,"catgName":"教师招聘考试","scatgid":18,"scatgName":"语文","ctag1":"","ctag2":"","ctag3":"","viewTime":0,"firstView":"","lastView":"","headImgUrl":"C:\\fakepath\\35 感觉极其特性 .jpg","creator":"111","flag":"Y","createTime":"2017-10-11 17:53:27","branchId":-1,"branchName":null,"orgId":15,"orgName":"良师说","isRefered":"N"},{"id":6,"code":"1kHOwTo0TkGM4wVTUXxP4PiZgbM6cuaC","title":"有图片测试","catgid":1524,"catgName":"教师招聘考试","scatgid":61,"scatgName":"美术","ctag1":"","ctag2":"","ctag3":"BK","viewTime":0,"firstView":"","lastView":"","headImgUrl":"","creator":"张老师","flag":"Y","createTime":"2017-10-17 09:38:42","branchId":-1,"branchName":null,"orgId":15,"orgName":"良师说","isRefered":"N"}]
     * code : 200
     */

    private String message;
    private int result;
    private int code;
    /**
     * id : 1
     * code : GasnwsxYR2ar1kKKAA1ErkCR1wUcoXqo
     * title : test2
     * catgid : 1592
     * catgName : 选调教师
     * scatgid : 221
     * scatgName : 综合素质
     * ctag1 :
     * ctag2 :
     * ctag3 :
     * viewTime : 0
     * firstView :
     * lastView :
     * headImgUrl : http://59.110.42.139:9393/images//imgfile/a359e8e7-bfc3-439a-b761-ac40336571e1.jpg
     * creator : 张珊
     * flag : Y
     * createTime : 2017-09-23 18:45:04
     * branchId : 3
     * branchName : 贵州分校
     * orgId : 15
     * orgName : 良师说
     * isRefered :
     */

    private List<DataBean> data;

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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private int id;
        private String code;
        private String title;
        private int catgid;
        private String catgName;
        private int scatgid;
        private String scatgName;
        private String ctag1;
        private String ctag2;
        private String ctag3;
        private int viewTime;
        private String firstView;
        private String lastView;
        private String headImgUrl;
        private String creator;
        private String flag;
        private String createTime;
        private int branchId;
        private String branchName;
        private int orgId;
        private String orgName;
        private String isRefered;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getCatgid() {
            return catgid;
        }

        public void setCatgid(int catgid) {
            this.catgid = catgid;
        }

        public String getCatgName() {
            return catgName;
        }

        public void setCatgName(String catgName) {
            this.catgName = catgName;
        }

        public int getScatgid() {
            return scatgid;
        }

        public void setScatgid(int scatgid) {
            this.scatgid = scatgid;
        }

        public String getScatgName() {
            return scatgName;
        }

        public void setScatgName(String scatgName) {
            this.scatgName = scatgName;
        }

        public String getCtag1() {
            return ctag1;
        }

        public void setCtag1(String ctag1) {
            this.ctag1 = ctag1;
        }

        public String getCtag2() {
            return ctag2;
        }

        public void setCtag2(String ctag2) {
            this.ctag2 = ctag2;
        }

        public String getCtag3() {
            return ctag3;
        }

        public void setCtag3(String ctag3) {
            this.ctag3 = ctag3;
        }

        public int getViewTime() {
            return viewTime;
        }

        public void setViewTime(int viewTime) {
            this.viewTime = viewTime;
        }

        public String getFirstView() {
            return firstView;
        }

        public void setFirstView(String firstView) {
            this.firstView = firstView;
        }

        public String getLastView() {
            return lastView;
        }

        public void setLastView(String lastView) {
            this.lastView = lastView;
        }

        public String getHeadImgUrl() {
            return headImgUrl;
        }

        public void setHeadImgUrl(String headImgUrl) {
            this.headImgUrl = headImgUrl;
        }

        public String getCreator() {
            return creator;
        }

        public void setCreator(String creator) {
            this.creator = creator;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getBranchId() {
            return branchId;
        }

        public void setBranchId(int branchId) {
            this.branchId = branchId;
        }

        public String getBranchName() {
            return branchName;
        }

        public void setBranchName(String branchName) {
            this.branchName = branchName;
        }

        public int getOrgId() {
            return orgId;
        }

        public void setOrgId(int orgId) {
            this.orgId = orgId;
        }

        public String getOrgName() {
            return orgName;
        }

        public void setOrgName(String orgName) {
            this.orgName = orgName;
        }

        public String getIsRefered() {
            return isRefered;
        }

        public void setIsRefered(String isRefered) {
            this.isRefered = isRefered;
        }
    }
}
