package aps.application.impl;

import aps.domain.model.scrape.DataPair;
import aps.domain.model.scrape.ScrapeObject;
import aps.domain.shared.ApsMaps;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MappingServiceImplTest {

    MappingServiceImpl toTest;
    ScrapeObject scrapeObject;
    ApsMaps apsMaps;


    @Before
    public void init() {
        toTest = new MappingServiceImpl();
        scrapeObject = new ScrapeObject();
        scrapeObject.setBaseUrl("www.test1.com");
        scrapeObject.setCustomerSurname("surname");
        scrapeObject.setCustomerName("name");
        List<DataPair> dps = new ArrayList<DataPair>();
        DataPair dp1 = new DataPair();
        dp1.setText("Statement number");
        dp1.setValue("dp1_value");
        dps.add(dp1);
        DataPair dp2 = new DataPair();
        dp2.setText("Account number");
        dp2.setValue("dp2_value");
        dps.add(dp2);
        DataPair dp3 = new DataPair();
        dp3.setText("Not a Field");
        dp3.setValue("dp3_value");
        dps.add(dp3);
        DataPair dp4 = new DataPair();
        dp4.setText("Electricity used");
        dp4.setValue("321");
        dps.add(dp4);
        scrapeObject.setDataPairs(dps);

        apsMaps = toTest.getMaps();
    }

    @Test
    public void standardMappings() {
        assertEquals("Account number", apsMaps.getStandardMaps().get(0).getLabel());
        assertEquals("String", apsMaps.getStandardMaps().get(0).getType());
        assertEquals("accountNumber", apsMaps.getStandardMaps().get(0).getInternalLabel());

        ScrapeObject mappedObject = toTest.createMappedObject(scrapeObject);
        assertEquals("www.test1.com", mappedObject.getBaseUrl());
        assertEquals("surname", mappedObject.getCustomerSurname());
        assertEquals("name", mappedObject.getCustomerName());
        assertEquals("accountNumber", mappedObject.getDataPairs().get(0).getText());
        assertEquals("dp1_value", mappedObject.getDataPairs().get(0).getValue());
        assertEquals("statementNumber", mappedObject.getDataPairs().get(1).getText());
        assertEquals("dp2_value", mappedObject.getDataPairs().get(1).getValue());
    }

    @Test
    public void createStatement() {

        ScrapeObject mappedObject = toTest.createMappedObject(scrapeObject);
        int result = toTest.createStatementFromScrapeObject(mappedObject);

        assertEquals(0, result);

    }
}

