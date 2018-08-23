package cn.touch.bootweb.serv;

import cn.touch.security.shiro.ITouchSubjectDao;
import cn.touch.security.shiro.TouchPrincipal;
import cn.touch.security.shiro.TouchUsernamePasswordToken;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.springframework.stereotype.Service;

/**
 * Created by chengqiang.han on 2018/8/23.
 */
@Service
public class TxlService implements ITouchSubjectDao {
    @Override
    public SimpleAuthorizationInfo getAuthorizationInfo(TouchPrincipal touchPrincipal) {
        return null;
    }

    @Override
    public TouchPrincipal getAuthenticationInfo(TouchUsernamePasswordToken touchUsernamePasswordToken) {
        return null;
    }
}
