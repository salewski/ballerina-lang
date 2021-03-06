/*
 * Copyright (c) 2019, WSO2 Inc. (http://wso2.com) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.ballerinalang.docgen.generator.model;

import com.google.gson.annotations.Expose;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Represent documentation for an Object.
 */
public class Object extends Construct {

    @Expose
    public List<DefaultableVariable> fields;
    @Expose
    public List<Function> methods;
    @Expose
    public Function initMethod;
    @Expose
    public List<Function> otherMethods;
    @Expose
    public boolean isAnonymous;

    public Object(String name, String description, boolean isDeprecated, List<DefaultableVariable> fields,
            List<Function> methods, boolean isAnonymous) {
        super(name, description, isDeprecated);
        this.fields = fields;
        this.methods = methods;
        Optional<Function> initMethod = getInitMethod(methods);
        this.initMethod = initMethod.isPresent() ? getInitMethod(methods).get() : null;
        this.otherMethods = getOtherMethods(methods);
        this.isAnonymous = isAnonymous;
    }

    public Optional<Function> getInitMethod(List<Function> methods) {
        return methods.stream()
                .filter(function -> function.name.equals("init"))
                .findFirst();
    }

    public List<Function> getOtherMethods(List<Function> methods) {
        return methods.stream()
                .filter(function -> !function.name.equals("init"))
                .collect(Collectors.toList());
    }

}
