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

package bnymellon.training.spring.framework;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import bnymellon.training.spring.framework.dao.ColoredShapeDao;
import bnymellon.training.spring.framework.model.ColoredShape;

public class Ex01_DB_XMLConfigured {

    public static void main(String[] args) {

        // XML based config loading
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");

        ColoredShapeDao coloredShapeDao = (ColoredShapeDao) context.getBean("coloredShapeDao");

        // Create a new shape.
        coloredShapeDao.create(4, "teal", "trapezium");

        // List the three original plus the new shape.
        List<ColoredShape> coloredShapes = coloredShapeDao.listColoredShapes();
        for (ColoredShape coloredShape : coloredShapes) {
            System.out.println(coloredShape.toString());
        }

        // Update a shape.
        coloredShapeDao.update(1, "red", "rectangle");

        // List the updated table content.
        coloredShapes = coloredShapeDao.listColoredShapes();
        for (ColoredShape coloredShape : coloredShapes) {
            System.out.println(coloredShape.toString());
        }

    }
}