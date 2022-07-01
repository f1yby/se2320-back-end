package com.example.back_end.entity;

import javax.persistence.*;

@Entity
@Table(name = "house", schema = "zlm", catalog = "")
public class HouseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private String id;
    @Basic
    @Column(name = "createTime")
    private String createTime;
    @Basic
    @Column(name = "displaySource")
    private String displaySource;
    @Basic
    @Column(name = "displayRentType")
    private String displayRentType;
    @Basic
    @Column(name = "icon")
    private String icon;
    @Basic
    @Column(name = "publishDate")
    private String publishDate;
    @Basic
    @Column(name = "pictures")
    private String pictures;
    @Basic
    @Column(name = "title")
    private String title;
    @Basic
    @Column(name = "location")
    private String location;
    @Basic
    @Column(name = "longitude")
    private String longitude;
    @Basic
    @Column(name = "latitude")
    private String latitude;
    @Basic
    @Column(name = "rentType")
    private Integer rentType;
    @Basic
    @Column(name = "onlineURL")
    private String onlineUrl;
    @Basic
    @Column(name = "district")
    private String district;
    @Basic
    @Column(name = "city")
    private String city;
    @Basic
    @Column(name = "price")
    private Integer price;
    @Basic
    @Column(name = "source")
    private String source;
    @Basic
    @Column(name = "reportNum")
    private String reportNum;
    @Basic
    @Column(name = "residential")
    private String residential;
    @Basic
    @Column(name = "squares")
    private Double squares;
    @Basic
    @Column(name = "layout")
    private String layout;
    @Basic
    @Column(name = "shi")
    private Integer shi;
    @Basic
    @Column(name = "ting")
    private Integer ting;
    @Basic
    @Column(name = "wei")
    private Integer wei;
    @Basic
    @Column(name = "metroLine")
    private Integer metroLine;
    @Basic
    @Column(name = "metroStation")
    private String metroStation;
    @Basic
    @Column(name = "firstPicUrl")
    private String firstPicUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDisplaySource() {
        return displaySource;
    }

    public void setDisplaySource(String displaySource) {
        this.displaySource = displaySource;
    }

    public String getDisplayRentType() {
        return displayRentType;
    }

    public void setDisplayRentType(String displayRentType) {
        this.displayRentType = displayRentType;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getPictures() {
        return pictures;
    }

    public void setPictures(String pictures) {
        this.pictures = pictures;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public Integer getRentType() {
        return rentType;
    }

    public void setRentType(Integer rentType) {
        this.rentType = rentType;
    }

    public String getOnlineUrl() {
        return onlineUrl;
    }

    public void setOnlineUrl(String onlineUrl) {
        this.onlineUrl = onlineUrl;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getReportNum() {
        return reportNum;
    }

    public void setReportNum(String reportNum) {
        this.reportNum = reportNum;
    }

    public String getResidential() {
        return residential;
    }

    public void setResidential(String residential) {
        this.residential = residential;
    }

    public Double getSquares() {
        return squares;
    }

    public void setSquares(Double squares) {
        this.squares = squares;
    }

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public Integer getShi() {
        return shi;
    }

    public void setShi(Integer shi) {
        this.shi = shi;
    }

    public Integer getTing() {
        return ting;
    }

    public void setTing(Integer ting) {
        this.ting = ting;
    }

    public Integer getWei() {
        return wei;
    }

    public void setWei(Integer wei) {
        this.wei = wei;
    }

    public Integer getMetroLine() {
        return metroLine;
    }

    public void setMetroLine(Integer metroLine) {
        this.metroLine = metroLine;
    }

    public String getMetroStation() {
        return metroStation;
    }

    public void setMetroStation(String metroStation) {
        this.metroStation = metroStation;
    }

    public String getFirstPicUrl() {
        return firstPicUrl;
    }

    public void setFirstPicUrl(String firstPicUrl) {
        this.firstPicUrl = firstPicUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HouseEntity that = (HouseEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (displaySource != null ? !displaySource.equals(that.displaySource) : that.displaySource != null)
            return false;
        if (displayRentType != null ? !displayRentType.equals(that.displayRentType) : that.displayRentType != null)
            return false;
        if (icon != null ? !icon.equals(that.icon) : that.icon != null) return false;
        if (publishDate != null ? !publishDate.equals(that.publishDate) : that.publishDate != null) return false;
        if (pictures != null ? !pictures.equals(that.pictures) : that.pictures != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (location != null ? !location.equals(that.location) : that.location != null) return false;
        if (longitude != null ? !longitude.equals(that.longitude) : that.longitude != null) return false;
        if (latitude != null ? !latitude.equals(that.latitude) : that.latitude != null) return false;
        if (rentType != null ? !rentType.equals(that.rentType) : that.rentType != null) return false;
        if (onlineUrl != null ? !onlineUrl.equals(that.onlineUrl) : that.onlineUrl != null) return false;
        if (district != null ? !district.equals(that.district) : that.district != null) return false;
        if (city != null ? !city.equals(that.city) : that.city != null) return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        if (source != null ? !source.equals(that.source) : that.source != null) return false;
        if (reportNum != null ? !reportNum.equals(that.reportNum) : that.reportNum != null) return false;
        if (residential != null ? !residential.equals(that.residential) : that.residential != null) return false;
        if (squares != null ? !squares.equals(that.squares) : that.squares != null) return false;
        if (layout != null ? !layout.equals(that.layout) : that.layout != null) return false;
        if (shi != null ? !shi.equals(that.shi) : that.shi != null) return false;
        if (ting != null ? !ting.equals(that.ting) : that.ting != null) return false;
        if (wei != null ? !wei.equals(that.wei) : that.wei != null) return false;
        if (metroLine != null ? !metroLine.equals(that.metroLine) : that.metroLine != null) return false;
        if (metroStation != null ? !metroStation.equals(that.metroStation) : that.metroStation != null) return false;
        if (firstPicUrl != null ? !firstPicUrl.equals(that.firstPicUrl) : that.firstPicUrl != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (displaySource != null ? displaySource.hashCode() : 0);
        result = 31 * result + (displayRentType != null ? displayRentType.hashCode() : 0);
        result = 31 * result + (icon != null ? icon.hashCode() : 0);
        result = 31 * result + (publishDate != null ? publishDate.hashCode() : 0);
        result = 31 * result + (pictures != null ? pictures.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (longitude != null ? longitude.hashCode() : 0);
        result = 31 * result + (latitude != null ? latitude.hashCode() : 0);
        result = 31 * result + (rentType != null ? rentType.hashCode() : 0);
        result = 31 * result + (onlineUrl != null ? onlineUrl.hashCode() : 0);
        result = 31 * result + (district != null ? district.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (source != null ? source.hashCode() : 0);
        result = 31 * result + (reportNum != null ? reportNum.hashCode() : 0);
        result = 31 * result + (residential != null ? residential.hashCode() : 0);
        result = 31 * result + (squares != null ? squares.hashCode() : 0);
        result = 31 * result + (layout != null ? layout.hashCode() : 0);
        result = 31 * result + (shi != null ? shi.hashCode() : 0);
        result = 31 * result + (ting != null ? ting.hashCode() : 0);
        result = 31 * result + (wei != null ? wei.hashCode() : 0);
        result = 31 * result + (metroLine != null ? metroLine.hashCode() : 0);
        result = 31 * result + (metroStation != null ? metroStation.hashCode() : 0);
        result = 31 * result + (firstPicUrl != null ? firstPicUrl.hashCode() : 0);
        return result;
    }
}
