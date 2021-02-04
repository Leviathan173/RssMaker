package per.leviathan173.entity;

public class Article {
    private final String title;
    private final String publishTime;
    private final String author;
    private final String summary;
    private final String imgLink;
    private final String link;
    private final String magnet;

    public String getPublishTime() {
        return publishTime;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getSummary() {
        return summary;
    }

    public String getImgLink() {
        return imgLink;
    }

    public String getLink() {
        return link;
    }

    public String getMagnet() {
        return magnet;
    }


    /***
     *
     * @param title 标题
     * @param publishTime 发布时间
     * @param author 作者
     * @param summary 简介
     * @param imgLink 图片链接
     * @param link 文章链接
     * @param magnet 磁力链接
     */
    public Article(String title, String publishTime, String author, String summary, String imgLink, String link, String magnet) {
        this.title = title;
        this.publishTime = publishTime;
        this.author = author;
        this.summary = summary;
        this.imgLink = imgLink;
        this.link = link;
        this.magnet = magnet;
    }

    @Override
    public String toString() {
        return "per.leviathan173.entity.Article{" +
                "title='" + title + '\'' +
                ", publishTime='" + publishTime + '\'' +
                ", author='" + author + '\'' +
                ", summary='" + summary + '\'' +
                ", imgLink='" + imgLink + '\'' +
                ", link='" + link + '\'' +
                ", magnet='" + magnet + '\'' +
                '}';
    }
}
