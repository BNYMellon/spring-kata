/*
 * Copyright 2021 The Bank of New York Mellon.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package bnymellon.training.spring.boot.todo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Profile("production & !default")
public class TodoProductionServiceSwaggerConfig {

    final ProfileConfigurationProperties profileConfigurationProperties;

    public TodoProductionServiceSwaggerConfig(ProfileConfigurationProperties profileConfigurationProperties) {
        this.profileConfigurationProperties = profileConfigurationProperties;
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("todo")
                .select()
                .apis(RequestHandlerSelectors.basePackage("bnymellon.training.spring.boot.todo"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(profileConfigurationProperties.getTitle())
                .description("A " + profileConfigurationProperties.getDescriptiveText() +
                        " profile demo application showing how to setup a Spring Boot application")
                .termsOfServiceUrl("https://www.bnymellon.com/us/en/terms-of-use.jsp")
                .license("BNY License")
                .licenseUrl("https://www.bnymellon.com/us/en/terms-of-use.jsp")
                .version("1.0")
                .build();
    }

}
