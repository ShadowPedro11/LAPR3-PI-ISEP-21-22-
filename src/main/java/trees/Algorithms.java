package trees;

import java.util.function.Function;

/**
 * @author : Ricardo Venâncio - 1210828
 **/
public class Algorithms {

    public static <E extends Comparable<E>> E computeIfAbsent(AVL<E> avl, E key, Function<? super E, ? extends E> function){
        if(avl == null || function == null || key == null)
            return null;
        E f = avl.find(key);
        if(f == null){
            function.apply(key);
            avl.insert(key);
            return key;
        }
        return f;
    }
}
