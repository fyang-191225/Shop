package com.year.shop.common.vo;

import com.year.shop.common.result.ResultCode;
import lombok.Data;

/**
 * @author fyy
 * @date 2020/1/20 0020 下午 17:05
 */
@Data
public class R {
    private int code;
    private String msg;
    private Object data;

    public static R setResult(Boolean issucc, String data) {
        if (issucc) {
            return R.ok(data);
        } else {
            return R.fail(data);

        }
    }


    public static R setR(int code, String msg, Object data) {
        R r = new R();
        r.setCode(code);
        r.setData(data);
        r.setMsg(msg);
        return r;

    }

    public static R ok(Object data) {
        return setR(ResultCode.OK.getVal(), "OK", data);
    }

    public static R ok() {
        return setR(ResultCode.OK.getVal(), "OK", null);
    }

    public static R ok(String msg) {
        return setR(ResultCode.OK.getVal(), msg, null);
    }

    public static R fail() {
        return setR(ResultCode.ERROR.getVal(), "ERROR", null);
    }

    public static R fail(String msg) {
        return setR(ResultCode.ERROR.getVal(), msg, null);
    }
}


