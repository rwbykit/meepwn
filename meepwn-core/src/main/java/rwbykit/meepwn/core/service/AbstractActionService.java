package rwbykit.meepwn.core.service;

import java.io.Serializable;
import java.util.concurrent.Callable;

public abstract class AbstractActionService implements ActionService, ActionMessage, Callable<Serializable> {

    private Serializable inMessage;

    private Serializable outMessage;

    @Override
    public Serializable call() {
        try {
            this.outMessage = doExecute(this.inMessage);
            return this.outMessage;
        } catch (Exception e) {
            return this.handlerException(e);
        }
    }

    @Override
    public void setInMessage(Serializable inMessage) {
        this.inMessage = inMessage;
    }

    @Override
    public Serializable getOutMessage() {
        return this.outMessage;
    }

    public Serializable handlerException(Exception e) {
        return e.getMessage();
    }

}
