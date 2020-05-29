package dnhs.model;

public class Pencil extends Art{

    public Pencil(String type, String price, String name, String description){
        super(type, price, name, description);
    }

    public Pencil(String type, String price, String name){
        super(type, price, name);
    }

}

