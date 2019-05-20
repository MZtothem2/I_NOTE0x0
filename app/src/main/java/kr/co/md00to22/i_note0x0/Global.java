package kr.co.md00to22.i_note0x0;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Global {

//staics.....
    //LEVEL==GRADE
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

    //체크상태
    public final static int CHECK_O=1;
    public final static int CHECK_M=2;
    public final static int CHECK_X=3;
    public final static int CHECK_NONE=0;

    //알림장 삭제여부 (보이기)
    public final static boolean NOTE_VISIBLE=true;
    public final static boolean NOTE_DELETE=false;

    //날짜계산
    public final static int GETTIME_DAY=0;
    public final static int GETTIME_SECOND=1;



//정보
    private static VMemeber loginUser;
    private static ArrayList<VNotes> notes_got;
    private static ArrayList<VNoteOne> notesToShow; //noteToShow

    //교사만
    private static VOrganization loginOrg;
    private static ArrayList<VClasses> classStructures;
    private static ArrayList<VKids> orgKids;
    private static ArrayList<String> arrClsName;
    private static ArrayList<Integer> arrClsCode;
    private static ArrayList<String> arrKIdsName;
    private static ArrayList<Integer> arrKIdsCode;
    //부모만
    private static ArrayList<VKids> kids;
    private static VKids selectedKid;


    //method

    public static void arrangeClsDatas(){
        if (Global.getCls()==null) return;

        ArrayList<String> arrClsName=new ArrayList<>();
        ArrayList<Integer> arrClsCode=new ArrayList<>();

        arrClsCode.add(-1); arrClsName.add("전체");

        for (int i=0; i<Global.getCls().size(); i++){
            arrClsCode.add(Global.getCls().get(i).getClass_code());
            arrClsName.add(Global.getCls().get(i).getClass_name());
        }
        Global.setArrClsCode(arrClsCode);
        Global.setArrClsName(arrClsName);

    }

    public static void arrangeCls_KidsDatas(int clscode){
        if (Global.getOrgKids()==null) return;

        ArrayList<String> arrKIdsName=new ArrayList<>();
        ArrayList<Integer> arrKIdsCode=new ArrayList<>();

        arrKIdsCode.add(-1); arrKIdsName.add("전체");


        for (int i=0; i<orgKids.size(); i++){
          if(orgKids.get(i).getIn_class()==clscode){
              arrKIdsCode.add(orgKids.get(i).getKid_code());
              arrKIdsName.add(orgKids.get(i).getName());
          }
        }
        Global.setArrKIdsCode(arrKIdsCode);
        Global.setArrKIdsName(arrKIdsName);

    }

    public static String getTImeNow(){
        String time=new SimpleDateFormat("yyMMdd_HHmm").format(new Date());
        return time;
    }

    public static String transDateToDay(String yymmdd_HHmm, int type) throws ParseException {

        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyMMdd_HHmm");
        Date date=simpleDateFormat.parse(yymmdd_HHmm);

        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        int dayOW=calendar.get(Calendar.DAY_OF_WEEK);

        String dayOfWeek="";

        switch (dayOW){
            case 1:
                dayOfWeek="월요일";
                break;
            case 2:
                dayOfWeek="화요일";
                break;
            case 3:
                dayOfWeek="수요일";
                break;
            case 4:
                dayOfWeek="목요일";
                break;
            case 5:
                dayOfWeek="금요일";
                break;
            case 6:
                dayOfWeek="토요일";
                break;
            case 7:
                dayOfWeek="일요일";
                break;
        }

        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH)+1;
        int day=calendar.get(Calendar.DATE);
        int hour=calendar.get(Calendar.HOUR_OF_DAY);
        int minute=calendar.get(Calendar.MINUTE);
        int second=calendar.get(Calendar.SECOND);

        String s="";
        if (type==Global.GETTIME_DAY) s=year+"년 "+ month+"월 " + day+"일 "+ dayOfWeek;
        else s=year+"년 "+ month+"월 " + day+"일 "+ hour +":"+ minute +":" +second;
        return s;
    }

    public static int seperateNotes(){
        ArrayList<VNotes> notes=Global.getNotes_got();
        ArrayList<VNoteOne> rangedArr=new ArrayList<>();

        StringBuffer testBuffer=new StringBuffer();

        for(int i=0; i<notes.size(); i++){
            VNoteOne a=new VNoteOne();

            String date=notes.get(i).getWrite_date().split("_")[0];
            int kid=notes.get(i).getKid_code();

            a.putNote(notes.get(i));

            for(int k=i+1; k<notes.size(); k++){
                if(notes.get(k).getWrite_date().split("_")[0].equals(date) && notes.get(k).getKid_code()==kid){
                    a.putNote(notes.get(k));
                    i++;
                    break;
                }
            }
            rangedArr.add(a);
            testBuffer.append(a.checkNoteOne() + " // ");
        }//for

        Global.setNotesToShow(rangedArr);

        //Toast.makeText(this, "노트정리까지함", Toast.LENGTH_SHORT).show();

        Log.i("정리한 교사 알림장",testBuffer.toString());
        return 9;
    }

    public static String getNameOfKid(int kid_code){
        String name="";
        if (orgKids==null) return "";

        for (int i=0; i<orgKids.size(); i++){
            if(orgKids.get(i).getKid_code()==kid_code) name=orgKids.get(i).getName();
        }

        return name;
    }

    public static String getNameOfCls(int cls_code){
        String name="";
        if (getCls()==null) return "";

        for(int i=0; i<getCls().size(); i++){
            if(getCls().get(i).getClass_code()==cls_code) name=getCls().get(i).getClass_name();
        }

        return name;
    }

    //getter setter


    public static ArrayList<String> getArrClsName() {
        return arrClsName;
    }

    public static void setArrClsName(ArrayList<String> arrClsName) {
        Global.arrClsName = arrClsName;
    }

    public static ArrayList<Integer> getArrClsCode() {
        return arrClsCode;
    }

    public static void setArrClsCode(ArrayList<Integer> arrClsCode) {
        Global.arrClsCode = arrClsCode;
    }

    public static ArrayList<String> getArrKIdsName() {
        return arrKIdsName;
    }

    public static void setArrKIdsName(ArrayList<String> arrKIdsName) {
        Global.arrKIdsName = arrKIdsName;
    }

    public static ArrayList<Integer> getArrKIdsCode() {
        return arrKIdsCode;
    }

    public static void setArrKIdsCode(ArrayList<Integer> arrKIdsCode) {
        Global.arrKIdsCode = arrKIdsCode;
    }

    public static ArrayList<VNoteOne> getNotesToShow() {
        return notesToShow;
    }

    public static void setNotesToShow(ArrayList<VNoteOne> notesToShow) {
        Global.notesToShow = notesToShow;
    }

    public static ArrayList<VNotes> getNotes_got() {
        return notes_got;
    }

    public static void setNotes_got(ArrayList<VNotes> notes_got) {
        Global.notes_got = notes_got;
    }

    public static ArrayList<VKids> getOrgKids() {
        return orgKids;
    }

    public static void setOrgKids(ArrayList<VKids> kidsPercls) {
        Global.orgKids = kidsPercls;
    }


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

    public static ArrayList<VClasses> getCls() {
        return classStructures;
    }

    public static void setCls(ArrayList<VClasses> classStructures) {
        Global.classStructures = classStructures;
    }

    public static void addClass(String key, VClasses cls1){
        if (classStructures!=null) classStructures.add(cls1);
    }
}
