package com.example.ccdez.news314;

import java.util.List;

/**
 * 公共类
 */

public class Bean {
    public String reason;
    public Second result;

    public class Second {
        public String stat;
        public List<Third> data;

        public class Third {
            public String title;
            public String date;
            public String url;
            public String author_name;
            public String thumbnail_pic_s;
        }
    }
}
