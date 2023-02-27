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

package bnymellon.training.spring.framework.xml;

import bnymellon.training.spring.framework.model.Vehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;


// Refer to the bnymellon.training.spring.framework.xml package in the examples.
public class Lab01Test {

    private ApplicationContext context;

    @BeforeEach
    public void setup() {

        context = new ClassPathXmlApplicationContext("test-inject-with-xml.xml");
    }

    @Test
    public void injectThroughConstructorSimple() {

        // TODO: Add a bean definition with id=vehicleThroughConstructor in the src/test/resources/test-inject-with-xml.xml
        // UNCOMMENT BELOW CODE AND FIX IT.

        Vehicle vehicle = (Vehicle) context.getBean("vehicleThroughConstructor");
        assertEquals(
                "2017 car",
                vehicle.toString(),
                "The vehicle should be 2017 car"
        );

    }

    @Test
    public void injectThroughConstructorNamed() {

        // TODO: Add a bean definition with id=vehicleThroughConstructorNamed in the src/test/resources/test-inject-with-xml.xml
        // TODO: Create a Vehicle with year = 2016 and type = truck.
        // UNCOMMENT BELOW CODE AND FIX IT.

        Vehicle vehicle = (Vehicle) context.getBean("vehicleThroughConstructorNamed");
        assertEquals(
                "2016 truck",
                vehicle.toString(),
                "The vehicle should be 2016 truck"
        );

    }

    @Test
    public void injectThroughConstructorIndex() {

        // TODO: Add a bean definition with id=vehicleThroughConstructorIndex in the src/test/resources/test-inject-with-xml.xml
        // TODO: Create a Vehicle with year = 2015 and type = bike.
        // UNCOMMENT BELOW CODE AND FIX IT.

        Vehicle vehicle = (Vehicle) context.getBean("vehicleThroughConstructorIndex");
        assertEquals(
                "2015 bike",
                vehicle.toString(),
                "The vehicle should be 2015 bike"
        );

    }

    @Test
    public void injectThroughSetters() {

        // TODO: Add a bean definition with id=vehicleThroughSetter in the src/test/resources/test-inject-with-xml.xml
        // TODO: Create a Vehicle with year = 2014 and type = airplane.
        // UNCOMMENT BELOW CODE AND FIX IT.

        Vehicle vehicle = (Vehicle) context.getBean("vehicleThroughSetter");
        assertEquals(
                "2014 airplane",
                vehicle.toString(),
                "The vehicle should be 2014 airplane"
        );

    }

}
