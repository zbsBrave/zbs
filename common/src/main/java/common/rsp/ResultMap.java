package common.rsp;

import java.util.HashMap;

/**
 * @author zhangbaisen
 * @since 2021/1/11
 */
public class ResultMap<R> extends HashMap<String ,Object> {

    public static <R> ResultMap<R> ok() {
        return ResultMap.ofEnum(StatusEnum.SUCCESS);
    }

    public static <R> ResultMap<R> ok(R data) {
        return ResultMap.ofEnum(StatusEnum.SUCCESS,data);
    }

    public static <R> ResultMap<R> error() {
        return ResultMap.ofEnum(StatusEnum.ERROR);
    }

    public static <R> ResultMap<R> error(R data) {
        return ResultMap.ofEnum(StatusEnum.ERROR,data);
    }

    public static <R> ResultMap<R> error(int code, String msg) {
        return ResultMap.build(code,msg);
    }

    public static <R> ResultMap<R> error(int code, String msg, R data) {
        return ResultMap.build(code,msg,data);
    }

    public static <R> ResultMap<R> ofThrowable(int code, Throwable throwable) {
        return ResultMap.build(code, throwable.getClass().getName() + ", " + throwable.getMessage());
    }

    public static <R> ResultMap<R> ofEnum(StatusEnum resultEnum) {
        return new ResultMap<R>()
                .setCode(resultEnum.getCode()).setMessage(resultEnum.getMessage());
    }

    public static <R> ResultMap<R> ofEnum(StatusEnum resultEnum, R data) {
        return new ResultMap<R>()
                .setCode(resultEnum.getCode()).setMessage(resultEnum.getMessage()).setData(data);
    }

    public static <R> ResultMap<R> build(int code,String message) {
        return new ResultMap<R>()
                .setCode(code).setMessage(message);
    }

    public static <R> ResultMap<R> build(int code,String message,R data) {
        return new ResultMap<R>()
                .setCode(code).setMessage(message).setData(data);
    }

    public ResultMap<R> set(String key,Object value){
        super.put(key, value);
        return this;
    }

    public ResultMap<R> setCode(int code){
        super.put("code", code);
        return this;
    }

    public ResultMap<R> setMessage(String message){
        super.put("message", message);
        return this;
    }

    public ResultMap<R> setData(R data){
        super.put("data", data);
        return this;
    }
}
