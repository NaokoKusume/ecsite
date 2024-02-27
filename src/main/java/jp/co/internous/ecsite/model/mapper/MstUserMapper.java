package jp.co.internous.ecsite.model.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import jp.co.internous.ecsite.model.domain.MstUser;
import jp.co.internous.ecsite.model.form.LoginForm;

@Mapper
public interface MstUserMapper {
	
	@Select(value="SELECT * FROM mst_user WHERE user_name = #{userName} AND password = #{password}")
	MstUser findByUserNameAndPassword(LoginForm form);
	
	@Insert("INSERT INTO mst_user (user_name,full_name,password,is_admin) VALUES (#{userName},#{fullName},#{password},0)")
	@Options(useGeneratedKeys=true, keyProperty="id")
	int insert(MstUser newUser);

}
