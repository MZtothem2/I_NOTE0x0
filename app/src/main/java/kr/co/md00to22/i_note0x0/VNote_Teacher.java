package kr.co.md00to22.i_note0x0;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class VNote_Teacher implements Serializable {
    private String writeDate;

    private ArrayList<Integer> checks;
    private String napTime;
    private String note;

    private String photoUrls;
    private ArrayList<String> photoArray;

    int childCode;
    int organizationCode;

    public VNote_Teacher() {
    }

    public VNote_Teacher(String writeDate, int[] check_rbs, String napTime, String note, String photoUrl, int childCode, int organizationCode) {
        this.writeDate = writeDate;
        this.napTime = napTime;
        this.note = note;
        this.childCode=childCode;
        this.organizationCode=organizationCode;


        this.checks=new ArrayList<>();
        for (int i=0; i<check_rbs.length ; i++){
            this.checks.add(check_rbs[i]);
        }


        this.photoUrls= this.photoUrls+photoUrl+";";
        splitPhotoUrl();
    }

    private void splitPhotoUrl(){
        if (!photoUrls.contains(";")) return;

        photoArray=new ArrayList<>();

        String[] urls=photoUrls.split(";");
        for (int i=0; i<urls.length-1; i++){
            photoArray.add(urls[i]);
        }
    }


    public void addPhotoUrls(String photoUrl) {
        this.photoUrls= this.photoUrls+photoUrl+";";
        splitPhotoUrl();
    }


    public int deletePhoto(int photoIndex) {
        if (photoIndex>photoArray.size()-1) return G.FAIL;

        photoArray.remove(photoIndex);

        photoUrls="";
        for(int i=0; i<photoArray.size(); i++){
            photoUrls=photoArray.get(i)+";";
        }
        return G.SUCCESS;
    }

    public int deletePhoto(String photoUrl) {
        if (!photoArray.contains(photoUrl)) return G.FAIL;

        photoArray.remove(photoUrl);

        photoUrls="";
        for(int i=0; i<photoArray.size(); i++){
            photoUrls=photoArray.get(i)+";";
        }
        return G.SUCCESS;
    }

    public String getWriteDate() {
        return writeDate;
    }

    public void setWriteDate(String writeDate) {
        this.writeDate = writeDate;
    }


    public String getNapTime() {
        return napTime;
    }

    public void setNapTime(String check_nap) {
        this.napTime = check_nap;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPhotoUrls() {
        return photoUrls;
    }

    public void setPhotoUrls(String photoUrls) {
        this.photoUrls = photoUrls;
    }

    public ArrayList<Integer> getChecks() {
        return checks;
    }

    public void setChecks(ArrayList<Integer> checks) {
        this.checks = checks;
    }

    public ArrayList<String> getPhotoArray() {
        return photoArray;
    }

    public void setPhotoArray(ArrayList<String> photoArray) {
        this.photoArray = photoArray;
    }

    public int getChildCode() {
        return childCode;
    }

    public void setChildCode(int childCode) {
        this.childCode = childCode;
    }

    public int getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(int organizationCode) {
        this.organizationCode = organizationCode;
    }
}
