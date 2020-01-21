package rwbykit.meepwn.core.service.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rwbykit.meepwn.core.service.MessageConverter;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SocketMessageConverter implements MessageConverter {

    private final static Logger logger = LoggerFactory.getLogger(SocketMessageConverter.class);
    final static Pattern pattern = Pattern.compile("\n*|\t|\r");

    private String charset;

    public SocketMessageConverter() {
        this(StandardCharsets.UTF_8.name());
    }

    public SocketMessageConverter(String charset) {
        this.charset = charset;
    }

    @Override
    public Object pack(Serializable serializable) throws RuntimeException {
        try {
            return ((String) serializable).getBytes(charset);
        } catch (Exception e) {
            logger.debug("打包出现异常!", e);
        }
        return "";
    }

    @Override
    public Serializable unpack(Object object) throws RuntimeException {
        byte[] b = (byte[]) object;
        try {
            String str = new String(b, charset);
            logger.info("解包, 接受报文信息:"+ str);
            str = str.replaceAll("  ", "");
            Matcher m = pattern.matcher(str);
            return m.replaceAll("");
        } catch (UnsupportedEncodingException e) {
            logger.debug("解包出现异常!", e);
        }
        return "";
    }


    public String getCharset() {
        return charset;
    }


    public void setCharset(String charset) {
        this.charset = charset;
    }

}
