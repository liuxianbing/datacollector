/**
 * Copyright 2015 StreamSets Inc.
 *
 * Licensed under the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.streamsets.datacollector.runner;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.streamsets.datacollector.json.ObjectMapperFactory;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class TestRuntimeStats {

  @Test
  public void testSerialize() throws IOException {
    long startTime = System.currentTimeMillis();
    ByteArrayOutputStream bOut = new ByteArrayOutputStream();
    JsonGenerator jsonGenerator = new JsonFactory().createGenerator(bOut);
    RuntimeStats runtimeStats = new RuntimeStats();
    runtimeStats.setBatchCount(1);
    runtimeStats.setBatchStartTime(startTime);
    runtimeStats.setCurrentBatchAge(1);
    runtimeStats.setCurrentSourceOffset("1::0");
    runtimeStats.setTimeInCurrentStage(100);
    runtimeStats.setTimeOfLastReceivedRecord(startTime);

    runtimeStats.serialize(jsonGenerator);
    jsonGenerator.close();

    String ser = new String(bOut.toByteArray());
    ObjectMapper m = ObjectMapperFactory.get();
    RuntimeStats runtimeStats1 = m.readValue(ser, RuntimeStats.class);

    Assert.assertEquals(runtimeStats.getBatchCount(), runtimeStats1.getBatchCount());
    Assert.assertEquals(runtimeStats.getBatchStartTime(), runtimeStats1.getBatchStartTime());
    Assert.assertEquals(runtimeStats.getCurrentBatchAge(), runtimeStats1.getCurrentBatchAge());
    Assert.assertEquals(runtimeStats.getCurrentSourceOffset(), runtimeStats1.getCurrentSourceOffset());
    Assert.assertEquals(runtimeStats.getCurrentStage(), runtimeStats1.getCurrentStage());
    Assert.assertEquals(runtimeStats.getTimeInCurrentStage(), runtimeStats1.getTimeInCurrentStage());
    Assert.assertEquals(runtimeStats.getTimeOfLastReceivedRecord(), runtimeStats1.getTimeOfLastReceivedRecord());

  }

}
