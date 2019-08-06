package entity;

/**
 * @Function 区域
 * @author Double
 */

public class BusiArea {
    /**
     * 国家
     */
    private String country = "中国";
    /**
     * 省份
     */
    private String province = "广东";
    /**
     * 城市
     */
    private String city = "潮州";

    public BusiArea(String country, String province, String city) {
        this.country = country;
        this.province = province;
        this.city = city;
    }

    public BusiArea(String province, String city) {
        this.province = province;
        this.city = city;
    }

    public BusiArea(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
