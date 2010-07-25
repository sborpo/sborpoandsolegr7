package cs236369.hw5.xslt;

import java.io.InputStream;
import java.io.Writer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;

public class XsltTransformer {
	public static class TransformationError extends Exception{}
	
	public static void transform(InputStream xmlInputStream, InputStream xsltInputStream,Writer resultWriter) throws TransformationError
	{
		try{
		DocumentBuilderFactory factory= DocumentBuilderFactory.newInstance();
		DocumentBuilder builder= factory.newDocumentBuilder();
		Document inDoc= builder.parse(xmlInputStream);
		TransformerFactory tFactory= TransformerFactory.newInstance();
		Transformer transofrmer= tFactory.newTransformer(new StreamSource(xsltInputStream));
		transofrmer.transform(new DOMSource(inDoc), new StreamResult(resultWriter));
		}
		catch (Exception e)
		{
			throw new TransformationError();
		}
		
		
	}

}
