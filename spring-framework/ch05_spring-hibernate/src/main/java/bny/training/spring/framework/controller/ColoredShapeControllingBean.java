/*
 * Copyright 2024 The Bank of New York Mellon.
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

package bny.training.spring.framework.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bny.training.spring.framework.model.ColoredShape;
import bny.training.spring.framework.service.ColoredShapeService;

@Component
public class ColoredShapeControllingBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(ColoredShapeControllingBean.class);

    @Autowired
    private ColoredShapeService coloredShapeService;

    public void persistAndRetrieveColoredShapes() {

        ColoredShape coloredShape1 = new ColoredShape("teal", "trapezoid");
        ColoredShape coloredShape2 = new ColoredShape("turquoise", "triangle");

        LOGGER.info("\n\n\nPersisting the colored shapes:");
        // Save new colored shape
        coloredShapeService.addColoredShape(coloredShape1);
        coloredShapeService.addColoredShape(coloredShape2);


        // Get saved colored shape
        LOGGER.info("\n\n\nRetrieving ColoredShape 2:");
        ColoredShape fetched = coloredShapeService.getColoredShape(2L);
        LOGGER.info(fetched.toString());

        LOGGER.info("\n\n\nRetrieving ColoredShape 1:");
        fetched = coloredShapeService.getColoredShape(1L);
        LOGGER.info(fetched.toString());

        fetched.setShape("tetrahedron");
        coloredShapeService.addColoredShape(fetched);

        LOGGER.info("\n\n\nUpdating ColoredShape 1:");
        fetched = coloredShapeService.getColoredShape(1L);
        LOGGER.info(fetched.toString());
    }
}