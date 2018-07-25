package ftg.ps.project.ms.user.config;

import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "ftg.ps.project.ms.user")
public class FeignConfiguration {

}
