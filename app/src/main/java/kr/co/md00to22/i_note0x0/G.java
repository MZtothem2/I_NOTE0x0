package kr.co.md00to22.i_note0x0;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class  G {

//구분자
    public final static int MEMBER_GRADE_DIRECTOR=1;
    public final static int MEMBER_GRADE_TEACHER=2;
    public final static int MEMBER_GRADE_PARENT=3;

    public final static int CHECK_O=1;
    public final static int CHECK_M=2;
    public final static int CHECK_X=3;

    public final static int SUCCESS=1;
    public final static int FAIL=0;



    //교사의 재직여부
    public final static int IS_WORKING=1;
    public final static int IS_NOT_WORKING=-1;

    //자녀의 재원여부
    public final static int IS_ATTENDING=1;
    public final static int NOT_ATTENDING=-1;
    public final static int HAS_GRADUATED=0;

    //학부모의 승인여부
    public final static int WAIT_APPROVED=0;
    public final static int IS_APPROVED=1;
    public final static int NOT_APPROVED=0;

    //로그인여부
    public final static int NOT_LOGINED=0;



//변수
    private static MTeacher loginTeacher;
    private static MTeacher loginDirector;
    private static MParent loginParent;

    private static int Login_MEMBER_Grade=-1;
    private static boolean isLogined=false;
    private static boolean dataLoaded=false;
    private static boolean notesLoaded=false;
    private static boolean albumLoaded=false;



    //교사가 사용하는 변수
    private static MOrganization loginOrganization;
    private static ArrayList<MChild> allChildren;


    //private static String chargingClassName; //담임교사일경우
    private static ArrayList<VOnedayNote> allNotesT;

    //스피너
    private static ArrayList<String> classNames;
    private static ArrayList<Integer> classCodes;
    private static ArrayList<String> kidNames;
    private static ArrayList<Integer> kidCodes;


    //부모가 사용하는 변수
    public static int selectChildCodeP;
    private static ArrayList<MChild> childP=new ArrayList<>();
    private static ArrayList<VOnedayNote> allNotesP=new ArrayList<>();


    public static void setLoginID(MTeacher loginID) {


        setLogin_MEMBER_Grade(loginID.getMemberGrade());

        if (Login_MEMBER_Grade==MEMBER_GRADE_DIRECTOR) setLoginDirector(loginID);
        else if (Login_MEMBER_Grade==MEMBER_GRADE_TEACHER) setLoginTeacher(loginID);
        isLogined=true;

        if (allChildren==null) allChildren=new ArrayList<>();
        if (kidNames==null) kidNames=new ArrayList<>();
        if (kidCodes==null) kidCodes=new ArrayList<>();


    }

    public static void setLoginID(MParent loginID) {
        setLoginParent(loginID);
        isLogined=true;
        setLogin_MEMBER_Grade(loginID.getMemberGrade());

    }


    public static void calClassNKidsArrays(){

        if (allChildren==null || allChildren.size()==0) return;

        classNames=new ArrayList<>(); classNames.add("전체");
        classCodes=new ArrayList<>(); classCodes.add(-1);


        for(int i=0; i<G.getLoginOrganization().getClassStructure().size() ; i++){
            classNames.add(G.getLoginOrganization().getClassStructure().get(i).getClass_name());
            classCodes.add(G.getLoginOrganization().getClassStructure().get(i).getClass_code());
        }

        kidNames=new ArrayList<>(); kidNames.add("전체");
        kidCodes=new ArrayList<>(); kidCodes.add(-1);
        for(int i=0; i<G.allChildren.size(); i++){
                kidNames.add(allChildren.get(i).getName());
                kidCodes.add(allChildren.get(i).getChildCode());
            }

    }


    public static void calClassArrays(){

        if (G.getLoginOrganization().getClassStructure()==null || G.getLoginOrganization().getClassStructure().size()==0) return;

        classNames=new ArrayList<>(); classNames.add("전체");
        classCodes=new ArrayList<>(); classCodes.add(-1);


        for(int i=0; i<G.getLoginOrganization().getClassStructure().size() ; i++){
            classNames.add(G.getLoginOrganization().getClassStructure().get(i).getClass_name());
            classCodes.add(G.getLoginOrganization().getClassStructure().get(i).getClass_code());
        }


    }


    public static void structNotes(String oldestDay) {
        SimpleDateFormat sdf = new SimpleDateFormat("yy_MM_dd");
        String latestDayS = G.getTodayDate().get("_d");


        Date startDate = null;
        try {
            startDate = sdf.parse(oldestDay);
            Date endDate = sdf.parse(latestDayS);

            ArrayList<String> dates = new ArrayList<String>();
            Date currentDate = startDate;

            while (currentDate.compareTo(endDate) <= 0) {
                dates.add(sdf.format(currentDate));
                Calendar c = Calendar.getInstance();
                c.setTime(currentDate);
                c.add(Calendar.DAY_OF_MONTH, 1);
                currentDate = c.getTime();
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    public static String calDateData(String date_String, String limit){

        if (!date_String.contains("_")) return null;
        if (date_String.split("_").length<3) return null;

        String[] date=date_String.split("_");

        String a=date[0]+"_"+ date[1]+"_"+date[2];
        String dayOW=calDayOfWeek(a);

        if (date[1].contains("0")) date[1]=date[1].replace("0","");
        if (date[2].contains("0")) date[2]=date[2].replace("0","");

        String s="";
        if(limit.equals("date")){
            s=date[0]+"년 "+ date[1]+"월 "+date[2]+"일 "+dayOW;
        }else if (limit.equals("minute")  && date.length>=5){
            s=date[0]+"년 "+ date[1]+"월 "+date[2]+"일 "+dayOW+" "+date[3]+"시 "+ date[4]+"분";

        }else if (limit.equals("second") && date.length>=6){
            s=date[0]+"년 "+ date[1]+"월 "+date[2]+"일 "+dayOW+" "+date[3]+"시 "+ date[4]+"분"+ date[5]+"초";

        }else if (limit.equals("_d")){
            s=date[0]+"_"+ date[1]+"_"+date[2]+"_";

        }else if (limit.equals("_s") && date.length>=6){
            s=date[0]+"_"+ date[1]+"_"+date[2]+"_"+date[3]+"_"+ date[4]+"_"+ date[5];


        }else return null;
        return s;
    }

    public static HashMap<String, String> getTodayDate(){
        Calendar calendar=Calendar.getInstance();
        String year=calendar.get(Calendar.YEAR)+"";
        String month=calendar.get(Calendar.MONTH)+1+"";
        String date=calendar.get(Calendar.DATE)+"";

        String hour=calendar.get(Calendar.HOUR)+"";
        String minute=calendar.get(Calendar.MINUTE)+"";
        String second=calendar.get(Calendar.SECOND)+"";

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
        String month2=month;
        String date2=date;
        if (month2.length()==1) month2="0"+month;
        if (date2.length()==1) date2="0"+date;

        HashMap<String, String> dateData=new HashMap<>();
        dateData.put("year", year);   dateData.put("month", month);   dateData.put("date", date);  dateData.put("dayOfWeek", dayOfWeek);
        dateData.put("hour", hour);   dateData.put("minute", minute); dateData.put("second", second);
        dateData.put("_s", year.substring(year.length()-2)+"_"+month2+"_"+date2+"_"+hour+"_"+minute+"_"+second);
        dateData.put("_d", year.substring(year.length()-2)+"_"+month2+"_"+date2);

        dateData.put("full", year+"년 "+month+"월 "+date+"일 "+dayOfWeek+" "+hour+"시 "+minute+"분");
        return dateData;
    }

    public static String calDayOfWeek(String yyyy_MM_dd){
        DateFormat dateFormat=new SimpleDateFormat("yyyy_MM_dd");
        Date date = null;

        String s="";
        if (yyyy_MM_dd.split("_").length>3){
            s=yyyy_MM_dd.split("_")[0]+"_"+yyyy_MM_dd.split("_")[1]+"_"+yyyy_MM_dd.split("_")[2];
        }else if (yyyy_MM_dd.split("_").length==3){
            s=yyyy_MM_dd;
        }


        try {
           date=dateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);

        String dayOfWeek="";
        switch ( calendar.get(Calendar.DAY_OF_WEEK)){
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
        return dayOfWeek;
    }


    public static String findChildNameT(int childCode){
        if(allChildren==null || allChildren.size()==0) return null;

        String childName="";
        for(int i=0; i<allChildren.size(); i++){
            if (allChildren.get(i).getChildCode() == childCode)
                childName=allChildren.get(i).getName();
        }

        return childName;
    }

    public static String findClassName(int classCode){
        String className="";
        if (classCodes==null || classCodes.size()==0 || classNames==null || classNames.size()==0) return null;

        if(classCodes.size()==classNames.size()){
            for(int i=0; i<classCodes.size(); i++){
                if (classCodes.get(i)==classCode) className=classNames.get(i);
            }
        }else return null;

        return className;
    }

    public static ArrayList<VOnedayNote> getAllNotes() {
        return allNotesT;
    }

    public static int getIsWorking() {
        return IS_WORKING;
    }

    public static int getIsNotWorking() {
        return IS_NOT_WORKING;
    }

    public static int getIsAttending() {
        return IS_ATTENDING;
    }

    public static boolean getIsLogined() {
        return isLogined;
    }

    public static void setIsLogined(boolean isLogined) {
        G.isLogined = isLogined;
    }

    public static int getLogin_MEMBER_Grade() {
        return Login_MEMBER_Grade;
    }

    public static void setLogin_MEMBER_Grade(int login_MEMBER_CODE) {
        Login_MEMBER_Grade = login_MEMBER_CODE;
    }

    public static MTeacher getLoginTeacher() {
        return loginTeacher;
    }

    public static void setLoginTeacher(MTeacher loginTeacher) {
        G.loginTeacher = loginTeacher;
    }

    public static MTeacher getLoginDirector() {
        return loginDirector;
    }

    public static void setLoginDirector(MTeacher loginDirector) {
        G.loginDirector = loginDirector;
    }

    public static MParent getLoginParent() {
        return loginParent;
    }

    public static void setLoginParent(MParent loginParent) {
        G.loginParent = loginParent;
    }


    public static MOrganization getLoginOrganization() {
        return loginOrganization;
    }

    public static void setLoginOrganization(MOrganization loginOrganization) {
        G.loginOrganization = loginOrganization;
    }

    public static ArrayList<String> getKidNames() {
        return kidNames;
    }

    public static void setKidNames(ArrayList<String> kidNames) {
        G.kidNames = kidNames;
    }

    public static ArrayList<Integer> getKidCodes() {
        return kidCodes;
    }

    public static void setKidCodes(ArrayList<Integer> kidCodes) {
        G.kidCodes = kidCodes;
    }

    public static ArrayList<String> getClassNames() {
        return classNames;
    }

    public static void setClassNames(ArrayList<String> classNames) {
        G.classNames = classNames;
    }

    public static ArrayList<Integer> getClassCodes() {
        return classCodes;
    }

    public static void setClassCodes(ArrayList<Integer> classCodes) {
        G.classCodes = classCodes;
    }

    public static ArrayList<MChild> getAllChildren() {
        return allChildren;
    }

    public static void setAllChildren(ArrayList<MChild> allChildren) {
        G.allChildren = allChildren;
    }


    public static ArrayList<VOnedayNote> getAllNotesT() {
        return allNotesT;
    }

    public static void setAllNotesT(ArrayList<VOnedayNote> allNotesT) {
        G.allNotesT = allNotesT;
    }

    public static ArrayList<VOnedayNote> getAllNotesP() {
        return allNotesP;
    }

    public static void setAllNotesP(ArrayList<VOnedayNote> allNotesP) {
        G.allNotesP = allNotesP;
    }

    public static ArrayList<MChild> getChildP() {
        return childP;
    }

    public static void setChildP(ArrayList<MChild> childP) {
        G.childP = childP;
    }

    public static void addChildP(MChild child) {
        G.childP.add(child);
    }

    public static int getSelectChildCodeP() {
        return selectChildCodeP;
    }

    public static void setSelectChildCodeP(int selectChildP) {
        G.selectChildCodeP = selectChildP;
    }

    public static boolean isIsLogined() {
        return isLogined;
    }

    public static boolean isDataLoaded() {
        return dataLoaded;
    }

    public static void setDataLoaded(boolean dataLoaded) {
        G.dataLoaded = dataLoaded;
    }

    public static boolean isNotesLoaded() {
        return notesLoaded;
    }

    public static void setNotesLoaded(boolean notesLoaded) {
        G.notesLoaded = notesLoaded;
    }

    public static boolean isAlbumLoaded() {
        return albumLoaded;
    }

    public static void setAlbumLoaded(boolean albumLoaded) {
        G.albumLoaded = albumLoaded;
    }
}
