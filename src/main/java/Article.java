public class Article {
    private String title;
    private String publishTime;
    private String author;
    private String summary;
    private String imgLink;
    private String link;
    private String magnet;

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
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
        return "Article{" +
                "title='" + title + '\'' +
                ", publishTime='" + publishTime + '\'' +
                ", author='" + author + '\'' +
                ", summary='" + summary + '\'' +
                ", imgLink='" + imgLink + '\'' +
                ", link='" + link + '\'' +
                ", magnet='" + magnet + '\'' +
                '}';
    }
    public Article() {
    }
}
