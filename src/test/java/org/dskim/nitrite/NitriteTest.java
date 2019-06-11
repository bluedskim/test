package org.dskim.nitrite;

import org.dizitart.no2.*;
import org.dizitart.no2.filters.Filters;
import org.dizitart.no2.util.Iterables;
import org.h2.store.fs.FileUtils;
import org.junit.AfterClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NitriteTest {
    private static Logger logger = LoggerFactory.getLogger(NitriteTest.class);
    Document stat = new Document();
    static String dbFilePath = "/tmp/test.db";
    static int totalDonloadedAmount = 100;
    static int totalDonloadedBlogCount = 200;
    static int totalDonloadedPostCount = 300;

    @Test
    public void t1_documentCRUtest() {
        Nitrite db = Nitrite.builder()
                .compressed()
                .filePath(dbFilePath)
                .openOrCreate()
                ;

        // Create a NitriteController Collection
        NitriteCollection collection = db.getCollection("test");

        stat.put("name", "stat");
        stat.put("totalDonloadedAmount", totalDonloadedAmount);
        stat.put("totalDonloadedBlogCount", totalDonloadedBlogCount);
        stat.put("totalDonloadedPostCount", totalDonloadedPostCount);
        logger.debug("stat={}", stat);

        WriteResult writeResult = collection.insert(stat);
        logger.debug("doc.getId()={}", stat.getId());
        NitriteId nitriteId = Iterables.firstOrDefault(writeResult);
        assertEquals(nitriteId, stat.getId());

        Document sameValueDoc = collection.getById(nitriteId);
        logger.debug("doc.get(\"totalDonloadedAmount\")={}, sameValueDoc.get(\"totalDonloadedAmount\")={}"
                ,stat.get("totalDonloadedAmount")
                ,sameValueDoc.get("totalDonloadedAmount")
        );
        assertEquals(stat, sameValueDoc);
        db.close();
    }

    @Test
    public void t2_documentRUDtest() {
        Nitrite db = Nitrite.builder()
                .compressed()
                .filePath("/tmp/test.db")
                .openOrCreate()
                ;

        // Create a NitriteController Collection
        NitriteCollection collection = db.getCollection("test");

        Cursor results = collection.find(Filters.eq("name", "stat"));
        logger.debug("results.size()={}", results.size());
        assertEquals(results.size(), 1);
        Document selected = results.firstOrDefault();

        logger.debug("selected={}", selected);
        assertEquals(totalDonloadedAmount, selected.get("totalDonloadedAmount"));

        selected.put("totalDonloadedAmount", totalDonloadedAmount * 2);
        collection.update(selected);
        results = collection.find(Filters.eq("name", "stat"));
        logger.debug("results.size()={}", results.size());
        assertEquals(results.size(), 1);
        selected = results.firstOrDefault();
        assertEquals(totalDonloadedAmount * 2, selected.get("totalDonloadedAmount"));

        db.close();
    }

    //@Test
    public void t2_dontClose() {
        Nitrite db = Nitrite.builder()
                .compressed()
                .filePath("/tmp/test.db")
                .openOrCreate()
                ;

        System.exit(1);
    }

    @AfterClass
    public static void doYourOneTimeTeardown() {
        FileUtils.delete(dbFilePath);
    }
}
