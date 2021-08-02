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


import org.springframework.stereotype.Component;

// A Spring bean
@Component
public class ColoredShape {
    // default color
    protected String color = "green";

    // default shape
    protected String type = "circle";

    public ColoredShape() {
    }

    public ColoredShape(final String color, final String type) {
        if (color != null) {
            this.color = color;
        }
        if (type != null) {
            this.type = type;
        }
    }

    public String getColor() {
        return color;
    }

    public void setColor(final String color) {
        this.color = color;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String toString() {
        return color + ' ' + type;
    }

}