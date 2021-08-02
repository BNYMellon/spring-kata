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

public class Ex02_InstantiationThroughStaticFactory {

    // This is a static method. The content of this method is used to
    // construct the ColoredShape. There can be many such methods that
    // return other configurations.
    public static ColoredShape getInstance() {

        return new ColoredShape("harlequin", "hexagon");
    }

    public static void main(String args[]) {

        // XML based config loading
        ApplicationContext context =
                new ClassPathXmlApplicationContext(
                        "ex02-instantiation-through-static-factory.xml");

        ColoredShape coloredShapeBean = (ColoredShape) context.getBean("coloredShapeBean");

        System.out.println(coloredShapeBean.toString());
    }

}