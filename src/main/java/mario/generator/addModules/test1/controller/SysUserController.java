package mario.generator.addModules.test1.controller;

import mario.generator.addModules.test1.domain.SysUser;
import mario.generator.addModules.test1.mapper.SysUserMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
* @author zxz
* @date 2020-11-19
*/

@RestController
@RequestMapping("api")
public class SysUserController{
    @Autowired
    SysUserMapper sysUserMapper;

    /**
    *新增SysUser
    * @param resource /
    */
    @GetMapping("/createSysUser")
    public ResponseEntity createSysUser(SysUser resource){
        sysUserMapper.create(resource);
        return new ResponseEntity(HttpStatus.OK);
    }



}