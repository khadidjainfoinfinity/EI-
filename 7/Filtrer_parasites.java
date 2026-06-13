package DOM;
import org.w3c.dom.Node;
/**
Cette classe permet de filtrer les noeuds parasites 
de type "#text" rencontrés dans un document XML. 
*/
public class Filtrer_parasites{
	public boolean Filtrer (Node noeud){		
		return (noeud.getNodeName() != "#text");
	}
}

