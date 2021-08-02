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

package bnymellon.training.spring.framework.java;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import bnymellon.training.spring.framework.model.ColoredShapeHolder;
import bnymellon.training.spring.framework.model.DIConfiguration;

@ComponentScan(basePackages = {"bnymellon.training.spring.framework.model"})
public class Ex09_DIThroughJavaConfiguration {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(DIConfiguration.class);

        ColoredShapeHolder tealTrapezoidHolder = (ColoredShapeHolder) annotationConfigApplicationContext.getBean("tealTrapezoidHolder");

        System.out.println(tealTrapezoidHolder);
    }
}
