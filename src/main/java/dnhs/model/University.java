package dnhs.model;

public class University implements Comparable<University> {
    public String name;
    public String type;
    public String tuition;
    public String description;

    public University(String type, String tuition, String name, String description) {
        this.type = type;
        this.tuition = tuition;
        this.name = name;
        this.description = description;
    }

    public University(String type, String tuition, String name) {
        this.type = type;
        this.tuition = tuition;
        this.name = name;
    }

    public int compareTo(University o) {
        // TODO Auto-generated method stub
        return 0;
    }

}
