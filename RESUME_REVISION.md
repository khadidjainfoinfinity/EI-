# RÉSUMÉ DE RÉVISION — Documents Structurés (XML / DTD / XPath / XQuery / DOM)

> Tout ce qu'il faut savoir pour l'examen, avec exemples. Lis dans l'ordre.

---

# CHAPITRE 1 & 2 — XML : bases, syntaxe, nœuds

## 1. Types de documents
- **Non structuré** ("plat") : suite de caractères, aucune balise. Ex : texte brut.
- **Structuré** : éléments de structure complets. Ex : XML `<xxx>donnée</xxx>` = **élément**.
- **Semi-structuré** : XML est souvent qualifié ainsi (structure par balises mais pas la rigidité d'une BD relationnelle). Lexico3 = balises ouvrantes seulement → semi-structuré.
- CSV / TSV : structurés par virgules / tabulations.

## 2. XML
- **X**ML = e**X**tensible **M**arkup **L**anguage. C'est un **métalangage** à balises pour structurer de l'info textuelle, transférée sur le web.
- Balises = « métadonnées-objets ».
- **élément** = balises + données encapsulées.
- Versions : **XML 1.0** (W3C, 1998, la plus répandue) ; **XML 1.1** (2004, support Unicode).
- On trouve XML dans : Word (.docx), JavaFX (.fxml), Android (layout), RDF/OWL, Code::Blocks…

## 3. Règles de « document bien formé » (well-formed) — TRÈS IMPORTANT
1. **Une seule balise racine** (élément racine obligatoire, non répétable).
2. **Toute balise ouverte doit être fermée** : `<li>…</li>`.
3. **Balises bien imbriquées** : `<p><e>…</e></p>` ✅ et **PAS** `<p><e>…</p></e>` ❌.
4. **Sensible à la casse** : `<Document>` ≠ `<document>`. (Écrire en minuscules par habitude.)
5. **Attributs : valeur obligatoire + guillemets** : `<date anniversaire="071185">` (pas `anniversaire` seul, pas sans guillemets).
6. **Balises vides** auto-fermantes avec `/` : `<personne/>`, `<adresse />`.
7. **Commentaire** : `<!-- ... -->`.
8. **Caractères spéciaux échappés** :
   - `&` → `&amp;` (nommée) ou `&#38;` (numérique, code ASCII décimal)
   - `<` → `&lt;` ou `&#60;`

Si une règle est violée → **document mal formé** (not well-formed) → erreur d'analyse.

## 4. Les 5 types de nœuds
- Nœud **document** (nœud racine `/`) — représente TOUT le document
- Nœud **élément**
- Nœud **texte** (`#text`)
- Nœud **attribut**
- Nœud **commentaire**

> ⚠️ **Nœud ≠ élément** : un attribut ou un texte est un *nœud* mais pas un *élément*.

## 5. Structure générale d'un document XML
```xml
<?xml version="1.0" encoding="utf-8"?>          <!-- 1. Déclaration XML : facultative, non répétable, en 1er -->
<?xml-stylesheet type="text/css" href="feuille.css"?>  <!-- 2. Instruction de traitement : facultative -->
<!DOCTYPE itineraire SYSTEM "usthb.dtd">        <!-- 3. DTD : facultative -->
<!-- commentaire -->
<itineraire>                                     <!-- 4. Élément racine : OBLIGATOIRE, non répétable -->
   <etape distance="0km">départ</etape>
</itineraire>
```
- **Déclaration XML** : `version` puis `encoding` (dans cet ordre). UTF-8/UTF-16 = défaut → `encoding` inutile dans ce cas.
- **Commentaire** autorisé partout SAUF avant la déclaration XML.

## 6. DTD : déclaration (URI = URL ou URN)
- **URI** = Uniform Resource **I**dentifier (terme général). Se décline en :
  - **URL** (Locator) = comment y accéder (fichier local, http, ftp)
  - **URN** (Name) = un nom indépendant de l'accès. Ex : `urn:isbn:978-2-7117-2077-4`
- `<!DOCTYPE racine SYSTEM "usthb.dtd">` → DTD locale (URI de type URL)
- `<!DOCTYPE html PUBLIC "FPI" "URI">` → DTD publiée. **FPI** = Formal Public Identifier (ex : `"-//W3C//DTD XHTML 1.0 Strict//EN"`).

## 7. Espaces de noms (xmlns)
- **Par défaut (sans préfixe)** : `<book xmlns='urn:loc.gov:books'>`
- **Spécifique (avec préfixe)** : `<bk:book xmlns:bk='urn:loc.gov:books'>` → `<bk:title>`
- `xmlns` = **xml n**ame **s**pace.

## 8. Document valide (DTD)
- **Bien formé** = respecte la syntaxe XML.
- **Valide** = bien formé ET respecte une grammaire **DTD**.
- DTD type `PUBLIC` ou `SYSTEM`.

## 9. HTML vs XHTML
- XHTML = réécriture de HTML avec syntaxe XML (transition vers XML).
- **Transitional** : tolère des éléments HTML de présentation (passage en douceur).
- **Strict** : interdit attributs/éléments de présentation (`<font>`, `<center>`, attribut `text`) → CSS obligatoire.
- XHTML 2.0 abandonné en 2009 au profit de HTML 5. XHTML 5 = version XML de HTML 5.

---

# CHAPITRE 3 — DTD (Document Type Definition)

La DTD décrit la **structure** d'une classe de documents XML : quels éléments/attributs, leur imbrication, leur ordre.

## Composants

### `<!ELEMENT ...>` — déclarer un élément
| Modèle de contenu | Sens |
|---|---|
| `<!ELEMENT a EMPTY>` | élément vide |
| `<!ELEMENT em (#PCDATA)>` | données textuelles (**P**arsed **C**haracter **DATA**) |
| `<!ELEMENT memo (auteur, sujet)>` | sous-éléments (séquence) |

### Connecteurs
- `,` = **séquence** (ordre obligatoire) : `(nom, prenom)`
- `|` = **ou / choix** : `(ar|en)`
- `( )` = groupe : `(...,(a|b))`

### Indicateurs d'occurrence
| Symbole | Sens |
|---|---|
| *(rien)* | obligatoire, **1 seule fois** |
| `?` | optionnel (0 ou 1) |
| `+` | obligatoire et répétable (1 ou plus) |
| `*` | optionnel et répétable (0, 1 ou plusieurs) |

Ex : `<!ELEMENT personne (nom, prenom, dateDeNaissance?, adresse, email+, telephone*)>`

### `<!ATTLIST ...>` — déclarer un attribut
`<!ATTLIST élément nomAttribut TYPE DÉFAUT>`

**Types** :
- `CDATA` : chaîne, **aucune unicité**. `<!ATTLIST adresse pays CDATA #REQUIRED>`
- `ID` : valeur **unique dans tout le document**. `<!ATTLIST email n ID #REQUIRED>`
- `(ar|en)` : liste de choix (énumération).

**Valeurs par défaut** :
- `#IMPLIED` : présence **facultative**
- `#REQUIRED` : attribut **obligatoire**
- `#FIXED "val"` : valeur **fixée dans la DTD**, inférée vers chaque instance.

### Commentaire DTD : `<!-- ... -->`

## Exemple complet (contacts.dtd)
```dtd
<!ELEMENT contacts (personne+)>
<!ELEMENT personne (nom, prenom, dateDeNaissance, lieuDeNaissance?, adresse, email, telephone?,(a|b))>
<!ELEMENT a EMPTY>
<!ELEMENT b EMPTY>
<!ELEMENT nom (#PCDATA)>
<!ELEMENT prenom (#PCDATA)>
<!ELEMENT dateDeNaissance (#PCDATA)>
<!ELEMENT lieuDeNaissance (#PCDATA)>
<!ATTLIST lieuDeNaissance arrondissement CDATA #FIXED "Brooklyn">
<!ELEMENT adresse EMPTY>
<!ELEMENT email (#PCDATA)>
<!ATTLIST email n ID #REQUIRED>
<!ELEMENT telephone (#PCDATA)>
<!ATTLIST adresse lang (ar|en) #REQUIRED>
<!ATTLIST adresse pays CDATA #REQUIRED>
```

> **Règle pratique** : pour écrire une DTD à partir d'un XML, parcours l'arbre : pour chaque élément note ses enfants dans l'ordre (séquence + occurrences), `(#PCDATA)` si texte, `EMPTY` si vide, et ajoute un `<!ATTLIST>` par attribut.

> ⚠️ **Piège ID** : une valeur d'attribut `ID` ne peut PAS commencer par un chiffre, et ne peut pas contenir d'espace. (cf. exemple examen `datenaissance="a 1942-01-08"`).

---

# CHAPITRE 4 & 5 — XPath (LE plus important pour l'examen)

XPath = expressions qui désignent un ou plusieurs **nœuds** via des **chemins de localisation** dans l'arbre XML.

## Chemins de base
| Expression | Résultat |
|---|---|
| `/` | nœud racine = TOUT le document |
| `/livre/chapitre` | enfants directs (chemin absolu depuis la racine) |
| `//chapitre` | tous les `chapitre` **récursivement** (descendants, n'importe où) |
| `/livre/chapitre[2]` | le 2e `chapitre` (la **position** entre `[ ]`) |
| `.` | nœud courant (contexte) |
| `..` | nœud parent |
| `*` | **wildcard** : n'importe quel nœud d'élément |
| `@nom` | nœud d'**attribut** `nom` |
| `@*` | tous les attributs |

### Exemples wildcard
- `/AAA/BBB/*` → tous les enfants directs de BBB
- `/AAA/BBB/HHH/@*` → tous les attributs de HHH
- `/AAA/BBB//*` → tous les descendants de BBB

### Rappels utiles
- `/*` → élément racine
- `//*` → tous les éléments du document
- `/*/*/BBB` → les BBB ayant exactement 2 niveaux au-dessus
- Pour trouver `GGG` : `//GGG`, `/AAA/GGG`, `/*/GGG`, `/*/*/BBB/..` (remonter)

## Nœud d'attribut
- `//personne[@prenom]` → éléments `personne` **ayant** un attribut `prenom`
- `//personne[@prenom="Jack"]` → ceux dont `prenom` vaut `Jack`
- `/liste/personne/@nom` ou `//personne/@nom` → les nœuds d'attribut `nom`

## Opérateurs logiques (dans un prédicat)
- `//personne[@prenom="Jack"] or //personne[@prenom="Nolan"]` → `true`
- `//personne[@a and @b]` → personnes ayant les deux attributs
- `//personne[not(@date_de_naissance)]` → celles SANS cet attribut
- `//personne[@a or @b]` → l'un OU l'autre

## Fonctions XPath (à connaître par cœur)
| Fonction | Exemple | Rôle |
|---|---|---|
| `node()` | `/livre/chapitre/texte/node()` | nœuds élément + texte + commentaire |
| `text()` | `//Capot/text()` | le **nœud de texte** d'un élément |
| `position()` | `//personne[position()=3]` | la position |
| `last()` | `//personne[last()]` | le dernier (= `[position()=last()]`) |
| `count()` | `count(//personne)` | nombre de nœuds |
| `name()` | `//*[name()='BBB']` | nom du nœud |
| `starts-with()` | `//*[starts-with(name(),'B')]` | commence par |
| `string-length()` | `//*[string-length(name())=5]` | longueur |
| `string()` | `string(//chapitre)` | 1er nœud → chaîne |
| `number()` | `number(//HHH/@att)` | 1er nœud → nombre |
| `contains()` | `contains(//Capot/text(),'peu')` | booléen (sous-chaîne) |
| `concat()` | `concat(a/text(),' ',b/text())` | concaténation |
| `comment()` | `//comment()` | nœuds commentaire |

## Prédicats `[ ]` (filtres)
- `//atome[masse < 40]` → atomes dont la masse < 40
- `//*[string-length(name())=5]` → éléments dont le nom fait 5 caractères
- `//personne[not(contains(@prenom,'Billy'))]` → prénom ne contenant pas "Billy"

## `.` vs `text()` — PIÈGE D'EXAMEN
Pour `<u who="C">donc <vocal/> ben je prends 17h quand même</u>` :
- `//u[contains(., 'quand')]` → `.` = **valeur textuelle complète** de `u` (concatène TOUS les textes des descendants). Trouve "quand" → retourne **l'élément `u` entier**.
- `//u[contains(text(), 'quand')]` → `text()` = **seulement le 1er nœud de texte direct** de `u` (= "donc "). "quand" est dans le 2e nœud de texte (après `<vocal/>`), donc `text()` (qui ne prend que le premier) ne le trouve **pas** ici → **rien retourné** (ou faux selon le moteur).
> Retiens : `.` = tout le texte (récursif) ; `text()` = nœuds de texte directs uniquement.

## Différence `//x[2]` vs `(//x)[2]` vs `/descendant::x[2]` — PIÈGE
- `//titleStmt[2]` ou `/descendant::titleStmt[2]` → le `titleStmt` **qui est en position 2 parmi les enfants de son propre parent** (peut donner 0 ou plusieurs résultats).
- `(//titleStmt)[2]` → on récupère **TOUS** les `titleStmt` du document, PUIS on prend **le 2e de cette liste globale** (1 seul résultat).

## Les AXES (chapitre 5) — syntaxe `axe::`
| Axe | Localise | Équivalent court |
|---|---|---|
| `self::` | le nœud courant lui-même | `.` |
| `child::` | enfants directs | `/` |
| `parent::` | parent direct | `..` |
| `descendant::` | tous les descendants | (≈ `//` interne) |
| `descendant-or-self::` | descendants **+ soi-même** | `/.//` |
| `ancestor::` | tous les ancêtres | |
| `ancestor-or-self::` | ancêtres + soi-même | |
| `following-sibling::` | frères **après** | |
| `preceding-sibling::` | frères **avant** | |
| `following::` | tous nœuds après (dans le doc) | |
| `preceding::` | tous nœuds avant (sauf ancêtres) | |
| `attribute::` | attributs | `@` |

Exemples :
- `/AAA/BBB/self::*` ≡ `/AAA/BBB`
- `//BBB/parent::*` ≡ `//BBB/..`
- `/descendant::HHH[@fromage="Gouda"]` ; `/descendant::*/@*` (tous les attributs) ; `/descendant::text()` (tous les textes)
- `//BBB/child::*/parent::*` → remonte aux BBB qui ont des enfants

---

# CHAPITRE 6 — XQuery (langage de requête, testé sur BaseX)

XQuery = extraire des infos d'un (ou plusieurs) document(s) XML et **reconstruire** de nouveaux documents.
**BaseX** = SGBD XML.

## Expressions simples

### Valeurs atomiques (types XML Schema, préfixe `xs:`)
- `"Bonjour"` → `xs:string`
- `1.5` → `xs:decimal`
- `15` → `xs:integer`
- `xs:date("2008-12-01")` → `xs:date`
- `xs:boolean("true")` → `xs:boolean`

### Fonctions constructeur de type vs conversion
- Constructeur : `xs:date()`, `xs:integer()`/`xs:int()`, `xs:string()`, `xs:float()`, `xs:double()`, `xs:boolean()`
- Booléennes : `fn:true()`, `fn:false()` (0 argument)
- Conversion : `fn:boolean()`, `fn:string()`, `fn:number()` — **note : `xs:number()` n'existe PAS**, seul `fn:number()` existe.

### Séquences `( )`
- Suite **hétérogène** d'items séparés par `,` : `(39, "table", <v/>)`
- Séquence vide = `()` (longueur 0)
- **Pas d'imbrication** : `(39,(1,2),"x")` ≡ `(39,1,2,"x")` (aplatie)
- Indexation **commence à 1** : `(39,"table",<v/>)[1]` → `39`
- `1 to 5` → `(1,2,3,4,5)`
- Immuable.

### Variables `$`
```
declare variable $a := "X";        (: variable GLOBALE :)
declare variable $b := "Query";
$a, $b, concat($a,$b)              (: → X Query XQuery :)
```
- Commentaire XQuery : `(: ... :)`

### Littéraux XML + accolades `{ }`
Dans un littéral XML, `{ }` = « évalue ceci ». Hors accolades = texte brut.
```
declare variable $m := (1,2);
<e>
  <o>{$m[1]}</o>{$m[2],$m[1]}
  $m[2] {$m[2]}
</e>
(: → <e><o>1</o>2 1  $m[2] 2</e>  — le 1er $m[2] (hors {}) reste littéral :)
```

## Opérateurs
- Arithmétiques : `+ - * div mod`
- Logiques : `and or not`
- **Comparaison de VALEURS** (atomiques, item à item) : `eq ne lt le gt ge`
- **Comparaison GÉNÉRALE** (séquences, untypedAtomic, conversion implicite) : `= != < <= > >=`
- **Comparaison de NŒUDS** : `is` (identité), `<<` (avant), `>>` (après)

### Différence `eq` vs `=` — PIÈGE
- `eq` exige des **items uniques typés**. `doc(...)//arbre/annee eq 1860` → **erreur** si `//arbre/annee` est une séquence (« Item expected, sequence found ») OU « untypedAtomic and integer are not comparable ». Il faut un seul nœud + cast : `xs:int(doc(...)//arbre[@id="90"]/annee) eq 1860` → `true`.
- `=` fait la **conversion implicite** et accepte les séquences : `doc(...)//arbre/annee = 1860` → `true` (vrai s'il existe AU MOINS une valeur égale).
- `("Platane","Platane d'orient") = ("Platane","X")` → `true` (intersection non vide).
- `(39,(1,2),"table",<table/>) = (39,1,2,"table",<table/>)` → `true` (mais `eq` donnerait une erreur car séquences).

### `is`, `<<`, `>>`
- `doc("a-b.xml")//a[1] is doc("a-b.xml")//a[2]` → `false` (identités différentes même si valeur = 5)
- `doc("a-b.xml")//a[1] << doc("a-b.xml")//a[2]` → `true` (a[1] apparaît avant)
- Même nœud d'un fichier différent → `is` donne `false` (provenances différentes).

## Construction de nœuds : `element` / `attribute`
```
element racine {
   element element {
      attribute lang { "fr" }, "Texte", element autre {}
   }
}
(: → <racine><element lang="fr">Texte<autre/></element></racine> :)
```
- `{}` après `element X` = nœud vide.

## La fonction `doc()`
- `doc("URI")` retourne le **nœud de document** (racine). `doc("xpath-3.xml")//HHH` applique XPath sur le doc.
- Si le doc est déjà ouvert dans BaseX, `doc()` est inutile.

## Expressions complexes

### 1) Chemins XPath = requêtes XQuery
`//HHH`, `//*[@*="Cantal"]` sont des requêtes XQuery valides.

### 2) Conditions `if-then-else`
```
declare variable $arbre := //arbre[1];
if ($arbre/annee < 1900)
then concat($arbre/nom_commun," est un arbre ancien")
else concat($arbre/nom_commun," est un arbre récent")
```

### 3) Expressions FLWOR (prononcé "flower")
- **F**or : itère sur une séquence
- **L**et : définit une variable (locale)
- **W**here : filtre
- **O**rder by : trie (`ascending` défaut / `descending`)
- **R**eturn : retourne le résultat

```
for $i in (1 to 10) return $i           (: ou: for $i in 1 to 10 :)

let $v := //chapitre
return <a>{$v}</a>                       (: let + return :)

let $genres := //genre/text()
for $genre in $genres
return $genre                            (: let + for + return :)

for $a at $i in //HHH                     (: 'at $i' = position courante :)
return ($a,$i)

element resultat {
  let $noms := //nom_commun
  for $nom in $noms
  return element nom{$nom/text()}
}

let $arbres := //arbre
for $arbre in $arbres
where $arbre/hauteur > 30                  (: filtre :)
return $arbre

let $genres := //genre
for $g in $genres
order by $g descending
return $g
```

**Boucles imbriquées** :
```
element resultat {
  for $i in 1 to 5
  for $j in ("a","b","c")
  return concat($j,$i)
}
(: → a1 b1 c1 a2 b2 c2 ... a5 b5 c5 :)
```

### Portée des variables
- `let` dans un bloc `{ }` → variable **locale** à ce bloc. L'utiliser ailleurs → `Undeclared variable`.
- `declare variable` (en tête) → variable **globale**, accessible partout.

### Exemples d'exercices types (chap. 6)
- Genres sans répétition : `for $genre in distinct-values(//genre) return $genre` (ou `for $g in distinct-values(//genre/text()) ...`).
- Compter arbres > 30 m : `count(//arbre[hauteur > 30])`
- Arbres > 30 m dont genre commence par G ou T :
  `for $a in //arbre where $a/hauteur>30 and (starts-with($a/genre,"G") or starts-with($a/genre,"T")) return $a`
- Avec position dans `<res>` :
  ```
  element res {
    for $a at $i in //arbre
    where $a/hauteur>30 and (starts-with($a/genre,"G") or starts-with($a/genre,"T"))
    return <arbre>{$a/*, <position_XML>{$i}</position_XML>}</arbre>
  }
  ```

---

# CHAPITRE 7 — DOM : LECTURE (Java + parseur JAXP)

**DOM** (Document Object Model) = API W3C, multi-plateformes et multi-langages (Java, JS, PHP, Python, C++…), représentation **hiérarchique en mémoire** (arbre de nœuds). On utilise le parseur **JAXP** (Java API for XML Processing). (Autre parseur : Xerces/Apache.)

## Étapes pour LIRE un XML
```java
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Attr;
import org.w3c.dom.NamedNodeMap;

String xmlFile = args[0];                                  // argument = chemin du fichier
DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  // fabrique
DocumentBuilder parseur = factory.newDocumentBuilder();    // parseur
// abrégé : DocumentBuilder parseur = DocumentBuilderFactory.newInstance().newDocumentBuilder();
Document document = parseur.parse(xmlFile);                // analyse → objet Document (nœud racine)
Element liste = document.getDocumentElement();             // élément racine
System.out.println(liste.getNodeName());                   // → "liste"
```

## Méthode 1 : `getElementsByTagName()`
```java
NodeList eleves = liste.getElementsByTagName("eleve");     // collection de <eleve>
for (int i=0; i<eleves.getLength(); i++){
   eleves.item(i).getNodeName();                           // → "eleve"
   eleves.item(i).getFirstChild().getNodeName();           // → "#text"
   eleves.item(i).getFirstChild().getNodeValue();          // → "L2 ACAD" (valeur texte)
   // ou getTextContent() : texte du nœud + descendants
}
```
> Le **premier fils** d'un élément qui contient du texte est un nœud `#text`. `getNodeValue()` sur ce nœud `#text` donne la chaîne.

## Lecture d'attributs
- **Un attribut précis** : il faut **caster** Node → Element (on ne lit pas un attribut depuis une NodeList) :
```java
Element l_eleve = (Element) eleves.item(i);
l_eleve.getAttribute("nom");        // → "E1"
```
- **Tous les attributs** : `getAttributes()` → `NamedNodeMap`, caster en `Attr` :
```java
NamedNodeMap atts = eleves.item(i).getAttributes();
for(int e=0; e<atts.getLength(); e++){
   Attr attr = (Attr) atts.item(e);
   attr.getName();   // nom (ou atts.item(e).getNodeName())
   attr.getValue();  // valeur (ou atts.item(e).getNodeValue())
}
```

## Méthode 2 : `getChildNodes()` + filtrage des #text parasites
`getChildNodes()` renvoie **TOUS** les fils, **y compris les nœuds `#text` parasites** (sauts de ligne, espaces, tabulations dus à l'indentation). Si on fait `.getFirstChild().getNodeValue()` sur un nœud parasite → **NullPointerException**.

→ Il faut **filtrer** :
```java
// classe séparée
public class Filtrer_parasites {
   public boolean Filtrer(Node noeud) {
      return (noeud.getNodeName() != "#text");   // garde si ce n'est PAS un #text
   }
}
```
Utilisation dans Read.java :
```java
NodeList eleves_bis = liste.getChildNodes();
for (int o=0; o<eleves_bis.getLength(); o++){
   Filtrer_parasites a = new Filtrer_parasites();
   if (a.Filtrer(eleves_bis.item(o)))
      System.out.println(eleves_bis.item(o).getFirstChild().getNodeValue());
}
```
Variantes : méthode `Filtrer` non statique (`Read a = new Read(); a.Filtrer(...)`) ou statique (`if(Filtrer(...))`).

> **Rappel piège** : espaces / sauts de ligne / tabulations = des **objets `#text` parasites** pour DOM. `getNodeValue()` sur un nœud élément renvoie `null` (seuls #text, attribut, commentaire ont une valeur).

---

# CHAPITRE 8 — DOM : CONSTRUCTION & TRANSFORMATION

## A) CONSTRUIRE un document XML (CreateDoc.java)
Objectif : produire `create.xml` indenté, avec lien DTD et 2 espaces de noms.

```java
import javax.xml.parsers.*;
import org.w3c.dom.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.*;

DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
DocumentBuilder parseur = factory.newDocumentBuilder();
DOMImplementation domimp = parseur.getDOMImplementation();   // nécessaire pour créer un Document

// createDocument(URI_namespace_defaut, elementRacine, DocumentType)
Document doc = domimp.createDocument(null, "Racine", null);  // ici null, "Racine", null
doc.setXmlStandalone(true);                                  // supprime/contrôle l'attribut standalone

Element rac = doc.getDocumentElement();                      // récupère <Racine>
String EN="EN:", FR="FR:";
rac.setAttribute("xmlns:FR", "http://example.net/french");   // espace de noms (avec préfixe)
rac.setAttribute("xmlns:EN", "http://example.net/english");

Element prem_ele_FR = doc.createElement(FR+"langue");        // createElement
rac.appendChild(prem_ele_FR);                                // appendChild
Element prem_ele_EN = doc.createElement(EN+"langue");
rac.appendChild(prem_ele_EN);

prem_ele_FR.appendChild(doc.createTextNode("je suis le contenu du premier élément"));  // createTextNode
prem_ele_EN.appendChild(doc.createTextNode("I am the content of the second element"));

// --- Sortie sur fichier (vue par JAXP comme une transformation) ---
DOMSource ds = new DOMSource(doc);                           // 1. objet source
StreamResult res = new StreamResult("create.xml");           // 2. objet résultat (ou System.out)
TransformerFactory transform = TransformerFactory.newInstance();
Transformer tr = transform.newTransformer();                 // 3. transformateur

tr.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
tr.setOutputProperty(OutputKeys.INDENT, "yes");              // indentation auto
tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
tr.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "exemple-dom.dtd");  // lien DTD
tr.transform(ds, res);                                       // source → résultat
```

**Méthodes clés à retenir** :
| Méthode | Rôle |
|---|---|
| `getDOMImplementation()` | obtenir DOMImplementation |
| `createDocumentType("Racine", FPI, "xxx.dtd")` | créer un objet DTD (FPI = 2e arg) |
| `createDocument(URI, "Racine", dtd)` | créer le Document (URI namespace défaut = 1er arg) |
| `getDocumentElement()` | élément racine |
| `setAttribute("nom","val")` | créer/poser un attribut |
| `createElement("nom")` | créer un élément |
| `appendChild(node)` | ajouter un enfant |
| `createTextNode("txt")` | créer un nœud texte |
| `setXmlStandalone(true)` | gérer `standalone` |

**Sur `standalone`** : `standalone="yes"` = DTD **interne** (XML autonome) ; `"no"` = DTD **externe** (non autonome). Valeur par défaut = `"no"`. Forcer : `tr.setOutputProperty(OutputKeys.STANDALONE, "yes")`.

**Lien DTD — 2 méthodes** :
1. Sans objet dtd : `tr.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "exemple-dom.dtd");`
2. Avec objet dtd : `dtd.getSystemId()` (URI) et `dtd.getPublicId()` (FPI).

## B) TRANSFORMER source.xml → cible.xml (TransformDoc.java)
Idée : lire `source.xml`, en extraire une valeur, construire un nouveau document `cible.xml`.

```java
String xmlFile = args[0];
Document document_src = CreateDomParser.parseur().parse(xmlFile);   // parse la source
Document document_cib = transformer(document_src);                 // construit la cible

DOMSource ds = new DOMSource(document_cib);
StreamResult res = new StreamResult("cible.xml");
Transformer tr = TransformerFactory.newInstance().newTransformer();
document_cib.setXmlStandalone(true);
tr.transform(ds, res);

// méthode de transformation :
public static Document transformer(Document document_src) throws Exception {
   DOMImplementation domimp = CreateDomParser.i();
   Document document_cib = domimp.createDocument(null, "Racine", null);
   Element rac_cib = document_cib.getDocumentElement();
   String contenu = document_src.getElementsByTagName("Allumage")
                                .item(0).getFirstChild().getNodeValue();  // "Défectueux"
   Element texte = document_cib.createElement("texte");
   rac_cib.appendChild(texte);
   texte.appendChild(document_cib.createTextNode(contenu));
   return document_cib;
}
```
Résultat : `<?xml version="1.0" encoding="UTF-8"?><Racine><texte>Défectueux</texte></Racine>`

---

# CORRIGÉS — exercices XPath (corr ex cours 5)

**AF.xml** :
- Descendants de titleStmt : `/.../titleStmt//*` ou `/.../titleStmt/descendant::*` ou `(//titleStmt)[1]//*`
- Descendants + soi-même : `/.../titleStmt/descendant-or-self::*`
- Énoncés de l'opérateur : `//u[@who='O']/text()`
- Énoncés du client : `//u[@who='C']/text()`
- Contenant "quand" : `//u[contains(., 'quand')]` (ou `.../text()`)
- Contenant '?' : `//u[contains(., '?')]`

**rec_tag.xml** :
- Tous les NOM : `//taggertoken[contains(@pos,'NOM')]`
- Tous les VERBE : `//taggertoken[contains(@pos,'VER')]`
- NOM précédés d'un DET : `//taggertoken[contains(@pos,'DET')]/following-sibling::taggertoken[1][contains(@pos,'NOM')]`
- Séquences NOM ADJ / ADJ NOM : union `|` de following-sibling et preceding-sibling (voir détail dans le fichier).

**saintex.xml** :
- Traductions tchèque : `//traduction[@lang='tcheque']`
- Tchèque de la 2e phrase : `(//traduction[@lang='tcheque'])[2]`
- Français du narrateur : `//phrase[@loc='narrateur']//traduction[@lang='francais']`
- Idem texte seul : `.../traduction[@lang='francais']/text()`
- Petit Prince + '?' : `//phrase[@loc='LePetitPrince']/modalite[@type='interrogative']/traduction[@lang='francais']`
- Mot avec '?' : `.../traduction[@lang='francais']/mot[contains(text(),'?')]`

---

# 🎯 CORRIGÉ DE L'EXAMEN EXEMPLE (exemples_questions)

### Q. `element racine {attribute lang {"fr"}, attribute lang {"en"}}` — erreur ?
**OUI, erreur.** Un élément ne peut pas avoir **deux attributs de même nom** (`lang` en double) → conflit / attribut dupliqué (XQDY0025).
**Correction** : renommer un attribut, p.ex. `element racine {attribute lang {"fr"}, attribute lang2 {"en"}}` (ou fusionner : `attribute lang {"fr en"}`).

### Q. `element { ...//book[1]/name(@*[1]) } { attribute { ...//book[1]/name(*[2]) } { ...//book[1]/*[2] } }`
Contexte books2 : `<book title="XQuery"><author>...</author><publisher>O'Reilly</publisher></book>`
- Nom du 1er attribut de book[1] = `title` → nom de l'élément créé.
- Nom du 2e élément enfant de book[1] = `publisher` → nom de l'attribut.
- Valeur du 2e enfant = `O'Reilly`.
→ Résultat ≈ `<title publisher="O'Reilly"/>`.

1. **Remplacer `/name(@*[1])` par `/name(@*)` ?** — **Non** (en général). `@*` renvoie une *séquence* de tous les attributs ; `name()` attend **un seul** nœud. S'il y a >1 attribut → erreur. Avec un seul attribut ça passe, mais ce n'est pas robuste.
2. **Remplacer `/name(*[2])` par `/string(*[2])` ?** — **Non**. `name()` renvoie le **nom** de l'élément (`publisher`), `string()` renvoie son **contenu textuel** (`O'Reilly`). On a besoin du **nom** pour nommer l'attribut → `string()` donnerait un mauvais nom (et `O'Reilly` n'est pas un nom d'attribut valide).
3. **Pourquoi `/*[2]` et `/string(*[2])` donnent le même résultat (dernière ligne) ?** — Parce que la **valeur d'un attribut** est forcément du **texte**. `*[2]` (l'élément publisher) est atomisé en sa valeur textuelle `O'Reilly`, exactement ce que renvoie `string(*[2])`. Donc valeur d'attribut identique.
4. **Quand génère-t-elle une erreur si on ajoute un 2e attribut au 1er book ?** — Si on garde `@*[1]` ça va ; mais si la requête utilise `@*` (sans indice) **ou** si le nom du nouvel attribut entre en conflit, `name(@*)` recevrait une séquence de 2 attributs → **erreur** (un seul nœud attendu).

### Q. Rôle de `.` et `text()` pour `<u who="C" n="9">donc <vocal desc="e"/> ben je prends 17h quand même</u>`
- `//u[contains(., 'quand')]` : `.` = **toute la valeur textuelle** de `u` (concatène "donc " + " ben je prends 17h quand même") → contient "quand" → **retourne l'élément `u`**.
- `//u[contains(text(), 'quand')]` : `text()` = seulement le(s) **nœud(s) texte directs**. Le 1er est "donc " (avant `<vocal/>`). "quand" est dans le **2e** nœud texte. `contains(text(),...)` ne teste que le premier → **ne trouve pas** → ne retourne rien.

### Q. `/descendant::titleStmt[2]` vs `//titleStmt[2]` vs `(//titleStmt)[2]`
- `/descendant::titleStmt[2]` ≡ `//titleStmt[2]` → un `titleStmt` qui est **le 2e parmi les enfants de son parent** (filtre relatif au parent). Peut donner 0, 1 ou plusieurs résultats.
- `(//titleStmt)[2]` → prend **tous** les `titleStmt`, puis **le 2e de la liste globale** (1 seul résultat).

### Q. XML `<liste> A<eleve nom="E1" note="12"> L3 ACAD </eleve> B<eleve nom="E2" note="18">L3 ISIL</eleve> </liste>` + code DOM
1. **À quoi sert `standalone` ?** — Indique si le document est **autonome** : `"no"` = dépend d'une DTD/ressource **externe** ; `"yes"` = pas de dépendance externe (DTD interne).
2. **Compléter le code** : la suite de méthodes est `getFirstChild().getNodeValue().trim().replaceAll("\\s+", " ")` (ou `.toString()`), et la branche `else` : `e.item(o).getNodeValue().trim()`. (méthodes manquantes = `trim()` pour nettoyer espaces ; `replaceAll(...)` pour normaliser.) `Filtrer` retourne `noeud.getNodeName() != "#text"`.
3. **Que va afficher le code ?** — Il parcourt les fils de `<liste>`. Les fils sont : `#text` ("A"), `<eleve>`, `#text` ("B"), `<eleve>`, plus #text de blancs.
   - Pour les nœuds `eleve` (Filtrer vrai) : affiche le 1er fils texte nettoyé → `L3 ACAD` puis `L3 ISIL`.
   - Pour les nœuds `#text` non vides "A" et "B" (Filtrer faux, et ne finissant pas par "…") : affiche `A` et `B`.
   → Sortie ≈ `A`, `L3 ACAD`, `B`, `L3 ISIL` (les #text de blancs purs sont ignorés/produisent du vide).
4. **Rôle de la ligne en gras** (`if(a.Filtrer(e.item(o)))`) — **filtrer** : séparer les nœuds élément (`eleve`) des nœuds texte (#text), pour traiter chacun différemment.
5. **`getFirstChild().getNodeValue()` vs `getTextContent()` ?** — Pour un `eleve` ne contenant que du texte direct, **même résultat**. MAIS si l'élément a des sous-éléments, `getTextContent()` renvoie **tout** le texte (élément + descendants) alors que `getFirstChild().getNodeValue()` ne renvoie que le **premier** nœud texte. Donc résultat **différent** dès qu'il y a des enfants/plusieurs nœuds texte.

### Q. `<!DOCTYPE name SYSTEM "book_examen.dtd"> <name>W.B<family>Wallace</family></name>` — compléter `<!ELEMENT name (……)>`
`name` contient du **texte** ("W.B") **mélangé** avec un sous-élément `family` → contenu **mixte** :
**`<!ELEMENT name (#PCDATA | family)*>`**
(et `<!ELEMENT family (#PCDATA)>`)

### Q. `element racine {attribute lang {"fr"}, attribute lang {"en"}}` — pourquoi erreur ?
Deux attributs **portant le même nom** `lang` sur le même élément → interdit (un attribut doit être unique par élément).

### Q3. Expliquer la requête XQuery :
```
for $b1 in doc("books1.xml")//book, $b2 in doc("books2.xml")//book
where $b1/@*=$b2/@year
let $both := ($b2/*/*[2], $b1/*/*[2])
for $ele at $i in $b2/*/*[2]
return <a>{$ele, $both[$i + count($both) div 2]}</a>
```
Explication :
- Double `for` = **produit cartésien** (jointure) des `book` de books1 et books2.
- `where $b1/@* = $b2/@year` : ne garde que les paires où **un attribut quelconque** de $b1 égale l'attribut `year` de $b2 (jointure sur l'année).
- `let $both := (...)` : séquence concaténant le **2e enfant du 1er enfant** de $b2 puis celui de $b1 (`*/*[2]` = 2e petit-enfant via le 1er enfant).
- `for $ele at $i in $b2/*/*[2]` : itère sur ces nœuds de $b2 avec leur position `$i`.
- `return <a>{$ele, $both[$i + count($both) div 2]}</a>` : pour chaque `$ele`, on l'affiche **plus** l'élément situé à l'indice `$i + (taille de $both)/2` → c.-à-d. on apparie l'élément de $b2 avec l'élément **correspondant de $b1** (décalage de la moitié de la séquence). Produit des `<a>` contenant la paire (valeur books2, valeur books1 associée).

### Q. `<arbres><arbre id="6"><nom_commun>Oranger des osages</nom_commun></arbre>...</arbres>`
1. **Remplacer `@*` dans R1 pour récupérer la valeur d'attribut de `arbre`** → `@id` (`string($arbre/@id)`).
2. **Compléter R2** pour afficher nom commun + son ID :
   `string($nom/parent::arbre/@id)` → axe à compléter = **`parent::arbre`** :
   ```
   for $nom in doc("arbres.xml")//nom_commun
   return element nom{$nom/text()," ID", string($nom/parent::arbre/@id)}
   ```
   (l'attribut `id` est sur l'élément `arbre`, parent de `nom_commun`.)
3. **Si `id="6"` est ajouté à l'élément racine `<arbres>`, quelle requête fonctionne ?**
   - **R2 fonctionne** (R2 remonte explicitement à `parent::arbre`, donc cible toujours l'`id` de `arbre`, pas celui de la racine).
   - **R1 ne marche pas** correctement : `$arbre/@*` cible l'attribut de `arbre`, mais si on s'attendait à `@*` à la racine ça changerait. Justification : R2 est robuste car elle remonte explicitement au bon élément `arbre`.

### Q. `<enc><personne datenaissance="a 1942-01-08"/></enc>` + `<!ATTLIST personne datenaissance ID #REQUIRED>`
1. **Pourquoi erreur ?** — Le type `ID` impose une valeur conforme à un **NAME XML** : elle ne peut **pas contenir d'espace** et ne doit **pas commencer par un chiffre**. Ici `"a 1942-01-08"` contient un **espace** → invalide pour un `ID`.
2. **Deux solutions** :
   - **(a)** Changer le **type** de l'attribut en `CDATA` : `<!ATTLIST personne datenaissance CDATA #REQUIRED>` (CDATA accepte les espaces).
   - **(b)** Garder `ID` mais **corriger la valeur** pour qu'elle soit un NAME valide : supprimer l'espace → `datenaissance="a1942-01-08"` (commence par une lettre, sans espace).

---

# ✅ CHECKLIST ANTI-PIÈGES (à relire 5 min avant)
1. **Bien formé** ≠ **valide** (valide = + conforme DTD).
2. `.` = tout le texte récursif ; `text()` = nœuds texte **directs** (souvent le premier seulement).
3. `//x[2]` (position chez le parent) ≠ `(//x)[2]` (2e de la liste globale).
4. `eq` (1 item typé) ≠ `=` (séquences, conversion implicite). `eq` sur séquence/untyped → erreur.
5. `is` compare l'**identité** des nœuds ; `<<`/`>>` l'**ordre**.
6. Indexation séquence XQuery **commence à 1**.
7. DOM : `getChildNodes()` ramène les **#text parasites** → filtrer (`getNodeName() != "#text"`).
8. `getNodeValue()` sur un nœud **élément** = `null`. La valeur texte est sur son fils `#text`.
9. Lire un **attribut** : caster Node → Element (`getAttribute`) ou Node → Attr (`getAttributes`).
10. Attribut **ID** : pas d'espace, ne commence pas par un chiffre, unique dans tout le doc.
11. Deux attributs de **même nom** sur un élément = interdit (XML et XQuery).
12. `standalone="yes"` = DTD interne (autonome) ; `"no"` (défaut) = DTD externe.
13. DTD : `,`=séquence, `|`=choix, `?`=0/1, `+`=1+, `*`=0+, `#PCDATA`=texte, `EMPTY`=vide, contenu mixte `(#PCDATA|x)*`.
14. Construction DOM : `createElement` + `appendChild` ; `createTextNode` ; `setAttribute`.
15. Sortie DOM sur fichier = une **transformation** (DOMSource + StreamResult + Transformer).
