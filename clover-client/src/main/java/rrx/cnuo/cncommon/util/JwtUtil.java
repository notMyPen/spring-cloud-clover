package rrx.cnuo.cncommon.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import rrx.cnuo.cncommon.accessory.PermissionException;

/**
 * JWT工具类
 * @author xuhongyu
 * @date 2019年8月12日
 */
public class JwtUtil {
	    public static final String SECRET = "qazwsx123444$#%#()*&& asdaswwi1235 ?;!@#kmmmpom in***xx**&";
	    public static final String TOKEN_PREFIX = "Bearer";
	    public static final String HEADER_AUTH = "Authorization";

	    /**
	     * JWT生成方法，根据用户名或用户id生成（验证的时候同样传入JWT进行验证）
	     * @param user
	     * @return
	     */
	    public static String generateToken(String user) {
	        HashMap<String, Object> map = new HashMap<>();
	        map.put("id", new Random().nextInt());
	        map.put("user", user);
	        String jwt = Jwts.builder()
    			  .setSubject("user info").setClaims(map)
    			  .signWith(SignatureAlgorithm.HS512, SECRET)
    			  .compact();
	        String finalJwt = TOKEN_PREFIX + " " +jwt;
	        return finalJwt;
	    }

	    /**
	     * JWT解析方法
	     * @param token
	     * @return
	     */
	    public static Map<String,String> validateToken(String token) {
	        if (token != null) {
	        	HashMap<String, String> map = new HashMap<String, String>();//具体业务中将此处的map换成vo
	            Map<String,Object> body = Jwts.parser()
	                    .setSigningKey(SECRET)
	                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
	                    .getBody();
	            String id =  String.valueOf(body.get("id"));
	            String user = (String) (body.get("user"));
	            map.put("id", id);
	            map.put("user", user);
	            if(StringUtils.isBlank(user)) {
					throw new PermissionException("user is error, please check");
	            }
	            return map;
	        } else {
	        	throw new PermissionException("token is error, please check");
	        }
	    }
	    
}
