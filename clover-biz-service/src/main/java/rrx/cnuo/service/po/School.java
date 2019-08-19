package rrx.cnuo.service.po;

public class School {
    private Integer id;

    private String name;

    private String place;

    private String type;

    private String properties;

    private Integer collegeLevel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place == null ? null : place.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getProperties() {
        return properties;
    }

    public void setProperties(String properties) {
        this.properties = properties == null ? null : properties.trim();
    }

    public Integer getCollegeLevel() {
        return collegeLevel;
    }

    public void setCollegeLevel(Integer collegeLevel) {
        this.collegeLevel = collegeLevel;
    }
}