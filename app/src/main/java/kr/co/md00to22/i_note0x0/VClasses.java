package kr.co.md00to22.i_note0x0;

public class VClasses {
    private int class_code;
    private String class_name;
    private int class_age;
    private int in_organization;

    public VClasses() {
    }

    public VClasses(int class_code, String class_name, int class_age, int in_organization) {
        this.class_code = class_code;
        this.class_name = class_name;
        this.class_age = class_age;
        this.in_organization = in_organization;
    }

    public int getClass_code() {
        return class_code;
    }

    public void setClass_code(int class_code) {
        this.class_code = class_code;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public int getClass_age() {
        return class_age;
    }

    public void setClass_age(int class_age) {
        this.class_age = class_age;
    }

    public int getIn_organization() {
        return in_organization;
    }

    public void setIn_organization(int in_organization) {
        this.in_organization = in_organization;
    }
}
