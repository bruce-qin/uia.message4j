/*******************************************************************************
 * * Copyright (c) 2015, UIA
 * * All rights reserved.
 * * Redistribution and use in source and binary forms, with or without
 * * modification, are permitted provided that the following conditions are met:
 * *
 * * * Redistributions of source code must retain the above copyright
 * * notice, this list of conditions and the following disclaimer.
 * * * Redistributions in binary form must reproduce the above copyright
 * * notice, this list of conditions and the following disclaimer in the
 * * documentation and/or other materials provided with the distribution.
 * * * Neither the name of the {company name} nor the
 * * names of its contributors may be used to endorse or promote products
 * * derived from this software without specific prior written permission.
 * *
 * * THIS SOFTWARE IS PROVIDED BY THE REGENTS AND CONTRIBUTORS "AS IS" AND ANY
 * * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * * DISCLAIMED. IN NO EVENT SHALL THE REGENTS AND CONTRIBUTORS BE LIABLE FOR ANY
 * * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *******************************************************************************/
package uia.message;

import java.util.HashMap;
import java.util.List;

import uia.message.codec.BlockCodec;
import uia.message.codec.BlockCodecException;
import uia.message.fx.ValueFx;
import uia.message.model.xml.BitBlockListType;
import uia.message.model.xml.BitBlockRefType;
import uia.message.model.xml.BitBlockSeqListType;
import uia.message.model.xml.BitBlockSeqType;
import uia.message.model.xml.BitBlockType;
import uia.message.model.xml.BlockBaseType;
import uia.message.model.xml.MessageType;
import uia.message.model.xml.PropType;
import uia.utils.ByteUtils;
import uia.utils.PropertyUtils;

/**
 * Depend on the structure defines in XML to serialize a object.
 *
 * @author Kyle
 */
public class MessageSerializer {

    private final DataExFactory factory;

    private final MessageType mt;

    // private final ArrayList<Byte> result;

    private byte[] resultBytes;

    private int indexByte;

    private int indexBit;

    private final HashMap<String, Object> blockValues;

    MessageSerializer(DataExFactory factory, MessageType mt) {
        this.factory = factory;
        this.mt = mt;
        //this.result = new ArrayList<Byte>();
        this.blockValues = new HashMap<String, Object>();
        this.resultBytes = new byte[0];
    }

    /**
     * Serialize the object into byte array.
     *
     * @param obj The object need to be serialized.
     * @return Byte array.
     * @throws BlockCodecException throw when serialize fail.
     */
    public byte[] write(Object obj) throws BlockCodecException {
        this.indexByte = 0;
        this.indexBit = 0;
        //this.result.clear();
        this.blockValues.clear();

        BitBlockSeqType bodyType = this.mt.getBody();
        encode(bodyType.getName(), bodyType, obj);
        //byte[] data = new byte[this.result.size()];
        //for (int i = 0; i < this.result.size(); i++) {
        //    data[i] = this.result.get(i).byteValue();
        //}

        //this.result.clear();
        this.blockValues.clear();

        //return data;

        // be careful
        return this.resultBytes;
    }

    @SuppressWarnings("unchecked")
    private void encode(String seqName, BitBlockSeqType seqType, Object obj) throws BlockCodecException {
        this.factory.seqTouched(seqName, true, this.indexByte * 8 + this.indexBit);
        try {
            if (obj == null) {
                throw new BlockCodecException(seqName + "> encode failed. value is null");
            }

            for (BlockBaseType blockType : seqType.getBlockOrBlockListOrBlockSeq()) {
                String name = blockType.getName();

                // exists check
                if (!exists(name, blockType, obj)) {
                    continue;
                }

                if (blockType instanceof BitBlockRefType) {
                    String referenceName = ((BitBlockRefType) blockType).getReference();
                    blockType = this.factory.getReferenceBlock(referenceName);
                    if (blockType == null) {
                        throw new BlockCodecException(seqName + "> blockRef failed. \'" +
                                referenceName + "\' " + seqType.getClassName() + "." + name + " references is not defined.");
                    }
                }

                try {
                    if (blockType instanceof BitBlockSeqListType) {
                        Object value = PropertyUtils.read(obj, name);
                        encode(name, (BitBlockSeqListType) blockType, (List<Object>) value);
                    }
                    else if (blockType instanceof BitBlockListType) {
                        Object value = PropertyUtils.read(obj, name);
                        encode(name, (BitBlockListType) blockType, (List<Object>) value);
                    }
                    else if (blockType instanceof BitBlockSeqType) {
                        String cn = ((BitBlockSeqType) blockType).getClassName();
                        Object value = (cn != null && cn.length() > 0) ?
                                PropertyUtils.read(obj, name) :
                                obj;
                        if (value == null) {
                            throw new BlockCodecException(seqName + "> property failed. " +
                                    seqType.getName() + "." + name + " is null");
                        }
                        encode(name, (BitBlockSeqType) blockType, value);
                    }
                    else {
                        Object value = PropertyUtils.read(obj, name);
                        if (value == null) {
                            throw new BlockCodecException(seqName + "> property failed. " +
                                    seqType.getName() + "." + name + " is null");
                        }
                        encode(name, (BitBlockType) blockType, value);
                    }
                }
                catch (BlockCodecException ex1) {
                    throw ex1;
                }
                catch (Exception ex2) {
                    throw new BlockCodecException(seqName + "> encode failed. " +
                            seqType.getName() + "." + name + " ex:" + ex2.getMessage(),
                            ex2);
                }
            }
        }
        finally {
            this.factory.seqTouched(seqName, false, this.indexByte * 8 + this.indexBit);
        }
    }

