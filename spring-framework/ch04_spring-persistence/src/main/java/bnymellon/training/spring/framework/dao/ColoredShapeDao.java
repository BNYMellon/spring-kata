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

package bnymellon.training.spring.framework.dao;

import java.util.List;

import bnymellon.training.spring.framework.model.ColoredShape;

public interface ColoredShapeDao {

    /**
     * C of the CRUD Create a ColoredShape.
     */
    void create(int id, String color, String shape);

    /**
     * R of the CRUD Read a ColoredShape from the table corresponding to an id.
     */
    ColoredShape getColoredShape(int id);

    /**
     * R of the CRUD Read all ColoredShape instnces in the table
     */
    List<ColoredShape> listColoredShapes();

    /**
     * U of the CRUD Update a ColoredShape
     */
    void update(int id, String color, String shape);

    /**
     * D of the CRUD Delete a ColoredShape from the table corresponding to a an id.
     */
    void delete(int id);

}