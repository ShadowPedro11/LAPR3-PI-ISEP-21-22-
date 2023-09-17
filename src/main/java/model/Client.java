package model;

/**
 * @author : Ricardo Venâncio - 1210828
 **/
public class Client extends Entity {
    public Client(String localID, String name, Coordinates local) {
        super(localID, name, local);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Client c){
            return c.getName().equals(this.getName());
        }
        return false;
    }

}
