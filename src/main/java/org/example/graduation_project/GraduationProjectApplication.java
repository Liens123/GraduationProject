package org.example.graduation_project;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.apache.ibatis.mapping.Environment;



@OpenAPIDefinition(
        info = @Info(title = "毕业设计接口文档", version = "1.0", description = "登录、退出、用户查询等接口")
)
@SpringBootApplication
@MapperScan("org.example.graduation_project.mapper")
public class GraduationProjectApplication {


    public static void main(String[] args) {

        SpringApplication.run(GraduationProjectApplication.class, args);

    }

}
