package com.example.radsabazompoapi;

public class Country {

    private  long id;
    private String mark;

    private String nameSr;
    private String nameEn;

    private String capitalCitySr;
    private String capitalCityEn;

    private String neighboringCountrySr;
    private String neighboringCountryEn;

    private String countryLandmarkSr;
    private String countryLandmarkEn;

    private double capitalCityLatitude;
    private double capitalCityLongitude;

    private String cityCoatOfArmsImage;
    private String flagImage;
    private String landmarkImage;

    @Override
    public String toString() {
        return "Country{" +
                "id='" + id + '\'' +
                "mark='" + mark + '\'' +
                ", nameSr='" + nameSr + '\'' +
                ", nameEn='" + nameEn + '\'' +
                ", capitalCitySr='" + capitalCitySr + '\'' +
                ", capitalCityEn='" + capitalCityEn + '\'' +
                ", neighboringCountrySr='" + neighboringCountrySr + '\'' +
                ", neighboringCountryEn='" + neighboringCountryEn + '\'' +
                ", countryLandmarkSr='" + countryLandmarkSr + '\'' +
                ", countryLandmarkEn='" + countryLandmarkEn + '\'' +
                ", capitalCityLatitude=" + capitalCityLatitude +
                ", capitalCityLongitude=" + capitalCityLongitude +
                ", cityCoatOfArmsImage='" + cityCoatOfArmsImage + '\'' +
                ", flagImage='" + flagImage + '\'' +
                ", landmarkImage='" + landmarkImage + '\'' +
                '}';
    }

    public Country() {
    }

    public Country(long id, String mark, String nameSr, String nameEn, String capitalCitySr, String capitalCityEn,
                   String neighboringCountrySr, String neighboringCountryEn, String countryLandmarkSr,
                   String countryLandmarkEn, double latitude, double longitude) {
        this.id=id;
        this.mark = mark;
        this.nameSr = nameSr;
        this.nameEn = nameEn;
        this.capitalCitySr = capitalCitySr;
        this.capitalCityEn = capitalCityEn;
        this.neighboringCountrySr = neighboringCountrySr;
        this.neighboringCountryEn = neighboringCountryEn;
        this.countryLandmarkSr = countryLandmarkSr;
        this.countryLandmarkEn = countryLandmarkEn;

        capitalCityLatitude = latitude;
        capitalCityLongitude = longitude;

        cityCoatOfArmsImage = mark + "_grb";
        flagImage = mark + "_zastava";
        landmarkImage = mark + "_znamenitost";
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNameSr() {
        return nameSr;
    }

    public void setNameSr(String nameSr) {
        this.nameSr = nameSr;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getCapitalCitySr() {
        return capitalCitySr;
    }

    public void setCapitalCitySr(String capitalCitySr) {
        this.capitalCitySr = capitalCitySr;
    }

    public String getCapitalCityEn() {
        return capitalCityEn;
    }

    public void setCapitalCityEn(String capitalCityEn) {
        this.capitalCityEn = capitalCityEn;
    }

    public String getNeighboringCountrySr() {
        return neighboringCountrySr;
    }

    public void setNeighboringCountrySr(String neighboringCountrySr) {
        this.neighboringCountrySr = neighboringCountrySr;
    }

    public String getNeighboringCountryEn() {
        return neighboringCountryEn;
    }

    public void setNeighboringCountryEn(String neighboringCountryEn) {
        this.neighboringCountryEn = neighboringCountryEn;
    }

    public String getCountryLandmarkSr() {
        return countryLandmarkSr;
    }

    public void setCountryLandmarkSr(String countryLandmarkSr) {
        this.countryLandmarkSr = countryLandmarkSr;
    }

    public String getCountryLandmarkEn() {
        return countryLandmarkEn;
    }

    public void setCountryLandmarkEn(String countryLandmarkEn) {
        this.countryLandmarkEn = countryLandmarkEn;
    }

    public String getCityCoatOfArmsImage() {
        return cityCoatOfArmsImage;
    }

    public void setCityCoatOfArmsImage(String cityCoatOfArmsImage) {
        this.cityCoatOfArmsImage = cityCoatOfArmsImage;
    }

    public String getFlagImage() {
        return flagImage;
    }

    public void setFlagImage(String flagImage) {
        this.flagImage = flagImage;
    }

    public String getLandmarkImage() {
        return landmarkImage;
    }

    public void setLandmarkImage(String landmarkImage) {
        this.landmarkImage = landmarkImage;
    }

    public double getCapitalCityLatitude() {
        return capitalCityLatitude;
    }

    public void setCapitalCityLatitude(double capitalCityLatitude) {
        this.capitalCityLatitude = capitalCityLatitude;
    }

    public double getCapitalCityLongitude() {
        return capitalCityLongitude;
    }

    public void setCapitalCityLongitude(double capitalCityLongitude) {
        this.capitalCityLongitude = capitalCityLongitude;
    }


    public void makeCityCoatOfArmsImageProperty(){
        cityCoatOfArmsImage=mark + "_grb";
    }
    public void makeFlagImageProperty(){
        flagImage=mark + "_zastava";
    }
    public void makeLandmarkImageProperty(){
        landmarkImage=mark + "_znamenitost";
    }

}
