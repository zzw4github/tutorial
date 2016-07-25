package me.zzw.app.nutz.module;

/**
 * Created by Administrator on 2016-07-25.
 */
import me.zzw.app.nutz.service.EmailService;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.lang.random.R;

public abstract class BaseModule {

    /** 注入同名的一个ioc对象 */
    @Inject protected Dao dao;

    @Inject protected EmailService emailService;

    protected byte[] emailKEY = R.sg(24).next().getBytes();

}
