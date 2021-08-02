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

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import bnymellon.training.spring.framework.model.ColoredShape;

@Repository
@Transactional(readOnly = true)
public class ColoredShapeDaoImpl implements ColoredShapeDao {

    @Autowired
    private SessionFactory sessionFactory;

    protected Session getSession() {

        return this.sessionFactory.getCurrentSession();
    }

    @Override
    public ColoredShape findById(final Long id) {

        return getSession().get(ColoredShape.class, id);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void save(final ColoredShape coloredShape) {

        getSession().saveOrUpdate(coloredShape);
    }
}