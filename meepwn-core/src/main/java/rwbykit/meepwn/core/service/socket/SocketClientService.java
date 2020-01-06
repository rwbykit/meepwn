package rwbykit.meepwn.core.service.socket;

import rwbykit.meepwn.core.service.ProtocolService;

import java.io.Serializable;

public interface SocketClientService extends ProtocolService {

    public Serializable sendAndReceive(Serializable sendMessage) throws Exception;

}
