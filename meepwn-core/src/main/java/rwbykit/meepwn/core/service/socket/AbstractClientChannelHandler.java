package rwbykit.meepwn.core.service.socket;

import java.io.Serializable;

import rwbykit.meepwn.core.service.ActionMessage;
import rwbykit.meepwn.core.service.DataConverter;
import rwbykit.meepwn.core.service.MessageConverter;

public class AbstractClientChannelHandler extends AbstractChannelInboundHandler implements ActionMessage {

	public AbstractClientChannelHandler(DataConverter dataConverter, MessageConverter messageConverter) {
		super(dataConverter, messageConverter);
	}
	
	private boolean isWrite = false;
	
	private Serializable outMessage;
	
	private Serializable inMessage;
	
	private boolean isRead = false;
	
	public boolean isWrite() {
		return isWrite;
	}

	public void setWrite(boolean isWrite) {
		this.isWrite = isWrite;
	}

	@Override
	public Serializable getOutMessage() {
		return outMessage;
	}

	public void setOutMessage(Serializable outMessage) {
		this.outMessage = outMessage;
	}

	public Serializable getInMessage() {
		return inMessage;
	}

	@Override
	public void setInMessage(Serializable inMessage) {
		this.inMessage = inMessage;
	}

	public boolean isRead() {
		return isRead;
	}

	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}

}
