package top.shaojie;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;


/**
 * @author ShaoJie
 * @Date 2019/10/21
 */
public class JdbcRealmTest {

    DruidDataSource dataSource = new DruidDataSource();

    {
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/test");
        dataSource.setUrl("root");
        dataSource.setPassword("123456");
    }

    @Test
    public void test() {
        JdbcRealm jdbcRealm = new JdbcRealm();
        jdbcRealm.setDataSource(dataSource);
        // 开启权限数据
        jdbcRealm.setPermissionsLookupEnabled(true);

//        String sql = "";
//        jdbcRealm.setAuthenticationQuery();

        // 构建 SecurityManager 环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(jdbcRealm);

        // 主题提交主体认真
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("shaojie", "123456");
        subject.login(token);

        System.out.println("isAuthenticated:" + subject.isAuthenticated());

        subject.checkRoles("admin");
        subject.checkPermissions("user:delete","user:update");
    }

}
