package common.rsp;

/**
 * 响应状态码枚举类
 * @author zhangbaisen
 * @since 2021/1/11
 */
public enum StatusEnum {
    //http状态码
    SUCCESS(200,"success"),
    ERROR(500,"服务异常"),
    //todo 业务状态码
    ;
    private int code;
    private String message;
    StatusEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
