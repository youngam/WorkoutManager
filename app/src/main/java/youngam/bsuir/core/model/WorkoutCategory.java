package youngam.bsuir.core.model;

/**
 * Created by Alex on 13.03.2015.
 */
public class WorkoutCategory {
   private String name;
   private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public WorkoutCategory(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
