package rwbykit.meepwn.core.service;

import java.io.Serializable;

public interface MessageConverter<T> {

    /**
     * 打包
     * @param serializable
     * @return
     * @throws RuntimeException
     */
    public T pack(Serializable serializable) throws RuntimeException;

    /**
     * 解包
     * @param object
     * @return
     * @throws RuntimeException
     */
    public Serializable unpack(T object) throws RuntimeException;

}
