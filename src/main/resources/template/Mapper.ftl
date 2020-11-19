package ${package}.mapper;

import ${package}.domain.${className};
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
/**
* @author ${author}
* @date ${date}
*/
@Mapper
@Repository
public interface ${className}Mapper {
    /**
    *新增
    * @param resources /
    * @return ${className}
    */
    ${className} create(${className} resources);

    /**
    *根据id查询
    */
    ${className} read(Long id);

    /**
    * 编辑
    * @param resources /
    */
    void update(${className} resources);

    /**
    *删除
    * @param id /
    */
    void deleteById(Long id);

    /**
    *sql语句动态查询（自定义条件查询）
    */
    List<${className}> findByCondition(${className} resource);

}