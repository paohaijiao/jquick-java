package com.github.paohaijiao.extract.banner;

import com.github.paohaijiao.banner.JQuickBanner;
import com.github.paohaijiao.banner.impl.JQuickBannerImpl;
import com.github.paohaijiao.config.JQuickBannerConfig;
import org.junit.Test;

import java.io.IOException;

public class JBannerTest {
    @Test
    public void banner() throws IOException {
        JQuickBannerConfig config=new JQuickBannerConfig();
        JQuickBanner banner=new JQuickBannerImpl(config);
        banner.printBanner();
        System.out.println("banner");
    }
}
