package kr.co.md00to22.i_note0x0;

public class VOrganization {

    private int org_code;
    private String name;
    private String address;
    private String phone;

    public VOrganization() {
    }

    public VOrganization(int org_code, String name, String address, String phone) {
        this.org_code = org_code;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public int getOrg_code() {
        return org_code;
    }

    public void setOrg_code(int org_code) {
        this.org_code = org_code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
