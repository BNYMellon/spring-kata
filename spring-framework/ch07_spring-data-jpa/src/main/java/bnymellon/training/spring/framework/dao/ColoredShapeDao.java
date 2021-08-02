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

import org.springframework.data.repository.CrudRepository;

import bnymellon.training.spring.framework.model.ColoredShape;

// No implementation !!! Only interface.

/**
 * @see org.springframework.data.repository.CrudRepository
 * @see org.springframework.data.repository.Repository
 */
public interface ColoredShapeDao extends CrudRepository<ColoredShape, Long> {

    // Notice how the save and findById (a.k.a findOne) methods are not even declared,
    // leave alone implemented.

    ColoredShape findByColor(final String color);

    ColoredShape findByShape(final String shape);
}