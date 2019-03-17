package kr.co.md00to22.i_note0x0;

public class MTeacher extends Member {
    //교사
//    private String id;
//    private int mIdentifyCode;
//    private String password;
//    private String name;
//    private int memberGrade;
//    private int organization_code;
//    String deviceToken;
//    int isApproved;

    private int chargingClassCode;
    private String chargingClassName;

    private int isWorking;

    public MTeacher(String mail, String password, String name, int memberGrade, int isWorking, int organizaiotnCode, int mIdentifyCode) {
        this.setId(mail);
        this.setPassword(password);
        this.setName(name);
        this.setMemberGrade(memberGrade);
        this.setOrganization_code(organizaiotnCode);
        this.isWorking = isWorking;
        this.setmIdentifyCode(mIdentifyCode);
        //todo: testBelongingOrznizaion으로 DB에서 해당기관 자료 찾기

    }


    public int getIsWorking() {
        return isWorking;
    }

    public void setIsWorking(int isWorking) {
        this.isWorking = isWorking;
    }

    public int getChargingClassCode() {
        return chargingClassCode;
    }

    public void setChargingClassCode(int chargingClassCode) {
        this.chargingClassCode = chargingClassCode;
    }

    public String getChargingClassName() {
        return chargingClassName;
    }

    public void setChargingClassName(String chargingClassName) {
        this.chargingClassName = chargingClassName;
    }
}
