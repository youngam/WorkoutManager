package youngam.bsuir.core.model;

/**
 * Created by Alex on 25.04.2015.
 */
public class DateTime {
    private String date;
    private String time;
    private String id;

    public DateTime(String id, String date, String time){
        this.id = id;
        this.date = date;
        this.time = time;
    }
    public DateTime(String date, String time){
        this.date = date;
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
