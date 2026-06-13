package DOM;
import org.w3c.dom.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.*;//Pour créer l'objet Transformateur
public class TransformDoc{
	public static void main(String[] args) throws Exception{
		String xmlFile = args[0]; 
		Document document_src = CreateDomParser.parseur().parse(xmlFile);
		Document document_cib = transformer(document_src);
		//***************Impression du résultat************//
		DOMSource ds = new DOMSource(document_cib);
		StreamResult res = new StreamResult("cible.xml");
		TransformerFactory transform = TransformerFactory.newInstance();
		Transformer tr = transform.newTransformer();
		document_cib.setXmlStandalone(true);
		tr.transform(ds, res);
	}
		//*****Transformation : source.xml ==> cible.xml (résultat)*****//
	public static Document transformer (Document document_src) throws Exception{
		DOMImplementation domimp = CreateDomParser.i();
		Document document_cib =	domimp.createDocument(null,"Racine", null);
		Element rac_cib= document_cib.getDocumentElement();
		String contenu =  document_src.getElementsByTagName("Allumage").item(0).getFirstChild().getNodeValue();
		//Ou pour récupérer "Un peu cabossé" écrire :
		//String contenu = document_src.getElementsByTagName("Capot").item(0).getFirstChild().getNodeValue();
		Element texte = document_cib.createElement("texte");		
		rac_cib.appendChild(texte);
		texte.appendChild(document_cib.createTextNode(contenu));
		return document_cib;
	}
}
