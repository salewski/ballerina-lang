/*
 *  Copyright (c) 2020, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  WSO2 Inc. licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.ballerinalang.mysql.query;

import org.ballerinalang.model.values.BBoolean;
import org.ballerinalang.model.values.BDecimal;
import org.ballerinalang.model.values.BError;
import org.ballerinalang.model.values.BFloat;
import org.ballerinalang.model.values.BInteger;
import org.ballerinalang.model.values.BMap;
import org.ballerinalang.model.values.BString;
import org.ballerinalang.model.values.BValue;
import org.ballerinalang.mysql.BaseTest;
import org.ballerinalang.mysql.utils.SQLDBUtils;
import org.ballerinalang.test.util.BCompileUtil;
import org.ballerinalang.test.util.BRunUtil;
import org.ballerinalang.test.util.CompileResult;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.wso2.ballerinalang.compiler.util.TypeTags;

import java.io.File;
import java.text.DecimalFormat;
import java.util.LinkedHashMap;

/**
 * This test class verifies the behaviour of the ParameterizedQuery passed into the testQuery operation.
 *
 * @since 1.3.0
 */
public class ParamsQueryTest {
    private CompileResult result;
    private static final String DB_NAME = "TEST_SQL_PARAMS_QUERY";
    private static final String SQL_SCRIPT = SQLDBUtils.SQL_RESOURCE_DIR + File.separator + SQLDBUtils.QUERY_DIR +
            File.separator + "simple-params-test-data.sql";

    static {
        BaseTest.addDBSchema(DB_NAME, SQL_SCRIPT);
    }

    @BeforeClass
    public void setup() {
        result = BCompileUtil.compileOffline(SQLDBUtils.getBalFilesDir(SQLDBUtils.QUERY_DIR,
                "simple-params-query-test.bal"));
    }

    @Test
    public void testQuerySingleIntParam() {
        BValue[] returns = BRunUtil.invokeFunction(result, "querySingleIntParam");
        validateDataTableResult(returns);
    }

    @Test
    public void testQueryDoubleIntParam() {
        BValue[] returns = BRunUtil.invokeFunction(result, "queryDoubleIntParam");
        validateDataTableResult(returns);
    }

    @Test
    public void testQueryIntAndLongParam() {
        BValue[] returns = BRunUtil.invokeFunction(result, "queryIntAndLongParam");
        validateDataTableResult(returns);
    }

    @Test
    public void testQueryStringParam() {
        BValue[] returns = BRunUtil.invokeFunction(result, "queryStringParam");
        validateDataTableResult(returns);
    }

    @Test
    public void testQueryIntAndStringParam() {
        BValue[] returns = BRunUtil.invokeFunction(result, "queryIntAndStringParam");
        validateDataTableResult(returns);
    }

    @Test
    public void testQueryDoubleParam() {
        BValue[] returns = BRunUtil.invokeFunction(result, "queryDoubleParam");
        validateDataTableResult(returns);
    }

    @Test
    public void testQueryFloatParam() {
        BValue[] returns = BRunUtil.invokeFunction(result, "queryFloatParam");
        validateDataTableResult(returns);
    }

    @Test
    public void testQueryDoubleAndFloatParam() {
        BValue[] returns = BRunUtil.invokeFunction(result, "queryDoubleAndFloatParam");
        validateDataTableResult(returns);
    }

    @Test
    public void testQueryDecimalParam() {
        BValue[] returns = BRunUtil.invokeFunction(result, "queryDecimalParam");
        validateDataTableResult(returns);
    }

    @Test
    public void testQueryDecimalAnFloatParam() {
        BValue[] returns = BRunUtil.invokeFunction(result, "queryDecimalAnFloatParam");
        validateDataTableResult(returns);
    }

    @Test
    public void testQueryByteArrayParam() {
        BValue[] returns = BRunUtil.invokeFunction(result, "queryByteArrayParam");
        validateComplexTableResult(returns);
    }

    @Test
    public void testQueryTypeVarcharStringParam() {
        BValue[] returns = BRunUtil.invokeFunction(result, "queryTypeVarcharStringParam");
        validateDataTableResult(returns);
    }

    @Test
    public void testQueryTypeCharStringParam() {
        BValue[] returns = BRunUtil.invokeFunction(result, "queryTypeCharStringParam");
        validateDataTableResult(returns);
    }

    @Test
    public void testQueryTypeNCharStringParam() {
        BValue[] returns = BRunUtil.invokeFunction(result, "queryTypeNCharStringParam");
        validateDataTableResult(returns);
    }

