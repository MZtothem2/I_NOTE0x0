package kr.co.md00to22.i_note0x0;

import java.io.Serializable;

@SuppressWarnings("serial")
public class VPicture implements Serializable {
    private String loadDate;
    private int classCode;
    private int childCode;
    private String picturePath;


    public String getLoadDate() {
        return loadDate;
    }

    public void setLoadDate(String loadDate) {
        this.loadDate = loadDate;
    }

    public int getClassCode() {
        return classCode;
    }

    public void setClassCode(int classCode) {
        this.classCode = classCode;
    }

    public int getChildCode() {
        return childCode;
    }

    public void setChildCode(int childCode) {
        this.childCode = childCode;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }
}
