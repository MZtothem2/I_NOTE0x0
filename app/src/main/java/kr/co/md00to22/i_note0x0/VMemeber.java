package kr.co.md00to22.i_note0x0;

public class VMemeber {
    private String id;
    private String password;
    private int level;
    private int approved;

    private String name;
    private String phone;
    private String device_code;
    private int in_organization;

    public VMemeber() {
    }

    public VMemeber(String id, String password, int level, int approved, String name, String phone) {
        this.id = id;
        this.password = password;
        this.level = level;
        this.approved = approved;
        this.name = name;
        this.phone = phone;
    }

    public VMemeber(String id, String password, int level, int approved, String name, String phone, String device_code, int in_organization) {
        this.id = id;
        this.password = password;
        this.level = level;
        this.approved = approved;
        this.name = name;
        this.phone = phone;
        this.device_code = device_code;
        this.in_organization = in_organization;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getApproved() {
        return approved;
    }

    public void setApproved(int approved) {
        this.approved = approved;
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

    public String getDevice_code() {
        return device_code;
    }

    public void setDevice_code(String device_code) {
        this.device_code = device_code;
    }

    public int getIn_organization() {
        return in_organization;
    }

    public void setIn_organization(int in_organization) {
        this.in_organization = in_organization;
    }
}
