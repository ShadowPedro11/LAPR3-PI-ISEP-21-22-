package model;

/**
 * @author : Ricardo Venâncio - 1210828
 **/
public class Producer extends Entity {
    public Producer(String localID, String name, Coordinates local) {
        super(localID, name, local);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Producer p){
            return p.getName().equals(this.getName());
        }
        return false;
    }
}
