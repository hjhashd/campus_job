package com.coding24h.campus_job.mapper;

import com.coding24h.campus_job.entity.User;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Mapper
public interface UserMapper {
    @Select("SELECT u.*, r.role_name AS role " +
            "FROM user u " +
            "JOIN user_role ur ON u.user_id = ur.user_id " +
            "JOIN role r ON ur.role_id = r.role_id " +
            "WHERE u.username = #{username}")
    @Results({
            @Result(property = "userId", column = "user_id", id = true), // 确保ID映射
            @Result(property = "identityType", column = "identity_type"),
    })
    User findByUsername(@Param("username") String username);//这个是用来查用户角色的

    @Insert("INSERT INTO user (identity_type, username, password, email, phone, avatar, " +
            "school, major, education, company_name, company_size) " +
            "VALUES (#{identityType}, #{username}, #{password}, #{email}, #{phone}, #{avatar}, " +
            "#{school}, #{major}, #{education}, #{companyName}, #{companySize})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    void insertUser(User user);


    @Select("SELECT * FROM user WHERE user_id = #{userId}")
    User findById(Long userId);

    @Update("UPDATE user SET " +
            "company_name = #{user.companyName}, " +
            "credit_code = #{user.creditCode}, " +
            "company_size = #{user.companySize}, " +
            "industry = #{user.industry}, " +
            "financing_stage = #{user.financingStage}, " +
            "headquarters = #{user.headquarters}, " +
            "updated_at = #{user.updatedAt} " +
            "WHERE user_id = #{user.userId}")
    int updateCompanyInfo(@Param("user") User user);


    @Select("SELECT * FROM user WHERE user_id = #{userId}")
    @Results({
            @Result(property = "userId", column = "user_id"),
            @Result(property = "identityType", column = "identity_type"),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "email", column = "email"),
            @Result(property = "phone", column = "phone"),
            @Result(property = "avatar", column = "avatar"),
            @Result(property = "school", column = "school"),
            @Result(property = "major", column = "major"),
            @Result(property = "education", column = "education"),
            @Result(property = "companyName", column = "company_name"),
            @Result(property = "companySize", column = "company_size"),
            @Result(property = "contactPerson", column = "contact_person"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "updatedAt", column = "updated_at"),
            @Result(property = "financingStage", column = "financing_stage"),
            @Result(property = "industry", column = "industry"),
            @Result(property = "headquarters", column = "headquarters"),
            @Result(property = "creditCode", column = "credit_code"),
            @Result(property = "role", column = "role"),
            @Result(property = "status", column = "status")
    })
    User getUserById(Long userId);


    // 1. 添加用户
    @Insert("INSERT INTO user (identity_type, username, password, email, phone, avatar, " +
            "school, major, education, company_name, company_size, contact_person, financing_stage, " +
            "industry, headquarters, credit_code, status) " +
            "VALUES (#{identityType}, #{username}, #{password}, #{email}, #{phone}, #{avatar}, " +
            "#{school}, #{major}, #{education}, #{companyName}, #{companySize}, #{contactPerson}, " +
            "#{financingStage}, #{industry}, #{headquarters}, #{creditCode}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    void insert(User user);

    // 2. 更新用户信息
    @Update("UPDATE user SET " +
            "username = #{username}, email = #{email}, phone = #{phone}, " +
            "school = #{school}, major = #{major}, education = #{education}, " +
            "company_name = #{companyName}, company_size = #{companySize}, " +
            "contact_person = #{contactPerson}, financing_stage = #{financingStage}, " +
            "industry = #{industry}, headquarters = #{headquarters}, " +
            "credit_code = #{creditCode}, status = #{status}, " +
            "updated_at = NOW() " +
            "WHERE user_id = #{userId}")
    void update(User user);

    // 3. 删除用户
    @Delete("DELETE FROM user WHERE user_id = #{userId}")
    void delete(Long userId);

    @Update("UPDATE user SET status = " +
            "CASE #{status} " +
            "   WHEN 'ACTIVE' THEN 1 " +
            "   WHEN 'INACTIVE' THEN 0 " +
            "END " +
            "WHERE user_id = #{userId}")
    void updateStatus(@Param("userId") Long userId, @Param("status") String status);

    // 4. 修复统计方法
    @Select("SELECT COUNT(*) FROM user WHERE status = " +
            "CASE #{status} " +
            "   WHEN 'ACTIVE' THEN 1 " +
            "   WHEN 'INACTIVE' THEN 0 " +
            "END")
    long countUsersByStatus(String status);

    // 分页查询用户
    // 修改分页查询方法
    @Select("<script>" +
            "SELECT *," +
            "CASE status " +
            "   WHEN 1 THEN 'ACTIVE' " +
            "   WHEN 0 THEN 'INACTIVE' " +
            "END AS status_text " +
            "FROM user " +
            "<where>" +
            "   <if test='identityType != null and identityType != \"\"'> AND identity_type = #{identityType} </if>" +
            "   <if test='status != null and status != \"\"'> " +
            "       <choose>" +
            "           <when test='status == \"ACTIVE\"'> AND status = 1 </when>" +
            "           <when test='status == \"INACTIVE\"'> AND status = 0 </when>" +
            "       </choose>" +
            "   </if>" +
            "   <if test='search != null and search != \"\"'> " +
            "       AND (username LIKE CONCAT('%', #{search}, '%') " +
            "            OR email LIKE CONCAT('%', #{search}, '%') " +
            "            OR phone LIKE CONCAT('%', #{search}, '%') " +
            "            OR company_name LIKE CONCAT('%', #{search}, '%')) " +
            "   </if>" +
            "</where>" +
            "ORDER BY created_at DESC " +
            "LIMIT #{offset}, #{size}" +
            "</script>")
    @Results({
            @Result(property = "userId", column = "user_id"),
            @Result(property = "status", column = "status"),
            @Result(property = "statusText", column = "status_text"),
            // 新增createdAt映射
            @Result(property = "createdAt", column = "created_at", javaType = LocalDateTime.class)
    })
    List<User> findUsersWithPaging(@Param("identityType") String identityType,
                                   @Param("status") String status,
                                   @Param("search") String search,
                                   @Param("offset") int offset,
                                   @Param("size") int size);

    // 修改计数方法
    @Select("<script>" +
            "SELECT COUNT(*) FROM user " +
            "<where>" +
            "   <if test='identityType != null and identityType != \"\"'> AND identity_type = #{identityType} </if>" +
            "   <if test='status != null and status != \"\"'> " +
            "       <choose>" +
            "           <when test='status == \"ACTIVE\"'> AND status = 1 </when>" +
            "           <when test='status == \"INACTIVE\"'> AND status = 0 </when>" +
            "       </choose>" +
            "   </if>" +
            "   <if test='search != null and search != \"\"'> " +
            "       AND (username LIKE CONCAT('%', #{search}, '%') " +
            "            OR email LIKE CONCAT('%', #{search}, '%') " +
            "            OR phone LIKE CONCAT('%', #{search}, '%') " +
            "            OR company_name LIKE CONCAT('%', #{search}, '%')) " +
            "   </if>" +
            "</where>" +
            "</script>")
    int countUsers(@Param("identityType") String identityType,
                   @Param("status") String status,
                   @Param("search") String search);

    // 统计所有用户数量
    @Select("SELECT COUNT(*) FROM user")
    long countAllUsers();



    // 按身份类型统计用户
    @Select("SELECT COUNT(*) FROM user WHERE identity_type = #{identityType}")
    long countUsersByIdentityType(String identityType);



}