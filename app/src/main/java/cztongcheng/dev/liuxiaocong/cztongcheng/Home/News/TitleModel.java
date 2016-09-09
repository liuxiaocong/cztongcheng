package cztongcheng.dev.liuxiaocong.cztongcheng.Home.News;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;

/**
 * Created by LiuXiaocong on 8/31/2016.
 */
@Entity
public class TitleModel implements Serializable {
    @Id(autoincrement = true)
    private Long id;

    private String title;
    private String content;
    private String orginal;
    private String imageUrl;


    @Convert(converter = ENewsTypeConverter.class, columnType = Integer.class)
    private ENewsType newsType;

    @Generated(hash = 707831110)
    public TitleModel(Long id, String title, String content, String orginal, String imageUrl,
                      ENewsType newsType) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.orginal = orginal;
        this.imageUrl = imageUrl;
        this.newsType = newsType;
    }

    @Generated(hash = 1146938928)
    public TitleModel() {
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setOrginal(String orginal) {
        this.orginal = orginal;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setNewsType(ENewsType newsType) {
        this.newsType = newsType;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getOrginal() {
        return orginal;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public ENewsType getNewsType() {
        return newsType;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}
