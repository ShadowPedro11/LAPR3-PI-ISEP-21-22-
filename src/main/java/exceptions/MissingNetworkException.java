package exceptions;

/**
 * @author : Ricardo Venâncio - 1210828
 **/
public class MissingNetworkException extends Exception{
    public MissingNetworkException(String errorMessage){
        super(errorMessage);
    }
}
