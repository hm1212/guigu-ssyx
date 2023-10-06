package com.atguigu.ssyx.common.result;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class Result<T> {
    //状态码
    private Integer code;
    //响应信息
    private String message;
    //数据
    private T data;

    //构造方法私有化
    private  Result(){}

    public static <T> Result<T>  build(T data,ResultCodeEnum resultCode){
        Result<T> result = new Result<>();
        result.setCode(resultCode.getCode());
        result.setMessage(resultCode.getMessage());
        result.setData(data==null?null:data);
        return  result;
    }

    //成功的方法
    public static<T> Result<T> success(T data){
       return build(data,ResultCodeEnum.SUCCESS);
    }

    //失败的方法
    public static<T> Result<T> fail(T data){
        return build(data,ResultCodeEnum.FAIL);
    }

}
