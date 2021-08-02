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

package bnymellon.training.spring.framework;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import bnymellon.training.spring.framework.model.BulkVehicleOrder;

import static org.springframework.test.util.AssertionErrors.assertEquals;

//TODO: Run this test with the right runner.
//TODO: Use default XML context configuration to configure an order of 10 count of 2017 car.
public class Lab01Test {

    @Autowired
    private BulkVehicleOrder bulkVehicleOrder;

    @Test
    public void defaultXmlContextPath() {

        // TODO: Add a vehicle XML bean definition with id=vehicle as a 2017 car.
        // TODO: Add a bulkVehicleOrder XML bean definition with the above vehicle and quantity of 10.
        // UNCOMMENT BELOW CODE AND TEST.
/*
        assertEquals(
                10,
                bulkVehicleOrder.getQuantity(),
                "The quantity of vehicles should be [10]"
        );

        assertEquals(
                "2017 car",
                bulkVehicleOrder.getVehicle().toString(),
                "The shape should be [2017 car]"
        );
*/
    }

}
