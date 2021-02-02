package work.v2m.touch.bean;

/**
 * Created by <a href="mailto:88052350@qq.com">chengqiang.han</a> on 2021/1/29.
 */
public class CodeStatus {
    private int code;
    private String msg;

    public CodeStatus(){
        this.code = HttpStatus.OK.value();
        this.msg = HttpStatus.OK.getReasonPhrase();
    }

    public CodeStatus(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

//    static StatusCode build() {
//        return new StatusCode();
//    }

    public CodeStatus code(int code) {
        this.code = code;
        return this;
    }

    public CodeStatus message(String msg) {
        this.msg = msg;
        return this;
    }

    public int code() {
        return code;
    }

    public String message() {
        return msg;
    }
}
