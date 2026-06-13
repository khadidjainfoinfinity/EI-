package DOM;
import org.w3c.dom.Attr;
import org.w3c.dom.NamedNodeMap;
//import org.w3c.dom.Node;
import org.w3c.dom.Element; 
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
//---------------------------------------------
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
public class Read{
	public static void main( String [] args ) throws Exception{
		//Récupération de l'argument représentant le document à parser (analyser)
		//String xmlFile = "C:/Users/asus/Desktop/read.xml";
		String xmlFile= args[0];
		//Création d'une instance de la classe « DocumentBuilderFactory » (ici « factory »). La classe « DocumentBuilderFactory » définit une API factory,  permettant d'obtenir un parseur, qui produit un arbre d'objets DOM, à partir du document XML
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		//Création d'une instance de la classe « DocumentBuilder » (ici « parseur »), permettant d'obtenir, par la suite, un objet « Document » à partir du document XML. Cet objet est une instance d’une classe implémentant l’interface Document
		DocumentBuilder parseur = factory.newDocumentBuilder();
		//La méthode parse() retourne un objet « Document » (ou objet Document DOM), en appliquant la méthode "parse()" sur notre "parseur". Cette méthode permet d'analyser le contenu de notre document XML. 
		Document document = parseur.parse(xmlFile);
		//Récupération du noeud d'élément racine ou l'élément racine (en anglais : document element) du document XML « read.xml ».
		Element liste = document.getDocumentElement();
		System.out.println("Élément racine => "+liste.getNodeName());
		//****************************Méthode 1 : getElementsByTagName()****************************************************//
		//Récupération des noeuds d'élément <eleve>. Ces noeuds sont placés dans une collection de noeuds de type « NodeList ». 
		//Ces noeuds peuvent être récupérés via leur indice à l'aide de la méthode "item(indice)".
		NodeList eleves = liste.getElementsByTagName("eleve");
		for (int i = 0;i<eleves.getLength();i++){
			//Récupération du noeud de la collection dont l'indice est passé en paramètre à la méthode "item()".
			//Ensuite, récupération du nom de ce noeud XML.			
			System.out.println("Nom du noeud XML => "+eleves.item(i).getNodeName());
			/* ou
			Node un_eleve = eleves.item(i);
			System.out.println("Noeud XML => "+un_eleve.getNodeName());
			 */
			//Retourne le nom du noeud du premier fils de l'indice de l'item de la collection.
			System.out.println("Nom du noeud du premier fils d'eleve => "+eleves.item(i).getFirstChild().getNodeName());
			//Retourne la valeur du noeud du premier fils de l'indice de l'item de la collection. 
			System.out.println("Valeur du premier fils d'eleve => "+eleves.item(i).getFirstChild().getNodeValue());
			//Il est possible aussi d'utiliser la méthode getTextContent(), qui retourne le texte du noeud et celui 
			//de ses descendants.
			//System.out.println("Valeur du premier fils d'eleve => "+eleves.item(i).getTextContent());
			//*****************************Lecture d'un attribut - getAttribute()************************************************//
			//Coercion (conversion forcée -cast-, car on ne peut pas récupérer la valeur de l'attribut à partir d'une "NodeList".
			//On convertit donc notre "Node" en "Element".
			Element l_eleve = (Element) eleves.item(i);
			//On affiche l'attribut en utilisant la méthode getAttribute(), en précisant l'attribut à afficher.
			System.out.println("Nom de l'étudiant => "+ l_eleve.getAttribute("nom"));//cette méthode ne s'applique pas sur une NodeList (voir la signature de cette méthode)
			//***************Lecture d'un attribut - getAttributes()*******************************************//
			//On place les attributs dans un objet d'une classe implémentant l'interface NamedNodeMap.
			NamedNodeMap atts = eleves.item(i).getAttributes(); 
			//Ou NamedNodeMap atts = eleves.item(i).getAttributes(); 
			for( int e = 0; e < atts.getLength(); e++ ){
				//Cast (Node ==> Attr)
				Attr attr =  (Attr) atts.item(e);
				//String nom = atts.item(e).getNodeName();
				//String val = atts.item(e).getNodeValue();
				String nom = attr.getName();
				String val = attr.getValue();
				System.out.println(nom);
				System.out.println(val);
			}
		}
		//****************************Méthode 2 : getChildNodes()**********************************************//
				NodeList eleves_bis =  liste.getChildNodes();
				for (int o = 0;o<eleves_bis.getLength();o++){
					//On affiche le nom de tous les noeuds fils de l'élément racine <liste>
					System.out.println("Nom du noeud "+eleves_bis.item(o).getNodeName());
					//Problème d'objets textes (#text) parasites si on active la ligne ci-dessous :
					//System.out.println("Année-Section "+eleves_bis.item(o).getFirstChild().getNodeValue());
					//On exploite la méthode Filtrer() de notre classe « Filtrer_parasites »
					//à partir de « Read.java » (classe « Read »).
					Filtrer_parasites a = new Filtrer_parasites();
 					if(a.Filtrer(eleves_bis.item(o)))
						System.out.println("Année-Section "+eleves_bis.item(o).getFirstChild().getNodeValue());
				}
	}
	/*slide 36
	public boolean Filtrer (Node noeud){		
		return (noeud.getNodeName() != "#text");
	}
	*/	
	/*slide 37
	public static boolean Filtrer (Node noeud){		
		return (noeud.getNodeName() != "#text");
	}
	*/
}





