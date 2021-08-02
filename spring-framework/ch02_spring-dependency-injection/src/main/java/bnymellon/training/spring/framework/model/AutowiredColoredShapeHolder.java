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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AutowiredColoredShapeHolder {

    private OrangeOval orangeOval;

    // default count is 1.
    private int quantity = 1;

    public AutowiredColoredShapeHolder() {
    }

    // ************************************************************************
    // Notice the @Autowired annotation. This will pull the bean reference from the context.
    // ************************************************************************
    @Autowired
    public AutowiredColoredShapeHolder(final OrangeOval orangeOval) {
        this.orangeOval = orangeOval;
    }

    public AutowiredColoredShapeHolder(final OrangeOval orangeOval, int quantity) {
        this.orangeOval = orangeOval;
        this.quantity = quantity;
    }

    public OrangeOval getColoredShape() {
        return orangeOval;
    }

    public void setColoredShape(final OrangeOval orangeOval) {
        this.orangeOval = orangeOval;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(final int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return quantity + " count of colored shape: [ " + orangeOval + " ]";
    }
}