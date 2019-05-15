package kr.co.md00to22.i_note0x0;

import java.util.ArrayList;

public class VKids {

    private int kid_code;
    private String name;
    private int status;
    private int age;
    private int in_organization;
    private String in_orgName;
    private int in_class;
    private String photoUrl;
    private ArrayList<String> adults;

    public VKids() {
    }

    public VKids(int kid_code, String name, int status, int in_organization, String in_orgName) {
        this.kid_code = kid_code;
        this.name = name;
        this.status = status;
        this.in_organization = in_organization;
        this.in_orgName=in_orgName;
    }

    public VKids(int kid_code, String name, int status, int age, int in_organization,String in_orgName, int in_class,String photoUrl ,ArrayList<String> adults ) {
        this.kid_code = kid_code;
        this.name = name;
        this.status = status;
        this.age = age;
        this.in_organization = in_organization;
        this.in_class = in_class;
        this.adults = adults;
        this.photoUrl=photoUrl;
        this.in_orgName=in_orgName;
    }


    public String getIn_orgName() {
        return in_orgName;
    }

    public void setIn_orgName(String in_orgName) {
        this.in_orgName = in_orgName;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public int getKid_code() {
        return kid_code;
    }

    public void setKid_code(int kid_code) {
        this.kid_code = kid_code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getIn_organization() {
        return in_organization;
    }

    public void setIn_organization(int in_organization) {
        this.in_organization = in_organization;
    }

    public int getIn_class() {
        return in_class;
    }

    public void setIn_class(int in_class) {
        this.in_class = in_class;
    }

    public ArrayList<String> getAdults() {
        return adults;
    }

    public void setAdults(ArrayList<String> adults) {
        this.adults = adults;
    }

    public String addAdults(String new_adults) {
        if (this.adults==null) this.adults=new ArrayList<>();

        if (this.adults.size()<4){
            this.adults.add(new_adults);
            return "OK";
        }
        else return "ISFULLED";
    }

//    public String removeAdults(String adultCode){
//        if(this.adults==null || this.adults.size()==0){
//            return "NO ADULT";
//        }else{
//            for(int i=0; i<this.adults.size() ; i++){
//
//            }
//        }
//
//    }


}
