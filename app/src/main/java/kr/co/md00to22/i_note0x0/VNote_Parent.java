package kr.co.md00to22.i_note0x0;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class VNote_Parent implements Serializable, Parcelable {
    private String writeDate;

    private ArrayList<Integer> checks;

    private String gohome_adlut;
    private String gohome_time;

    private String note;

    private String photoUrls;
    private ArrayList<String> photoArray;

    private int childCode;
    private int classCode;

    public VNote_Parent() {
    }

    public VNote_Parent(String writeDate, int[] check_rbs, String gohome_adlut, String gohome_time, String note, String photoUrl, int childCode) {
        this.writeDate = writeDate;
        this.gohome_adlut = gohome_adlut;
        this.gohome_time = gohome_time;
        this.note = note;

        this.checks=new ArrayList<>();
        for (int i=0; i<check_rbs.length ; i++){
            this.checks.add(check_rbs[i]);
        }

        this.childCode=childCode;

        if (photoUrl==null || photoUrl.equals("")) this.photoUrls="";
        else this.photoUrls=photoUrl+";";
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


    public void addPhoto(String photoUrls) {
        this.photoUrls += photoUrls+";";
        splitPhotoUrl();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    public ArrayList<Integer> getChecks() {
        return checks;
    }

    public void setChecks(ArrayList<Integer> checks) {
        this.checks = checks;
    }

    public String getWriteDate() {
        return writeDate;
    }

    public void setWriteDate(String writeDate) {
        this.writeDate = writeDate;
    }


    public String getGohome_adlut() {
        return gohome_adlut;
    }

    public void setGohome_adlut(String gohome_adlut) {
        this.gohome_adlut = gohome_adlut;
    }

    public String getGohome_time() {
        return gohome_time;
    }

    public void setGohome_time(String gohome_time) {
        this.gohome_time = gohome_time;
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

    public int getClassCode() {
        return classCode;
    }

    public void setClassCode(int classCode) {
        this.classCode = classCode;
    }
}
