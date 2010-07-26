package cs236369.hw5.xslt;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Writer;
import java.net.URL;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.fileupload.util.Streams;
import org.w3c.dom.Document;

public class XsltTransformer {
	public static class TransformationError extends Exception{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;}
	
	public static void transform(Document doc, String url,Writer resultWriter) throws TransformationError
	{
		try{
		Document inDoc= doc;
		TransformerFactory tFactory= TransformerFactory.newInstance();
		File f = new File(url);
		StreamSource stream = new StreamSource(f);
		Transformer transofrmer= tFactory.newTransformer(stream);
	
		transofrmer.transform(new DOMSource(inDoc), new StreamResult(resultWriter));
		transofrmer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		transofrmer.setOutputProperty(OutputKeys.METHOD, "xml");
		transofrmer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new TransformationError();
			
		}
		
		
	}

}
