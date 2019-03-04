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

package com.webauthn4j.response.attestation.statement;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.webauthn4j.util.UnsignedNumberUtil;

public enum TPMEccCurve {
    TPM_ECC_NONE(0x0000),
    TPM_ECC_NIST_P192(0x0001),
    TPM_ECC_NIST_P224(0x0002),
    TPM_ECC_NIST_P256(0x0003),
    TPM_ECC_NIST_P384(0x0004),
    TPM_ECC_NIST_P521(0x0005),
    TPM_ECC_BN_P256(0x0010),
    TPM_ECC_BN_P638(0x0011),
    TPM_ECC_SM2_P256(0x0020);

    private final int value;

    TPMEccCurve(int value) {
        this.value = value;
    }

    public byte[] getBytes() {
        return UnsignedNumberUtil.toBytes(getValue());
    }

    public static TPMEccCurve create(int value) {
        switch (value) {
            case 0x0000:
                return TPM_ECC_NONE;
            case 0x0001:
                return TPM_ECC_NIST_P192;
            case 0x0002:
                return TPM_ECC_NIST_P224;
            case 0x0003:
                return TPM_ECC_NIST_P256;
            case 0x0004:
                return TPM_ECC_NIST_P384;
            case 0x0005:
                return TPM_ECC_NIST_P521;
            case 0x0010:
                return TPM_ECC_BN_P256;
            case 0x0011:
                return TPM_ECC_BN_P638;
            case 0x0020:
                return TPM_ECC_SM2_P256;
            default:
                throw new IllegalArgumentException("value '" + value + "' is out of range");
        }
    }

    @JsonCreator
    private static TPMEccCurve fromJson(int value) throws InvalidFormatException {
        try{
            return create(value);
        }
        catch (IllegalArgumentException e){
            throw new InvalidFormatException(null, "value is out of range", value, TPMEccCurve.class);
        }
    }

    @JsonValue
    public int getValue() {
        return value;
    }

}
