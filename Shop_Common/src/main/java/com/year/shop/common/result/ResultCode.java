package com.year.shop.common.result;

public enum ResultCode {
    OK(200),ERROR(400),NOTFOUND(404);
    private int val;
    ResultCode(int v) {
        val = v;
    }

    public int getVal() {
        return val;
    }
}
