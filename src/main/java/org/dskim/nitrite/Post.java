package org.dskim.nitrite;

import org.dizitart.no2.Document;
import org.dizitart.no2.mapper.Mappable;
import org.dizitart.no2.mapper.NitriteMapper;

public class Post implements Mappable {
    public String id;		// 고유 아이디
    public String title;

    @Override
    public Document write(NitriteMapper mapper) {
        Document document = new Document();
        document.put("id", id);
        document.put("title", title);
        return document;
    }

    @Override
    public void read(NitriteMapper mapper, Document document) {
        if (document != null) {
            id = (String)document.get("id");
            title = (String)document.get("title");
        }
    }
}
