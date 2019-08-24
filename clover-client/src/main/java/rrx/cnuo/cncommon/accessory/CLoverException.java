package rrx.cnuo.cncommon.accessory;

/**
 * 自定义异常类的主要作用是区分异常发生的位置，当用户遇到异常时，根据异常名就可以知道哪里有异常，根据异常提示信息进行修改。
 *
 * @version 1.0
 */
public class CLoverException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CLoverException(String msg) {
        super(msg);
    }

}