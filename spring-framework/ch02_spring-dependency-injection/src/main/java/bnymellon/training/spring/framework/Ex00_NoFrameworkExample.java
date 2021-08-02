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

import bnymellon.training.spring.framework.model.ColoredShape;
import bnymellon.training.spring.framework.model.ColoredShapeHolder;
import bnymellon.training.spring.framework.model.CyanCylinder;
import bnymellon.training.spring.framework.model.RedRectangle;

public class Ex00_NoFrameworkExample {

    public static void main(String[] args) {

        ColoredShapeHolder coloredShapeHolder = new ColoredShapeHolder();

        //Should print "1 count of colored shape: [ green circle ]"
        System.out.println(coloredShapeHolder);

        // ---------------------------------------------------------------------------------------

        ColoredShape redRectangle = new RedRectangle();

        // Create a holder of 5 red rectangles
        ColoredShapeHolder redRectangleHolder =
                new ColoredShapeHolder(redRectangle, 5);

        //Should print "5 count of colored shape: [ red rectangle ]"
        System.out.println(redRectangleHolder);

        // ---------------------------------------------------------------------------------------

        ColoredShape cyanCylinder = new CyanCylinder();

        // Create a holder of 7 cyan cylinders
        ColoredShapeHolder cyanCylinderHolder =
                new ColoredShapeHolder(cyanCylinder, 7);

        //Should print "7 count of colored shape: [ cyan cylinder ]"
        System.out.println(cyanCylinderHolder);

        // ---------------------------------------------------------------------------------------

        ColoredShape blackBox = new ColoredShape();
        blackBox.setColor("black");
        blackBox.setType("box");

        // Create a holder of 3 black box
        ColoredShapeHolder blackBoxHolder = new ColoredShapeHolder();
        blackBoxHolder.setColoredShape(blackBox);
        blackBoxHolder.setQuantity(3);

        //Should print "3 count of colored shape: [ black box ]"
        System.out.println(blackBoxHolder);
    }
}