package hengai.com.shishuo.bean;

import java.util.List;

/**
 * Created by yu on 2017/10/11.
 */

public class QuestionBean {

    /**
     * message : 获得抽题文件成功！
     * result : 1
     * data : {"id":2,"files":[{"id":10,"flag":"Y","seq":1,"code":"2727895003482559","type":"jpg","url":"http://59.110.42.139:9393/images//imgfile/dbe9e7f8-2550-4526-a093-e20c30aac60b.jpg"},{"id":11,"flag":"Y","seq":2,"code":"1116363183088000","type":"jpg","url":"http://59.110.42.139:9393/images//imgfile/089993b9-1dcd-426f-88b5-c46ccab1fcc8.jpg"},{"id":12,"flag":"Y","seq":3,"code":"2800707144794533","type":"jpg","url":"http://59.110.42.139:9393/images//imgfile/92f0d25a-a982-4ddc-aef6-0a612f0bb7da.jpg"}],"num":3,"name":"学习作曲应具备的条件","code":"122076865994"}
     * code : 200
     */

    private String message;
    private int result;
    /**
     * id : 2
     * files : [{"id":10,"flag":"Y","seq":1,"code":"2727895003482559","type":"jpg","url":"http://59.110.42.139:9393/images//imgfile/dbe9e7f8-2550-4526-a093-e20c30aac60b.jpg"},{"id":11,"flag":"Y","seq":2,"code":"1116363183088000","type":"jpg","url":"http://59.110.42.139:9393/images//imgfile/089993b9-1dcd-426f-88b5-c46ccab1fcc8.jpg"},{"id":12,"flag":"Y","seq":3,"code":"2800707144794533","type":"jpg","url":"http://59.110.42.139:9393/images//imgfile/92f0d25a-a982-4ddc-aef6-0a612f0bb7da.jpg"}]
     * num : 3
     * name : 学习作曲应具备的条件
     * code : 122076865994
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
        private int id;
        private int num;
        private String name;
        private String code;
        /**
         * id : 10
         * flag : Y
         * seq : 1
         * code : 2727895003482559
         * type : jpg
         * url : http://59.110.42.139:9393/images//imgfile/dbe9e7f8-2550-4526-a093-e20c30aac60b.jpg
         */

        private List<FilesBean> files;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public List<FilesBean> getFiles() {
            return files;
        }

        public void setFiles(List<FilesBean> files) {
            this.files = files;
        }

        public static class FilesBean {
            private int id;
            private String flag;
            private int seq;
            private String code;
            private String type;
            private String url;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getFlag() {
                return flag;
            }

            public void setFlag(String flag) {
                this.flag = flag;
            }

            public int getSeq() {
                return seq;
            }

            public void setSeq(int seq) {
                this.seq = seq;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
