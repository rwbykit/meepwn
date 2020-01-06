package rwbykit.meepwn.core.service;

import java.io.Serializable;

public interface ActionMessage {

    /**
     * 设置接收的信息
     * @param inMessage
     */
    public void setInMessage(Serializable inMessage);

    /**
     * 获得发送的信息
     * @return
     */
    public Serializable getOutMessage();

}
