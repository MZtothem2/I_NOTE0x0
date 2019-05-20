package kr.co.md00to22.i_note0x0;

import java.io.Serializable;
import java.util.ArrayList;

public class VNotes implements Serializable {
    private int note_num;
    private int write_level;
    private String write_mem;
    private String write_date;
    private int kid_code;
    private int class_code;
    private int org_code;
    private boolean visibility=true;

    private String contnet;
    private int[] check;
    private String checkT;
    private ArrayList<String> photo_urls;

    public VNotes() {
    }

    public VNotes(int note_num, int write_level, String write_mem, String write_date, int kid_code, int class_code) {
        this.note_num = note_num;
        this.write_level = write_level;
        this.write_mem = write_mem;
        this.write_date = write_date;
        this.kid_code = kid_code;
        this.class_code = class_code;

    }

    public VNotes(int note_num, int write_level, String write_mem, String write_date, int kid_code, int class_code, int org_code, String contnet, int[] check, String checkT, ArrayList<String> photo) {
        this.note_num = note_num;
        this.write_level = write_level;
        this.write_mem = write_mem;
        this.write_date = write_date;
        this.kid_code = kid_code;
        this.class_code = class_code;
        this.contnet = contnet;
        this.check = check;
        this.checkT = checkT;
        this.photo_urls = photo;
        this.org_code=org_code;
    }

    public boolean getVisibility() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    public int getOrg_code() {
        return org_code;
    }

    public void setOrg_code(int org_code) {
        this.org_code = org_code;
    }

    public int getNote_num() {
        return note_num;
    }

    public void setNote_num(int note_num) {
        this.note_num = note_num;
    }

    public int getWrite_level() {
        return write_level;
    }

    public void setWrite_level(int write_level) {
        this.write_level = write_level;
    }

    public String getWrite_mem() {
        return write_mem;
    }

    public void setWrite_mem(String write_mem) {
        this.write_mem = write_mem;
    }

    public String getWrite_date() {
        return write_date;
    }

    public void setWrite_date(String write_date) {
        this.write_date = write_date;
    }

    public int getKid_code() {
        return kid_code;
    }

    public void setKid_code(int kid_code) {
        this.kid_code = kid_code;
    }

    public int getClass_code() {
        return class_code;
    }

    public void setClass_code(int class_code) {
        this.class_code = class_code;
    }

    public String getContnet() {
        return contnet;
    }

    public void setContnet(String contnet) {
        this.contnet = contnet;
    }

    public int[] getCheck() {
        return check;
    }

    public void setCheck(int[] check) {
        this.check = check;
    }

    public void addCheck(int check1, int check2, int check3) {
        this.check = new int[]{check1, check2, check3};
    }

    public String getCheckT() {
        return checkT;
    }

    public void setCheckT(String checkT) {
        this.checkT = checkT;
    }

    public ArrayList<String>  getPhoto_urls() {
        return photo_urls;
    }

    public void setPhoto_urls(ArrayList<String>  photoes) {
        this.photo_urls = photoes;
    }

    public void addPhoto(String photo1, String photo2, String photo3, String photo4, String photo5) {
       if (this.photo_urls==null) this.photo_urls=new ArrayList<>();

       if (this.photo_urls.size()==0) {
           photo_urls.add(photo1);
           photo_urls.add(photo2);
           photo_urls.add(photo3);
           photo_urls.add(photo4);
           photo_urls.add(photo5);
       }
    }
    public void addPhoto(String photo) {
        if (this.photo_urls==null) this.photo_urls=new ArrayList<>();

        if (this.photo_urls.size()<5) photo_urls.add(photo);
        else return;
    }
}
