package rwbykit.meepwn.core.service;

import java.nio.ByteBuffer;

public interface DataConverter {

    /**
     * 数据读取
     * @param buffer
     * @return
     */
    public byte[] read(ByteBuffer buffer);

    /**
     * 数据写入
     * @param bytes
     * @return
     */
    public ByteBuffer write(byte[] bytes);

}
