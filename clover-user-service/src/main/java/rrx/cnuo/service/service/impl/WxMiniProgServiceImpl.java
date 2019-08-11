package rrx.cnuo.service.service.impl;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import rrx.cnuo.cncommon.utils.RedisTool;
import rrx.cnuo.cncommon.vo.AccountHelper;
import rrx.cnuo.cncommon.vo.JsonResult;
import rrx.cnuo.cncommon.vo.UserWxInfoVo;
import rrx.cnuo.cncommon.vo.config.WeChatMiniConfig;
import rrx.cnuo.service.po.UserPassport;
import rrx.cnuo.service.service.WxMiniProgService;
import rrx.cnuo.service.service.data.UserPassportDataService;
import rrx.cnuo.service.utils.AccessToken;

/**
 * 小程序相关操作
 * @author xuhongyu
 * @date 2019年6月26日
 */
@Slf4j
@Service
public class WxMiniProgServiceImpl implements WxMiniProgService {

	@Value("classpath:static/img/bg.png")
    Resource bgRes;

    @Value("classpath:static/img/text_bg.png")
    Resource tbgRes;
    
    @Value("classpath:static/img/share_community.png")
    Resource communityRes;
    
	@Autowired private RedisTool redis;
	@Autowired private WeChatMiniConfig weChatMiniConfig;
	@Autowired private UserPassportDataService userPassportDataService;
	
