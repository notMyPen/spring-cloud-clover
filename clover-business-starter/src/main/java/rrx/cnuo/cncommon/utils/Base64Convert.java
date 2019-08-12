package rrx.cnuo.cncommon.utils;

import org.springframework.web.multipart.commons.CommonsMultipartFile;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Author: yuetongfei
 * @Date: 2018-10-28
 **/
public class Base64Convert {

    BASE64Decoder decoder = new BASE64Decoder();

    public static String toBase64(CommonsMultipartFile file) throws IOException {
        String strBase64 = null;
        try {
            InputStream in = file.getInputStream();
            // in.available()返回文件的字节长度
            byte[] bytes = new byte[in.available()];
            // 将文件中的内容读入到数组中
            in.read(bytes);
            strBase64 = new BASE64Encoder().encode(bytes); //将字节流数组转换为字符串
            in.close();
        } catch (FileNotFoundException fe) {
            fe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return strBase64;
    }
}
