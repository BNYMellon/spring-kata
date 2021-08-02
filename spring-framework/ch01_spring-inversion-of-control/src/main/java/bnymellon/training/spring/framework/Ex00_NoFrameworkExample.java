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

public class Ex00_NoFrameworkExample {

    public static void main(String[] args) {

        String color = "pink";

        String type = "polygon";

        // We are 'controlling' color through the command line argument.
        if (args.length > 0) {
            if (args[0] != null && args[0].length() > 0) {
                color = args[0];
            }
            if (args.length > 1 && args[1] != null && args[1].length() > 0) {
                type = args[1];
            }
        }

        ColoredShape coloredShape = new ColoredShape(color, type);

        System.out.println(coloredShape);
    }
}