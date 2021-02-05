package work.v2m.touch.bean;

import java.io.Serializable;

/**
 * Created by <a href="mailto:88052350@qq.com">chengqiang.han</a> on 2021/1/29.
 */
public class RW<T> implements Serializable {
    private static final long serialVersionUID = -3453218436408965717L;

    public static final HttpStatus DEFAULT_ERR = HttpStatus.BAD_REQUEST;

    private int code = HttpStatus.OK.value();
    private String msg = HttpStatus.OK.getReasonPhrase();
    private T data;

    public RW() {
    }

    private RW(int code, String message, T result) {
        this.code = code;
        this.msg = message;
        this.data = result;
    }

    public boolean isSuccess(){
        return HttpStatus.OK.value()==code;
    }

    public static <T> RW<T> unauthorized() {
        return RW.withHttpStatus(HttpStatus.UNAUTHORIZED);
    }

    public static <T> RW<T> fail() {
        return createR(DEFAULT_ERR, null);
    }

    public static <T> RW<T> fail(HttpStatus httpStatus) {
        return createR(httpStatus, null);
    }

    private static <T> RW<T> fail(HttpStatus httpStatus, T data) {
        return createR(httpStatus,data);
    }

    public static <T> RW<T> fail(T data) {
        return createR(DEFAULT_ERR,data);
    }

    public static <T> RW<T> fail(String message) {
        return createR(DEFAULT_ERR.value(), message, null);
    }

    public static <T> RW<T> fail(String message,T data) {
        return createR(DEFAULT_ERR.value(), message, data);
    }

    public static <T> RW<T> fail(CodeStatus codeStatus) {
        return createR(codeStatus, null);
    }

    public static <T> RW<T> fail(CodeStatus codeStatus, T data) {
        return createR(codeStatus.code(), codeStatus.message(),data);
    }

    public static <T> RW<T> success() {
        return createR(HttpStatus.OK, null);
    }

    public static <T> RW<T> success(T data) {
        return createR(HttpStatus.OK, data);
    }

    public static <T> RW<T> success(String message, T data) {
        return createR(HttpStatus.OK.value(), message,data);
    }

    public static <T> RW<T> withHttpStatus(HttpStatus httpStatus){
        return createR(httpStatus,null);
    }

    public static <T> RW<T> withHttpStatus(HttpStatus httpStatus, T data){
        return createR(httpStatus,data);
    }

    public static <T> RW<T> withCodeStatus(CodeStatus codeStatus){
        return createR(codeStatus,null);
    }

    public static <T> RW<T> withCodeStatus(CodeStatus codeStatus, T data){
        return createR(codeStatus,data);
    }

    protected static <T> RW<T> createR(CodeStatus codeStatus, T data){
        return createR(codeStatus.code(), codeStatus.message(),data);
    }

    protected static <T> RW<T> createR(HttpStatus httpStatus, T data){
        return createR(httpStatus.value(), httpStatus.getReasonPhrase(),data);
    }

    protected static <T> RW<T> createR(int code, String msg, T data){
        return new RW(code, msg, data);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
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


