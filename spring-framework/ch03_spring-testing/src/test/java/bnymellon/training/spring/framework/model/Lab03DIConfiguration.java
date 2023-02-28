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

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

//TODO: Ensure that this Configuration is stereotyped.
@Configuration
@ComponentScan(basePackages = {"bnymellon.training.spring.framework.model"})
public class Lab03DIConfiguration {

    //TODO: Create a bean each for:

    // twoPlane2016Order
    
    @Bean("twoPlane2016Order")
    @Qualifier("twoPlane2016Order")
    public BulkVehicleOrder myBulkVehicle() {
        return new BulkVehicleOrder(new Vehicle("plane", 2016), 2);
    }

    @Bean("sixFlatbed2017Order")
    @Qualifier("sixFlatbed2017Order")
    public BulkVehicleOrder myOtherVehicle() {
        return new BulkVehicleOrder(new Vehicle("flatbed", 2017), 6);
    }
    // sixFlatbed2017Order

    // TODO: Add qualifiers.

}
