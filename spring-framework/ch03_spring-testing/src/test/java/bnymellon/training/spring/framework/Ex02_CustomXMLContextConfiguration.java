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

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import bnymellon.training.spring.framework.model.ColoredShapeHolder;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:/ex02.xml"})
public class Ex02_CustomXMLContextConfiguration {

    @Autowired
    private ColoredShapeHolder coloredShapeHolder;

    @Test
    public void customXmlContextPath() {
        assertEquals(
                5,
                coloredShapeHolder.getQuantity(),
                "The quantity of shapes should be [5]"
        );
        assertEquals(
                "red rhombus",
                coloredShapeHolder.getColoredShape().toString(),
                "The shape should be [red rhombus]"
        );
    }

}
