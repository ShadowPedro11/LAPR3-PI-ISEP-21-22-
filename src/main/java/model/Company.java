package model;

/**
 * @author : Ricardo Venâncio - 1210828
 **/
public class Company extends Entity {
    public Company(String localID, String name, Coordinates local) {
        super(localID, name, local);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Company c){
            return c.getName().equals(this.getName());
        }
        return false;
    }

}
