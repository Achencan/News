package com.example.ccdez.news314;

import java.util.List;

/**
 * Created by ccdez on 2017/9/13 0013.
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
