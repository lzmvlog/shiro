package top.shaojie;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;
import top.shaojie.realm.CustomRealm;

/**
 * @author ShaoJie
 * @Date 2019/10/21
 */
public class CustomRealmTest {

    @Test
    public void test(){
        CustomRealm customRealm = new CustomRealm();

        // 构建 SecurityManager 环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(customRealm);

        // 配置散列 密码加密
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("md5");
        matcher.setHashIterations(1);

        customRealm.setCredentialsMatcher(matcher);

        // 主题提交主体认真
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("shaojie", "123456");
        subject.login(token);

        System.out.println("isAuthenticated:" + subject.isAuthenticated());

//        subject.checkRoles("admin");
//        subject.checkPermissions("user:delete","user:update");

        subject.checkRoles("admin");
        subject.checkPermissions("user:delete","user:add");

    }
}
