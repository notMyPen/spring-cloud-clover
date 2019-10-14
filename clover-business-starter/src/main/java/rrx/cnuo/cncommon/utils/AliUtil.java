package rrx.cnuo.cncommon.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;

import lombok.extern.slf4j.Slf4j;
import rrx.cnuo.cncommon.vo.FileType;
import rrx.cnuo.cncommon.vo.config.AliOssConfigBean;
import rrx.cnuo.cncommon.vo.config.BasicConfig;

@Slf4j
public class AliUtil {

	/**
	 * 将网络资源下载后上传到阿里oss，并返回图片对应的key
     * @author xuhongyu
     * @param url
     * @param compress 是否压缩
     * @param redis
     * @return
     * @throws Exception
     */
    public static String uploadFileFromNetUrl(String urlStr,int type,Boolean compress,RedisTool redis,AliOssConfigBean aliOssConfigBean) throws Exception{
    	InputStream is = null;
    	try {
			// 构造URL
			URL url = new URL(urlStr);
			// 打开连接
			URLConnection con = url.openConnection();
			// 设置请求超时为5s
			con.setConnectTimeout(5 * 1000);
			// 输入流
			is = con.getInputStream();
			byte[] readBytes = IOUtils.toByteArray(is);
			return uploadFileFromBytes(readBytes,type, compress, redis, aliOssConfigBean);
		}finally {
			if(is != null) {
				is.close();
			}
		}
    }
    
	/**
	 * 将用户从前端上传的图片文件上传到阿里oss，并返回图片对应的key
     * @author xuhongyu
     * @param file
     * @param compress 是否压缩
     * @param redis
     * @return
     * @throws Exception
     */
    public static String uploadFileFromMultipartFile(MultipartFile file,int type, Boolean compress,RedisTool redis,AliOssConfigBean aliOssConfigBean) throws Exception{
    	InputStream inputStream = null;
    	try {
			inputStream = file.getInputStream();
			byte[] readBytes = IOUtils.toByteArray(inputStream);
			return uploadFileFromBytes(readBytes,type, compress, redis, aliOssConfigBean);
		} finally {
			if(inputStream != null) {
				inputStream.close();
			}
		}
    }
    
    /**
     * 将文件对应的字节数据上传到阿里oss，并返回图片对应的key
     * @author xuhongyu
     * @param readBytes
     * @param compress 是否压缩
     * @param redis
     * @return
     * @throws Exception
     */
    private static String uploadFileFromBytes(byte[] readBytes,int type, Boolean compress,RedisTool redis,AliOssConfigBean aliOssConfigBean) throws Exception{
		//取得当前上传文件的文件名称
//        String fileKey = UUID.randomUUID().toString().replaceAll("-","") + RandomGenerator.generateRandomString(8);
        String fileKey = StarterToolUtil.generatorStringId(redis);

        OSSClient ossClient = new OSSClient("http://"+aliOssConfigBean.getOssBucketUrl(), aliOssConfigBean.getAliyunAccesskeyId(), aliOssConfigBean.getAliyunAccesskeySecret());
        /*FileModel fileModel = new FileModel();
        fileModel.setFileName(myFileName + suffix);//压缩文件名
        fileModel.setOriginalFileName(myFileName+"_origin"+suffix);//原始文件名
        fileModel.setThumbFileName(myFileName+"_thumb"+suffix);//缩略图文件名
        fileModel.setFileType(FileType.PIC);*/
        try {
        	//存储源文件
        	String filePath = null;
        	if(type == FileType.PIC.getType()) {
        		filePath = aliOssConfigBean.getPicPath();
        		if (null != compress && compress) {
        			//存储压缩文件
        			byte[] compressedBytes = FileCompressUtils.imageCompressToJpg(readBytes);
        			readBytes = compressedBytes;
        			//存储缩略图
        			/*byte[] thumbBytes = ImageCompressUtils.imageNarrowToJpg(readBytes, 240);*/
                }
        	}else {
        		filePath = aliOssConfigBean.getVideoPath();
        		//TODO 视频压缩
        	}
            ossClient.putObject(aliOssConfigBean.getOssWxappBucketName(), filePath + "/" + fileKey,new ByteArrayInputStream(readBytes));
        }finally {
            ossClient.shutdown();
        }
        return fileKey;
    }
    
    /**
     * 将图片对应key映射为url
     * @author xuhongyu
     * @param fileKey
     * @return
     */
    public static String getPicPathByFileName(String fileKey,int type,BasicConfig basicConfig,AliOssConfigBean aliOssConfigBean) {
    	OSSClient ossClient = null;
		try {
			ossClient = new OSSClient("http://"+aliOssConfigBean.getOssBucketUrl(), aliOssConfigBean.getAliyunAccesskeyId(), aliOssConfigBean.getAliyunAccesskeySecret());
			// 设置URL过期时间为1小时。
			Date expiration = new Date(new Date().getTime() + 3600 * 1000);
			String filePath = null;
			if(type == FileType.PIC.getType()) {
				filePath = aliOssConfigBean.getPicPath();
			}else {
				filePath = aliOssConfigBean.getVideoPath();
			}
			// 生成以GET方法访问的签名URL，访客可以直接通过浏览器访问相关内容。
			URL url = ossClient.generatePresignedUrl(aliOssConfigBean.getOssWxappBucketName(), filePath+"/"+fileKey, expiration);
			if(basicConfig.isProdEnvironment()){
				return aliOssConfigBean.getPicCdnUrl() +"/"+ filePath+"/"+fileKey+"?"+url.getQuery();
			}else{
				//http://img-test-rrx.oss-cn-beijing.aliyuncs.com/boardPic/2915f530d9a54e7d827514e1679061d3vnx7k4iX.png?Expires=1556442287&OSSAccessKeyId=LTAIZ6oQxRUHKkmM&Signature=poPZTMAJZcFhmJ0MOS%2BlfugW3%2FQ%3D
				return "http://"+url.getHost()+url.getFile();
			}
		} catch (ClientException e) {
			log.error(e.getMessage(),e);
		}finally{
			// 关闭OSSClient。
			if(ossClient != null)
				ossClient.shutdown();
		}
		return null;
    }
}
