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

package bnymellon.training.spring.framework.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import bnymellon.training.spring.framework.model.ColoredShape;
import bnymellon.training.spring.framework.service.ColoredShapeService;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Controller
@RequestMapping("/colored-shapes")
public class ColoredShapeController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ColoredShapeController.class);

    private ColoredShapeService coloredShapeService;

    @Autowired
    public void setColoredShapeService(final ColoredShapeService coloredShapeService) {
        this.coloredShapeService = coloredShapeService;
    }

    @ApiOperation(
            value = "Create a new record",
            notes = "Use this resource to add a new record.",
            position = 10,
            tags = {"colored-shape-service"}
    )
    @ApiResponses({
            @ApiResponse(code = 201, message = "Record created."),
            @ApiResponse(code = 400, message = "Bad or malformed request"),
            @ApiResponse(code = 403, message = "User is not entitled to create this record."),
            @ApiResponse(code = 412, message = "Todo record creation failed, due to validations."),
            @ApiResponse(code = 500, message = "An unexpected error has occurred. Todo record was not created."),
    })
    @RequestMapping(
            method = RequestMethod.POST,
            consumes = {APPLICATION_JSON_VALUE, APPLICATION_FORM_URLENCODED_VALUE},
            produces = {APPLICATION_JSON_VALUE}
    )
    @ResponseStatus(HttpStatus.CREATED)
    public void saveColoredShape(@RequestBody ColoredShape coloredShape) {
        coloredShapeService.saveColoredShape(coloredShape);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ColoredShape getColoredShape(@PathVariable("id") Long id) {
        return coloredShapeService.getColoredShape(id);
    }

    @RequestMapping( method = RequestMethod.GET )
    @ResponseBody
    public List< ColoredShape > findAll(){
        return coloredShapeService.findAll();
    }

    @RequestMapping( value = "/{id}", method = RequestMethod.PUT )
    @ResponseStatus( HttpStatus.OK )
    public void updateColoredShape(@PathVariable( "id" ) Long id, @RequestBody ColoredShape coloredShape) {
        ColoredShape existingColoredShape = coloredShapeService.getColoredShape(id);
        existingColoredShape.setColor(coloredShape.getColor());
        existingColoredShape.setShape(coloredShape.getShape());
        coloredShapeService.saveColoredShape(existingColoredShape);
    }


    public void persistAndRetrieveColoredShapes() {
        ColoredShape coloredShape1 = new ColoredShape("teal", "trapezoid");
        ColoredShape coloredShape2 = new ColoredShape("turquoise", "triangle");

        LOGGER.info("\n\n\nPersisting the colored shapes:");
        // Save new colored shape
        coloredShapeService.saveColoredShape(coloredShape1);
        coloredShapeService.saveColoredShape(coloredShape2);


        // Get saved colored shape
        LOGGER.info("\n\n\nRetrieving ColoredShape 2:");
        ColoredShape fetched = coloredShapeService.getColoredShape(2L);
        LOGGER.info(fetched.toString());

        LOGGER.info("\n\n\nRetrieving ColoredShape 1:");
        fetched = coloredShapeService.getColoredShape(1L);
        LOGGER.info(fetched.toString());

        fetched.setShape("tetrahedron");
        coloredShapeService.saveColoredShape(fetched);

        LOGGER.info("\n\n\nUpdating ColoredShape 1:");
        fetched = coloredShapeService.getColoredShape(1L);
        LOGGER.info(fetched.toString());

        LOGGER.info("\n\n\nMagic ColoredShape:");
        fetched = coloredShapeService.getColoredShapeByShape("triangle");
        LOGGER.info(fetched.toString());

        LOGGER.info("\n\n\nMagic ColoredShape:");
        fetched = coloredShapeService.getColoredShapeByColor("teal");
        LOGGER.info(fetched.toString());
    }

}