package sharePoint.news;

/**
 *
 * @author Zeeshan Akhter
 */
import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.ws.BindingProvider;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

import com.microsoft.schemas.sharepoint.soap.GetListItems;
import com.microsoft.schemas.sharepoint.soap.GetListItemsResponse;
import com.microsoft.schemas.sharepoint.soap.Lists;
import com.microsoft.schemas.sharepoint.soap.ListsSoap;
import java.io.IOException;
import java.io.StringReader;
import java.net.Authenticator;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * @author Zeeshan Akhter
 */
public class SharepointClient {

    private static Logger LOGGER = Logger.getLogger(SharepointClient.class.getName());

    public static void main(String[] args) {
//          public static void getNews(String listName, String rowLimit, ArrayList<String> listColumnNames,
//            String viewName, String query, String viewFields, String queryOptions, String webID) {
        try {

            //Authentication parameters
            String userName = "UserName";
            String password = "Password";
            String domain = "domain";
            String webserviceUrl = "http://www.abc.com/en/_vti_bin/lists.asmx?WSDL";
            SharepointClient sharepointClient = new SharepointClient();
            //Opening the SOAP port of the Lists Web Service
            ListsSoap port = sharepointClient.sharePointListsAuth(domain + "/" + userName, password, webserviceUrl);

            /*
             * Lists Web service parameters
             * The list names below must be the *original* names of the list.
             * if a list or column was renamed from SharePoint afterwards,
             * these parameters don't change.
             */
            String listName = "News";
            String rowLimit = "50";
            ArrayList<String> listColumnNames = new ArrayList<String>();
            listColumnNames.add("ID");
            listColumnNames.add("Title");
            listColumnNames.add("MohTitleAr");
            listColumnNames.add("MohPicture");
            listColumnNames.add("MohPostingDate");
            listColumnNames.add("MohExpiryDate");
            listColumnNames.add("MohBrief");
            listColumnNames.add("MohBriefAr");
            listColumnNames.add("MetaInfo");
            listColumnNames.add("ContentType");
            listColumnNames.add("Created");
            listColumnNames.add("Modified");
            listColumnNames.add("Author");
            listColumnNames.add("Editor");
            listColumnNames.add("LinkTitleNoMenu");
            listColumnNames.add("LinkTitle");
            listColumnNames.add("LinkTitle2");
            listColumnNames.add("MblNotify");
            String viewName = "";
            String query = "<Query>\n"
                    + "<Where>\n"
                    + "<Lt>\n"
                    + "<FieldRef Name=\"ID\" />\n"
                    + "<Value Type=\"Counter\">1000</Value>\n"
                    + "</Lt>\n"
                    + "</Where>\n<OrderBy><FieldRef Name='Created' Ascending='False' /></OrderBy>"
                    + "</Query>";

            String viewFields = "<ViewFields  >\n"
                    + "<FieldRef Name=\"ID\"></FieldRef>\n"
                    + "<FieldRef Name=\"MblNotify\"></FieldRef>\n"
                    + "<FieldRef Name=\"Title\"></FieldRef>\n"
                    + "<FieldRef Name=\"MohTitleAr\"></FieldRef>\n"
                    + "<FieldRef Name=\"MohPicture\"></FieldRef>\n"
                    + "<FieldRef Name=\"MohPostingDate\"></FieldRef>\n"
                    + "<FieldRef Name=\"MohExpiryDate\"></FieldRef>\n"
                    + "<FieldRef Name=\"Priority\"></FieldRef>\n"
                    + "<FieldRef Name=\"MohBrief\"></FieldRef>\n"
                    + "<FieldRef Name=\"MohBriefAr\"></FieldRef>\n"
                    + "<FieldRef Name=\"ContentType\"></FieldRef>\n"
                    + "<FieldRef Name=\"Created\"></FieldRef>\n"
                    + "<FieldRef Name=\"Modified\"></FieldRef>\n"
                    + "<FieldRef Name=\"Author\"></FieldRef>\n"
                    + "<FieldRef Name=\"Editor\"></FieldRef>\n"
                    + "<FieldRef Name=\"LinkTitleNoMenu\"></FieldRef>\n"
                    + "<FieldRef Name=\"LinkTitle\"></FieldRef>\n"
                    + "<FieldRef Name=\"LinkTitle2\"></FieldRef>"
                    + "<FieldRef Name=\"MetaInfo\"></FieldRef>\n"
                    + "</ViewFields>";

            String queryOptions
                    = "<QueryOptions> <IncludeMandatoryColumns>TRUE</IncludeMandatoryColumns> <DateInUtc>TRUE</DateInUtc></QueryOptions>";

            String webID = "";

            //Displays the lists items in the console
            List<News> newsList = sharepointClient.displaySharePointNewsList(port, listName, rowLimit, listColumnNames, viewName, query, viewFields, queryOptions, webID);
//            sharepointClient.displaySharePointList(port, listName, listColumnNames, rowLimit);
            // System.out.println("News Count :: " + newsList.size());
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    /**
     * Connects to a SharePoint Lists Web Service through the given open port,
     * and reads News of the given list.
     *
     * @param listName original name of the Sharepoint list that is going to be
     * read
     * @param rowLimit limits the number of rows (list items) that are going to
     * be returned
     * @param listColumnNames arraylist containing the various names of the
     * Columns of the SharePoint list that are going to be read. If the column
     * name isn't found, then an exception will be thrown
     * @param viewName Here are additional parameters that may be set
     * @param query xml caml query format A Query element containing the query
     * that determines which records are returned and in what order, and that
     * can be assigned to a Node object, as in the following example.
     * @param viewFields xml caml format column names A ViewFields element that
     * specifies which fields to return in the query and in what order, and that
     * can be assigned to a Node object, as in the following example.
     * @param queryOptions xml caml format queryOptions An XML fragment in the
     * following form that contains separate nodes for the various properties of
     * the Query object, and that can be assigned to a Node object.
     * @param webID Optional. A string containing the GUID of the parent Web
     * site for the list surrounded by curly braces ({}). Setting this parameter
     * to null means the Web site specified by the Url property of the service
     * will be used, and if the Url property of the service is not specified,
     * the root Web site will be used.
     * @throws Exception
     */
    public static List<News> getNewsList(String listName, String rowLimit, ArrayList<String> listColumnNames,
            String viewName, String query, String viewFields, String queryOptions, String webID) {
        List<News> newsList = new ArrayList<News>();
        try {

            //Authentication parameters
            String userName = "MOHSPFarm";
            String password = "M0H$PF@RM1";
            String domain = "mohousingw";
            String webserviceUrl = "http://www.housing.gov.bh/en/_vti_bin/lists.asmx?WSDL";
            SharepointClient sharepointClient = new SharepointClient();
            //Opening the SOAP port of the Lists Web Service
            ListsSoap port = sharepointClient.sharePointListsAuth(domain + "/" + userName, password, webserviceUrl);
            //Displays the lists items in the console
            newsList = sharepointClient.displaySharePointNewsList(port, listName, rowLimit, listColumnNames, viewName, query, viewFields, queryOptions, webID);
        } catch (Exception ex) {
            System.err.println(ex);
        }
        return newsList;
    }

    /**
     * Creates a port connected to the SharePoint Lists Web Service given.
     * Authentication is done here.
     *
     * @param userName SharePoint username
     * @param password SharePoint password
     * @param webserviceUrl SharePoint webservice Url
     * @return port ListsSoap port, connected with SharePoint
     * @throws Exception in case of invalid parameters or connection error.
     */
    public ListsSoap sharePointListsAuth(String userName, String password, String webserviceUrl) throws Exception {
        ListsSoap port = null;
        if (userName != null && password != null) {
            try {
//                URL wsdlLocation = new URL("http://www.housing.gov.bh/en/_vti_bin/lists.asmx?WSDL");
                URL wsdlLocation = new URL(webserviceUrl);
                Authenticator.setDefault(new RunHttpSpnego.MyAuthenticator());
                Lists service = new Lists(wsdlLocation);
                port = service.getListsSoap();
//                if (LOGGER.isLoggable(Level.INFO)) {
//                    LOGGER.info("LISTS Web Service Auth Username: " + userName);
//                }
                ((BindingProvider) port).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, userName);
                ((BindingProvider) port).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, password);
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("Error: " + e.toString());
            }
        } else {
            throw new Exception("Couldn't authenticate: Invalid connection details given.");
        }
        return port;
    }

