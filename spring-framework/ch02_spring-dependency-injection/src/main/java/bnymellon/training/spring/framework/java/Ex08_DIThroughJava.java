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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import bnymellon.training.spring.framework.model.AutowiredColoredShapeHolder;

@ComponentScan(basePackages = {"bnymellon.training.spring.framework.model"})
public class Ex08_DIThroughJava {

    @Autowired
    private AutowiredColoredShapeHolder autowiredColoredShapeHolder;

    public static void main(String[] args) {

        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext();
        annotationConfigApplicationContext.register(Ex08_DIThroughJava.class);
        annotationConfigApplicationContext.refresh();

        AutowiredColoredShapeHolder autowiredColoredShapeHolder = annotationConfigApplicationContext.getBean
                (AutowiredColoredShapeHolder.class);
        System.out.println(autowiredColoredShapeHolder);
    }

}
