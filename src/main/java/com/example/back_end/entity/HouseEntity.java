package com.example.back_end.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "house", schema = "zlm", catalog = "")
public class HouseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private String id;
    @Basic
    @Column(name = "create_time")
    private String createTime;
    @Basic
    @Column(name = "display_source")
    private String displaySource;
    @Basic
    @Column(name = "display_rent_type")
    private String displayRentType;
    @Basic
    @Column(name = "icon")
    private String icon;
    @Basic
    @Column(name = "publish_date")
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
    @Column(name = "rent_type")
    private Integer rentType;
    @Basic
    @Column(name = "online_url")
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
    @Column(name = "report_num")
    @JsonIgnore
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
    @Column(name = "metro_line")
    private Integer metroLine;
    @Basic
    @Column(name = "metro_station")
    private String metroStation;
    @Basic
    @Column(name = "first_pic_url")
    private String firstPicUrl;
    @Basic
    @Column(name = "geocode")
    private String geocode;
    @Basic
    @Column(name = "compress")
    private String compress;

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
        return Objects.equals(id, that.id) && Objects.equals(createTime, that.createTime) && Objects.equals(displaySource, that.displaySource) && Objects.equals(displayRentType, that.displayRentType) && Objects.equals(icon, that.icon) && Objects.equals(publishDate, that.publishDate) && Objects.equals(pictures, that.pictures) && Objects.equals(title, that.title) && Objects.equals(location, that.location) && Objects.equals(longitude, that.longitude) && Objects.equals(latitude, that.latitude) && Objects.equals(rentType, that.rentType) && Objects.equals(onlineUrl, that.onlineUrl) && Objects.equals(district, that.district) && Objects.equals(city, that.city) && Objects.equals(price, that.price) && Objects.equals(source, that.source) && Objects.equals(reportNum, that.reportNum) && Objects.equals(residential, that.residential) && Objects.equals(squares, that.squares) && Objects.equals(layout, that.layout) && Objects.equals(shi, that.shi) && Objects.equals(ting, that.ting) && Objects.equals(wei, that.wei) && Objects.equals(metroLine, that.metroLine) && Objects.equals(metroStation, that.metroStation) && Objects.equals(firstPicUrl, that.firstPicUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createTime, displaySource, displayRentType, icon, publishDate, pictures, title, location, longitude, latitude, rentType, onlineUrl, district, city, price, source, reportNum, residential, squares, layout, shi, ting, wei, metroLine, metroStation, firstPicUrl);
    }

    public String getGeocode() {
        return geocode;
    }

    public void setGeocode(String geocode) {
        this.geocode = geocode;
    }

    public String getCompress() {
        return compress;
    }

    public void setCompress(String compress) {
        this.compress = compress;
    }
}
