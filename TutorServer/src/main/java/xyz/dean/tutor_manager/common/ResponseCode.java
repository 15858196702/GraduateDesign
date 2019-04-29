package xyz.dean.tutor_manager.common;

public class ResponseCode {
    /** 操作成功 */
    public static final int SUCCESS = 1;

    /** 密码错误 */
    public static final int WRONG_PWD = -100;
    /** 无效token */
    public static final int INVALID_TOKEN = -101;
    /** 用户未注册 */
    public static final int NOT_REGISTER = -102;

    /** 用户名被占用 */
    public static final int NAME_IS_TAKEN = -110;

    /** 未知错误 */
    public static final int UNKNOWN_ERROR = -500;
    /** 数据库发生错误 */
    public static final int DB_ERROR = -501;
}
