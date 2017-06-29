package com.javarush.task.task33.task3309;

import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
//import javax.xml.bind.annotation.XmlRootElement;
//import javax.xml.bind.annotation.XmlType;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.util.regex.Pattern;

/*
Комментарий внутри xml
*/
public class Solution {
    public static String toXmlWithComment(Object obj, String tagName, String comment) {
        try
        {
            JAXBContext context = JAXBContext.newInstance(obj.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            marshaller.marshal(obj, doc);

            NodeList nodes = doc.getElementsByTagName("*");
            for (int i = 0; i < nodes.getLength(); i++)
            {
                Node node = nodes.item(i);
                if (node.getNodeName().equals(tagName))
                {
                    Comment com = doc.createComment(comment);
                    node.getParentNode().insertBefore(com, node);
                }
                replaceTextWithCDATA(node, doc);
            }

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            StringWriter sw = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(sw));
            return sw.toString();
        }
        catch (JAXBException | ParserConfigurationException | TransformerException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    private static void replaceTextWithCDATA(Node node, Document doc)
    {
        if ((node.getNodeType() == 3) && (Pattern.compile("[<>&'\"]").matcher(node.getTextContent()).find()))
        {
            Node cnode = doc.createCDATASection(node.getNodeValue());
            node.getParentNode().replaceChild(cnode, node);
        }
        NodeList list = node.getChildNodes();
        for (int i = 0; i < list.getLength(); i++)
        {
            replaceTextWithCDATA(list.item(i), doc);
        }
    }

    public static void main(String[] args) {
//        String result = Solution.toXmlWithComment(new AnExample(), "needCDATA", "it's a comment - <needCDATA>");
//        System.out.println(result);
    }
//    @XmlType(name = "anExample")
//    @XmlRootElement
//    public static class AnExample {
//        public String[] needCDATA = new String[]{"need CDATA because of < and >", ""};
//    }
}