    /**
     * Creates a string from an XML file with start and end indicators
     *
     * @param docToString document to convert
     * @return string of the xml document
     */
    public static String xmlToString(Document docToString) {

        String returnString = "";
        try {
            //create string from xml tree
            //Output the XML
            //set up a transformer
            TransformerFactory transfac = TransformerFactory.newInstance();
            Transformer trans;
            trans = transfac.newTransformer();
            trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            trans.setOutputProperty(OutputKeys.INDENT, "yes");
            StringWriter sw = new StringWriter();
            StreamResult streamResult = new StreamResult(sw);
            DOMSource source = new DOMSource(docToString);
            trans.transform(source, streamResult);
            String xmlString = sw.toString();
            //print the XML
            returnString = returnString + xmlString;
        } catch (TransformerException ex) {
            LOGGER.severe(ex.toString());
        }
        return returnString;
    }

    public static Element generateXmlNode(String sXML) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(false);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document documentOptions = builder.parse(new InputSource(new StringReader(sXML)));
        Element elementOptions = documentOptions.getDocumentElement();
        return elementOptions;
    }

    /**
     * Connects to a SharePoint Lists Web Service through the given open port,
     * and reads all the elements of the given list. Only the ID and the given
     * attributes (column names) are displayed, as well as a dump of the SOAP
     * response from the Web Service (for debugging purposes).
     *
     * @param port an already authentificated SharePoint Online SOAP port
     * @param listName original name of the Sharepoint list that is going to be
     * read
     * @param rowLimit limits the number of rows (list items) that are going to
     * be returned
     * @param listColumnNames arraylist containing the various names of the
     * Columns of the SharePoint list that are going to be read. If the column
     * name isn't found, then an exception will be thrown
     * @param viewName Here are additional parameters that may be set
     * @param query xml caml query format A Query element containing the query
     * that determines which records are returned and in what order, and that
     * can be assigned to a Node object, as in the following example.
     * @param viewFields xml caml format column names A ViewFields element that
     * specifies which fields to return in the query and in what order, and that
     * can be assigned to a Node object, as in the following example.
     * @param queryOptions xml caml format queryOptions An XML fragment in the
     * following form that contains separate nodes for the various properties of
     * the Query object, and that can be assigned to a Node object.
     * @param webID Optional. A string containing the GUID of the parent Web
     * site for the list surrounded by curly braces ({}). Setting this parameter
     * to null means the Web site specified by the Url property of the service
     * will be used, and if the Url property of the service is not specified,
     * the root Web site will be used.
     * @throws Exception
     */
    public List<News> displaySharePointNewsList(ListsSoap port, String listName, String rowLimit, ArrayList<String> listColumnNames,
            String viewName, String query, String viewFields, String queryOptions, String webID
    ) throws Exception {
        List<News> newsList = new ArrayList<News>();
        if (port != null && listName != null && listColumnNames != null && rowLimit != null) {
            try {

                GetListItems.Query queryString = new GetListItems.Query();
                queryString.getContent().add(generateXmlNode(query));

                GetListItems.ViewFields viewFieldsItems = new GetListItems.ViewFields();
                viewFieldsItems.getContent().add(generateXmlNode(viewFields));

                GetListItems.QueryOptions queryOptionsItems
                        = new GetListItems.QueryOptions();
                queryOptionsItems.getContent().add(generateXmlNode(queryOptions));

                //Call the service method and get the lists items as an xml result
                GetListItemsResponse.GetListItemsResult result = port.getListItems(listName, null, queryString, viewFieldsItems, rowLimit, queryOptionsItems, null);
                //Calling the List Web Service
                //  GetListItemsResponse.GetListItemsResult result = port.getListItems(listName, viewName, query, viewFields, rowLimit, queryOptions, webID);
                Object listResult = result.getContent().get(0);
                if ((listResult != null) && (listResult instanceof Element)) {
                    Element node = (Element) listResult;

                    //Dumps the retrieved info in the console
                    Document document = node.getOwnerDocument();
                    //   System.out.println("Document== " + document.toString());
                    LOGGER.info("SharePoint Online News List WebService Response:\n" + SharepointClient.xmlToString(document));

                    //selects a list of nodes which have z:row elements
                    NodeList list = node.getElementsByTagName("z:row");
                    LOGGER.info("=> " + list.getLength() + " results from SharePoint Online News");

                    //Displaying every result received from SharePoint, with its ID
                    for (int i = 0; i < list.getLength(); i++) {
                        News news = new News();

                        //Gets the attributes of the current row/element
                        NamedNodeMap attributes = list.item(i).getAttributes();
                     //   LOGGER.info("******** Item ID: " + attributes.getNamedItem("ows_ID").getNodeValue() + " ********");

                        //Displays all the attributes of the list item that correspond to the column names given
                        for (String columnName : listColumnNames) {
                            String internalColumnName = "ows_" + columnName;
                            if (attributes.getNamedItem(internalColumnName) != null) {

                                if ("ID".equalsIgnoreCase(columnName)) {
                                    news.setID(attributes.getNamedItem(internalColumnName).getNodeValue());
                                }
                                if ("MblNotify".equalsIgnoreCase(columnName)) {
                                    if (attributes.getNamedItem(internalColumnName).getNodeValue() == null || attributes.getNamedItem(internalColumnName).getNodeValue().isEmpty() || attributes.getNamedItem(internalColumnName).getNodeValue().equalsIgnoreCase("null")) {
                                        news.setPushNotificationStatus("0");
                                    } else {
                                        news.setPushNotificationStatus(attributes.getNamedItem(internalColumnName).getNodeValue());
                                    }
                                }
                                if ("Title".equalsIgnoreCase(columnName)) {
                                    news.setTitle(attributes.getNamedItem(internalColumnName).getNodeValue());
                                }
                                if ("MohTitleAr".equalsIgnoreCase(columnName)) {
                                    news.setMohTitleAr(attributes.getNamedItem(internalColumnName).getNodeValue());
                                }

                                if ("MohPostingDate".equalsIgnoreCase(columnName)) {
                                    news.setMohPostingDate(attributes.getNamedItem(internalColumnName).getNodeValue());
                                }
                                if ("MohExpiryDate".equalsIgnoreCase(columnName)) {
                                    news.setMohExpiryDate(attributes.getNamedItem(internalColumnName).getNodeValue());
                                }
                                if ("MohBrief".equalsIgnoreCase(columnName)) {
                                    String mohBrief = attributes.getNamedItem(internalColumnName).getNodeValue();
                                    // mohBrief = (mohBrief.replace("line-height:107", "line-height:20"));
                                    news.setMohBrief(mohBrief);
                                }
                                if ("MohBriefAr".equalsIgnoreCase(columnName)) {
                                    String mohBriefAr = attributes.getNamedItem(internalColumnName).getNodeValue();
                                    // mohBriefAr = (mohBriefAr.replace("line-height:107", "line-height:20"));
                                    news.setMohBriefAr(mohBriefAr);
                                }

                                if ("MetaInfo".equalsIgnoreCase(columnName)) {
                                    news.setMetaInfo(attributes.getNamedItem(internalColumnName).getNodeValue());
                                }
                                if ("ContentType".equalsIgnoreCase(columnName)) {
                                    news.setContentType(attributes.getNamedItem(internalColumnName).getNodeValue());
                                }
                                if ("Created".equalsIgnoreCase(columnName)) {
                                    news.setCreated(attributes.getNamedItem(internalColumnName).getNodeValue());
                                }
                                if ("Modified".equalsIgnoreCase(columnName)) {
                                    news.setModified(attributes.getNamedItem(internalColumnName).getNodeValue());
                                }
                                if ("Author".equalsIgnoreCase(columnName)) {
                                    news.setAuthor(attributes.getNamedItem(internalColumnName).getNodeValue());
                                }
                                if ("Editor".equalsIgnoreCase(columnName)) {
                                    news.setEditor(attributes.getNamedItem(internalColumnName).getNodeValue());
                                }
                                if ("LinkTitleNoMenu".equalsIgnoreCase(columnName)) {
                                    news.setLinkTitleNoMenu(attributes.getNamedItem(internalColumnName).getNodeValue());
                                }

                                if ("LinkTitle".equalsIgnoreCase(columnName)) {
                                    news.setLinkTitle(attributes.getNamedItem(internalColumnName).getNodeValue());
                                }
                                if ("LinkTitle2".equalsIgnoreCase(columnName)) {
                                    news.setLinkTitle2(attributes.getNamedItem(internalColumnName).getNodeValue());
                                }
                                if ("Priority".equalsIgnoreCase(columnName)) {
                                    news.setPriority(attributes.getNamedItem(internalColumnName).getNodeValue());
                                }

                                if ("MohPicture".equalsIgnoreCase(columnName)) {
                                    String newsImg = attributes.getNamedItem(internalColumnName).getNodeValue();

                                    if (newsImg.contains(".JPG")) {
                                        newsImg = newsImg.substring(newsImg.lastIndexOf("src="), newsImg.lastIndexOf(".JPG\""));
                                        newsImg = newsImg.replaceAll("src=\"", "http://www.housing.gov.bh");
                                        newsImg = newsImg + ".jpg";
                                    } else if (newsImg.contains(".jpg")) {
                                        newsImg = newsImg.substring(newsImg.lastIndexOf("src="), newsImg.lastIndexOf(".jpg\""));
                                        newsImg = newsImg.replaceAll("src=\"", "http://www.housing.gov.bh");
                                        newsImg = newsImg + ".jpg";
                                    } else if (newsImg.contains(".png")) {
                                        newsImg = newsImg.substring(newsImg.lastIndexOf("src="), newsImg.lastIndexOf(".png\""));
                                        newsImg = newsImg.replaceAll("src=\"", "http://www.housing.gov.bh");
                                        newsImg = newsImg + ".png";
                                    } else if (newsImg.contains(".PNG")) {
                                        newsImg = newsImg.substring(newsImg.lastIndexOf("src="), newsImg.lastIndexOf(".PNG\""));
                                        newsImg = newsImg.replaceAll("src=\"", "http://www.housing.gov.bh");
                                        newsImg = newsImg + ".png";
                                    }

                                    news.setMohPicture(newsImg);
                                    //    LOGGER.info(columnName + "::: " + ((attributes.getNamedItem(internalColumnName)).getAttributes().getNamedItem("src")));
                                }
                                //    LOGGER.info(columnName + "::: " + attributes.getNamedItem(internalColumnName).getNodeValue());

                            } else {

                                internalColumnName = (internalColumnName.replace("ows_", ""));
                                if ("MblNotify".equalsIgnoreCase(internalColumnName)) {
                                    news.setPushNotificationStatus("0");
                                }
                                if ("MohPicture".equalsIgnoreCase(internalColumnName)) {
                                    String newsImg = "";
                                    news.setMohPicture(newsImg);
                                }
//                                else {
//                                    throw new Exception("Couldn't find the '" + columnName + "' column in the '" + listName + "' list in SharePoint.\n");
//                                }

                            }
                        }
                        //System.out.println(news.toString());
                        //System.out.println("news.getID=" + news.getID());
                        newsList.add(news);
                    }
                } else {
                    throw new Exception(listName + " list response from SharePoint is either null or corrupt\n");
                }
            } catch (Exception ex) {
                throw new Exception("Exception. See stacktrace." + ex.toString() + "\n");
            }
        }
        return newsList;
    }

    /**
     * Connects to a SharePoint Lists Web Service through the given open port,
     * and reads all the elements of the given list.
     *
     * @param port an already authentificated SharePoint Online SOAP port
     * @param listName original name of the Sharepoint list that is going to be
     * read
     * @return a String representing the Document object of the SOAP response.
     * @throws Exception
     */
    public String getListItems(ListsSoap port, String listName) throws Exception {
        String xmlToStrinResult = "";

        if (port != null && listName != null) {
            try {
                //Here are additional parameters that may be set
                String viewName = "";
                GetListItems.ViewFields viewFields = null;
                GetListItems.Query query = null;
                GetListItems.QueryOptions queryOptions = null;
                String webID = "";
                String rowLimit = "";

                //Calling the List Web Service
                GetListItemsResponse.GetListItemsResult result = port.getListItems(listName, viewName, query, viewFields, rowLimit, queryOptions, webID);
                Object listResult = result.getContent().get(0);
                if ((listResult != null) && (listResult instanceof Element)) {
                    Element node = (Element) listResult;

                    //Dumps the retrieved info in the console
                    Document document = node.getOwnerDocument();
                    xmlToStrinResult = SharepointClient.xmlToString(document);
                    LOGGER.info("SharePoint Online Lists Web Service Response:" + xmlToStrinResult);
                } else {
                    xmlToStrinResult = listName + " list response from SharePoint is either null or corrupt";
                }
            } catch (Exception ex) {
                xmlToStrinResult = "Exception occurred.\nPosible cause: invalid 'listName' parameter.\nStacktrace: " + ex.toString();
            }
        }
        return xmlToStrinResult;
    }

    /**
     * Checks-out the specified file
     *
     * @param port Lists web service port
     * @param pageUrl
     * @return true if the operation succeeded; otherwise, false.
     */
    public boolean checkOutFile(ListsSoap port, String pageUrl) {
        if (LOGGER.isLoggable(Level.INFO)) {
            LOGGER.info("Checking-out pageUrl=" + pageUrl);
        }
        String checkoutToLocal = "true";
        String lastModified = "";
        boolean result = port.checkOutFile(pageUrl, checkoutToLocal, lastModified);
        if (LOGGER.isLoggable(Level.INFO)) {
            LOGGER.info("Check-out result = " + result);
        }
        return result;
    }

    /**
     * Undo checked-out file
     *
     * @param port Lists web service port
     * @param pageUrl
     * @return true if the operation succeeded; otherwise, false.
     */
    public boolean undoCheckOutFile(ListsSoap port, String pageUrl) {
        if (LOGGER.isLoggable(Level.INFO)) {
            LOGGER.info("Undo checkout pageUrl=" + pageUrl);
        }
        boolean result = port.undoCheckOut(pageUrl);
        if (LOGGER.isLoggable(Level.INFO)) {
            LOGGER.info("Undo checkout result = " + result);
        }
        return result;
    }

    /**
     * Checks-in the specified file
     *
     * @param port Lists web service port
     * @param pageUrl
     * @param comment
     * @return true if the operation succeeded; otherwise, false.
     */
    public boolean checkInFile(ListsSoap port, String pageUrl, String comment) {
        if (LOGGER.isLoggable(Level.INFO)) {
            LOGGER.info("Checking-in pageUrl=" + pageUrl + " comment=" + comment);
        }
        // checkinType = values 0, 1 or 2, where 0 = MinorCheckIn, 1 = MajorCheckIn, and 2 = OverwriteCheckIn.
        String checkinType = "0";
        boolean result = port.checkInFile(pageUrl, comment, checkinType);
        if (LOGGER.isLoggable(Level.INFO)) {
            LOGGER.info("Check-in result = " + result);
        }
        return result;
    }

}
