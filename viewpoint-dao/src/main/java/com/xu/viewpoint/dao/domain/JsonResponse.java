package com.xu.viewpoint.dao.domain;

/**
 * @author: xuJing
 * @date: 2024/11/20 19:50
 */

public class JsonResponse <T>{

    private String code;

    private String msg;

    private T data;

    public JsonResponse(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public JsonResponse(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public JsonResponse(T data) {
        this.code = "0";
        this.msg = "success";
        this.data = data;
    }

    /**
     * 请求成功，但是不需要返回数据
     * @return JsonResponse
     */
    public static JsonResponse<String> success(){
        return new JsonResponse<>(null);
    }

    /**
     * 请求成功，返回数据  字符串（例如登录令牌）
     * @return JsonResponse
     */
    public static JsonResponse<String> success(String data){
        return new JsonResponse<>(data);
    }

    /**
     * 请求失败，返回失败信息
     * @return JsonResponse
     */
    public static JsonResponse<String> fail(){
        return new JsonResponse<>("1", "fail");
    }

    /**
     * 请求失败，返回失败信息 (定制化失败信息)
     * @return JsonResponse
     */
    public static JsonResponse<String> fail(String code, String msg){
        return new JsonResponse<>(code, msg);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
