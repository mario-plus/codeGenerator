package mario.generator.addModules.test1.mapper;

import mario.generator.addModules.test1.domain.SysUser;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
/**
* @author zxz
* @date 2020-11-19
*/
@Mapper
@Repository
public interface SysUserMapper {
    /**
    *新增
    * @param resources /
    * @return SysUser
    */
    SysUser create(SysUser resources);

    /**
    *根据id查询
    */
    SysUser read(Long id);

    /**
    * 编辑
    * @param resources /
    */
    void update(SysUser resources);

    /**
    *删除
    * @param id /
    */
    void deleteById(Long id);

    /**
    *sql语句动态查询（自定义条件查询）
    */
    List<SysUser> findByCondition(SysUser resource);

}