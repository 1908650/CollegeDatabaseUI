package dnhs.model;

public abstract class Art extends Object implements Comparable<Art> {
    public String name;
    public String type;
    public String price;
    public String description;

    public Art(String type, String price, String name, String description) {
        this.type = type;
        this.price = price;
        this.name = name;
        this.description = description;
    }

    public Art(String type, String price, String name) {
        this.type = type;
        this.price = price;
        this.name = name;
    }

    @Override
    public int compareTo(Art compareart) {
        float compare_price = Float.parseFloat(compareart.price);
        /* For Ascending order */
        if (Float.parseFloat(this.price) - compare_price >= 0)
            return 1;
        else
            return -1;
    }

}
