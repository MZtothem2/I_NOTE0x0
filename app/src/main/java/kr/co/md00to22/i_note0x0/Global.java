package kr.co.md00to22.i_note0x0;

import java.util.ArrayList;
import java.util.HashMap;

public class Global {
    public final static int MEMBER_GRADE_DIRECTOR=1;
    public final static int MEMBER_GRADE_TEACHER=2;
    public final static int MEMBER_GRADE_PARENT=3;

    //재원여부
    public final static int KID_STATE_ING=1;
    public final static int KID_STATE_GRADUATED=0;
    public final static int KID_STATE_STOP=-1;

    //승인여부
    public final static int WAIT_APPROVED=-1;
    public final static int IS_APPROVED=1;
    public final static int NOT_APPROVED=0;

    //정보
    private static VMemeber loginUser;
    private static VOrganization loginOrg;

    //교사만
    private static HashMap<String, VClasses> classStructures;

    //부모만
    private static ArrayList<VKids> kids;
    private static VKids selectedKid;

    public static ArrayList<VKids> getKids() {
        return kids;
    }

    public static void setKids(ArrayList<VKids> kids) {
        Global.kids = kids;
    }

    public static VKids getSelectedKid() {
        return selectedKid;
    }

    public static void setSelectedKid(VKids selectedKid) {
        Global.selectedKid = selectedKid;
    }

    public static VOrganization getLoginOrg() {
        return loginOrg;
    }

    public static void setLoginOrg(VOrganization loginOrg) {
        Global.loginOrg = loginOrg;
    }

    public static VMemeber getLoginUser() {
        return loginUser;
    }

    public static void setLoginUser(VMemeber loginUser) {
        Global.loginUser = loginUser;
//        if(loginUser.getLevel()==Global.MEMBER_GRADE_PARENT) kids=new ArrayList<>();
    }

    public static HashMap<String, VClasses> getCls() {
        return classStructures;
    }

    public static void setCls(HashMap<String, VClasses> classStructures) {
        Global.classStructures = classStructures;
    }

    public static void addClass(String key, VClasses cls1){
        if (classStructures!=null) classStructures.put(key,cls1);
    }
}
