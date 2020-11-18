package mario.generator.service;

import mario.generator.dao.ColumeInfo;
import mario.generator.dao.PathConfig;

import java.io.IOException;
import java.util.List;

public interface GeneratorService {

    /*
    * 获取数据库表
    * */
    Object getTables();

    /**
     * 查询数据库的表字段数据数据
     * @param table /
     * @return /
     */
    List<ColumeInfo> query(String table);

    /**
     * 代码生成
     */
    void generator(String tableName, PathConfig pathConfig) throws IOException;
}