    @Test
    public void testQueryTypeNVarCharStringParam() {
        BValue[] returns = BRunUtil.invokeFunction(result, "queryTypeNVarCharStringParam");
        validateDataTableResult(returns);
    }

    @Test
    public void testQueryTypeVarCharIntegerParam() {
        BValue[] returns = BRunUtil.invokeFunction(result, "queryTypeVarCharIntegerParam");
        SQLDBUtils.assertNotError(returns[0]);
        Assert.assertTrue(returns[0] instanceof BMap);
        LinkedHashMap result = ((BMap) returns[0]).getMap();
        Assert.assertEquals(result.size(), 8);
        Assert.assertEquals(((BInteger) result.get("row_id")).intValue(), 3);
        Assert.assertEquals(((BString) result.get("string_type")).stringValue(), "1");
    }

    @Test
    public void testQueryTypBooleanBooleanParam() {
        BValue[] returns = BRunUtil.invokeFunction(result, "queryTypBooleanBooleanParam");
        validateDataTableResult(returns);
    }

    @Test
    public void testQueryTypBitIntParam() {
        BValue[] returns = BRunUtil.invokeFunction(result, "queryTypBitIntParam");
        validateDataTableResult(returns);
    }

    @Test
    public void testQueryTypBitStringParam() {
        BValue[] returns = BRunUtil.invokeFunction(result, "queryTypBitStringParam");
        validateDataTableResult(returns);
    }

    @Test
    public void testQueryTypBitInvalidIntParam() {
        BValue[] returns = BRunUtil.invokeFunction(result, "queryTypBitInvalidIntParam");
        Assert.assertEquals(returns[0].getType().getTag(), TypeTags.ERROR);
        Assert.assertTrue(((BError) returns[0]).getMessage().contains("Only 1 or 0 can be passed"));
    }

    @Test
    public void testQueryTypeIntIntParam() {
        BValue[] returns = BRunUtil.invokeFunction(result, "queryTypeIntIntParam");
        validateNumericTableResult(returns);
    }

    @Test
    public void testQueryTypeTinyIntIntParam() {
        BValue[] returns = BRunUtil.invokeFunction(result, "queryTypeTinyIntIntParam");
        validateNumericTableResult(returns);
    }

    @Test
    public void testQueryTypeSmallIntIntParam() {
        BValue[] returns = BRunUtil.invokeFunction(result, "queryTypeSmallIntIntParam");
        validateNumericTableResult(returns);
    }

    @Test
    public void testQueryTypeMediumIntIntParam() {
        BValue[] returns = BRunUtil.invokeFunction(result, "queryTypeMediumIntIntParam");
        validateNumericTableResult(returns);
    }

    @Test
    public void testQueryTypeBigIntIntParam() {
        BValue[] returns = BRunUtil.invokeFunction(result, "queryTypeBigIntIntParam");
        validateNumericTableResult(returns);
    }

    @Test
    public void testQueryTypeDoubleDoubleParam() {
        BValue[] returns = BRunUtil.invokeFunction(result, "queryTypeDoubleDoubleParam");
        validateNumericTableResult(returns);
    }

    @Test
    public void testQueryTypeDoubleIntParam() {
        BValue[] returns = BRunUtil.invokeFunction(result, "queryTypeDoubleIntParam");
        SQLDBUtils.assertNotError(returns[0]);
        Assert.assertTrue(returns[0] instanceof BMap);
        LinkedHashMap result = ((BMap) returns[0]).getMap();
        Assert.assertEquals(result.size(), 11);
        Assert.assertEquals(result.get("id"), new BInteger(2));
        Assert.assertEquals(((BFloat) result.get("float_type")).floatValue(), 1234.0);
    }

    @Test
    public void testQueryTypeDoubleDecimalParam() {
        BValue[] returns = BRunUtil.invokeFunction(result, "queryTypeDoubleDecimalParam");
        validateNumericTableResult(returns);
    }

    @Test
    public void testQueryTypeFloatDoubleParam() {
        BValue[] returns = BRunUtil.invokeFunction(result, "queryTypeFloatDoubleParam");
        validateNumericTableResult(returns);
    }

    @Test
    public void testQueryTypeRealDoubleParam() {
        BValue[] returns = BRunUtil.invokeFunction(result, "queryTypeRealDoubleParam");
        validateNumericTableResult(returns);
    }

