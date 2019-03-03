/*
 * Copyright 2002-2018 the original author or authors.
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

package com.webauthn4j.validator;

import com.webauthn4j.converter.AuthenticatorDataConverter;
import com.webauthn4j.converter.CollectedClientDataConverter;
import com.webauthn4j.converter.util.CborConverter;
import com.webauthn4j.converter.util.JsonConverter;
import com.webauthn4j.response.attestation.authenticator.AuthenticatorData;
import com.webauthn4j.response.client.ClientDataType;
import com.webauthn4j.response.client.CollectedClientData;
import com.webauthn4j.response.extension.authenticator.AuthenticationExtensionAuthenticatorOutput;
import com.webauthn4j.response.extension.client.AuthenticationExtensionsClientOutputs;
import com.webauthn4j.response.extension.client.ExtensionClientOutput;
import com.webauthn4j.server.ServerProperty;
import com.webauthn4j.test.TestUtil;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class AuthenticationObjectTest {

    private JsonConverter jsonConverter = new JsonConverter();
    private CborConverter cborConverter = new CborConverter();

    @Test
    void test() {

        byte[] credentialId = new byte[32];
        CollectedClientData clientData = TestUtil.createClientData(ClientDataType.CREATE);
        byte[] clientDataBytes = new CollectedClientDataConverter(jsonConverter).convertToBytes(clientData);
        AuthenticatorData<AuthenticationExtensionAuthenticatorOutput> authenticatorData = TestUtil.createAuthenticatorData();
        byte[] authenticatorDataBytes = new AuthenticatorDataConverter(cborConverter).convert(authenticatorData);
        AuthenticationExtensionsClientOutputs<ExtensionClientOutput> clientExtensions = new AuthenticationExtensionsClientOutputs<>();
        ServerProperty serverProperty = TestUtil.createServerProperty();
        LocalDateTime timestamp = LocalDateTime.now();
        AuthenticationObject authenticationObject = new AuthenticationObject(
                credentialId,
                clientData,
                clientDataBytes,
                authenticatorData,
                authenticatorDataBytes,
                clientExtensions,
                serverProperty,
                timestamp
        );

        assertAll(
                () -> assertThat(authenticationObject.getCredentialId()).isEqualTo(credentialId),
                () -> assertThat(authenticationObject.getCollectedClientData()).isEqualTo(clientData),
                () -> assertThat(authenticationObject.getCollectedClientDataBytes()).isEqualTo(clientDataBytes),
                () -> assertThat(authenticationObject.getAuthenticatorData()).isEqualTo(authenticatorData),
                () -> assertThat(authenticationObject.getAuthenticatorDataBytes()).isEqualTo(authenticatorDataBytes),
                () -> assertThat(authenticationObject.getClientExtensions()).isEqualTo(clientExtensions),
                () -> assertThat(authenticationObject.getServerProperty()).isEqualTo(serverProperty),
                () -> assertThat(authenticationObject.getTimestamp()).isEqualTo(timestamp)
        );
    }

}