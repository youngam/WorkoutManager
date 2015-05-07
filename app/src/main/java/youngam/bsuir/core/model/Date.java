package youngam.bsuir.core.model;

/**
 * Created by Alex on 25.04.2015.
 */
public class Date {
    private long date;
    private String id;

    public Date(long date, String id){
        this.date = date;
        this.id = id;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
