package kr.co.md00to22.i_note0x0;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Sign_Member {
    private String id;
    private long mIdentifyCode;
    private String password;
    private String name;
    private int memberGrade;
    private long organization_code;
    String deviceToken;
    int isApproved;

    public Sign_Member() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getmIdentifyCode() {
        return mIdentifyCode;
    }

    public void setmIdentifyCode() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyMMddHHmmss");
        String s=sdf.format(new Date());
        String tempIdCode="77"+memberGrade+s;

        mIdentifyCode=Long.parseLong(tempIdCode);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMemberGrade() {
        return memberGrade;
    }

    public void setMemberGrade(int memberGrade) {
        this.memberGrade = memberGrade;
    }

    public long getOrganization_code() {
        return organization_code;
    }

    public void setOrganization_code(long organization_code) {
        this.organization_code = organization_code;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public int getIsApproved() {
        return isApproved;
    }

    public void setmIdentifyCode(long mIdentifyCode) {
        this.mIdentifyCode = mIdentifyCode;
    }

    public void setIsApproved(int isApproved) {
        this.isApproved = isApproved;
    }


}
