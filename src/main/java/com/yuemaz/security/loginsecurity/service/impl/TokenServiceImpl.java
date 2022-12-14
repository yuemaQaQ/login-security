package com.yuemaz.security.loginsecurity.service.impl;

import com.yuemaz.security.loginsecurity.constant.CacheConstants;
import com.yuemaz.security.loginsecurity.constant.Constants;
import com.yuemaz.security.loginsecurity.entity.LoginUser;
import com.yuemaz.security.loginsecurity.service.TokenService;
import com.yuemaz.security.loginsecurity.utils.RedisCache;
import com.yuemaz.security.loginsecurity.utils.SnowFlake;
import com.yuemaz.security.loginsecurity.utils.StringUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author: create by w5g
 * @date: 2022/7/21 13:26
 */
@Service
public class TokenServiceImpl implements TokenService {

    // 令牌自定义标识
    @Value("${token.header}")
    private String header;

    // 令牌秘钥
    @Value("${token.secret}")
    private String secret;

    // 令牌有效期（默认30分钟）
    @Value("${token.expireTime}")
    private int expireTime;

    protected static final long MILLIS_SECOND = 1000;

    protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;

    private static final Long MILLIS_MINUTE_TEN = 20 * 60 * 1000L;

    @Resource
    private SnowFlake snowFlake;

    @Resource
    private RedisCache redisCache;


    /**
     * 创建令牌
     *
     * @param loginUser 用户信息
     * @return 令牌
     */
    @Override
    public String createToken(LoginUser loginUser) {
        String token = String.valueOf(snowFlake.nextId());
        loginUser.setToken(token);
        refreshToken(loginUser);
        Map<String, Object> claims = new HashMap<>();
        claims.put(Constants.LOGIN_USER_KEY, token);
        return createToken(claims);
    }

    /**
     * 验证令牌有效期，相差不足20分钟，自动刷新缓存
     *
     * @param loginUser
     * @return 令牌
     */
    @Override
    public void verifyToken(LoginUser loginUser) {
        long expireTime = loginUser.getExpireTime();
        long currentTime = System.currentTimeMillis();
        if (expireTime - currentTime <= MILLIS_MINUTE_TEN)
        {
            refreshToken(loginUser);
        }
    }

    /**
     * 获取用户身份信息
     *
     * @param request
     * @return 用户信息
     */
    @Override
    public LoginUser getLoginUser(HttpServletRequest request) {
        // 获取请求携带的令牌
        String token = getToken(request);
        if (StringUtils.isNotEmpty(token))
        {
            try
            {
                Claims claims = parseToken(token);
                // 解析对应的权限以及用户信息
                String uuid = (String) claims.get(Constants.LOGIN_USER_KEY);
                String userKey = getTokenKey(uuid);
                LoginUser user = redisCache.getCacheObject(userKey);
                return user;
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 设置用户身份信息
     *
     * @param loginUser
     */
    @Override
    public void setLoginUser(LoginUser loginUser) {
        if (StringUtils.isNotNull(loginUser) && StringUtils.isNotEmpty(loginUser.getToken()))
        {
            refreshToken(loginUser);
        }
    }

    /**
     * 删除用户身份信息
     *
     * @param token
     */
    @Override
    public void delLoginUser(String token) {
        if (StringUtils.isNotEmpty(token))
        {
            String userKey = getTokenKey(token);
            redisCache.deleteObject(userKey);
        }
    }


    /** --------------私有方法----------------- **/

    /**
     * 从数据声明生成令牌
     * @param claims
     * @return
     */
    private String createToken(Map<String, Object> claims)
    {
        String token = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret).compact();
        return token;
    }

    /**
     * 刷新令牌有效期
     *
     * @param loginUser 登录信息
     */
    private void refreshToken(LoginUser loginUser)
    {
        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setExpireTime(loginUser.getLoginTime() + expireTime * MILLIS_MINUTE);
        // 根据uuid将loginUser缓存
        String userKey = getTokenKey(loginUser.getToken());
        String userIdKey = getTokenKey(loginUser.getUser().getId());
        // 登录互踢
        if (StringUtils.isNotNull(redisCache.getCacheObject(userIdKey))) {
            redisCache.deleteObject(redisCache.getCacheObject(userIdKey).toString());
        }
        redisCache.setCacheObject(userKey, loginUser, expireTime, TimeUnit.MINUTES);
        redisCache.setCacheObject(userIdKey, userKey, expireTime, TimeUnit.MINUTES);
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    private Claims parseToken(String token)
    {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 获取请求token
     *
     * @param request
     * @return token
     */
    private String getToken(HttpServletRequest request)
    {
        String token = request.getHeader(header);
        if (StringUtils.isNotEmpty(token) && token.startsWith(Constants.TOKEN_PREFIX))
        {
            token = token.replace(Constants.TOKEN_PREFIX, "");
        }
        return token;
    }

    private String getTokenKey(String uuid)
    {
        return CacheConstants.LOGIN_TOKEN_KEY + uuid;
    }

    private String getTokenKey(Long id)
    {
        return CacheConstants.LOGIN_ID_TOKEN_KEY + id;
    }
}
