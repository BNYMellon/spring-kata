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

@Configuration
//CoponentScan is optional and only required if there are other components that depend on, or are depended by this configuration.
@ComponentScan(basePackages = {"bnymellon.training.spring.framework"})
public class DIConfiguration {

    @Bean(name = "tealTrapezoid")
    public ColoredShape myColoredShape() {
        return new ColoredShape("teal", "trapezoid");
    }

    @Bean ("tealTrapezoidHolder")
    @Qualifier("tealTrapezoidHolder")
    public ColoredShapeHolder myColoredShapeHolder() {
        return new ColoredShapeHolder(myColoredShape(), 10);
    }




    @Bean ("pinkPolygonHolder")
    @Qualifier("pinkPolygonHolder")
    public ColoredShapeHolder mySecondColoredShapeHolder() {
        return new ColoredShapeHolder(new ColoredShape("pink", "polygon"), 7);
    }

}
