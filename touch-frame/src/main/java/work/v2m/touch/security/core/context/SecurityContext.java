package work.v2m.touch.security.core.context;

import work.v2m.touch.security.core.Authentication;

import java.io.Serializable;

/**
 * Created by <a href="mailto:88052350@qq.com">chengqiang.han</a> on 2021/2/2.
 */
public interface SecurityContext extends Serializable {

    /**
     * Obtains the currently authenticated principal, or an authentication request token.
     * @return the <code>Authentication</code> or <code>null</code> if no authentication
     * information is available
     */
    Authentication getAuthentication();

    /**
     * Changes the currently authenticated principal, or removes the authentication
     * information.
     * @param authentication the new <code>Authentication</code> token, or
     * <code>null</code> if no further authentication information should be stored
     */
    void setAuthentication(Authentication authentication);

}
