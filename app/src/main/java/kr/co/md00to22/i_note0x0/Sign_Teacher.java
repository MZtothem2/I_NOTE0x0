package kr.co.md00to22.i_note0x0;

public class Sign_Teacher extends Sign_Member {
    //교사
//    private String id;
//    private int mIdentifyCode;
//    private String password;
//    private String name;
//    private int memberGrade;
//    private int organization_code;
//    String deviceToken;
//    int isApproved;


    public Sign_Teacher(String mail, String password, String name, int memberGrade) {
        this.setId(mail);
        this.setPassword(password);
        this.setName(name);
        this.setMemberGrade(memberGrade);
        //todo: testBelongingOrznizaion으로 DB에서 해당기관 자료 찾기
        setmIdentifyCode();
    }

    public Sign_Teacher() {
    }
}
