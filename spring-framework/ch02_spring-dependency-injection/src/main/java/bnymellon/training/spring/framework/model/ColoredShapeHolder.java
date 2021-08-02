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

public class ColoredShapeHolder {

    private ColoredShape coloredShape = new ColoredShape();

    // default count is 1.
    private int quantity = 1;

    public ColoredShapeHolder() {
    }

    public ColoredShapeHolder(final ColoredShape coloredShape, int quantity) {

        this.coloredShape = coloredShape;
        this.quantity = quantity;
    }

    public ColoredShape getColoredShape() {

        return coloredShape;
    }

    public void setColoredShape(final ColoredShape coloredShape) {

        this.coloredShape = coloredShape;
    }

    public int getQuantity() {

        return quantity;
    }

    public void setQuantity(final int quantity) {

        this.quantity = quantity;
    }

    @Override
    public String toString() {

        return quantity + " count of colored shape: [ " + coloredShape + " ]";
    }
}