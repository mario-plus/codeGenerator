package mario.generator.controller;


import mario.generator.dao.PathConfig;
import mario.generator.service.GeneratorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/generator")
public class GeneratorController {
    final GeneratorService generatorService;

    public GeneratorController(GeneratorService generatorService) {
        this.generatorService = generatorService;
    }


    @GetMapping(value = "/tables/all")
    public ResponseEntity<Object> getTables(){
        Object tables = generatorService.getTables();
        return new ResponseEntity<>(tables,HttpStatus.OK);
    }

    @GetMapping(value = "/gen")
    public ResponseEntity<Object> generator(@RequestParam(name = "tableName") String tableName,
                                            @RequestBody PathConfig pathConfig) throws IOException {
        generatorService.generator(tableName,pathConfig);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
