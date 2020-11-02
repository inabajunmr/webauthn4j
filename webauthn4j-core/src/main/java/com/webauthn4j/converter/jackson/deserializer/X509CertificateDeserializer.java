/*
 * Copyright 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.webauthn4j.converter.jackson.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.webauthn4j.util.CertificateUtil;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.io.IOException;
import java.security.cert.X509Certificate;

/**
 * Jackson Deserializer for {@link X509Certificate}
 */
public class X509CertificateDeserializer extends StdDeserializer<X509Certificate> {


    public X509CertificateDeserializer() {
        super(X509Certificate.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @Nullable X509Certificate deserialize(@NonNull JsonParser p, @NonNull DeserializationContext ctxt) throws IOException {
        byte[] value = p.getBinaryValue();
        if (value.length == 0) {
            return null;
        }
        return CertificateUtil.generateX509Certificate(value);
    }
}
