package common.rsp;

/**
 * @author zhangbaisen
 * @since 2021/1/11
 */
public class Result<R> {
    private int code;
    private String message;
    private R data;

    public static <R> Result<R> ok() {
        return Result.ofEnum(StatusEnum.SUCCESS);
    }

    public static <R> Result<R> ok(R data) {
        return Result.ofEnum(StatusEnum.SUCCESS,data);
    }

    public static <R> Result<R> error() {
        return Result.ofEnum(StatusEnum.ERROR);
    }

    public static <R> Result<R> error(R data) {
        return Result.ofEnum(StatusEnum.ERROR,data);
    }

    public static <R> Result<R> error(int code, String message) {
        return new Result<R>().setCode(code).setMessage(message);
    }

    public static <R> Result<R> error(int code, String message, R data) {
        return new Result<R>().setCode(code).setMessage(message).setData(data);
    }

    public static <R> Result<R> ofThrowable(int code, Throwable throwable) {
        Result<R> result = new Result<>();
        result.setCode(code);
        result.setMessage(throwable.getClass().getName() + ", " + throwable.getMessage());
        return result;
    }

    public static <R> Result<R> ofEnum(StatusEnum resultEnum) {
        return new Result<R>().setCode(resultEnum.getCode()).setMessage(resultEnum.getMessage());
    }

    public static <R> Result<R> ofEnum(StatusEnum statusEnum, R data) {
        return new Result<R>().setCode(statusEnum.getCode()).setMessage(statusEnum.getMessage()).setData(data);
    }

    public int getCode() {
        return code;
    }

    public Result<R> setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Result<R> setMessage(String message) {
        this.message = message;
        return this;
    }

    public R getData() {
        return data;
    }

    public Result<R> setData(R data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