    @Test
    public void testQueryTypeNumericDoubleParam() {
        BValue[] returns = BRunUtil.invokeFunction(result, "queryTypeNumericDoubleParam");
        validateNumericTableResult(returns);
    }

    @Test
    public void testQueryTypeNumericIntParam() {
        BValue[] returns = BRunUtil.invokeFunction(result, "queryTypeNumericIntParam");
        SQLDBUtils.assertNotError(returns[0]);
        Assert.assertTrue(returns[0] instanceof BMap);
        LinkedHashMap result = ((BMap) returns[0]).getMap();
        Assert.assertEquals(result.size(), 11);
        Assert.assertEquals(result.get("id"), new BInteger(2));
        Assert.assertEquals(((BFloat) result.get("float_type")).floatValue(), 1234.0);
    }

    @Test
    public void testQueryTypeNumericDecimalParam() {
        BValue[] returns = BRunUtil.invokeFunction(result, "queryTypeNumericDecimalParam");
        validateNumericTableResult(returns);
    }

    @Test
    public void testQueryTypeDecimalDoubleParam() {
        BValue[] returns = BRunUtil.invokeFunction(result, "queryTypeDecimalDoubleParam");
        validateNumericTableResult(returns);
    }

    @Test
    public void testQueryTypeDecimalDecimalParam() {
        BValue[] returns = BRunUtil.invokeFunction(result, "queryTypeDecimalDecimalParam");
        validateNumericTableResult(returns);
    }

    @Test
    public void testQueryTypeBinaryByteParam() {
        BValue[] returns = BRunUtil.invokeFunction(result, "queryTypeBinaryByteParam");
        validateComplexTableResult(returns);
    }

    @Test
    public void testQueryTypeBinaryReadableByteChannelParam() {
        BValue[] returns = BRunUtil.invokeFunction(result, "queryTypeBinaryReadableByteChannelParam");
        validateComplexTableResult(returns);
    }

    @Test
    public void testQueryTypeVarBinaryReadableByteChannelParam() {
        BValue[] returns = BRunUtil.invokeFunction(result, "queryTypeVarBinaryReadableByteChannelParam");
        validateComplexTableResult(returns);
    }

    @Test
    public void testQueryTypeTinyBlobByteParam() {
        BValue[] returns = BRunUtil.invokeFunction(result, "queryTypeTinyBlobByteParam");
        validateComplexTableResult(returns);
    }
    @Test
    public void testQueryTypeBlobByteParam() {
        BValue[] returns = BRunUtil.invokeFunction(result, "queryTypeBlobByteParam");
        validateComplexTableResult(returns);
    }
    @Test
    public void testQueryTypeMediumBlobByteParam() {
        BValue[] returns = BRunUtil.invokeFunction(result, "queryTypeMediumBlobByteParam");
        validateComplexTableResult(returns);
    }
    @Test
    public void testQueryTypeLongBlobByteParam() {
        BValue[] returns = BRunUtil.invokeFunction(result, "queryTypeLongBlobByteParam");
        validateComplexTableResult(returns);
    }

    @Test
    public void testQueryTypeBlobReadableByteChannelParam() {
        BValue[] returns = BRunUtil.invokeFunction(result, "queryTypeBlobReadableByteChannelParam");
        validateComplexTableResult(returns);
    }

    @Test
    public void testQueryTypeTinyTextStringParam() {
        BValue[] returns = BRunUtil.invokeFunction(result, "queryTypeTinyTextStringParam");
        validateComplexTableResult(returns);
    }

    @Test
    public void testQueryTypeTextStringParam() {
        BValue[] returns = BRunUtil.invokeFunction(result, "queryTypeTextStringParam");
        validateComplexTableResult(returns);
    }

    @Test
    public void testQueryTypeMediumTextStringParam() {
        BValue[] returns = BRunUtil.invokeFunction(result, "queryTypeMediumTextStringParam");
        validateComplexTableResult(returns);
    }

    @Test
    public void testQueryTypeLongTextStringParam() {
        BValue[] returns = BRunUtil.invokeFunction(result, "queryTypeLongTextStringParam");
        validateComplexTableResult(returns);
    }

    @Test
    public void testQueryTypeTextReadableCharChannelParam() {
        BValue[] returns = BRunUtil.invokeFunction(result, "queryTypeTextReadableCharChannelParam");
        validateComplexTableResult(returns);
    }

