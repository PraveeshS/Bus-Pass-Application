package com.example.buspassapplication;

public class Apply {
    private String name;
    private String collegeName;
    private String gender;
    private String from;
    private String to;
    private String year;
    private String aadhaarNo;
    private String mobileNo;
    private String imageURL;
    private String passStatus;

    public Apply() {
        // Default constructor required for Firebase deserialization
    }
    public Apply(String name, String collegeName, String gender, String from, String to, String year, String aadhaarNo, String mobileNo) {
        this.name = name;
        this.collegeName = collegeName;
        this.gender = gender;
        this.from = from;
        this.to = to;
        this.year = year;
        this.aadhaarNo =aadhaarNo;
        this.mobileNo = mobileNo;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getAadhaarNo() {
        return aadhaarNo;
    }

    public void setAadhaarNo(String aadhaarNo) {
        this.aadhaarNo = aadhaarNo;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getPassStatus() {
        return passStatus;
    }

    public void setPassStatus(String passStatus) {
        this.passStatus = passStatus;
    }
}

