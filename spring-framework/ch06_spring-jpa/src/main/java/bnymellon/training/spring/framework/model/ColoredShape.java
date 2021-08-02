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

package bnymellon.training.spring.framework.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "COLORED_SHAPE")
public class ColoredShape implements Serializable {

    private static final long serialVersionUID = 4784487603905008443L;

    private Long id;

    // default color
    protected String color = "green";

    // default shape
    protected String shape = "circle";

    public ColoredShape() {
    }

    public ColoredShape(final String color, final String shape) {
        if (color != null) {
            this.color = color;
        }
        if (shape != null) {
            this.shape = shape;
        }
    }

    public ColoredShape(final Long id, final String color, final String shape) {
        this.id = id;
        this.color = color;
        this.shape = shape;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(final String color) {
        this.color = color;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(final String type) {
        this.shape = type;
    }

    public String toString() {
        return "ID: [" + id + "]: Color: [" + color + "], Shape: [" + shape +"]";
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ColoredShape that = (ColoredShape) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (color != null ? !color.equals(that.color) : that.color != null) return false;
        return shape != null ? shape.equals(that.shape) : that.shape == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (color != null ? color.hashCode() : 0);
        result = 31 * result + (shape != null ? shape.hashCode() : 0);
        return result;
    }
}