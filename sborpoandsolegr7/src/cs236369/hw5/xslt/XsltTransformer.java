package cs236369.hw5.xslt;

import java.io.InputStream;
import java.io.Writer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;

public class XsltTransformer {
	public static class TransformationError extends Exception{}
	
	public static void transform(Document doc, InputStream xsltInputStream,Writer resultWriter) throws TransformationError
	{
		try{
		Document inDoc= doc;
		TransformerFactory tFactory= TransformerFactory.newInstance();
		Transformer transofrmer= tFactory.newTransformer(new StreamSource(xsltInputStream));
		
		transofrmer.transform(new DOMSource(inDoc), new StreamResult(resultWriter));
		transofrmer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		transofrmer.setOutputProperty(OutputKeys.METHOD, "xml");
		transofrmer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
		}
		catch (Exception e)
		{
			throw new TransformationError();
		}
		
		
	}

}
