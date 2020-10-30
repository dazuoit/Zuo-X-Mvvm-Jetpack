package com.zuo.xmvvm.net;

import java.io.Serializable;
import java.util.List;

/**
 * Description: <NewsDetail><br>
 * Author:      mxdl<br>
 * Date:        2019/5/27<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class NewsDetail {

    /**
     * AS : {"Query":"mvvm","FullResults":1,"Results":[{"Type":"AS","Suggests":[{"Txt":"mvvm","Type":"AS","Sk":"","HCS":0},{"Txt":"mvvm模式和mvc的区别","Type":"AS","Sk":"AS1"},{"Txt":"mvvm框架","Type":"AS","Sk":"AS2"},{"Txt":"mvvm模式","Type":"AS","Sk":"AS3"},{"Txt":"mvvm书籍","Type":"AS","Sk":"AS4"},{"Txt":"mvvm light","Type":"AS","Sk":"AS5"},{"Txt":"mvvm原理","Type":"AS","Sk":"AS6"},{"Txt":"mvvm的理解","Type":"AS","Sk":"AS7"}]}]}
     */








        /**
         * Query : mvvm
         * FullResults : 1
         * Results : [{"Type":"AS","Suggests":[{"Txt":"mvvm","Type":"AS","Sk":"","HCS":0},{"Txt":"mvvm模式和mvc的区别","Type":"AS","Sk":"AS1"},{"Txt":"mvvm框架","Type":"AS","Sk":"AS2"},{"Txt":"mvvm模式","Type":"AS","Sk":"AS3"},{"Txt":"mvvm书籍","Type":"AS","Sk":"AS4"},{"Txt":"mvvm light","Type":"AS","Sk":"AS5"},{"Txt":"mvvm原理","Type":"AS","Sk":"AS6"},{"Txt":"mvvm的理解","Type":"AS","Sk":"AS7"}]}]
         */

        private String Query;
        private int FullResults;
        private List<ResultsBean> Results;

        public String getQuery() {
            return Query;
        }

        public void setQuery(String Query) {
            this.Query = Query;
        }

        public int getFullResults() {
            return FullResults;
        }

        public void setFullResults(int FullResults) {
            this.FullResults = FullResults;
        }

        public List<ResultsBean> getResults() {
            return Results;
        }

        public void setResults(List<ResultsBean> Results) {
            this.Results = Results;
        }

        public static class ResultsBean {
            /**
             * Type : AS
             * Suggests : [{"Txt":"mvvm","Type":"AS","Sk":"","HCS":0},{"Txt":"mvvm模式和mvc的区别","Type":"AS","Sk":"AS1"},{"Txt":"mvvm框架","Type":"AS","Sk":"AS2"},{"Txt":"mvvm模式","Type":"AS","Sk":"AS3"},{"Txt":"mvvm书籍","Type":"AS","Sk":"AS4"},{"Txt":"mvvm light","Type":"AS","Sk":"AS5"},{"Txt":"mvvm原理","Type":"AS","Sk":"AS6"},{"Txt":"mvvm的理解","Type":"AS","Sk":"AS7"}]
             */

            private String Type;
            private List<SuggestsBean> Suggests;

            public String getType() {
                return Type;
            }

            public void setType(String Type) {
                this.Type = Type;
            }

            public List<SuggestsBean> getSuggests() {
                return Suggests;
            }

            public void setSuggests(List<SuggestsBean> Suggests) {
                this.Suggests = Suggests;
            }

            public static class SuggestsBean {
                /**
                 * Txt : mvvm
                 * Type : AS
                 * Sk :
                 * HCS : 0
                 */

                private String Txt;
                private String Type;
                private String Sk;
                private int HCS;

                public String getTxt() {
                    return Txt;
                }

                public void setTxt(String Txt) {
                    this.Txt = Txt;
                }

                public String getType() {
                    return Type;
                }

                public void setType(String Type) {
                    this.Type = Type;
                }

                public String getSk() {
                    return Sk;
                }

                public void setSk(String Sk) {
                    this.Sk = Sk;
                }

                public int getHCS() {
                    return HCS;
                }

                public void setHCS(int HCS) {
                    this.HCS = HCS;
                }
            }
        }

}
