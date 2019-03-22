package kr.co.md00to22.i_note0x0;

import java.io.Serializable;

@SuppressWarnings("serial")
public class VOnedayNote implements Serializable {
    private VNote_Parent noteParent;
    private VNote_Teacher noteTeacher;
    private int childCode;
    private int classCode;
    private String writeDate;

    public VOnedayNote() {
    }

    public VOnedayNote(VNote_Parent noteParent, VNote_Teacher noteTeacher, int childCode, int classCode, String writeDate) {
        this.noteParent = noteParent;
        this.noteTeacher = noteTeacher;
        this.childCode = childCode;
        this.classCode=classCode;
        this.writeDate=writeDate;
    }

    public int getChildCode() {
        return childCode;
    }

    public void setChildCode(int childCode) {
        this.childCode = childCode;
    }

    public VNote_Parent getNoteParent() {
        return noteParent;
    }

    public void setNoteParent(VNote_Parent noteParent) {
        this.noteParent = noteParent;
    }

    public VNote_Teacher getNoteTeacher() {
        return noteTeacher;
    }

    public void setNoteTeacher(VNote_Teacher noteTeacher) {
        this.noteTeacher = noteTeacher;
    }

    public int getClassCode() {
        return classCode;
    }

    public void setClassCode(int classCode) {
        this.classCode = classCode;
    }

    public String getWriteDate() {
        return writeDate;
    }

    public void setWriteDate(String writeDate) {
        this.writeDate = writeDate;
    }


}
