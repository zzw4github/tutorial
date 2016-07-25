package me.zzw.app.nutz;

import org.nutz.mvc.annotation.*;
import org.nutz.mvc.ioc.provider.ComboIocProvider;

/**
 * Created b
 * y Administrator on 2016-07-25.
 */
@ChainBy(args="mvc/nutzbook-mvc-chain.js")
@Localization(value="msg/", defaultLocalizationKey="zh-CN")
@SetupBy(value=MainSetup.class)
@IocBy(type=ComboIocProvider.class, args={"*js", "ioc/",
        "*anno", "net.wendal.nutzbook",
        "*tx",
        "*org.nutz.integration.quartz.QuartzIocLoader"})
@Modules(scanPackage=true)
public class MainModule {
}
