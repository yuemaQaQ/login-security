package com.yuemaz.security.loginsecurity.resp;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.yuemaz.security.loginsecurity.constant.HttpStatus;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@SuppressWarnings("all")
public class Result<T> {

    private Integer code;

    private boolean success;

    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public static Result ok() {
        Result result = new Result();
        result.setCode(HttpStatus.SUCCESS)
                .setSuccess(true)
                .setMessage("成功");
        return result;
    }

    public static Result fail() {
        Result result = new Result();
        result.setCode(HttpStatus.CREATED)
                .setSuccess(false)
                .setMessage("失败");
        return result;
    }
}