    private void encode(String name, BitBlockType blockType, Object obj) throws BlockCodecException {
        this.blockValues.put(name, obj);

        int bitLength = 0;

        if (blockType.getSizeFx() != null && blockType.getSizeFx().trim().length() > 0) {
            ValueFx fx = this.factory.getFx(blockType.getSizeFx().trim());
            bitLength = fx.encode(name, obj, this.blockValues);
        }
        else if (blockType.getSizeBlock() != null && blockType.getSizeBlock().length() > 0) {
            String sizeBlock = blockType.getSizeBlock();
            try {
            	bitLength = SizeFx.calculate(sizeBlock, this.blockValues);
            }
            catch(Exception ex) {
                throw new BlockCodecException(name + "> codec sizeBlock parse failed. propType:" + name + " ex:" + ex.getMessage(), ex);
            }
        }
        else {
            bitLength = blockType.getSize();
        }

        bitLength = "bit".equalsIgnoreCase(blockType.getSizeUnit()) ? bitLength : bitLength * 8;

        @SuppressWarnings("rawtypes")
        BlockCodec codec = this.factory.getCodec(blockType.getDataType());
        codec.reset();
        if (blockType.getCodecPropSet() != null) {
            for (PropType prop : blockType.getCodecPropSet().getProp()) {
                try {
                    PropertyUtils.write(codec, prop.getName(), prop.getValue());
                }
                catch (Exception ex) {
                    throw new BlockCodecException(name + "> codec property failed. propType:" + prop.getName() + " ex:" + ex.getMessage(), ex);
                }
            }
        }

        byte[] bytes;
        try {
            @SuppressWarnings("unchecked")
            byte[] temp = codec.encode(obj, bitLength);
            if (bitLength >= 0) {
                bytes = ByteUtils.offsetBits(
                        temp,
                        this.indexBit,
                        bitLength);
            }
            else {
                bytes = temp;
            }
            put(bytes);
            this.factory.valueHandled(name, new BlockInfo(obj, temp, bitLength));
        }
        catch (Exception ex) {
            throw new BlockCodecException(name + "> encode failed(" + blockType.getName() + "). ex:" + ex.getMessage(),
                    ex);
        }

        // note: last block with dynamic size.
        if (bitLength < 0) {
            bitLength = bytes.length * 8 - this.indexBit;
        }

        int byteCount = bitLength / 8;
        int bitCount = bitLength % 8;

        this.indexByte += byteCount;
        this.indexBit += bitCount;
        if (this.indexBit >= 8) {
            this.indexByte++;
            this.indexBit -= 8;
        }
    }

    private void encode(String listName, BitBlockListType listType, List<Object> objs) throws BlockCodecException {
        this.factory.listTouched(listName, true, this.indexByte * 8 + this.indexBit);

        // TODO: fix if objs.size() is different from countBlock

        if (objs != null) {
            // body
            for (int i = 0; i < objs.size(); i++) {
                encode(listName, listType, objs.get(i));
            }
        }
        this.factory.listTouched(listName, false, this.indexByte * 8 + this.indexBit);
    }

    private void encode(String listName, BitBlockSeqListType listType, List<Object> objs) throws BlockCodecException {
        this.factory.listTouched(listName, true, this.indexByte * 8 + this.indexBit);

        if (objs != null) {
            // body
            for (int i = 0; i < objs.size(); i++) {
                encode(listName, listType, objs.get(i));
            }
        }
        this.factory.listTouched(listName, false, this.indexByte * 8 + this.indexBit);
    }

    private void put(byte[] data) {
        // be careful
        byte[] value = new byte[this.indexByte + data.length];
        System.arraycopy(this.resultBytes, 0, value, 0, this.resultBytes.length);
        for (int i = 0; i < data.length; i++) {
            byte one = value[this.indexByte + i];
            value[this.indexByte + i] = (byte) (one + data[i]);
        }
        this.resultBytes = value;

        /** fix: 2015-03-12
        while (this.result.size() < this.indexByte + data.length) {
            this.result.add((byte) 0);
        }

        for (int i = 0; i < data.length; i++) {
            byte value = this.result.get(this.indexByte + i).byteValue();
            this.result.remove(this.indexByte + i);
            this.result.add(this.indexByte + i, (byte) (value + data[i]));
        }
         */
    }

    private boolean exists(String blockName, BlockBaseType block, Object obj) throws BlockCodecException {
        if (block.getOptionBlock() != null && block.getOptionBlock().length() > 0) {
            try {
                Object option = PropertyUtils.read(obj, block.getOptionBlock());
                String v = option.getClass() == byte[].class ? ByteUtils.toHexString((byte[]) option, "") : option.toString();
                return block.isOptionEq() ?
                        block.getOptionValue().equals(v) : !block.getOptionValue().equals(v);
            }
            catch (Exception ex) {
                throw new BlockCodecException(blockName + "> existsProp failed. ex:" + ex.getMessage(), ex);
            }
        }
        else {
            return true;
        }
    }
}
