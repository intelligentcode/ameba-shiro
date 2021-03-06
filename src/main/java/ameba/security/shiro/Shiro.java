package ameba.security.shiro;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * @author icode
 */
public class Shiro {
    private static final String NAMES_SPLITER = ",";

    private Shiro() {
    }

    /**
     * 获取 Subject
     *
     * @return Subject
     */
    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    /**
     * 验证当前用户是否属于该角色？,使用时与lacksRole 搭配使用
     *
     * @param roleName 角色名
     * @return 属于该角色：true，否则false
     */
    public static boolean hasRole(String roleName) {
        return getSubject() != null && StringUtils.isNotBlank(roleName) && getSubject().hasRole(roleName);
    }

    /**
     * 与hasRole标签逻辑相反，当用户不属于该角色时验证通过。
     *
     * @param roleName 角色名
     * @return 不属于该角色：true，否则false
     */
    public static boolean lacksRole(String roleName) {
        return !hasRole(roleName);
    }

    /**
     * 验证当前用户是否属于以下任意一个角色。
     *
     * @param roleNames 角色列表
     * @return 属于:true,否则false
     */
    public static boolean hasAnyRoles(String roleNames) {
        return StringUtils.isNotBlank(roleNames) &&
                hasAnyRoles(StringUtils.deleteWhitespace(roleNames).split(NAMES_SPLITER));
    }

    /**
     * 验证当前用户是否属于以下任意一个角色。
     *
     * @param roleNames 角色列表
     * @return 属于:true,否则false
     */
    public static boolean hasAnyRoles(String... roleNames) {
        Subject subject = getSubject();
        if (subject != null && roleNames != null && roleNames.length > 0) {
            // Iterate through roles and check to see if the user has one of the
            // roles
            for (String role : roleNames) {
                if (subject.hasRole(role)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 验证当前用户是否属于以下所有角色。
     *
     * @param roleNames 角色列表
     * @return 属于:true,否则false
     */
    public static boolean hasAllRoles(String roleNames) {
        return StringUtils.isNotBlank(roleNames) &&
                hasAllRoles(StringUtils.deleteWhitespace(roleNames).split(NAMES_SPLITER));
    }

    /**
     * 验证当前用户是否属于以下所有角色。
     *
     * @param roleNames 角色列表
     * @return 属于:true,否则false
     */
    public static boolean hasAllRoles(String... roleNames) {
        Subject subject = getSubject();
        if (subject != null && roleNames != null && roleNames.length > 0) {
            // Iterate through roles and check to see if the user has one of the
            // roles
            for (String role : roleNames) {
                if (!subject.hasRole(role)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 验证当前用户是否拥有指定权限,使用时与lacksPermission 搭配使用
     *
     * @param permission 权限名
     * @return 拥有权限：true，否则false
     */
    public static boolean hasPermission(String permission) {
        return getSubject() != null
                && StringUtils.isNotBlank(permission)
                && getSubject().isPermitted(permission);
    }

    /**
     * 与hasPermission标签逻辑相反，当前用户没有制定权限时，验证通过。
     *
     * @param permission 权限名
     * @return 拥有权限：true，否则false
     */
    public static boolean lacksPermission(String permission) {
        return !hasPermission(permission);
    }

    /**
     * 已认证通过的用户。不包含已记住的用户，这是与user标签的区别所在。与notAuthenticated搭配使用
     *
     * @return 通过身份验证：true，否则false
     */
    public static boolean isAuthenticated() {
        return getSubject() != null && getSubject().isAuthenticated();
    }

    /**
     * 是否为记住我的用户
     *
     * @return 通过记住我认证：true，否则false
     */
    public static boolean isRemembered() {
        return getSubject() != null && getSubject().isRemembered();
    }

    /**
     * 未认证通过用户，与authenticated标签相对应。与guest标签的区别是，该标签包含已记住用户。。
     *
     * @return 没有通过身份验证：true，否则false
     */
    public static boolean isNotAuthenticated() {
        return !isAuthenticated();
    }

    /**
     * 认证通过或已记住的用户。与guset搭配使用。
     *
     * @return 用户：true，否则 false
     */
    public static boolean isUser() {
        return isAuthenticated() || isRemembered();
    }

    /**
     * 验证当前用户是否为“访客”，即未认证（包含未记住）的用户。用user搭配使用
     *
     * @return 访客：true，否则false
     */
    public static boolean isGuest() {
        return !isUser();
    }

    /**
     * 输出当前用户信息，通常为登录帐号信息。
     *
     * @return 当前用户信息
     */
    @SuppressWarnings("unchecked")
    public static <T> T getPrincipal() {
        return (T) getSubject().getPrincipal();
    }
}
