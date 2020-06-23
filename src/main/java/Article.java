import java.util.Arrays;

public class Article {
    private String title;
    private String content;
    private String imgLink;
    private String link;
    private String magnet;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getMagnet() {
        return magnet;
    }

    public void setMagnet(String magnet) {
        this.magnet = magnet;
    }

    @Override
    public String toString() {
        return "Article{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", imgLink='" + imgLink + '\'' +
                ", link='" + link + '\'' +
                ", magnets=" + magnet +
                '}';
    }

    public Article(String title, String content, String imgLink, String link, String magnet) {
        this.title = title;
        this.content = content;
        this.imgLink = imgLink;
        this.link = link;
        this.magnet = magnet;
    }

    public Article() {
    }
}
