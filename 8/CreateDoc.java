package DOM;
//----------------------------------------------
import javax.xml.parsers.*;
//----------------------------------------------
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
//import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
//----------------------------------------------
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.OutputKeys;

public class CreateDoc{
	public static void main(String[] args) throws Exception{	
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();//Construction d'un parseur.
		DocumentBuilder parseur = factory.newDocumentBuilder();//Construction d'un parseur.
		//Syntaxe abrégée des deux précédentes lignes de code pour la création du parseur.
		//DocumentBuilder parseur = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		//La méthode "getDOMImplementation()" retourne une instance de "DOMImplementation".
		//Cette étape est nécessaire, afin de créer par la suite un objet Document DOM.
		DOMImplementation domimp = parseur.getDOMImplementation();
//*****************************************************************************************************//		
		//Création d'un nouvel objet Document DOM, qui contiendra l'élément racine "Racine".
		//Nous pouvons placer un namespace sur l'élément racine (ici la valeur du premier argument est "null").
		//Nous pouvons aussi placer un objet DocumentType qui est la DTD associée au document XML (ici la valeur du
		//troisième argument est "null").
		//DocumentType dtd = domimp.createDocumentType("Racine",null,"exemple-dom.dtd");
		//Cette méthode permet de créer un objet Document DOM.
		Document doc = domimp.createDocument(null,"Racine",null);		
//*****************************************************************************************************//		
		//La méthode setXmlStandalone() permet de supprimer 
		//l'attribut "standalone" (DTD).
		doc.setXmlStandalone(true);
//*****************************************************************************************************//		
		//Récupération du nœoeud d'élément racine "Racine".
		Element rac = doc.getDocumentElement();
		String EN="EN:", FR= "FR:";
		//Création du premier nœoeud  d'attribut (espace de nom spécifique - avec préfixe).
		rac.setAttribute("xmlns:FR", "http://example.net/french");
		//Création du deuxième nœoeud  d'attribut (espace de nom spécifique - avec préfixe).
		rac.setAttribute("xmlns:EN", "http://example.net/english");
//*****************************************************************************************************//		
		//Création d'un premier nœoeud d'élément.
		Element prem_ele_FR = doc.createElement(FR+"langue");
		//Ajout de ce premier nœoeud d'élément au nœud d'élément racine.
		rac.appendChild(prem_ele_FR);
		//Création d'un deuxième nœoeud d'élément.
		Element prem_ele_EN = doc.createElement(EN+"langue");		
		//Ajout de ce deuxième nœoeud d'élément au nœoeud d'élément racine.
		rac.appendChild(prem_ele_EN);
//*****************************************************************************************************//		
		//Création d'un premier nœoeud de texte et ajout de ce noeud au premier élément "langue".
		prem_ele_FR.appendChild((doc.createTextNode("je suis le contenu du premier élément")));
		//Création d'un deuxième nœoeud de texte et ajout de ce noeud au deuxième élément "langue".
		prem_ele_EN.appendChild((doc.createTextNode("I am the content of the second element")));
//*****************************************************************************************************//
		//Création de l'objet source, qui est instance 
		//de la classe "javax.xml.transform.dom.DOMSource".
		DOMSource ds = new DOMSource(doc);
		//Création de l'objet résultat, qui est une instance
		//de la classe "javax.xml.transform.stream.StreamResult".
		StreamResult res = new StreamResult("create.xml"); 
		//Nous pouvons utiliser la ligne de code ci-dessous
		//pour afficher le résultat sur la sortie standard 
		//StreamResult res = new StreamResult(System.out); 
//*****************************************************************************************************//	
		//La sortie sur fichier est vue par JAXP comme un cas particulier de transformation.
		
		//Obtention d'une instance de "TransformerFactory".
		TransformerFactory transform = TransformerFactory.newInstance();
		//Création du transformateur "tr".
		Transformer tr = transform.newTransformer();
		//Syntaxe abrégée des deux précédentes lignes de code.
		//Transformer tr = TransformerFactory.newInstance().newTransformer();
//*****************************************************************************************************//		
		//Spécification de l'encodage.
		tr.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
		//Indentation automatique du code source XML.
		tr.setOutputProperty(OutputKeys.INDENT, "yes");
		//Paramétrer l'indentation
		tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
		//Ligne de création du lien vers la DTD.
		tr.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "exemple-dom.dtd");
		//Pour récupérer le FPI (DTD de type PUBLIC)
		//tr.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, dtd.getPublicId());
		//Pour récupérer l'URI de la DTD
	    //tr.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, dtd.getSystemId());	
		//Transformation du document source (source XML) en un résultat (fichier XML).		
		tr.transform(ds, res);
	}
}