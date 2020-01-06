package rwbykit.meepwn.core.service;

import java.io.Serializable;

public interface MessageConverter {

    /**
     * 打包
     * @param serializable
     * @return
     * @throws RuntimeException
     */
    public Object pack(Serializable serializable) throws RuntimeException;

    /**
     * 解包
     * @param object
     * @return
     * @throws RuntimeException
     */
    public Serializable unpack(Object object) throws RuntimeException;

}
