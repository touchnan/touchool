/*
 * cn.touch.kit.MethodResult.java
 * Sep 6, 2012 
 */
package cn.touch.kit;

/**
 * Sep 6, 2012
 * 
 * @author <a href="mailto:touchnan@gmail.com">chegnqiang.han</a>
 * 
 */
public class MethodResult {
    public MethodResult() {
        this(true, null);
    }
    
    public MethodResult(boolean success) {
        this(success,null);
    }
    
    public MethodResult(String msg) {
        this(false, msg);
    }

    public MethodResult(Throwable e) {
        this(false, e.getMessage());
    }

    public MethodResult(boolean success, String msg) {
        super();
        this.success = success;
        this.msg = msg;
    }

    private boolean success;
    private String msg;
    /**
     * @return the success
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * @param success the success to set
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * @return the msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * @param msg the msg to set
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }
    
    
}
