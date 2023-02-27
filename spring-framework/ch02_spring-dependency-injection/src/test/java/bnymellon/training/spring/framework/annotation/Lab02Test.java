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

package bnymellon.training.spring.framework.annotation;

import bnymellon.training.spring.framework.model.ConstructorAnnotatedBulkVehicleOrder;
import bnymellon.training.spring.framework.model.ConstructorAnnotatedWithQualifierBulkVehicleOrder;
import bnymellon.training.spring.framework.model.SetterAnnotatedBulkVehicleOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Refer to the bnymellon.training.spring.framework.annotation package in the examples.
public class Lab02Test {
    
    private ApplicationContext context;

    @BeforeEach
    public void setup() {

    }

    @Test
    public void injectThroughSetterAnnotation() {
        context = new ClassPathXmlApplicationContext("test-inject-with-annotations1.xml");

        // TODO: Add a bean definition with id=vehicle as a 2017 car in the src/test/resources/test-inject-with-annotations1.xml
        // TODO: Add a bean definition with id=setterAnnotatedBulkVehicleOrder in the src/test/resources/test-inject-with-annotations1.xml
        // TODO: 'Wire' the setter for the vehicle, in the SetterAnnotatedBulkVehicleOrder.java
        // UNCOMMENT BELOW CODE AND FIX IT.

        SetterAnnotatedBulkVehicleOrder setterAnnotatedBulkVehicleOrder =
                (SetterAnnotatedBulkVehicleOrder) context.getBean("setterAnnotatedBulkVehicleOrder");
        assertEquals(
                "1 count of 2017 car",
                setterAnnotatedBulkVehicleOrder.toString(),
                "The order should be: 1 count of 2017 car"
        );

    }

    @Test
    public void injectThroughConstructor() {
        context = new ClassPathXmlApplicationContext("test-inject-with-annotations2.xml");

        // TODO: Add a bean definition with id=vehicle as a 2016 truck in the src/test/resources/test-inject-with-annotations2.xml
        // TODO: Add a bean definition with id=constructorAnnotatedBulkVehicleOrder in the src/test/resources/test-inject-with-annotations2.xml
        // TODO: 'Wire' the setter for the vehicle, in the ConstructorAnnotatedBulkVehicleOrder.java
        // UNCOMMENT BELOW CODE AND FIX IT.

        ConstructorAnnotatedBulkVehicleOrder constructorAnnotatedBulkVehicleOrder =
                (ConstructorAnnotatedBulkVehicleOrder) context.getBean("constructorAnnotatedBulkVehicleOrder");
        assertEquals(
                "5 count of 2016 truck",
                constructorAnnotatedBulkVehicleOrder.toString(),
                "The order should be: 5 count of 2016 truck"
        );

    }

    @Test
    public void injectThroughConstructorWithQualifier() {
        context = new ClassPathXmlApplicationContext("test-inject-with-annotations3.xml");

        // TODO: Add a bean definition with id=vehicle as a 2014 airplane, with a qualifier, in the src/test/resources/test-inject-with-annotations3.xml
        // TODO: Add a bean definition with id=vehicle2 as a 2017 car, with a qualifier, in the src/test/resources/test-inject-with-annotations3.xml
        // TODO: Add a bean definition with id=constructorAnnotatedWithQualifierBulkVehicleOrder in the src/test/resources/test-inject-with-annotations3.xml
        // TODO: 'Wire' the constructor for the 'vehicle', with a qualifier, in the ConstructorAnnotatedWithQualifierBulkVehicleOrder.java
        // UNCOMMENT BELOW CODE AND FIX IT.

        ConstructorAnnotatedWithQualifierBulkVehicleOrder constructorAnnotatedWithQualifierBulkVehicleOrder =
                (ConstructorAnnotatedWithQualifierBulkVehicleOrder) context.getBean("constructorAnnotatedWithQualifierBulkVehicleOrder");
        assertEquals(
                "7 count of 2014 airplane",
                constructorAnnotatedWithQualifierBulkVehicleOrder.toString(),
                "The order should be: 7 count of 2014 airplane"
        );

    }

}