    @Test
    public void testQueryTypeNTextReadableCharChannelParam() {
        BValue[] returns = BRunUtil.invokeFunction(result, "queryTypeNTextReadableCharChannelParam");
        validateComplexTableResult(returns);
    }

    @Test
    public void testQueryDateStringParam() {
        BValue[] returns = BRunUtil.invokeFunction(result, "queryDateStringParam");
        validateDateTimeTypesTableResult(returns);
    }

    @Test
    public void testQueryDateString2Param() {
        BValue[] returns = BRunUtil.invokeFunction(result, "queryDateString2Param");
        validateDateTimeTypesTableResult(returns);
    }

    @Test
    public void testQueryTimeDateStringParam() {
        BValue[] returns = BRunUtil.invokeFunction(result, "queryTimeStringParam");
        validateDateTimeTypesTableResult(returns);
    }

    @Test
    public void testQueryTimeDateStringInvalidParam() {
        BValue[] returns = BRunUtil.invokeFunction(result, "queryTimeStringInvalidParam");
        Assert.assertEquals(returns[0].getType().getTag(), TypeTags.ERROR);
        Assert.assertTrue(((BError) returns[0]).getMessage().contains("IllegalArgumentException"));
    }

    @Test
    public void testQueryTimestampDateStringParam() {
        BValue[] returns = BRunUtil.invokeFunction(result, "queryTimestampStringParam");
        validateDateTimeTypesTableResult(returns);
    }

    @Test
    public void testQueryTimestampDateStringInvalidParam() {
        BValue[] returns = BRunUtil.invokeFunction(result, "queryTimestampStringInvalidParam");
        Assert.assertEquals(returns[0].getType().getTag(), TypeTags.ERROR);
        Assert.assertTrue(((BError) returns[0]).getMessage().contains(" Timestamp format must be yyyy-mm-dd hh:mm:ss"));
    }

    @Test
    public void testQueryEnumStringParam() {
        BValue[] returns = BRunUtil.invokeFunction(result, "queryEnumStringParam");
        validateEnumTable(returns);
    }

    @Test
    public void testQueryEnumStringParam2() {
        BValue[] returns = BRunUtil.invokeFunction(result, "queryEnumStringParam2");
        validateEnumTable(returns);
    }

    @Test
    public void testQuerySetStringParam() {
        BValue[] returns = BRunUtil.invokeFunction(result, "querySetStringParam");
        validateSetTable(returns);
    }

    @Test
    public void testQueryGeoParam() {
        BValue[] returns = BRunUtil.invokeFunction(result, "queryGeoParam");
        validateGeoTable(returns);
    }

    @Test
    public void testQueryGeoParam2() {
        BValue[] returns = BRunUtil.invokeFunction(result, "queryGeoParam2");
        validateGeoTable(returns);
    }

    @Test
    public void testQueryJsonParam() {
        BValue[] returns = BRunUtil.invokeFunction(result, "queryJsonParam");
        validateJsonTable(returns);
    }

    @Test
    public void testQueryJsonParam2() {
        BValue[] returns = BRunUtil.invokeFunction(result, "queryJsonParam2");
        validateJsonTable(returns);
    }

    @Test
    public void testQueryJsonParam3() {
        BValue[] returns = BRunUtil.invokeFunction(result, "queryJsonParam3");
        validateJsonTable(returns);
    }


    private void validateDataTableResult(BValue[] returns) {
        SQLDBUtils.assertNotError(returns[0]);
        Assert.assertTrue(returns[0] instanceof BMap);
        LinkedHashMap result = ((BMap) returns[0]).getMap();
        Assert.assertEquals(result.size(), 8);
        Assert.assertEquals(((BInteger) result.get("row_id")).intValue(), 1);
        Assert.assertEquals(((BInteger) result.get("int_type")).intValue(), 1);
        Assert.assertEquals(((BInteger) result.get("long_type")).intValue(), 9223372036854774807L);
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        Assert.assertEquals(decimalFormat.format(((BFloat) result.get("float_type")).floatValue()), "123.34");
        Assert.assertEquals(((BFloat) result.get("double_type")).floatValue(), 2139095039D);
        Assert.assertTrue(((BBoolean) result.get("boolean_type")).booleanValue());
        Assert.assertEquals(((BDecimal) result.get("decimal_type")).decimalValue().doubleValue(), 23.45);
        Assert.assertEquals(((BString) result.get("string_type")).stringValue(), "Hello");
    }

