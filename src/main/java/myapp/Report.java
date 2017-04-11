package myapp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ONBA7293 on 11/01/2017.
 */
public class Report {
    @Id
    private String id;
    private String report;
    private Surfer surfer;
    @JsonIgnore
    private Date date;

    public String getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }
    public Surfer getSurfer() {
        return surfer;
    }

    public String getReport() {
        return report;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setSurfer(Surfer surfer) {
        this.surfer = surfer;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public Report() {
    }

    public Report(Surfer surfer, String report)
    {
        this.date = new Date();
        this.surfer = surfer;
        this.report = report;
    }

    @Override
    public String toString() {
        return String.format(
                "Report[date='%s', surfer='%s', report='%s']",
                date, surfer, report);
    }
}
