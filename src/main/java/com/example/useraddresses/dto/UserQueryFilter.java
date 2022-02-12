package com.example.useraddresses.dto;

import java.io.Serializable;

// TODO: 12.02.2022 fill
public class UserQueryFilter implements Serializable {
    private int page;
    private int limit;
    private String sortingField;
    private boolean sortingAscending = true;
    private String city;
    private String firstName;
    private String lastName;
    private String patronymic;
    private Long countryId;

    public int getPage() {
        return Math.max(0, this.page);
    }

    public void setPage(int page) {
        this.page = Math.max(0, page);
    }

    public int getLimit() {
        return Math.max(0, this.limit);
    }

    public void setLimit(int limit) {
        this.limit = Math.max(0, limit);
    }

    public String getSortingField() {
        return sortingField;
    }

    public void setSortingField(String sortingField) {
        this.sortingField = sortingField;
    }

    public boolean isSortingAscending() {
        return sortingAscending;
    }

    public void setSortingAscending(boolean sortingAscending) {
        this.sortingAscending = sortingAscending;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }
}
