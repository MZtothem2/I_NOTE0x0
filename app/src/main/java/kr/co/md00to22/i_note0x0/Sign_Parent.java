package kr.co.md00to22.i_note0x0;

public class Sign_Parent extends Sign_Member {
    //교사
//    private String identifyCode;
//    private String password;
//    private String name;
//    private int memberCode;
//    String deviceToken;
//    int isApproved;


    public Sign_Parent() {
    }

    public Sign_Parent(String mail, String password, String name, int memberGrade) {
        this.setId(mail);
        this.setPassword(password);
        this.setName(name);
        this.setMemberGrade(memberGrade);

        //todo: testBelongingOrznizaion으로 DB에서 해당기관 자료 찾기
        setmIdentifyCode();
    }


}