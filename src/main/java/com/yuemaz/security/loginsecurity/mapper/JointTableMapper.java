package com.yuemaz.security.loginsecurity.mapper;

import java.util.Set;

/**
 * @author: create by w5g
 * @date: 2022/7/26 11:26
 */
public interface JointTableMapper {

    /**
     * 根据用户ID查询对应的权限标识
     * @param userId
     * @return
     */
    Set<String> selectPermsByUserId(Long userId);

    /**
     * 根据用户ID查询对应的权角色标识
     * @param userId
     * @return
     */
    Set<String> selectRolesByUserId(Long userId);
}
