/**
 * Copyright 2016 StreamSets Inc.
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
package com.streamsets.lib.security.http;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.TimeUnit;

public class DisconnectedSessionHandler {
  private final Cache<String, SSOPrincipal> principalsCache;

  public DisconnectedSessionHandler() {
    principalsCache =
        CacheBuilder.newBuilder().expireAfterAccess(30, TimeUnit.SECONDS).expireAfterWrite(24, TimeUnit.HOURS).build();
  }

  public void add(SSOPrincipal principal) {
    principalsCache.put(principal.getTokenStr(), principal);
  }

  public SSOPrincipal get(String token) {
    return principalsCache.getIfPresent(token);
  }

  public void remove(String token) {
    principalsCache.invalidate(token);
  }

}
