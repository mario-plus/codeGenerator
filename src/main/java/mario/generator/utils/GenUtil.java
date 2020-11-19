package mario.generator.utils;

import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.extra.template.*;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import mario.generator.dao.ColumeInfo;
import mario.generator.dao.PathConfig;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Component
public class GenUtil {
    @PersistenceContext
    private EntityManager em;
    /**
     * 获取后端代码模板名称
     * @return List
     */


    //1.获取文件类型集合
    private static List<String> getAdminTemplateNames() {
        List<String> templateNames = new ArrayList<>();
        templateNames.add("Entity");
        templateNames.add("Mapper");
        templateNames.add("Controller");
        //templateNames.add("Mapper.xml");
        //
        /*templateNames.add("Dto");
        templateNames.add("QueryCriteria");
        templateNames.add("Service");
        templateNames.add("ServiceImpl");
        templateNames.add("Repository");*/
        return templateNames;
    }

    //2.获取生成代码路径
    private static String getAdminFilePath(String templateName,String className,PathConfig pathConfig) {
        String projectPath = pathConfig.getProjectPath();//精确到java文件夹
        String filePath = projectPath + File.separator + pathConfig.getPackageName().replace(".", File.separator);
        System.out.println(projectPath);
        System.out.println(filePath);
        if ("Entity".equals(templateName)) {
            return filePath + File.separator  + "domain" + File.separator + className + ".java";
        }
        if ("Mapper".equals(templateName)){
            return filePath + File.separator  + "mapper" + File.separator + className + "Mapper.java";
        }
        if ("Controller".equals(templateName)){
            return filePath + File.separator  + "controller" + File.separator + className + "Controller.java";
        }
        /*if ("Mapper.xml".equals(templateName)){
            System.out.println("/resources/mybatis/mapper/"  + className + "Mapper.xml");
            return "";
        }*/
        return null;
    }


    //3.获取模板需要信息
    private  Map<String,Object> getGenMap(String tableName,PathConfig pathConfig){
        // 存储模版字段数据
        Map<String,Object> genMap = new HashMap<>(16);
        // 保存字段信息
        List<Map<String,Object>> columns = new ArrayList<>();
        genMap.put("package",pathConfig.getPackageName());
        // 作者
        genMap.put("author",pathConfig.getAuthor());
        // 创建日期
        genMap.put("date", LocalDate.now().toString());
        // 大写开头的类名
        String className = StringUtils.toCapitalizeCamelCase(tableName);
        String changeClassName = StringUtils.toCamelCase(tableName);
        genMap.put("className", className);
        genMap.put("changeClassName",changeClassName);
        List<ColumeInfo> columeInfos = query(tableName);
        columeInfos.forEach(columeInfo -> {
            Map<String,Object> listMap = new HashMap<>(16);
            listMap.put("remark",columeInfo.getColumeRemark());
            System.out.println(columeInfo.getDataType()+"-------------------------");
            String colType = ColUtil.cloToJava(columeInfo.getDataType());
            listMap.put("columnType",colType);
            // 小写开头的字段名
            String changeColumnName = StringUtils.toCamelCase(columeInfo.getColumeName().toString());
            listMap.put("changeColumnName",changeColumnName);
            columns.add(listMap);
        });
        genMap.put("columns",columns);
        return genMap;
    }

    //4.代码生成入口
    public  void generatorCode(String tableName, PathConfig pathConfig) throws IOException {
        TemplateEngine engine = TemplateUtil.createEngine(new TemplateConfig("template", TemplateConfig.ResourceMode.CLASSPATH));
        List<String> templates = getAdminTemplateNames();
        //数据(表字段)
        Map<String, Object> genMap = getGenMap(tableName,pathConfig);

        templates.forEach(templateName->{
            Template template = engine.getTemplate(templateName+".ftl");
            String filePath = getAdminFilePath(templateName,genMap.get("className").toString(),pathConfig);
            assert filePath != null;
            File file = new File(filePath);
            try {
                genFile(file,template,genMap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    //5.查询数据库的具体表的所有字段
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

    //6.生成文件
    private static void genFile(File file, Template template, Map<String, Object> map) throws IOException {
        // 生成目标文件
        Writer writer = null;
        try {
            FileUtil.touch(file);
            writer = new FileWriter(file);
            template.render(map, writer);
        } catch (TemplateException | IOException e) {
            throw new RuntimeException(e);
        } finally {
            assert writer != null;
            writer.close();
        }
    }

    public static void main(String[] args) {

    }
}
