package com.phone.okhttpretrofit.net;

/**
 * Created by MLXPHONE on 2017/5/26.
 */

public class ResultException extends RuntimeException {
    private int errCode = 0;

    public int getErrCode() {
        return errCode;
    }

    public ResultException(int errCode, String msg) {
        super(msg);
        this.errCode = errCode;
    }
}
