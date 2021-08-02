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

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import bnymellon.training.spring.framework.model.ColoredShape;

public class Ex03_InstantiationThroughInstanceFactory {

    public Ex03_InstantiationThroughInstanceFactory() {
        System.out.println("Ex03_InstantiationThroughInstanceFactory is now loaded.");
    }

    // This is an instance method. The class is first loaded as is visible
    // from the printed statement of the default constructor.
    public ColoredShape getInstance() {

        return new ColoredShape("red", "rhombus");
    }

    public static void main(String args[]) {

        // XML based config loading
        ApplicationContext context =
                new ClassPathXmlApplicationContext(
                        "ex03-instantiation-through-instance-factory.xml");

        ColoredShape coloredShapeBean = (ColoredShape) context.getBean("coloredShapeBean");

        System.out.println(coloredShapeBean.toString());
    }
}