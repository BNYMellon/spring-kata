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

// TODO: Fix this class to Autowire using a setter
public class SetterAnnotatedBulkVehicleOrder {

    private Vehicle vehicle;
    private int quantity = 1;

    public SetterAnnotatedBulkVehicleOrder() {
    }

    public SetterAnnotatedBulkVehicleOrder(final Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public SetterAnnotatedBulkVehicleOrder(final Vehicle vehicle, final int quantity) {
        this.vehicle = vehicle;
        this.quantity = quantity;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(final Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(final int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return quantity + " " + "count of " + vehicle.toString();
    }
}
