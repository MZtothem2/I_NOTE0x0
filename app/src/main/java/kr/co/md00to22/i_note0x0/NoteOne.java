package kr.co.md00to22.i_note0x0;

import java.io.Serializable;

public class NoteOne  implements Serializable {
    private VNotes pnote;
    private VNotes tnote;

    public NoteOne() {
    }

    public NoteOne(VNotes note) {
        if (note.getWrite_level()==Global.MEMBER_GRADE_PARENT) pnote=note;
        else tnote=note;
    }

    public NoteOne(VNotes note1, VNotes note2) {
        if(note1!=null){
            if (note1.getWrite_level()==Global.MEMBER_GRADE_PARENT) pnote=note1;
            else tnote=note1;
        }
        if(note2!=null){
            if (note2.getWrite_level()==Global.MEMBER_GRADE_PARENT) pnote=note2;
            else tnote=note2;
        }
    }

    public String checkNoteOne(){
        String r="";
        if (this.pnote!=null) r+="P";
        if (this.tnote!=null) r+="T";

        return r;
    }

    public void putNote(VNotes note){
        if (note.getWrite_level()==Global.MEMBER_GRADE_PARENT) pnote=note;
        else tnote=note;
    }
    public VNotes getPnote() {
        return pnote;
    }

    public void setPnote(VNotes pnote) {
        this.pnote = pnote;
    }

    public VNotes getTnote() {
        return tnote;
    }

    public void setTnote(VNotes tnote) {
        this.tnote = tnote;
    }
}
