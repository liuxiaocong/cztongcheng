package cztongcheng.dev.liuxiaocong.cztongcheng.Config;

import java.util.List;

/**
 * Created by LiuXiaocong on 9/13/2016.
 */
public class ConfigBean {

    /**
     * id : 0
     * name : ECZCommon
     * title : 潮州
     * sourceList : [{"url":"http://3g.czbtv.com/wapczxw/","titleListFilter":"#czxw a","titleSubFilter":"","targetUrlFromTileFilter":"","targetUrlDomain":"http://3g.czbtv.com/wapczxw/","targetContentFilter":"#czxw"}]
     */

    private List<ItemListBean> itemList;

    public List<ItemListBean> getItemList() {
        return itemList;
    }

    public void setItemList(List<ItemListBean> itemList) {
        this.itemList = itemList;
    }

    public static class ItemListBean {
        private int id;
        private String name;
        private String title;
        /**
         * url : http://3g.czbtv.com/wapczxw/
         * titleListFilter : #czxw a
         * titleSubFilter :
         * targetUrlFromTileFilter :
         * targetUrlDomain : http://3g.czbtv.com/wapczxw/
         * targetContentFilter : #czxw
         */

        private List<SourceListBean> sourceList;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<SourceListBean> getSourceList() {
            return sourceList;
        }

        public void setSourceList(List<SourceListBean> sourceList) {
            this.sourceList = sourceList;
        }

        public static class SourceListBean {
            private String url;
            private String titleListFilter;
            private String titleSubFilter;
            private String targetUrlFromTileFilter;
            private String targetUrlDomain;
            private String targetContentFilter;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getTitleListFilter() {
                return titleListFilter;
            }

            public void setTitleListFilter(String titleListFilter) {
                this.titleListFilter = titleListFilter;
            }

            public String getTitleSubFilter() {
                return titleSubFilter;
            }

            public void setTitleSubFilter(String titleSubFilter) {
                this.titleSubFilter = titleSubFilter;
            }

            public String getTargetUrlFromTileFilter() {
                return targetUrlFromTileFilter;
            }

            public void setTargetUrlFromTileFilter(String targetUrlFromTileFilter) {
                this.targetUrlFromTileFilter = targetUrlFromTileFilter;
            }

            public String getTargetUrlDomain() {
                return targetUrlDomain;
            }

            public void setTargetUrlDomain(String targetUrlDomain) {
                this.targetUrlDomain = targetUrlDomain;
            }

            public String getTargetContentFilter() {
                return targetContentFilter;
            }

            public void setTargetContentFilter(String targetContentFilter) {
                this.targetContentFilter = targetContentFilter;
            }
        }
    }
}
