package cn.wu.mock.mybatis.builder.xml;

import cn.wu.mock.mybatis.io.Resources;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

public class XMLMapperEntityResolver implements EntityResolver {

    private static final String IBATIS_CONFIG_SYSTEM = "ibatis-3-config.dtd";
    private static final String IBATIS_MAPPER_SYSTEM = "ibatis-3-mapper.dtd";
    private static final String MYBATIS_CONFIG_SYSTEM = "mybatis-3-config.dtd";
    private static final String MYBATIS_MAPPER_SYSTEM = "mybatis-3-mapper.dtd";
    private static final String MYBATIS_CONFIG_DTD = "cn/wu/mock/mybatis/builder/xml/mybatis-3-config.dtd";
    private static final String MYBATIS_MAPPER_DTD = "cn/wu/mock/mybatis/builder/xml/mybatis-3-mapper.dtd";

    @Override
    public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
        //主要做的事情就是将xml约束文件默认需要联网读取改变成从本地读取约束文件，以保证不需要联网也可以开发
        try {
            if (systemId != null) {
                String lowerCaseSystemId = systemId.toLowerCase(Locale.ENGLISH);
                if (lowerCaseSystemId.contains(MYBATIS_CONFIG_SYSTEM) || lowerCaseSystemId.contains(IBATIS_CONFIG_SYSTEM)) {
                    return getInputSource(MYBATIS_CONFIG_DTD,publicId,systemId);
                } else if (lowerCaseSystemId.contains(MYBATIS_MAPPER_SYSTEM) || lowerCaseSystemId.contains(IBATIS_MAPPER_SYSTEM)) {
                    return getInputSource(MYBATIS_MAPPER_DTD,publicId,systemId);
                }
            }
            return null;
        } catch (Exception e) {
            throw new SAXException(e.toString());
        }
    }

    private InputSource getInputSource(String path, String publicId, String systemId) {
        InputSource source = null;
        if (path != null) {
            try {
                InputStream in = Resources.getResourceAsStream(path);
                source = new InputSource(in);
                source.setPublicId(publicId);
                source.setSystemId(systemId);
            } catch (IOException e) {
                //ignore, null is ok
            }
        }
        return source;
    }
}
