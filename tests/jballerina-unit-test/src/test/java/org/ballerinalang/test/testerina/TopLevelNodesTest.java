/*
 *  Copyright (c) 2020, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  WSO2 Inc. licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.ballerinalang.test.testerina;

import org.ballerinalang.test.util.BAssertUtil;
import org.ballerinalang.test.util.BCompileUtil;
import org.ballerinalang.test.util.CompileResult;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Test cases for duplicate definitions in tests vs src.
 */
@Test
public class TopLevelNodesTest {
    private CompileResult compileResult;

    @BeforeClass
    public void setup() {
        compileResult = BCompileUtil.compileWithTests("test-src/testerina/TopLevelNodesDupTest", "duptest");
    }

    @Test(description = "Test Toplevel nodes duplication")
    public void testModule() {
        Assert.assertEquals(compileResult.getErrorCount(), 4);

        int index = 0;
        BAssertUtil.validateError(compileResult, index++, "redeclared symbol 'testDuplicate'", 4, 17);
        BAssertUtil.validateError(compileResult, index++, "redeclared symbol 'Person'", 8, 13);
        BAssertUtil.validateError(compileResult, index++, "redeclared symbol 'testString'", 13, 8);
        BAssertUtil.validateError(compileResult, index++, "redeclared symbol 'Company'", 15, 13);
    }
}
