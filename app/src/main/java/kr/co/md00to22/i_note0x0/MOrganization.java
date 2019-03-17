package kr.co.md00to22.i_note0x0;

import java.util.ArrayList;

public class MOrganization {
    private int organization_code;
    private String name;
    private String address;
    private String contactNumber;


//    private ArrayList<MTeacher> teachers;
//    private ArrayList<MChild> totalchildren;
    private ArrayList<VClass> classStructure;

    public MOrganization(String name, String address, String contactNumber, int organization_code) {
        this.name = name;
        this.address = address;
        this.contactNumber = contactNumber;
        this.organization_code=organization_code;

//        teachers=new ArrayList<>();
//        totalchildren=new ArrayList<>();
        classStructure=new ArrayList<>();

    }

    public int getOrganization_code() {
        return organization_code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }


    public ArrayList<VClass> getClassStructure() {
        return classStructure;
    }

    public void setClassStructure(ArrayList<VClass> classStructure) {
        this.classStructure = classStructure;
    }

    public void addClasses(VClass vClass){
        classStructure.add(vClass);
    }


}
