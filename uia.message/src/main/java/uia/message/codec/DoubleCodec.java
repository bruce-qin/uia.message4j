/*******************************************************************************
 * Copyright 2017 UIA
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package uia.message.codec;

import java.nio.ByteBuffer;

/**
 * Convert data between double and byte array. <br>
 *
 * @author Kyle
 */
public class DoubleCodec implements BlockCodec<Double> {

    @Override
    public Double zeroValue() {
        return 0.0d;
    }

    @Override
    public Double decode(byte[] data, int bitLength) throws BlockCodecException {
        try {
            return Double.valueOf(ByteBuffer.wrap(data).getDouble());
        }
        catch (Exception ex) {
            throw new BlockCodecException("double codec failure. " + ex.getMessage(), ex);
        }
    }

    @Override
    public byte[] encode(Double data, int bitLength) throws BlockCodecException {
        try {
            double value = data.doubleValue();
            return ByteBuffer.allocate(8).putDouble(value).array();
        }
        catch (Exception ex) {
            throw new BlockCodecException("double encode failure. " + ex.getMessage(), ex);
        }
    }

    @Override
    public void reset() {
    }

    @Override
    public String getValueType() {
        return "double";
    }
}
