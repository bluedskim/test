package org.dskim.nitrite;

import org.dizitart.no2.Document;
import org.dizitart.no2.mapper.Mappable;
import org.dizitart.no2.mapper.NitriteMapper;

public class Blog implements Mappable {
    public String serviceName; // ex) 네이버 블로그, 이글루스, 티스토리

    public String userId;	// 해당 서비스의 사용자 아이디(baseDir의 최하위 폴더명)
    public String blogName;
    public String email;
    public Post currentPost;
    public int currentPostNumber = 0;

    public Blog(String userId) {
        this.userId = userId;
    }

    @Override
    public Document write(NitriteMapper mapper) {
        Document document = new Document();
        document.put("userId", userId);
        document.put("blogName", blogName);
        document.put("email", email);
        document.put("currentPost", currentPost);
        document.put("currentPostNumber", currentPostNumber);
        return document;
    }

    @Override
    public void read(NitriteMapper mapper, Document document) {
        if (document != null) {
            userId = (String)document.get("userId");
            blogName = (String)document.get("blogName");
            email = (String)document.get("email");
            currentPost = (Post)document.get("currentPost");
            currentPostNumber = (Integer)document.get("currentPostNumber");
        }
    }
}
