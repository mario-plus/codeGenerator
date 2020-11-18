package mario.generator.service.impl;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import mario.generator.dao.ColumeInfo;
import mario.generator.dao.PathConfig;
import mario.generator.service.GeneratorService;
import mario.generator.utils.GenUtil;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@Service
public class GeneratorServiceImpl implements GeneratorService {

    @PersistenceContext
    private EntityManager em;
    final GenUtil genUtil;

    public GeneratorServiceImpl(GenUtil genUtil) {
        this.genUtil = genUtil;
    }

    /*
    * 查询所有数据库
    * */
    @Override
    public Object getTables() {
        // 使用预编译防止sql注入
        String sql = "select table_name ,create_time , engine, table_collation, table_comment from information_schema.tables " +
                "where table_schema = (select database()) " +
                "order by create_time desc";
        Query query = em.createNativeQuery(sql);
        return query.getResultList();
    }

    /*
    * 获取数据库表的字段信息(字段名，字段类型，字段含义)
    * */
    @Override
    public List<ColumeInfo> query(String tableName) {
        // 使用预编译防止sql注入
        String sql = "select column_name, data_type, column_comment from information_schema.columns " +
                "where table_name = ? and table_schema = (select database()) order by ordinal_position";
        Query query = em.createNativeQuery(sql);
        query.setParameter(1,tableName);
        List results = query.getResultList();
        List<ColumeInfo> list = new ArrayList<>();
        results.forEach(result->{
            ColumeInfo columeInfo = new ColumeInfo();
            //对象转数组
            JSONArray object = JSONUtil.parseArray(JSONObject.toJSONString(result));
            columeInfo.setColumeName(object.get(0).toString());
            columeInfo.setDataType(object.get(1).toString());
            columeInfo.setColumeRemark(object.get(2).toString());
            list.add(columeInfo);
        }
        );
        return list;
    }

    @Override
    public void generator(String tableName, PathConfig pathConfig) throws IOException {
        genUtil.generatorCode(tableName,pathConfig);
    }


}
