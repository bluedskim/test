package org.dskim.nitrite;

import org.dizitart.no2.*;
import org.dizitart.no2.util.Iterables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/nitrite")
public class NitriteController {
    private static Logger logger = LoggerFactory.getLogger(NitriteController.class);

    static int totalDonloadedAmount = 100;
    static int totalDonloadedBlogCount = 200;
    static int totalDonloadedPostCount = 300;

    @Autowired
    Nitrite nitrite;

    @GetMapping(value = "", produces = "application/json")
    public List<Document> nitrite() {
        // Create a NitriteController Collection
        NitriteCollection collection = nitrite.getCollection("test");

        Document stat = new Document();
        stat.put("name", "stat");
        stat.put("totalDonloadedAmount", totalDonloadedAmount);
        stat.put("totalDonloadedBlogCount", totalDonloadedBlogCount);
        stat.put("totalDonloadedPostCount", totalDonloadedPostCount);
        logger.debug("stat={}", stat);

        WriteResult writeResult = collection.insert(stat);
        logger.debug("doc.getId()={}", stat.getId());
        NitriteId nitriteId = Iterables.firstOrDefault(writeResult);

        Document sameValueDoc = collection.getById(nitriteId);
        logger.debug("stat.get(\"totalDonloadedAmount\")={}, sameValueDoc.get(\"totalDonloadedAmount\")={}"
                ,stat.get("totalDonloadedAmount")
                ,sameValueDoc.get("totalDonloadedAmount")
        );

        Cursor results = collection.find();
        logger.debug("results.size()={}", results.size());

        return results.toList();
    }
}
