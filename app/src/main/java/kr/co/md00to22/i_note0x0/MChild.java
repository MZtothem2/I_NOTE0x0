package kr.co.md00to22.i_note0x0;

import java.util.ArrayList;


public class MChild {
    private int organizaionCode;
    private String name;
    private int birthY;

    private int isAttending;
    private int childCode;

    private int classcode;
    private int[] parentCode;

    ArrayList<Integer> parents;


    public MChild(String test, String testName, int testBirthY, int testIsAttending, int testChildCode, int parentsCode, int organizaionCode, int classcode) {
        this.organizaionCode=organizaionCode;
        this.childCode = testChildCode;
        parents=new ArrayList<>();
        parents.add(parentsCode);
        this.classcode=classcode;


        this.name = testName;
        this.birthY = testBirthY;
        this.isAttending = testIsAttending;

    }

    public MChild(String name, int birthY, int isAttending, int childCode, String parentCodes, int organizaionCode, int classcode) {
        this.organizaionCode=organizaionCode;
        this.childCode = childCode;
//        parents=new ArrayList<>();
//        parents.add(parentsCode);
        this.classcode=classcode;


        this.name = name;
        this.birthY = birthY;
        this.isAttending =isAttending;

        parentCode=splitPcodes(parentCodes);
    }



    private int[] splitPcodes(String parentCodes){
        if (parentCodes==null || parentCodes.equals("") || !parentCodes.contains(";")) return null;

        String[] parents=parentCodes.split(";");
        int[] parentCode=new int[parents.length];


        for(int i=0; i<parents.length; i++){
            parentCode[i]=Integer.parseInt(parents[i]);
        }
        return parentCode;
    }


    public int getClasscode() {
        return classcode;
    }

    public void setClasscode(int classcode) {
        this.classcode = classcode;
    }

    public long getOrganizaionCode() {
        return organizaionCode;
    }

    public void setOrganizaionCode(int organizaionCode) {
        this.organizaionCode = organizaionCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBirthY() {
        return birthY;
    }

    public void setBirthY(int birthY) {
        this.birthY = birthY;
    }

    public int getIsAttending() {
        return isAttending;
    }

    public void setIsAttending(int isAttending) {
        this.isAttending = isAttending;
    }

    public int getChildCode() {
        return childCode;
    }

    public void setChildCode(int childCode) {
        this.childCode = childCode;
    }

    public int[] getParentCode() {
        return parentCode;
    }

    public void setParentCode(int[] parentCode) {
        this.parentCode = parentCode;
    }

    public void addParentsTest(int pcode){
        parents.add(pcode);
    }
}
