/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.apache.shindig.gadgets;

/**
 * Defines where the gadget is being rendered.
 */
public enum RenderingContext {
  // Used when rendering gadgets of type=html|inline. gadgets.config.init is not
  // injected into the gadget render, and container mediated.
  // TODO: rename this to "RENDER_GADGET"?
  GADGET("gadget", "0", false),

  // Used when rendering gadgets of type=url. Unlike RenderingContext.GADGET,
  // this special context is explicitly requested by the gadget (to include
  // gadgets.config.init), while still considered a gadget render.
  CONFIGURED_GADGET("gadget", "2", true),

  // Used when rendering container data (not a gadget render)
  CONTAINER("container", "1", true),

  // Used when retrieving metadata about a gadget. Processing is generally
  // identical to processing under GADGET, but some operations may be safely
  // skipped, such as preload processing.
  METADATA(null, null, null);

  private final String featureBundleTag;
  private final String paramValue;
  private final Boolean configurable;

  private RenderingContext(String featureXmlTag, String paramValue, Boolean configurable) {
    this.featureBundleTag = featureXmlTag;
    this.paramValue = paramValue;
    this.configurable = configurable;
  }

  public String getFeatureBundleTag() {
    return featureBundleTag;
  }

  public boolean isConfigurable() {
    return configurable;
  }

  public String getParamValue() {
    return paramValue;
  }

  public static RenderingContext valueOfParam(String param) {
    // Exception: when no &c= parameter provided, default to GADGET.
    if (param == null) {
      return RenderingContext.GADGET;
    }
    for (RenderingContext rc : RenderingContext.values()) {
      String rcParam = rc.getParamValue();
      if (rcParam != null && rcParam.equals(param)) {
        return rc;
      }
    }
    return null;
  }
}
