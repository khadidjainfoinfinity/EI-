package DOM;
import javax.xml.parsers.*;
import org.w3c.dom.DOMImplementation;
public class CreateDomParser{
	static DocumentBuilder p;
	public static DocumentBuilder parseur() throws ParserConfigurationException {
		p = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		return p;
	}
 	public static DOMImplementation i() throws ParserConfigurationException {
		DOMImplementation imp =	p.getDOMImplementation();
		return imp;
	}
}
