package TT4J.interfaces;


/**
 * Created by Stokowiec on 2015-07-01.
 */
public interface Encrypter {
    String encrypt(String inputString) throws Exception;
    String encryptShort(String inputString) throws Exception;
}
