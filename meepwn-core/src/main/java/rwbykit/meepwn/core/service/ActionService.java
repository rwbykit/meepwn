package rwbykit.meepwn.core.service;

import java.io.Serializable;

interface ActionService {

    public Serializable doExecute(Serializable inMessage) throws Exception;

}
