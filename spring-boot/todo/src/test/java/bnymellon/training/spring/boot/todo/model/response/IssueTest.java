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

package bnymellon.training.spring.boot.todo.model.response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class IssueTest {

    private Issue issue1;
    private Issue issue2;
    private Issue issue3;

    @BeforeEach
    public void setUp() {

        issue1 = new Issue();
        issue1.setIssueCode("IssueCode");
        issue1.setIssueMessage("IssueMessage");

        issue2 = new Issue();
        issue2.setIssueCode("IssueCode");
        issue2.setIssueMessage("IssueMessage");

        issue3 = new Issue();
        issue3.setIssueCode("IssueCode3");
        issue3.setIssueMessage("IssueMessage3");
    }

    @Test
    public void testEquals() {

        assertEquals(
                issue1,
                issue2,
                "Issue1 and Issue2 should be equal");

        assertNotEquals(
                issue1,
                issue3,
                "Issue1 and Issue3 should not be equal");
    }

    @Test
    public void testHashCode() {

        assertEquals(
                issue1.hashCode(),
                issue2.hashCode(),
                "Issue1 and Issue2 should have the same hashCode");

        assertNotEquals(
                issue1.hashCode(),
                issue3.hashCode(),
                "Issue1 and Issue3 should not have the same hashCode");
    }

    @Test
    public void testToString() {

        assertEquals(
                issue1.toString(),
                issue2.toString(),
                "Issue1 and Issue2 should have the same toString");

        assertNotEquals(
                issue1.toString(),
                issue3.toString(),
                "Issue1 and Issue3 should not have the same toString");
    }
}