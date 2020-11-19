package ${package}.controller;

import ${package}.domain.${className};
import ${package}.mapper.${className}Mapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
* @author ${author}
* @date ${date}
*/

@RestController
@RequestMapping("api")
public class ${className}Controller{
    @Autowired
    ${className}Mapper ${changeClassName}Mapper;

    /**
    *新增${className}
    * @param resource /
    */
    @GetMapping("/create${className}")
    public ResponseEntity create${className}(${className} resource){
        ${changeClassName}Mapper.create(resource);
        return new ResponseEntity(HttpStatus.OK);
    }


}