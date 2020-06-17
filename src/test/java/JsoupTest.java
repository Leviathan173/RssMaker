import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.List;

public class JsoupTest {
    private static String data;
    void Init(){
        data =
                "\t<link rel=\"alternate\" hreflang=\"ja\" href=\"/wp/?lang=ja\"/><link rel=\"alternate\" hreflang=\"en\" href=\"/wp/?lang=en\"/><link rel=\"stylesheet\" href=\"https://www.liuli.se/wp/wp-content/plugins/wp-page-numbers/panther/wp-page-numbers.css\" type=\"text/css\" media=\"screen\" /><style type=\"text/css\" id=\"custom-background-css\">\n" +
                "body.custom-background { background-image: url(\"https://www.liuli.se/wp/wp-content/uploads/2016/06/camouflage.png\"); background-position: left top; background-size: auto; background-repeat: repeat; background-attachment: scroll; }\n" +
                "</style>\n" +
                "\t\n" +
                "</head>\n" +
                "\n" +
                "<body class=\"home blog custom-background two-column right-sidebar wpf-dark wpft-\">\n" +
                "<div id=\"page\" class=\"hfeed\">\n" +
                "\t<header id=\"branding\" role=\"banner\">\n" +
                "\t\t\t<hgroup>\n" +
                "\t\t\t\t<h1 id=\"site-title\"><span><a href=\"https://www.liuli.se/wp/\" rel=\"home\">琉璃神社 ★ HACG.me</a></span></h1>\n" +
                "\t\t\t\t<h2 id=\"site-description\">最新的ACG资讯 分享同人动漫的快乐</h2>\n" +
                "\t\t\t</hgroup>\n" +
                "\n" +
                "\t\t\t\t\t\t<a href=\"https://www.liuli.se/wp/\">\n" +
                "\t\t\t\t\t\t\t\t\t<img src=\"https://www.liuli.se/wp/wp-content/uploads/2019/02/2019.jpg\" width=\"1250\" height=\"360\" alt=\"琉璃神社 ★ HACG.me\" />\n" +
                "\t\t\t\t\t\t\t</a>\n" +
                "\t\t\t\n" +
                "\t\t\t\t\t\t\t\t<form method=\"get\" id=\"searchform\" action=\"https://www.liuli.se/wp/\">\n" +
                "\t\t<label for=\"s\" class=\"assistive-text\">搜索</label>\n" +
                "\t\t<input type=\"text\" class=\"field\" name=\"s\" id=\"s\" placeholder=\"搜索\" />\n" +
                "\t\t<input type=\"submit\" class=\"submit\" name=\"submit\" id=\"searchsubmit\" value=\"搜索\" />\n" +
                "\t</form>\n" +
                "\t\t\t\n" +
                "\t\t\t<nav id=\"access\" role=\"navigation\">\n" +
                "\t\t\t\t<h3 class=\"assistive-text\">主页</h3>\n" +
                "\t\t\t\t\t\t\t\t<div class=\"skip-link\"><a class=\"assistive-text\" href=\"#content\">跳至主内容区域</a></div>\n" +
                "\t\t\t\t\t\t\t\t\t<div class=\"skip-link\"><a class=\"assistive-text\" href=\"#secondary\">跳至副内容区域</a></div>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"menu-%e8%8f%9c%e5%8d%95ssl-container\"><ul id=\"menu-%e8%8f%9c%e5%8d%95ssl\" class=\"menu\"><li id=\"menu-item-21941\" class=\"menu-item menu-item-type-custom menu-item-object-custom current-menu-item menu-item-21941\"><a href=\"/wp/\" aria-current=\"page\">最新更新</a></li>\n" +
                "<li id=\"menu-item-67909\" class=\"menu-item menu-item-type-taxonomy menu-item-object-category menu-item-67909\"><a href=\"https://www.liuli.se/wp/age.html\">文章</a></li>\n" +
                "<li id=\"menu-item-67902\" class=\"menu-item menu-item-type-taxonomy menu-item-object-category menu-item-67902\"><a href=\"https://www.liuli.se/wp/anime.html\">动画</a></li>\n" +
                "<li id=\"menu-item-67910\" class=\"menu-item menu-item-type-taxonomy menu-item-object-category menu-item-67910\"><a href=\"https://www.liuli.se/wp/comic.html\">漫画</a></li>\n" +
                "<li id=\"menu-item-67911\" class=\"menu-item menu-item-type-taxonomy menu-item-object-category menu-item-67911\"><a href=\"https://www.liuli.se/wp/game.html\">游戏</a></li>\n" +
                "<li id=\"menu-item-77050\" class=\"menu-item menu-item-type-taxonomy menu-item-object-category menu-item-has-children menu-item-77050\"><a href=\"https://www.liuli.se/wp/other.html\">其他</a>\n" +
                "<ul class=\"sub-menu\">\n" +
                "\t<li id=\"menu-item-77051\" class=\"menu-item menu-item-type-taxonomy menu-item-object-category menu-item-77051\"><a href=\"https://www.liuli.se/wp/goods.html\">周边</a></li>\n" +
                "\t<li id=\"menu-item-77052\" class=\"menu-item menu-item-type-taxonomy menu-item-object-category menu-item-77052\"><a href=\"https://www.liuli.se/wp/book.html\">轻小说</a></li>\n" +
                "\t<li id=\"menu-item-77053\" class=\"menu-item menu-item-type-taxonomy menu-item-object-category menu-item-77053\"><a href=\"https://www.liuli.se/wp/op.html\">音乐</a></li>\n" +
                "</ul>\n" +
                "</li>\n" +
                "<li id=\"menu-item-68071\" class=\"menu-item menu-item-type-post_type menu-item-object-page menu-item-68071\"><a href=\"https://www.liuli.se/wp/about.html\">使用说明</a></li>\n" +
                "<li id=\"menu-item-68070\" class=\"menu-item menu-item-type-post_type menu-item-object-page menu-item-68070\"><a href=\"https://www.liuli.se/wp/community\">讨论版</a></li>\n" +
                "<li id=\"menu-item-67914\" class=\"menu-item menu-item-type-custom menu-item-object-custom menu-item-has-children menu-item-67914\"><a href=\"#\">语言</a>\n" +
                "<ul class=\"sub-menu\">\n" +
                "\t<li id=\"menu-item-67913\" class=\"menu-item menu-item-type-custom menu-item-object-custom menu-item-67913\"><a href=\"?lang=ja\">日本語</a></li>\n" +
                "\t<li id=\"menu-item-67917\" class=\"menu-item menu-item-type-custom menu-item-object-custom menu-item-67917\"><a href=\"?lang=zh\">中文</a></li>\n" +
                "\t<li id=\"menu-item-67915\" class=\"menu-item menu-item-type-custom menu-item-object-custom menu-item-67915\"><a href=\"?lang=en\">English</a></li>\n" +
                "</ul>\n" +
                "</li>\n" +
                "</ul></div>\t\t\t</nav><!-- #access -->\n" +
                "\t</header><!-- #branding -->\n" +
                "\n" +
                "\n" +
                "\t<div id=\"main\">\n" +
                "\n" +
                "\t\t<div id=\"primary\">\n" +
                "\t\t\t<div id=\"content\" role=\"main\">\n" +
                "\n" +
                "\t\t\t\n" +
                "\t\t\t\t\t\t<nav id=\"nav-above\">\n" +
                "\t\t\t<h3 class=\"assistive-text\">文章导航</h3>\n" +
                "\t\t\t<div class=\"nav-previous\"><a href=\"https://www.liuli.se/wp/page/2\" ><span class=\"meta-nav\">&larr;</span> 早期文章</a></div>\n" +
                "\t\t\t<div class=\"nav-next\"></div>\n" +
                "\t\t</nav><!-- #nav-above -->\n" +
                "\t\n" +
                "\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\n" +
                "\t<article id=\"post-63117\" class=\"post-63117 post type-post status-publish format-status sticky hentry category-age post_format-post-format-status\">\n" +
                "\t\t<header class=\"entry-header\">\n" +
                "\t\t\t<hgroup>\n" +
                "\t\t\t\t<h2 class=\"entry-title\"><a href=\"https://www.liuli.se/wp/63117.html\" rel=\"bookmark\"></a></h2>\n" +
                "\t\t\t\t<h3 class=\"entry-format\">状态</h3>\n" +
                "\t\t\t</hgroup>\n" +
                "\n" +
                "\t\t\t\t\t\t<div class=\"comments-link\">\n" +
                "\t\t\t\t<a href=\"https://www.liuli.se/wp/63117.html#comments\">391</a>\t\t\t</div>\n" +
                "\t\t\t\t\t</header><!-- .entry-header -->\n" +
                "\n" +
                "\t\t\t\t<div class=\"entry-content\">\n" +
                "\t\t\t<div class=\"avatar\">\n" +
                "\t\t\t\t<img alt='' src='//www.liuli.se/wp/wp-content/uploads/wpforo/avatars/acg-gy_1.jpg' class='avatar avatar-65 photo' height='65' width='65' />\t\t\t</div>\n" +
                "\n" +
                "\t\t\t<p>各位注意网站网址，<span style=\"color: #00ccff;\">谨防盗版网站</span>，由于经常被一些人攻击，有时候会换域名，<br />\n" +
                "当前域名是：<a href=\"https://www.liuli.se/wp/#\">liuli.se</a><br />\n" +
                "正版神社右侧有登陆选项，没有顶部广告图片轮播，也不会在点开帖子上方设有广告，而盗版则是与之相反的，也没有众多投稿者，比如H萌、PAD长、奶茶、青龙、张大爷、天使、yozakura、默示汉化等<br />\n" +
                "只要稍微注意一下，假的网站和我们同名的文章，更新速度要比我们的慢，一般我们这边发布文章后，假网站会过一段时间再来复制，又或者是修改提前发帖时间，稍微注意一下就能发现发帖评论时间对不上.<br />\n" +
                "请注意网站标题【琉璃神社★hacg.me】 才是真网站。<br />\n" +
                "假网站最大的特点是广告特别多，还有就是仿制的盗版网站很多功能没有复制完全，网页很难看。 <a href=\"https://www.liuli.se/wp/63117.html\" class=\"more-link\">继续阅读 <span class=\"meta-nav\">&rarr;</span></a></p>\n" +
                "\t\t\t\t\t</div><!-- .entry-content -->\n" +
                "\t\t\n" +
                "\t\t<footer class=\"entry-meta\">\n" +
                "\t\t\t<span class=\"sep\">发表于</span><a href=\"https://www.liuli.se/wp/63117.html\" title=\"下午8:49\" rel=\"bookmark\"><time class=\"entry-date\" datetime=\"2018-07-26T20:49:30+08:00\">2018年7月26日</time></a><span class=\"by-author\"><span class=\"sep\">由</span><span class=\"author vcard\"><a class=\"url fn n\" href=\"https://www.liuli.se/wp/author/acg-gy\" title=\"查看所有由多啦H萌发布的文章\" rel=\"author\">多啦H萌</a></span></span>\t\t\t\t\t\t<span class=\"sep\"> | </span>\n" +
                "\t\t\t<span class=\"comments-link\"><a href=\"https://www.liuli.se/wp/63117.html#comments\"><b>391</b>条回复</a></span>\n" +
                "\t\t\t\t\t\t\t\t</footer><!-- .entry-meta -->\n" +
                "\t</article><!-- #post-63117 -->\n" +
                "\n" +
                "\t\t\t\t\n" +
                "\t\t\t\t\t\n" +
                "\t<article id=\"post-78626\" class=\"post-78626 post type-post status-publish format-standard hentry category-game tag-eushully tag-rpg tag-slg tag-1898\">\n" +
                "\t\t<header class=\"entry-header\">\n" +
                "\t\t\t\t\t\t<h1 class=\"entry-title\"><a href=\"https://www.liuli.se/wp/78626.html\" rel=\"bookmark\">[エウシュリー] 天冥のコンキスタ</a></h1>\n" +
                "\t\t\t\n" +
                "\t\t\t\t\t\t<div class=\"entry-meta\">\n" +
                "\t\t\t\t<span class=\"sep\">发表于</span><a href=\"https://www.liuli.se/wp/78626.html\" title=\"下午11:09\" rel=\"bookmark\"><time class=\"entry-date\" datetime=\"2020-06-16T23:09:16+08:00\">2020年6月16日</time></a><span class=\"by-author\"><span class=\"sep\">由</span><span class=\"author vcard\"><a class=\"url fn n\" href=\"https://www.liuli.se/wp/author/acg-gy\" title=\"查看所有由多啦H萌发布的文章\" rel=\"author\">多啦H萌</a></span></span>\t\t\t</div><!-- .entry-meta -->\n" +
                "\t\t\t\n" +
                "\t\t\t\t\t\t<div class=\"comments-link\">\n" +
                "\t\t\t\t<a href=\"https://www.liuli.se/wp/78626.html#comments\">23</a>\t\t\t</div>\n" +
                "\t\t\t\t\t</header><!-- .entry-header -->\n" +
                "\n" +
                "\t\t\t\t<div class=\"entry-content\">\n" +
                "\t\t\t<p><img src=\"http://i2.acg.gy/20061613.jpg\" /><br />\n" +
                "这个游戏是本月除了幻想万华镜外销量最高的游戏。<br />\n" +
                "eushully社团开发的SLG游戏,剧情主要就是男主掌握诱惑技能，然后一路收女。<br />\n" +
                "游戏里大部分的女角色都有独立CG也会流血，天使恶魔什么的一大堆。有兴趣可以玩一下，游戏性还是很不错的 <a href=\"https://www.liuli.se/wp/78626.html\" class=\"more-link\">继续阅读 <span class=\"meta-nav\">&rarr;</span></a></p>\n" +
                "\t\t\t\t\t</div><!-- .entry-content -->\n" +
                "\t\t\n" +
                "\t\t<footer class=\"entry-meta\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"cat-links\">\n" +
                "\t\t\t\t<span class=\"entry-utility-prep entry-utility-prep-cat-links\">发表在</span> <a href=\"https://www.liuli.se/wp/game.html\" rel=\"category tag\">游戏</a>\t\t\t</span>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"sep\"> | </span>\n" +
                "\t\t\t\t\t\t\t<span class=\"tag-links\">\n" +
                "\t\t\t\t<span class=\"entry-utility-prep entry-utility-prep-tag-links\">标签为</span> <a href=\"https://www.liuli.se/wp/tag/eushully\" rel=\"tag\">Eushully</a>、<a href=\"https://www.liuli.se/wp/tag/rpg\" rel=\"tag\">RPG</a>、<a href=\"https://www.liuli.se/wp/tag/slg\" rel=\"tag\">SLG</a>、<a href=\"https://www.liuli.se/wp/tag/%e3%82%a8%e3%82%a6%e3%82%b7%e3%83%a5%e3%83%aa%e3%83%bc\" rel=\"tag\">エウシュリー</a>\t\t\t</span>\n" +
                "\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\t\t\t<span class=\"sep\"> | </span>\n" +
                "\t\t\t\t\t\t<span class=\"comments-link\"><a href=\"https://www.liuli.se/wp/78626.html#comments\"><b>23</b>条回复</a></span>\n" +
                "\t\t\t\n" +
                "\t\t\t\t\t</footer><!-- .entry-meta -->\n" +
                "\t</article><!-- #post-78626 -->\n" +
                "\n" +
                "\t\t\t\t\n" +
                "\t\t\t\t\t\n" +
                "\t<article id=\"post-78615\" class=\"post-78615 post type-post status-publish format-standard hentry category-game tag-adv tag-ensemble tag-50 tag-791 tag-29 tag-762\">\n" +
                "\t\t<header class=\"entry-header\">\n" +
                "\t\t\t\t\t\t<h1 class=\"entry-title\"><a href=\"https://www.liuli.se/wp/78615.html\" rel=\"bookmark\">[ensemble] Secret Agent～騎士学園の忍びなるもの～</a></h1>\n" +
                "\t\t\t\n" +
                "\t\t\t\t\t\t<div class=\"entry-meta\">\n" +
                "\t\t\t\t<span class=\"sep\">发表于</span><a href=\"https://www.liuli.se/wp/78615.html\" title=\"上午12:27\" rel=\"bookmark\"><time class=\"entry-date\" datetime=\"2020-06-16T00:27:57+08:00\">2020年6月16日</time></a><span class=\"by-author\"><span class=\"sep\">由</span><span class=\"author vcard\"><a class=\"url fn n\" href=\"https://www.liuli.se/wp/author/acg-gy\" title=\"查看所有由多啦H萌发布的文章\" rel=\"author\">多啦H萌</a></span></span>\t\t\t</div><!-- .entry-meta -->\n" +
                "\t\t\t\n" +
                "\t\t\t\t\t\t<div class=\"comments-link\">\n" +
                "\t\t\t\t<a href=\"https://www.liuli.se/wp/78615.html#comments\">23</a>\t\t\t</div>\n" +
                "\t\t\t\t\t</header><!-- .entry-header -->\n" +
                "\n" +
                "\t\t\t\t<div class=\"entry-content\">\n" +
                "\t\t\t<p><img src=\"http://i2.acg.gy/20061510.jpg\" /><br />\n" +
                "主人公御影迅是从江户时代开始就肩负国防任务忍者的子孙，<br />\n" +
                "在这样世家中成长的迅还在以忍者为目标修行，目标是秘密谍报员母亲的继承人。<br />\n" +
                "为了解决引起社会骚动的“蝙蝠事件”，他奉命转入冬華学園。</p>\n" +
                "<p>说到骑士和忍者，除了比较多的女仆咖啡厅，我有去过秋叶原的女骑士咖啡和女忍者咖啡厅，体验都还行，价格也不算高，<br />\n" +
                "只要点一杯饮品，大约2000日元，就能默认跪地或者你要求坐你旁边陪你唠嗑30分钟，<br />\n" +
                "你坐凳子她跪在你前面仰望你，挺刺激的。还可以让她们跳个舞唱歌。才艺都还不错。如果只需要精神享受这些XX咖啡都挺不错的 <a href=\"https://www.liuli.se/wp/78615.html\" class=\"more-link\">继续阅读 <span class=\"meta-nav\">&rarr;</span></a></p>\n" +
                "\t\t\t\t\t</div><!-- .entry-content -->\n" +
                "\t\t\n" +
                "\t\t<footer class=\"entry-meta\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"cat-links\">\n" +
                "\t\t\t\t<span class=\"entry-utility-prep entry-utility-prep-cat-links\">发表在</span> <a href=\"https://www.liuli.se/wp/game.html\" rel=\"category tag\">游戏</a>\t\t\t</span>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"sep\"> | </span>\n" +
                "\t\t\t\t\t\t\t<span class=\"tag-links\">\n" +
                "\t\t\t\t<span class=\"entry-utility-prep entry-utility-prep-tag-links\">标签为</span> <a href=\"https://www.liuli.se/wp/tag/adv\" rel=\"tag\">ADV</a>、<a href=\"https://www.liuli.se/wp/tag/ensemble\" rel=\"tag\">ensemble</a>、<a href=\"https://www.liuli.se/wp/tag/%e5%ad%a6%e5%9b%ad\" rel=\"tag\">学园</a>、<a href=\"https://www.liuli.se/wp/tag/%e5%bf%8d%e8%80%85\" rel=\"tag\">忍者</a>、<a href=\"https://www.liuli.se/wp/tag/%e6%81%8b%e7%88%b1\" rel=\"tag\">恋爱</a>、<a href=\"https://www.liuli.se/wp/tag/%e9%aa%91%e5%a3%ab\" rel=\"tag\">骑士</a>\t\t\t</span>\n" +
                "\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\t\t\t<span class=\"sep\"> | </span>\n" +
                "\t\t\t\t\t\t<span class=\"comments-link\"><a href=\"https://www.liuli.se/wp/78615.html#comments\"><b>23</b>条回复</a></span>\n" +
                "\t\t\t\n" +
                "\t\t\t\t\t</footer><!-- .entry-meta -->\n" +
                "\t</article><!-- #post-78615 -->\n" +
                "\n" +
                "\t\t\t\t\n" +
                "\t\t\t\t\t\n" +
                "\t<article id=\"post-76607\" class=\"post-76607 post type-post status-publish format-standard hentry category-age tag-541 tag-135 tag-3341\">\n" +
                "\t\t<header class=\"entry-header\">\n" +
                "\t\t\t\t\t\t<h1 class=\"entry-title\"><a href=\"https://www.liuli.se/wp/76607.html\" rel=\"bookmark\">MisakaTranslator——可能是目前最好用的 日文翻译器</a></h1>\n" +
                "\t\t\t\n" +
                "\t\t\t\t\t\t<div class=\"entry-meta\">\n" +
                "\t\t\t\t<span class=\"sep\">发表于</span><a href=\"https://www.liuli.se/wp/76607.html\" title=\"上午11:23\" rel=\"bookmark\"><time class=\"entry-date\" datetime=\"2020-06-14T11:23:33+08:00\">2020年6月14日</time></a><span class=\"by-author\"><span class=\"sep\">由</span><span class=\"author vcard\"><a class=\"url fn n\" href=\"https://www.liuli.se/wp/author/hanmin822\" title=\"查看所有由hanmin822发布的文章\" rel=\"author\">hanmin822</a></span></span>\t\t\t</div><!-- .entry-meta -->\n" +
                "\t\t\t\n" +
                "\t\t\t\t\t\t<div class=\"comments-link\">\n" +
                "\t\t\t\t<a href=\"https://www.liuli.se/wp/76607.html#comments\">47</a>\t\t\t</div>\n" +
                "\t\t\t\t\t</header><!-- .entry-header -->\n" +
                "\n" +
                "\t\t\t\t<div class=\"entry-content\">\n" +
                "\t\t\t<p><img src=\"http://i2.acg.gy/20061401.jpg\" /><br />\n" +
                "第一次在神社发文，如有错误请见谅，其实这篇文章最早应该发于3月份，<br />\n" +
                "但是由于种种原因一直未被审核通过，时隔几个月，<br />\n" +
                "MisakaTranslator已经有了非常多的功能。所以干脆将这篇文章重写一下再发布。 <a href=\"https://www.liuli.se/wp/76607.html\" class=\"more-link\">继续阅读 <span class=\"meta-nav\">&rarr;</span></a></p>\n" +
                "\t\t\t\t\t</div><!-- .entry-content -->\n" +
                "\t\t\n" +
                "\t\t<footer class=\"entry-meta\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"cat-links\">\n" +
                "\t\t\t\t<span class=\"entry-utility-prep entry-utility-prep-cat-links\">发表在</span> <a href=\"https://www.liuli.se/wp/age.html\" rel=\"category tag\">文章</a>\t\t\t</span>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"sep\"> | </span>\n" +
                "\t\t\t\t\t\t\t<span class=\"tag-links\">\n" +
                "\t\t\t\t<span class=\"entry-utility-prep entry-utility-prep-tag-links\">标签为</span> <a href=\"https://www.liuli.se/wp/tag/%e5%b7%a5%e5%85%b7\" rel=\"tag\">工具</a>、<a href=\"https://www.liuli.se/wp/tag/%e6%95%99%e7%a8%8b\" rel=\"tag\">教程</a>、<a href=\"https://www.liuli.se/wp/tag/%e7%bf%bb%e8%af%91%e5%99%a8\" rel=\"tag\">翻译器</a>\t\t\t</span>\n" +
                "\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\t\t\t<span class=\"sep\"> | </span>\n" +
                "\t\t\t\t\t\t<span class=\"comments-link\"><a href=\"https://www.liuli.se/wp/76607.html#comments\"><b>47</b>条回复</a></span>\n" +
                "\t\t\t\n" +
                "\t\t\t\t\t</footer><!-- .entry-meta -->\n" +
                "\t</article><!-- #post-76607 -->\n" +
                "\n" +
                "\t\t\t\t\n" +
                "\t\t\t\t\t\n" +
                "\t<article id=\"post-78592\" class=\"post-78592 post type-post status-publish format-standard hentry category-comic tag-comic-lo tag-loli tag-3342 tag-1087 tag-1042 tag-1200 tag-1083 tag-297\">\n" +
                "\t\t<header class=\"entry-header\">\n" +
                "\t\t\t\t\t\t<h1 class=\"entry-title\"><a href=\"https://www.liuli.se/wp/78592.html\" rel=\"bookmark\">[あいらんどう] メスっこ大好き TENMAコミックスLO</a></h1>\n" +
                "\t\t\t\n" +
                "\t\t\t\t\t\t<div class=\"entry-meta\">\n" +
                "\t\t\t\t<span class=\"sep\">发表于</span><a href=\"https://www.liuli.se/wp/78592.html\" title=\"下午10:25\" rel=\"bookmark\"><time class=\"entry-date\" datetime=\"2020-06-13T22:25:16+08:00\">2020年6月13日</time></a><span class=\"by-author\"><span class=\"sep\">由</span><span class=\"author vcard\"><a class=\"url fn n\" href=\"https://www.liuli.se/wp/author/acg-gy\" title=\"查看所有由多啦H萌发布的文章\" rel=\"author\">多啦H萌</a></span></span>\t\t\t</div><!-- .entry-meta -->\n" +
                "\t\t\t\n" +
                "\t\t\t\t\t\t<div class=\"comments-link\">\n" +
                "\t\t\t\t<a href=\"https://www.liuli.se/wp/78592.html#comments\">14</a>\t\t\t</div>\n" +
                "\t\t\t\t\t</header><!-- .entry-header -->\n" +
                "\n" +
                "\t\t\t\t<div class=\"entry-content\">\n" +
                "\t\t\t<p><img src=\"http://i2.acg.gy/20061301.jpg\" /><br />\n" +
                "茜新社旗下作者あいらんどう的第一个单行本。新人啊，画风相当的不错。<br />\n" +
                "作品相当有趣，包含了师生，兄妹，邻居，姐弟等值得收藏，如果你喜欢，可以去购买一本。<br />\n" +
                "图片由HHZ04个人汉化，本站不提供下载 <a href=\"https://www.liuli.se/wp/78592.html\" class=\"more-link\">继续阅读 <span class=\"meta-nav\">&rarr;</span></a></p>\n" +
                "\t\t\t\t\t</div><!-- .entry-content -->\n" +
                "\t\t\n" +
                "\t\t<footer class=\"entry-meta\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"cat-links\">\n" +
                "\t\t\t\t<span class=\"entry-utility-prep entry-utility-prep-cat-links\">发表在</span> <a href=\"https://www.liuli.se/wp/comic.html\" rel=\"category tag\">漫画</a>\t\t\t</span>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"sep\"> | </span>\n" +
                "\t\t\t\t\t\t\t<span class=\"tag-links\">\n" +
                "\t\t\t\t<span class=\"entry-utility-prep entry-utility-prep-tag-links\">标签为</span> <a href=\"https://www.liuli.se/wp/tag/comic-lo\" rel=\"tag\">comic lo</a>、<a href=\"https://www.liuli.se/wp/tag/loli\" rel=\"tag\">loli</a>、<a href=\"https://www.liuli.se/wp/tag/%e3%81%82%e3%81%84%e3%82%89%e3%82%93%e3%81%a9%e3%81%86\" rel=\"tag\">あいらんどう</a>、<a href=\"https://www.liuli.se/wp/tag/%e5%85%84%e5%a6%b9\" rel=\"tag\">兄妹</a>、<a href=\"https://www.liuli.se/wp/tag/%e5%a7%90%e5%bc%9f\" rel=\"tag\">姐弟</a>、<a href=\"https://www.liuli.se/wp/tag/%e5%b8%88%e7%94%9f\" rel=\"tag\">师生</a>、<a href=\"https://www.liuli.se/wp/tag/%e6%b1%89%e5%8c%96%e5%8d%95%e8%a1%8c%e6%9c%ac\" rel=\"tag\">汉化单行本</a>、<a href=\"https://www.liuli.se/wp/tag/%e8%8c%9c%e6%96%b0%e7%a4%be\" rel=\"tag\">茜新社</a>\t\t\t</span>\n" +
                "\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\t\t\t<span class=\"sep\"> | </span>\n" +
                "\t\t\t\t\t\t<span class=\"comments-link\"><a href=\"https://www.liuli.se/wp/78592.html#comments\"><b>14</b>条回复</a></span>\n" +
                "\t\t\t\n" +
                "\t\t\t\t\t</footer><!-- .entry-meta -->\n" +
                "\t</article><!-- #post-78592 -->\n" +
                "\n" +
                "\t\t\t\t\n" +
                "\t\t\t\t\t\n" +
                "\t<article id=\"post-78571\" class=\"post-78571 post type-post status-publish format-standard hentry category-game tag-srpg tag-3338 tag-36\">\n" +
                "\t\t<header class=\"entry-header\">\n" +
                "\t\t\t\t\t\t<h1 class=\"entry-title\"><a href=\"https://www.liuli.se/wp/78571.html\" rel=\"bookmark\">[らぐな貿易船団] 法利恩戰記(Furion Chronicles)(繁體中文版)</a></h1>\n" +
                "\t\t\t\n" +
                "\t\t\t\t\t\t<div class=\"entry-meta\">\n" +
                "\t\t\t\t<span class=\"sep\">发表于</span><a href=\"https://www.liuli.se/wp/78571.html\" title=\"下午11:24\" rel=\"bookmark\"><time class=\"entry-date\" datetime=\"2020-06-12T23:24:31+08:00\">2020年6月12日</time></a><span class=\"by-author\"><span class=\"sep\">由</span><span class=\"author vcard\"><a class=\"url fn n\" href=\"https://www.liuli.se/wp/author/acg-gy\" title=\"查看所有由多啦H萌发布的文章\" rel=\"author\">多啦H萌</a></span></span>\t\t\t</div><!-- .entry-meta -->\n" +
                "\t\t\t\n" +
                "\t\t\t\t\t\t<div class=\"comments-link\">\n" +
                "\t\t\t\t<a href=\"https://www.liuli.se/wp/78571.html#comments\">24</a>\t\t\t</div>\n" +
                "\t\t\t\t\t</header><!-- .entry-header -->\n" +
                "\n" +
                "\t\t\t\t<div class=\"entry-content\">\n" +
                "\t\t\t<p><img src=\"http://i2.acg.gy/20061202.jpg\" /><br />\n";
    }
    public boolean Test(){
        Print.Println("开始Jsoup测试");
        Init();
        Document doc = Jsoup.parse(data);
        List<Element> list = doc.getElementsByClass("entry-title");
        if(list != null){
            Print.Println("Jsoup测试成功");
            return true;
        }else {
            Print.Println("Jsoup测试失败！");
            return false;
        }
    }
}
