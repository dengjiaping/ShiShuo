package hengai.com.shishuo.bean;

/**
 * Created by yu on 2017/10/30.
 */

public class Deletecomm {

    /**
     * message : 删除评论成功!
     * result : 1
     * code : 200
     */

    private String message;
    private int result;
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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
