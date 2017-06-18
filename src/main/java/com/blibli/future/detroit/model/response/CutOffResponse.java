package com.blibli.future.detroit.model.response;

import com.blibli.future.detroit.model.CutOffHistory;
import org.joda.time.LocalDate;

public class CutOffResponse {
    private Long id;
    private String begin;
    private String end;

    public CutOffResponse(CutOffHistory cutOffHistory) {
        this.id = cutOffHistory.getId();
        this.begin = cutOffHistory.getBeginInISOFormat();
        this.end = cutOffHistory.getEndInISOFormat();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBegin() {
        return begin;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
