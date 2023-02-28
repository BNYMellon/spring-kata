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

import bnymellon.training.spring.framework.model.BulkVehicleOrder;
import bnymellon.training.spring.framework.model.DIConfiguration;
import bnymellon.training.spring.framework.model.Lab03DIConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

//TODO: Run this test with the right runner.
//TODO: Configure using the Lab03DIConfiguration
//TODO: Create the two qualified bulk vehicle orders "2 count of 2016 plane" and "6 count of 2017 flatbed" in Lab03DIConfiguration.
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {Lab03DIConfiguration.class})
public class Lab03Test {

    @Autowired
    @Qualifier("twoPlane2016Order")
    private BulkVehicleOrder twoPlane2016Order;

    @Autowired
    @Qualifier("sixFlatbed2017Order")
    private BulkVehicleOrder sixFlatbed2017Order;

    @Test
    public void testingTheTwoPlane2016Order() {

        // UNCOMMENT BELOW CODE AND TEST.

        assertEquals(
                2,
                twoPlane2016Order.getQuantity(),
                "The quantity of shapes should be [2]"
        );

        assertEquals(
                "2016 plane",
                twoPlane2016Order.getVehicle().toString(),
                "The shape should be [2016 plane]"
        );

    }

    @Test
    public void testingTheSixFlatbed2017Order() {
        // UNCOMMENT BELOW CODE AND TEST.

        assertEquals(
                6,
                sixFlatbed2017Order.getQuantity(),
                "The quantity of shapes should be [6]"
        );

        assertEquals(
                "2017 flatbed",
                sixFlatbed2017Order.getVehicle().toString(),
                "The shape should be [2017 flatbed]"
        );

    }
}
