package com.example.sourthenlankacarrental.Guarantor;

public class Guarantor {
    private String id;
    private String name;
    private String phone;
    private String nic;
    private String gender;
    private String age;
    private String district;
    private String nic_url;
    private String customer_email;

    public Guarantor() {
    }

    public Guarantor(String id, String name, String phone, String nic, String gender, String age, String district, String nic_url, String customer_email) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.nic = nic;
        this.gender = gender;
        this.age = age;
        this.district = district;
        this.nic_url = nic_url;
        this.customer_email = customer_email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getNic_url() {
        return nic_url;
    }

    public void setNic_url(String nic_url) {
        this.nic_url = nic_url;
    }

    public String getCustomer_email() {
        return customer_email;
    }

    public void setCustomer_email(String customer_email) {
        this.customer_email = customer_email;
    }
}
