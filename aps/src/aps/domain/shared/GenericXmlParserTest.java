package aps.domain.shared;

import aps.domain.model.error.Error;
import aps.domain.model.scrape.DataPair;
import aps.domain.model.scrape.DataPairs;
import aps.domain.model.scrape.ScrapeObject;
import org.junit.Before;
import org.junit.Ignore;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test class for the GenericXmlParser Class.
 */
public class GenericXmlParserTest {

    GenericXmlParser toTestScrapeObject;
    GenericXmlParser toTestScrapeError;

    @Before
    public void init() {
        toTestScrapeObject = new GenericXmlParser(ScrapeObject.class);
        toTestScrapeError = new GenericXmlParser(Error.class);
    }

    @org.junit.Test
    public void parseScrapXml() {
        assertEquals("www.telkom.co.za", ((ScrapeObject) toTestScrapeObject.parseScrapXml(getSampleXmlResponse())).getBaseUrl());
        assertEquals("Account no", ((ScrapeObject) toTestScrapeObject.parseScrapXml(getSampleXmlResponse())).getDataPairs().get(0).getText());
        assertEquals("www.telkom.co.za", ((Error) toTestScrapeError.parseScrapXml(getSampleErrorXmlResponse())).getBaseUrl());
        assertEquals("INVALID_CREDENTIALS", ((Error) toTestScrapeError.parseScrapXml(getSampleErrorXmlResponse())).getScrapeErrorCode());
    }

    //TODO: To fix the problem with writing nested datapair tags
    @Ignore
    @org.junit.Test
    public void marshallScrapXml() {
        String xmlScrapeObjectString = toTestScrapeObject.marshallScrapXml(buildTestScrapeObject());
        String xmlScrapeErrorString = toTestScrapeError.marshallScrapXml(buildTestScrapeError());

        assertTrue(xmlScrapeObjectString.contains("<base-url>www.telkom.co.za</base-url>"));
        assertTrue(xmlScrapeObjectString.contains("<date>10/01/2008</date>"));
        assertTrue(xmlScrapeObjectString.contains("<time>13:50:00</time>"));
        assertTrue(xmlScrapeObjectString.contains("<text>Account no</text>"));
        assertTrue(xmlScrapeObjectString.contains("<value>53844946068883</value>"));
        assertTrue(xmlScrapeObjectString.contains("<scrapeErrorCode>INVALID_CREDENTIALS</scrapeErrorCode>"));

        assertTrue(xmlScrapeErrorString.contains("<base-url>www.telkom.co.za</base-url>"));
        assertTrue(xmlScrapeErrorString.contains("<scrapeErrorCode>INVALID_CREDENTIALS</scrapeErrorCode>"));
    }

    private String getSampleErrorXmlResponse() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<scrape-session>\n" +
                "    <base-url>www.telkom.co.za</base-url>\n" +
                "    <date>10/01/2008</date>\n" +
                "    <time>13:50:00</time>\n" +
                "    <scrapeErrorCode>INVALID_CREDENTIALS</scrapeErrorCode>\n" +
                "</scrape-session>";
    }

    private ScrapeObject buildTestScrapeObject() {
        ScrapeObject scrapeObject = new ScrapeObject();
        scrapeObject.setBaseUrl("www.telkom.co.za");
        scrapeObject.setDate("10/01/2008");
        scrapeObject.setTime("13:50:00");

        DataPairs dataPairs = new DataPairs();
        List<DataPair> dataPairList = new ArrayList<>();
        DataPair dataPair = new DataPair();
        dataPairList.add(dataPair);
        dataPairs.setDataPairs(dataPairList);

        dataPair.setText("Account no");
        dataPair.setValue("53844946068883");
        return scrapeObject;
    }

    private Error buildTestScrapeError() {
        Error error = new Error();
        error.setBaseUrl("www.telkom.co.za");
        error.setDate("10/01/2008");
        error.setTime("13:50:00");
        error.setScrapeErrorCode("INVALID_CREDENTIALS");
        return error;
    }

    private String getSampleXmlResponse() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<scrape-session>\n" +
                "    <base-url>www.telkom.co.za</base-url>\n" +
                "    <date>10/01/2008</date>\n" +
                "    <time>13:50:00</time>\n" +
                "    <datapairs>\n" +
                "        <datapair id=\"001\">\n" +
                "            <text>Account no</text>\n" +
                "            <value>53844946068883</value>\n" +
                "        </datapair>\n" +
                "        <datapair id=\"002\">\n" +
                "            <text>Service ref</text>\n" +
                "            <value>0117838898</value>\n" +
                "        </datapair>\n" +
                "        <datapair id=\"003\">\n" +
                "            <text>Previous Invoice</text>\n" +
                "            <value>R512.22</value>\n" +
                "        </datapair>\n" +
                "        <datapair id=\"004\">\n" +
                "            <text>Payment</text>\n" +
                "            <value>R513.00</value>\n" +
                "        </datapair>\n" +
                "        <datapair id=\"005\">\n" +
                "            <text>Opening Balance</text>\n" +
                "            <value>R0.78</value>\n" +
                "        </datapair>\n" +
                "    </datapairs>\n" +
                "</scrape-session>";
    }

}