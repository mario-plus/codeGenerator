package mario.generator.addModules.test1.domain;
import lombok.Data;
import java.io.Serializable;
import java.sql.Timestamp;
/**
* @author zxz
* @date 2020-11-19
*/
@Data
public class SysUser implements Serializable {
            /** ID */
        private Long userId;
            /** 部门名称 */
        private Long deptId;
            /** 用户名 */
        private String username;
            /** 昵称 */
        private String nickName;
            /** 性别 */
        private String gender;
            /** 手机号码 */
        private String phone;
            /** 邮箱 */
        private String email;
            /** 头像地址 */
        private String avatarName;
            /** 头像真实路径 */
        private String avatarPath;
            /** 密码 */
        private String password;
            /** 是否为admin账号 */
        private Boolean isAdmin;
            /** 状态：1启用、0禁用 */
        private Long enabled;
            /** 创建者 */
        private String createBy;
            /** 更新着 */
        private String updateBy;
            /** 修改密码的时间 */
        private Timestamp pwdResetTime;
            /** 创建日期 */
        private Timestamp createTime;
            /** 更新时间 */
        private Timestamp updateTime;
}