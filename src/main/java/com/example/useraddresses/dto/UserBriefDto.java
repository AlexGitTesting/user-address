package com.example.useraddresses.dto;


/**
 *
 */
// TODO: 12.02.2022 fill
public class UserBriefDto extends BaseDto {
    private String fullName;
    private String countryName;
    private String cityName;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public UserBriefDto(Long id, String fullName, String countryName, String cityName) {
        super(id);
        this.fullName = fullName;
        this.countryName = countryName;
        this.cityName = cityName;
    }

    public UserBriefDto() {
    }
}
