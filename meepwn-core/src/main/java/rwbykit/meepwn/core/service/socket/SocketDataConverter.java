package rwbykit.meepwn.core.service.socket;

import rwbykit.meepwn.core.service.DataConverter;

import java.nio.ByteBuffer;

public class SocketDataConverter implements DataConverter {

    private int headBytesLength;

    private boolean lengthContainsHeadBytes = false;

    public SocketDataConverter() {
        this(4, false);
    }


    public SocketDataConverter(int headBytesLength, boolean lengthContainsHeadBytes) {
        this.headBytesLength = headBytesLength;
        this.lengthContainsHeadBytes = lengthContainsHeadBytes;
    }

    @Override
    public byte[] read(ByteBuffer buffer) {

        int actLength = 0;

        int bufferLen = buffer.position();

        if (bufferLen < headBytesLength) {
            return null;
        }
        buffer.flip();
        byte[] headBytes = new byte[headBytesLength];
        buffer.get(headBytes);
        actLength = BitUtils.getInt(headBytes);
        if (lengthContainsHeadBytes) {
            actLength = actLength - headBytesLength;
        }

        if (bufferLen < actLength || actLength > buffer.remaining()) {
            return null;
        }

        byte[] bytes = new byte[actLength];
        buffer.get(bytes);
        return bytes;

    }

    @Override
    public ByteBuffer write(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(headBytesLength + bytes.length);
        byte[] headBytes = new byte[headBytesLength];
        BitUtils.putInt(headBytes, bytes.length);
        byteBuffer.put(headBytes);
        byteBuffer.put(bytes);
        return byteBuffer;
    }

    public int getHeadBytesLength() {
        return headBytesLength;
    }

    public void setHeadBytesLength(int headBytesLength) {
        this.headBytesLength = headBytesLength;
    }

    public boolean isLengthContainsHeadBytes() {
        return lengthContainsHeadBytes;
    }

    public void setLengthContainsHeadBytes(boolean lengthContainsHeadBytes) {
        this.lengthContainsHeadBytes = lengthContainsHeadBytes;
    }

}
