/**
 * 
 */


import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * Oct 18, 2013
 * @author <a href="mailto:touchnan@gmail.com">chegnqiang.han</a>
 *
 */
public class TestDegit {
    
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        new TestDegit().sha512();

    }
    
    public void sha512() {
        System.out.println(new TestDegit().sha512("admin"));
        System.out.println(new TestDegit().sha512("11223344"));
    }
    
    public String sha512(String unencryptedPassword)
    {
      byte[] bytes = unencryptedPassword.getBytes();
      byte[] hash = DigestUtils.sha512(bytes);
      return new String(Base64.encodeBase64(hash));
    }
}
