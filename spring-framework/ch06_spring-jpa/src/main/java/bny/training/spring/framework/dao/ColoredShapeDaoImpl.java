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

package bny.training.spring.framework.dao;

import bny.training.spring.framework.model.ColoredShape;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public class ColoredShapeDaoImpl implements ColoredShapeDao {

    @PersistenceContext
    private EntityManager em;


    @Override
    public ColoredShape findById(final Long id) {

        return (ColoredShape) em
                .createQuery("SELECT c FROM ColoredShape c WHERE c.id LIKE :id")
                .setParameter("id", id)
                .setMaxResults(1)
                .getResultList().get(0);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void save(final ColoredShape coloredShape) {

        em.merge(coloredShape);
    }
}