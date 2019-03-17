package kr.co.md00to22.i_note0x0;

public class Member {
    private String id;
    private int mIdentifyCode;
    private String password;
    private String name;
    private int memberGrade;
    private int organization_code;

    private String profilePhotoAddr;
    private String deviceToken;
    private int isApproved;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getmIdentifyCode() {
        return mIdentifyCode;
    }

    public void setmIdentifyCode(int index) {
        //String tempIdCode=G.NMEMBER+memberGrade+index;

        mIdentifyCode=index;
    }

    public void setmIdentifyCode(String test, int testNum) {
        mIdentifyCode=testNum;
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

    public int getOrganization_code() {
        return organization_code;
    }

    public void setOrganization_code(int organization_code) {
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

    public void setIsApproved(int isApproved) {
        this.isApproved = isApproved;
    }

    public String getProfilePhotoAddr() {
        return profilePhotoAddr;
    }

    public void setProfilePhotoAddr(String profilePhotoAdd) {
        this.profilePhotoAddr = profilePhotoAdd;
    }
}
