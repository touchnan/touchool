package work.v2m.touch.security.core;

import java.io.Serializable;

/**
 * Created by <a href="mailto:88052350@qq.com">chengqiang.han</a> on 2021/2/2.
 * reference from
 *    org.springframework.security.core.GrantedAuthority
 */
public interface GrantedAuthority extends Serializable {
    /**
     * <p>
     * If the <code>GrantedAuthority</code> cannot be expressed with sufficient precision
     * as a <code>String</code>, <code>null</code> should be returned. Returning
     * <code>null</code> will require an <code>AccessDecisionManager</code> (or delegate)
     * to specifically support the <code>GrantedAuthority</code> implementation, so
     * returning <code>null</code> should be avoided unless actually required.
     * @return a representation of the granted authority (or <code>null</code> if the
     * granted authority cannot be expressed as a <code>String</code> with sufficient
     * precision).
     */
    String getAuthority();
}
