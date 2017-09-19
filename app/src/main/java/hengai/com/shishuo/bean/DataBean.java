package hengai.com.shishuo.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by yu on 2017/8/9 0009.
 */

public class DataBean {

    /**
     * branchId : 0
     * catgset : [{"catg":{"code":"3168","id":1524,"level":"","name":"教师招聘考试"},"scatgs":[{"code":"DGGN","id":21,"level":"","name":"思想品德\u2014初中学科"},{"code":"XWXX","id":25,"level":"","name":"语文-小学"}]}]
     */

    private SettingBean setting;
    /**
     * alias : 湖南分校
     * cityId : 0
     * cityName :
     * code : aa
     * iconUrl :
     * id : 1
     * memo :
     * name : 湖南分校
     * orgName : 良师说
     * orgid : 15
     * prvnId : 19
     * prvnName : 湖南
     * status : Y
     */

    private List<BranchsBean> branchs;
    /**
     * code : 3168
     * id : 1524
     * level :
     * name : 教师招聘考试
     */

    private List<CatgsBean> catgs;

    /**
     "code": "MQ",
     "id": 1,
     "level": "MID",
     "name": "综合素质-中学"
     */
    @SerializedName("scatgs-1588")
    public List<CatgsBean> scatgs1588;
    @SerializedName("scatgs-1524")
    public List<CatgsBean> scatgs1524;
    @SerializedName("scatgs-1592")
    public List<CatgsBean> scatgs1591;

    public List<CatgsBean> getScatgs1524() {
        return scatgs1524;
    }

    public void setScatgs1524(List<CatgsBean> scatgs1524) {
        this.scatgs1524 = scatgs1524;
    }

    public List<CatgsBean> getScatgs1591() {
        return scatgs1591;
    }

    public void setScatgs1591(List<CatgsBean> scatgs1591) {
        this.scatgs1591 = scatgs1591;
    }

    public List<CatgsBean> getScatgs1588() {
        return scatgs1588;
    }

    public void setScatgs1588(List<CatgsBean> scatgs1588) {
        this.scatgs1588 = scatgs1588;
    }

    /**
     * code : KD
     * name : 幼儿园
     */

    private List<LevelsBean> levels;

    public SettingBean getSetting() {
        return setting;
    }

    public void setSetting(SettingBean setting) {
        this.setting = setting;
    }

    public List<BranchsBean> getBranchs() {
        return branchs;
    }

    public void setBranchs(List<BranchsBean> branchs) {
        this.branchs = branchs;
    }

    public List<CatgsBean> getCatgs() {
        return catgs;
    }

    public void setCatgs(List<CatgsBean> catgs) {
        this.catgs = catgs;
    }

    public List<LevelsBean> getLevels() {
        return levels;
    }

    public void setLevels(List<LevelsBean> levels) {
        this.levels = levels;
    }

    public static class SettingBean {
        private int branchId;
        /**
         * catg : {"code":"3168","id":1524,"level":"","name":"教师招聘考试"}
         * scatgs : [{"code":"DGGN","id":21,"level":"","name":"思想品德\u2014初中学科"},{"code":"XWXX","id":25,"level":"","name":"语文-小学"}]
         */

        private List<CatgsetBean> catgset;

        public int getBranchId() {
            return branchId;
        }

        public void setBranchId(int branchId) {
            this.branchId = branchId;
        }

        public List<CatgsetBean> getCatgset() {
            return catgset;
        }

        public void setCatgset(List<CatgsetBean> catgset) {
            this.catgset = catgset;
        }

        public static class CatgsetBean {
            /**
             * code : 3168
             * id : 1524
             * level :
             * name : 教师招聘考试
             */

            private CatgBean catg;
            /**
             * code : DGGN
             * id : 21
             * level :
             * name : 思想品德—初中学科
             */

            private List<ScatgsBean> scatgs;

            public CatgBean getCatg() {
                return catg;
            }

            public void setCatg(CatgBean catg) {
                this.catg = catg;
            }

            public List<ScatgsBean> getScatgs() {
                return scatgs;
            }

            public void setScatgs(List<ScatgsBean> scatgs) {
                this.scatgs = scatgs;
            }

            public static class CatgBean {
                private String code;
                private int id;
                private String level;
                private String name;

                public String getCode() {
                    return code;
                }

                public void setCode(String code) {
                    this.code = code;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getLevel() {
                    return level;
                }

                public void setLevel(String level) {
                    this.level = level;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }

            public static class ScatgsBean {
                private String code;
                private int id;
                private String level;
                private String name;

                public String getCode() {
                    return code;
                }

                public void setCode(String code) {
                    this.code = code;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getLevel() {
                    return level;
                }

                public void setLevel(String level) {
                    this.level = level;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }
        }
    }

    public static class BranchsBean {
        private String alias;
        private int cityId;
        private String cityName;
        private String code;
        private String iconUrl;
        private int id;
        private String memo;
        private String name;
        private String orgName;
        private int orgid;
        private int prvnId;
        private String prvnName;
        private String status;

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public int getCityId() {
            return cityId;
        }

        public void setCityId(int cityId) {
            this.cityId = cityId;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getIconUrl() {
            return iconUrl;
        }

        public void setIconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getOrgName() {
            return orgName;
        }

        public void setOrgName(String orgName) {
            this.orgName = orgName;
        }

        public int getOrgid() {
            return orgid;
        }

        public void setOrgid(int orgid) {
            this.orgid = orgid;
        }

        public int getPrvnId() {
            return prvnId;
        }

        public void setPrvnId(int prvnId) {
            this.prvnId = prvnId;
        }

        public String getPrvnName() {
            return prvnName;
        }

        public void setPrvnName(String prvnName) {
            this.prvnName = prvnName;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    public static class CatgsBean {
        private String code;
        private int id;
        private String level;
        private String name;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class LevelsBean {
        private String code;
        private String name;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    @Override
    public String toString() {
        return "DataBean{" +
                "setting=" + setting +
                ", branchs=" + branchs +
                ", catgs=" + catgs +
                ", levels=" + levels +
                '}';
    }
}
