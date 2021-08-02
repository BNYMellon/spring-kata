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

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import bnymellon.training.spring.framework.model.ColoredShape;

@Repository
public class ColoredShapeDaoImplEx02 implements ColoredShapeDao {

    private DataSource dataSource;

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public ColoredShapeDaoImplEx02(final DataSource dataSource) {

        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void create(final int id, final String color, final String shape) {

        String sql = "insert into coloredshape (id, color, shape) values (?, ?, ?)";

        jdbcTemplate.update(sql, id, color, shape);

        System.out.println("Added to DB, ID[" + id + "]: Color: [" + color + "], Shape: [" + shape + "]");
    }

    @Override
    public ColoredShape getColoredShape(final int id) {

        String sql = "select * from coloredshape where id = ?";

        ColoredShape coloredShape = jdbcTemplate.queryForObject(sql, new Object[]{id}, new ColoredShapeMapper());

        return coloredShape;
    }

    @Override
    public List<ColoredShape> listColoredShapes() {

        String sql = "select * from coloredshape";

        List<ColoredShape> coloredShapes = jdbcTemplate.query(sql, new ColoredShapeMapper());

        return coloredShapes;
    }

    @Override
    public void update(final int id, final String color, final String shape) {

        String sql = "update coloredshape set color = ?, shape = ? where id = ?";

        jdbcTemplate.update(sql, color, shape, id);

        System.out.println("Updated in DB, ID[" + id + "]: Color: [" + color + "], Shape: [" + shape + "]");
    }

    @Override
    public void delete(final int id) {

        String sql = "delete from coloredshape where id = ?";

        jdbcTemplate.update(sql, id);

        System.out.println("Deleted in DB, ID[" + id + "]");
    }


    private static final class ColoredShapeMapper implements RowMapper<ColoredShape> {

        public ColoredShape mapRow(ResultSet rs, int rowNum) throws SQLException {
            ColoredShape user = new ColoredShape();
            user.setId(rs.getLong("id"));
            user.setColor(rs.getString("color"));
            user.setShape(rs.getString("shape"));
            return user;
        }
    }
}