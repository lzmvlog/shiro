package top.shaojie.shiro.reaim;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author ShaoJie
 * @Date 2019/10/21
 */
@Component
public class CustomRealm extends AuthorizingRealm {

    Map<String, String> userMap = new HashMap<String, String>();

    {
        userMap.put("shaojie", "31ba57417dead0e611487b08d76e157c");
        super.setName("customRealm");
    }

    /**
     * 授权
     *
     * @param principalCollection
     * @return
     */
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String userName = (String) principalCollection.getPrimaryPrincipal();
        // 从数据库或者缓存中获取角色数据
        Set<String> roles = getRolesByUserName(userName); // 角色
        Set<String> permissions = getPermissionsByUserName(userName); // 权限
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setRoles(roles);
        simpleAuthorizationInfo.setStringPermissions(permissions);
        return simpleAuthorizationInfo;
    }

    /**
     * 获取权限信息
     *
     * @return
     */
    private Set<String> getPermissionsByUserName(String userName) {
        Set<String> sets = new HashSet<>();
        sets.add("user:add");
        sets.add("user:delete");
        return sets;
    }

    /**
     * 获取角色信息
     *
     * @param userName
     * @return
     */
    private Set<String> getRolesByUserName(String userName) {
        Set<String> sets = new HashSet<>();
        sets.add("admin");
        sets.add("user");
        return sets;
    }

    /**
     * 认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 1、从主体传过来的认证信息中获取用户名
        String userName = (String) authenticationToken.getPrincipal();
        // 2、通过用户名到数据库中获取凭证
        String password = getPasswordByUserName(userName);
        if (password == null) {
            return null;
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo("shaojie", password, "customRealm");
        // 对加盐解析
        authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes("shaojie"));
        return authenticationInfo;
    }

    /**
     * 模拟数据库查询数据
     *
     * @param userName
     * @return
     */
    private String getPasswordByUserName(String userName) {
        // 访问数据库
        return userMap.get(userName);
    }

    /**
     * 使用 Md5Hash 加密 加盐
     */
    public static void main(String[] args) {
        Md5Hash md5Hash = new Md5Hash("123456", "shaojie");
        System.out.println(md5Hash.toString());
    }

}
