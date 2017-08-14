package hengai.com.shishuo.bean;

/**
 * Created by yu on 2017/8/10.
 */

public class LoginBean {

    /**
     * message : 三方登陆绑定成功！
     * result : 1
     * code : 200
     * usertoken : {"uid":16,"token":"IbQNYe9f0VOVTO9fyf4j7JYApxo9GaFH"}
     */

    private String message;
    private int result;
    private int code;
    /**
     * uid : 16
     * token : IbQNYe9f0VOVTO9fyf4j7JYApxo9GaFH
     */

    private UsertokenBean usertoken;

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

    public UsertokenBean getUsertoken() {
        return usertoken;
    }

    public void setUsertoken(UsertokenBean usertoken) {
        this.usertoken = usertoken;
    }

    public static class UsertokenBean {
        private int uid;
        private String token;

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
