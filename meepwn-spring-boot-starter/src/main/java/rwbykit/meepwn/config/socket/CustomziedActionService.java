package rwbykit.meepwn.config.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rwbykit.meepwn.core.service.AbstractActionService;

import java.io.Serializable;

public class CustomziedActionService extends AbstractActionService {

    private final static Logger logger = LoggerFactory.getLogger(CustomziedActionService.class);

    @Override
    public Serializable doExecute(Serializable inMessage) throws Exception {

        Thread.sleep(1000);
        logger.info(String.valueOf(inMessage));
        return inMessage;
    }
}
