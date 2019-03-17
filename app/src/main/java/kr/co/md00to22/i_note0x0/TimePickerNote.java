package kr.co.md00to22.i_note0x0;

import android.widget.TimePicker;

public class TimePickerNote implements TimePicker.OnTimeChangedListener {
    int hourOfDay;
    int minute;

    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        this.hourOfDay=hourOfDay;
        this.minute=minute;
    }



    public String getSelectTime(){

        return this.hourOfDay+":"+this.minute;
    }
}
