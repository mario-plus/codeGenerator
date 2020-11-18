package ${package}.domain;
import lombok.Data;
import java.io.Serializable;
import java.sql.Timestamp;
/**
* @author ${author}
* @date ${date}
*/
@Data
public class ${className} implements Serializable {
<#if columns??>
    <#list columns as column>
        <#if column.remark != ''>
            /** ${column.remark} */
        </#if>
        private ${column.columnType} ${column.changeColumnName};
    </#list>
</#if>
}