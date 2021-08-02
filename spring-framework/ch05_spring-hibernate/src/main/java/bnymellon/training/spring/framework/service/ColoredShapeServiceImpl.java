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

package bnymellon.training.spring.framework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bnymellon.training.spring.framework.dao.ColoredShapeDao;
import bnymellon.training.spring.framework.model.ColoredShape;

@Service
@Transactional(readOnly = true)
public class ColoredShapeServiceImpl implements ColoredShapeService {

    @Autowired
    ColoredShapeDao coloredShapeDao;

    @Override
    public ColoredShape getColoredShape(final Long id) {

        return coloredShapeDao.findById(id);
    }

    @Override
    @Transactional(readOnly = false)
    public void addColoredShape(final ColoredShape coloredShape) {

        coloredShapeDao.save(coloredShape);
    }
}