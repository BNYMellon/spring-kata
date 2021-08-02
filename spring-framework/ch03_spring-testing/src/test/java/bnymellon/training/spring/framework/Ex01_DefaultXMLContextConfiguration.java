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

import bnymellon.training.spring.framework.model.ColoredShapeHolder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
public class Ex01_DefaultXMLContextConfiguration {

    @Autowired
    private ColoredShapeHolder coloredShapeHolder;

    @Test
    public void defaultXmlContextPath() {
        assertEquals(
                2,
                coloredShapeHolder.getQuantity(),
                "The quantity of shapes should be [2]");

        assertEquals(
                "orange oval",
                coloredShapeHolder.getColoredShape().toString(),
                "The shape should be [orange oval]");
    }
}