	@Override
    public void getMiniProgramCode(String scene, String pageUrl, HttpServletResponse response) throws Exception {
        log.info("/user/getMiniProgramCode, request, scene:" + scene + "  page:" + pageUrl);
        ServletOutputStream outputStream = null;
        try {
            byte [] bytes = AccessToken.createMiniProgramCode(redis, scene, pageUrl,weChatMiniConfig);
            outputStream = response.getOutputStream();
            outputStream.write(bytes);
        } catch (Exception e) {
            log.error("/user/getMiniProgramCode, getMiniProgramCode", e);
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (Exception e) {
                    log.error("/user/getMiniProgramCode, close outputStream", e);
                }
            }
        }
        log.info("/user/getMiniProgramCode, response: success");
    }

	@Override
	public JsonResult<UserWxInfoVo> saveMiniBaseInfo(UserWxInfoVo infoVo) throws Exception{
		JsonResult<UserWxInfoVo> result = new JsonResult<>();
        result.setStatus(JsonResult.SUCCESS);
        
        Long uid = AccountHelper.getUserId();
        UserPassport userPassport = userPassportDataService.selectByPrimaryKey(uid);
        if(StringUtils.isNotBlank(userPassport.getNickName()) && StringUtils.isNotBlank(userPassport.getAvatarUrl())){
        	return result;
        }
        
		if(StringUtils.isBlank(infoVo.getNickName()) || StringUtils.isBlank(infoVo.getAvatarUrl())){
			result.setStatus(JsonResult.FAIL);
            result.setMsg("参数有误：nickName、avatarUrl均不能为空");
            return result;
		}
		UserPassport param = new UserPassport();
		param.setUid(uid);
		param.setAvatarUrl(infoVo.getAvatarUrl());
		param.setNickName(infoVo.getNickName());
		userPassportDataService.updateByPrimaryKeySelective(param);
		return result;
	}
	
	@Override
	public byte[] genDynamicGraph(Long prodId) throws Exception {
		// 大小
        int w = 750;
        int h = 1320;
        // 读取背景
        BufferedImage communityImg = ImageIO.read(communityRes.getInputStream());
        // 读取头像
//        BufferedImage headImg = loadImage(releaser.getAvatarUrl());
        // 输出的图片
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) image.getGraphics();
        //消除文字锯齿
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        //消除画图锯齿
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // 绘制背景
        g.drawImage(communityImg, 0, 0, null);
        
        //绘制小程序二维码
        String scene = prodId + "_0_0";
        BufferedImage miniProgramCodeImg = createMiniProgramCodeImage(scene, weChatMiniConfig.getPageUrl());
        g.drawImage(convertCircular(miniProgramCodeImg), 222, 674, 306, 306, null);
        
        // 绘制赏金信息
        /*double ratio = Math.toRadians(-30);
        g.drawImage(rotateImage(ratio, b.toString()), 0, 0, null);*/
        
		return imageToBytes(image);
	}
	
	@Override
	public byte[] getShareFriendsGraph(Long uid) throws Exception {
		UserPassport releaser = userPassportDataService.selectByPrimaryKey(uid);
		
		// 大小
        int w = 630;
        int h = 500;
        // 读取背景
        BufferedImage bgImg = ImageIO.read(bgRes.getInputStream());
        BufferedImage tbgImg = ImageIO.read(tbgRes.getInputStream());
        // 读取头像
        BufferedImage headImg = loadImage(releaser.getAvatarUrl());
        // 输出的图片
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) image.getGraphics();
        //消除文字锯齿
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        //消除画图锯齿
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // 绘制背景
        g.drawImage(bgImg, 0, 0, null);
        // 绘制文字背景
        g.drawImage(tbgImg, 35, 129, null);
        // 绘制头像
        g.drawImage(convertCircular(headImg), 435, 60, 120, 120, null);
        
        BigDecimal amt = new BigDecimal(30000).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP);
        // 绘制姓名文字
        g.setFont(loadSongFont(Font.BOLD, 26));
        g.setColor(new Color(51, 51, 51));
        int y = 225;//y的初始值
        g.drawString("我要找借款人，领"+amt.toString()+"元赏金帮我找吧", 65, y);
        y += 40;
        g.setFont(loadSongFont(Font.PLAIN, 24));
        g.drawString("借款人要求：", 70, y);
        int rowh = 33;
        y += rowh;
        g.setFont(loadSongFont(Font.PLAIN, 20));
        
        StringBuilder sb = new StringBuilder();
        sb.append("年龄：").append(18).append("至").append(40).append("岁").append(";");
        sb.append("婚姻状况：").append("已婚").append(";");
        if(sb.length() == 0){
        	g.drawString("无要求", 100, y);
        }else{
        	String[] textArr = sb.toString().split(";");
        	for(int i=0;i<textArr.length;i++){
        		String text = textArr[i];
        		if(i % 2 == 0){
        			g.drawString(text, 100, y);
        		}else{
        			//在奇数角标x往后移动，y换行
        			g.drawString(text, 310, y);
        			y += rowh;
        		}
        	}
        }
        // 绘制赏金信息
        double ratio = Math.toRadians(-30);
        g.drawImage(rotateImage(ratio, amt.toString()), 0, 0, null);
        
		return imageToBytes(image);
	}
	
	private static byte[] imageToBytes(BufferedImage image) throws IOException {
        ImageWriter imageWriter = getImageWriter();
        ImageWriteParam imageWriteParam = getImageWriterParam(imageWriter);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        imageWriter.setOutput(ImageIO.createImageOutputStream(byteArrayOutputStream));
        IIOImage tempImage = new IIOImage(image, null, null);

        imageWriter.write(null, tempImage, imageWriteParam);                //压缩
        return byteArrayOutputStream.toByteArray();
    }
	
	private static ImageWriter getImageWriter() {
        Iterator<ImageWriter> iter4 = ImageIO.getImageWritersByFormatName("jpeg");
        ImageWriter thirdCompressWriter2 = iter4.next();

        return thirdCompressWriter2;
    }
	
	private static ImageWriteParam getImageWriterParam( ImageWriter imageWriter ) {
        ImageWriteParam iwp2 = imageWriter.getDefaultWriteParam();
        iwp2.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        iwp2.setCompressionQuality(1.0F);
        iwp2.setProgressiveMode(ImageWriteParam.MODE_DISABLED );

        ColorModel colorModel1 = ColorModel.getRGBdefault();
        iwp2.setDestinationType(new ImageTypeSpecifier(colorModel1, colorModel1.createCompatibleSampleModel(16, 16)));

        return iwp2;
    }

	/**
	 * 图片保留圆形区域
	 * @author xuhongyu
	 * @param bi
	 * @return
	 * @throws IOException
	 */
    private BufferedImage convertCircular(BufferedImage bi) throws IOException
    {
        // 透明底的图片
        BufferedImage bi2 = new BufferedImage(bi.getWidth(), bi.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        Ellipse2D.Double shape = new Ellipse2D.Double(0, 0, bi.getWidth(), bi.getHeight());
        Graphics2D g = bi2.createGraphics();
        g.setClip(shape);

        // 使用 setRenderingHint 设置抗锯齿
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // 绘图
        g.drawImage(bi, 0, 0, null);

        // 设置颜色
        g.setBackground(Color.WHITE);
        g.dispose();
        return bi2;
    }

    /**
     * 绘制赏金信息
     * @author xuhongyu
     * @param ratio
     * @param text
     * @return
     * @throws IOException
     */
    private BufferedImage rotateImage(double ratio, String text) throws IOException
    {
        BufferedImage image = new BufferedImage(238, 160, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g = (Graphics2D) image.getGraphics();

        // 使用 setRenderingHint 设置抗锯齿
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // 绘图
        g.setColor(new Color(253,227,169));
        g.rotate(ratio);
        g.fillRect(-100, 70, 320, 50);

        g.setColor(new Color(255,73,90));
        Font font = loadHeiTiFont(Font.PLAIN, 26);
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.rotate(Math.toRadians(ratio), 0, 0);
        Font rotatedFont = font.deriveFont(affineTransform);
        g.setFont(rotatedFont);

        // 绘制赏金文字
        g.drawString(text, -20, 106);

        g.setBackground(Color.WHITE);
        g.dispose();
        return image;
    }

    // 防止未传入参数
    /*private String trimString(String str){
        if(str == null) return "";
        return str.trim();
    }*/

    /**
     * 加载网络图片
     * @author xuhongyu
     * @param str
     * @return
     * @throws IOException
     */
    private BufferedImage loadImage(String str) throws IOException{
        URL url = new URL(str);
        URLConnection con = url.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        //不超时
        con.setConnectTimeout(0);

        //不允许缓存
        con.setUseCaches(false);
        con.setDefaultUseCaches(false);
        InputStream is = con.getInputStream();

        //先读入内存
        ByteArrayOutputStream buf = new ByteArrayOutputStream(8192);
        byte[] b = new byte[1024];
        int len;
        while ((len = is.read(b)) != -1)
        {
            buf.write(b, 0, len);
        }

        //读图像
        is = new ByteArrayInputStream(buf.toByteArray());
        return ImageIO.read(is);
    }
    
    private BufferedImage createMiniProgramCodeImage(String scene,String pageUrl) throws Exception{
    	byte [] bytes = AccessToken.createMiniProgramCode(redis, scene, pageUrl,weChatMiniConfig);
    	//读图像
    	return ImageIO.read(new ByteArrayInputStream(bytes));
    }
    
    private Font loadSongFont(int style,float size){
    	try {
    		String fontfilename = "static/font/simsun.ttf";
    		ClassPathResource res = new ClassPathResource(fontfilename);
    		InputStream is = res.getInputStream();
    		Font fontBase = Font.createFont(Font.TRUETYPE_FONT,is);
    		
    		Font actionJsonBase = fontBase.deriveFont(style,size);//通过复制此 Font对象并应用新样式和大小，创建一个新 Font对象。
    		return actionJsonBase;
    	} catch (Exception e) {
    		log.error("自定义字体错误", e);
    		throw new RuntimeException("自定义字体错误");
    	}
    }
	
	private Font loadHeiTiFont(int style,float size){
		try {
			String fontfilename = "static/font/simhei.ttf";
			ClassPathResource res = new ClassPathResource(fontfilename);
			InputStream is = res.getInputStream();
			Font fontBase = Font.createFont(Font.TRUETYPE_FONT,is);
			
			/*String path = this.getClass().getResource("/").getPath() + "static/font/PingFang SC Regular.ttf";
			Font fontBase = Font.createFont(Font.TRUETYPE_FONT, new File(path));*/
			
			Font actionJsonBase = fontBase.deriveFont(style,size);//通过复制此 Font对象并应用新样式和大小，创建一个新 Font对象。
			return actionJsonBase;
		} catch (Exception e) {
			log.error("自定义字体错误", e);
			throw new RuntimeException("自定义字体错误");
		}
    }
}