    private void validateComplexTableResult(BValue[] returns) {
        SQLDBUtils.assertNotError(returns[0]);
        Assert.assertTrue(returns[0] instanceof BMap);
        LinkedHashMap result = ((BMap) returns[0]).getMap();
        Assert.assertEquals(result.size(), 11);
        Assert.assertEquals(((BInteger) result.get("row_id")).intValue(), 1);
        Assert.assertEquals(result.get("text_type").toString(), "very long text");
    }

    private void validateDateTimeTypesTableResult(BValue[] returns) {
        SQLDBUtils.assertNotError(returns[0]);
        Assert.assertTrue(returns[0] instanceof BMap);
        LinkedHashMap result = ((BMap) returns[0]).getMap();
        Assert.assertEquals(result.size(), 5);
        Assert.assertEquals(((BInteger) result.get("row_id")).intValue(), 1);
        Assert.assertTrue(result.get("date_type").toString().startsWith("2017-02-03"));
    }

    private void validateNumericTableResult(BValue[] returns) {
        SQLDBUtils.assertNotError(returns[0]);
        Assert.assertTrue(returns[0] instanceof BMap);
        LinkedHashMap result = ((BMap) returns[0]).getMap();
        Assert.assertEquals(result.size(), 11);
        Assert.assertEquals(result.get("id"), new BInteger(1));
        Assert.assertEquals(result.get("int_type"), new BInteger(2147483647));
        Assert.assertEquals(result.get("bigint_type"), new BInteger(9223372036854774807L));
        Assert.assertEquals(result.get("smallint_type"), new BInteger(32767));
        Assert.assertEquals(result.get("mediumint_type"), new BInteger(8388607));
        Assert.assertEquals(result.get("tinyint_type"), new BInteger(127));
        Assert.assertEquals(result.get("bit_type"), new BBoolean(true));
        Assert.assertEquals(((BDecimal) result.get("decimal_type")).value().doubleValue(), 1234.567);
        Assert.assertEquals(((BDecimal) result.get("numeric_type")).value().doubleValue(), 1234.567);
        DecimalFormat df = new DecimalFormat("###.###");
        Assert.assertEquals(df.format(((BFloat) result.get("float_type")).floatValue()), "1234.57");
        Assert.assertEquals(df.format(((BFloat) result.get("real_type")).floatValue()), "1234.567");
    }

    private void validateEnumTable(BValue[] returns) {
        String id = "id";
        String enumType = "enum_type";
        SQLDBUtils.assertNotError(returns[0]);
        Assert.assertTrue(returns[0] instanceof BMap);
        LinkedHashMap result = ((BMap) returns[0]).getMap();
        Assert.assertEquals(result.size(), 2);
        Assert.assertEquals(((BInteger) result.get(id)).intValue(), 1);
        Assert.assertEquals(result.get(enumType).toString(), "doctor");
    }

    private void validateSetTable(BValue[] returns) {
        String id = "row_id";
        String setType = "set_type";
        SQLDBUtils.assertNotError(returns[0]);
        Assert.assertTrue(returns[0] instanceof BMap);
        LinkedHashMap result = ((BMap) returns[0]).getMap();
        Assert.assertEquals(result.size(), 2);
        Assert.assertEquals(((BInteger) result.get(id)).intValue(), 1);
        Assert.assertEquals(result.get(setType).toString(), "a,d");
    }

    private void validateGeoTable(BValue[] returns) {
        String id = "id";
        String type = "geomText";
        SQLDBUtils.assertNotError(returns[0]);
        Assert.assertTrue(returns[0] instanceof BMap);
        LinkedHashMap result = ((BMap) returns[0]).getMap();
        Assert.assertEquals(result.size(), 2);
        Assert.assertEquals(((BInteger) result.get(id)).intValue(), 1);
        Assert.assertEquals(result.get(type).toString(), "POINT(7 52)");
    }

    private void validateJsonTable(BValue[] returns) {
        String id = "id";
        String type = "json_type";
        SQLDBUtils.assertNotError(returns[0]);
        Assert.assertTrue(returns[0] instanceof BMap);
        LinkedHashMap result = ((BMap) returns[0]).getMap();
        Assert.assertEquals(result.size(), 2);
        Assert.assertEquals(((BInteger) result.get(id)).intValue(), 1);
        String jsonString = result.get(type).toString();
        Assert.assertTrue(jsonString.equalsIgnoreCase("{\"id\": 100, \"name\": \"Joe\", \"groups\": \"[2,5]\"}"));
    }

}
