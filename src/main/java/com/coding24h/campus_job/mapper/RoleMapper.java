package com.coding24h.campus_job.mapper;

import org.apache.ibatis.annotations.*;

@Mapper
public interface RoleMapper {
    @Select("SELECT role_id FROM role WHERE role_name = #{roleName}")
    Long findRoleIdByName(@Param("roleName") String roleName);

    @Insert("INSERT INTO user_role(user_id, role_id) VALUES (#{userId}, #{roleId})")
    void insertUserRole(@Param("userId") Long userId, @Param("roleId") Long roleId);

    @Delete("DELETE FROM user_role WHERE user_id = #{userId}")
    void deleteUserRoles(Long userId);
}