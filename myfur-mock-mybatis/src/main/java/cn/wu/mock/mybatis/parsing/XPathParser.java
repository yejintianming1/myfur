package cn.wu.mock.mybatis.parsing;

import cn.wu.mock.mybatis.builder.xml.BuilderException;
import org.w3c.dom.Document;
import org.xml.sax.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import java.io.Reader;
import java.util.Properties;

public class XPathParser {

    private final Document document;
    private boolean validation;
    private EntityResolver entityResolver;
    private Properties variables;
    private XPath xpath;

    public XPathParser(Reader reader, boolean validation, Properties variables, EntityResolver entityResolver) {
        commonConstructor(validation,variables,entityResolver);
        this.document = createDocument(new InputSource(reader));
    }

    private Document createDocument(InputSource inputSource) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            factory.setValidating(validation);

            factory.setNamespaceAware(false);
            factory.setIgnoringComments(true);
            factory.setIgnoringElementContentWhitespace(false);
            factory.setExpandEntityReferences(true);

            DocumentBuilder builder = factory.newDocumentBuilder();
            builder.setEntityResolver(entityResolver);
            builder.setErrorHandler(new ErrorHandler() {
                @Override
                public void warning(SAXParseException exception) throws SAXException {
                }

                @Override
                public void error(SAXParseException exception) throws SAXException {
                    throw exception;
                }

                @Override
                public void fatalError(SAXParseException exception) throws SAXException {
                    throw exception;
                }
            });
            return builder.parse(inputSource);
        } catch (Exception e) {
            throw new BuilderException("Error creating document instance.  Cause: "+e,e);
        }
    }

    private void commonConstructor(boolean validation,Properties variables,EntityResolver entityResolver) {
        this.validation = validation;
        this.entityResolver = entityResolver;
        this.variables = variables;
        XPathFactory factory = XPathFactory.newInstance();
        this.xpath = factory.newXPath();
    }
}
