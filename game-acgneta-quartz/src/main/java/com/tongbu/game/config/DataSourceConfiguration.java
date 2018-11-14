package com.tongbu.game.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.tongbu.game.common.enums.DataSourceType;
import com.tongbu.game.entity.DataSourceEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jokin
 * @date 2018/9/29 13:45
 */
@Configuration
public class DataSourceConfiguration {
    private Logger logger = LoggerFactory.getLogger(DataSourceConfiguration.class);

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.game2element")
    public DataSourceEntity dataSourceGame() {
        return new DataSourceEntity();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.quartz")
    public DataSourceEntity dataSourceQuartz() {
        return new DataSourceEntity();
    }

    /**
     * 配置为默认库
     */
    @Bean(name = "master")
    public DataSource primaryDataSource() {
        DataSourceEntity sourceEntity = dataSourceGame();
        return druidDataSource(sourceEntity.getUrl(), sourceEntity.getUsername(), sourceEntity.getPassword(), sourceEntity.getDriverClassName());
    }

    /**
     * 第二个数据库配置
     */
    @Bean(name = "slave")
    public DataSource secondaryDataSource() {
        DataSourceEntity sourceEntity = dataSourceQuartz();
        return druidDataSource(sourceEntity.getUrl(), sourceEntity.getUsername(), sourceEntity.getPassword(), sourceEntity.getDriverClassName());
    }

    /**
     * \@Primary和@Qualifier这两个注解的意思:
     * \@Primary：  意思是在众多相同的bean中，优先使用@Primary注解的bean.
     * \@Qualifier ： 这个注解则指定某个bean有没有资格进行注入。
     */
    @Bean(name = "dynamicDataSource")
    @Qualifier("dynamicDataSource")
    @Primary
    public DataSource dataSource() {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        DataSource master = primaryDataSource();
        DataSource slave = secondaryDataSource();
        //设置默认数据源
        dynamicDataSource.setDefaultTargetDataSource(master);
        //配置多数据源
        Map<Object, Object> map = new HashMap<>();
        // key需要跟ThreadLocal中的值对应
        map.put(DataSourceType.Master.getName(), master);
        map.put(DataSourceType.Slave.getName(), slave);
        dynamicDataSource.setTargetDataSources(map);
        return dynamicDataSource;
    }


    /**
     * 使用连接池
     * */
    private DruidDataSource druidDataSource(String url, String userName, String password, String driverClassName) {
        // 使用DruidDataSource连接串配置
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(url);
        datasource.setUsername(userName);
        datasource.setPassword(password);
        datasource.setDriverClassName(driverClassName);
        // 初始化大小，最小，最大
        datasource.setMaxActive(20);
        datasource.setInitialSize(2);
        // 最大等待毫秒数, 单位为 ms, 如果超过此时间将接到异常,设为-1表示无限制
        datasource.setMaxWait(60000);
        // 最小等待(空闲)连接中的数量
        datasource.setMinIdle(1);
        // 在空闲连接回收器线程运行期间休眠的时间值,以毫秒为单位. 如果设置为非正数,则不运行空闲连接回收器线程
        datasource.setTimeBetweenEvictionRunsMillis(60000);
        //连接池中保持空闲而不被空闲连接回收器线程 ,回收的最小时间值,单位毫秒
        datasource.setMinEvictableIdleTimeMillis(300000);
        // SQL查询,用来验证从连接池取出的连接,在将连接返回给调用者之前.如果指定, 则查询必须是一个SQL SELECT并且必须返回至少一行记录
        datasource.setValidationQuery("SELECT 'x'");
        // 指明连接是否被空闲连接回收器(如果有)进行检验.如果检测失败, 则连接将被从池中去除.
        // 注意: 设置为true后如果要生效,validationQuery参数必须设置为非空字符串
        datasource.setTestWhileIdle(true);
        // 指明是否在从池中取出连接前进行检验,如果检验失败 则从池中去除连接并尝试取出另一个. 注意: 设置为true后如果要生
        // 效,validationQuery参数必须设置为非空字符串
        datasource.setTestOnBorrow(true);
        // 指明是否在归还到池中前进行检验
        datasource.setTestOnReturn(false);
        //开启池的prepared statement 池功能
        datasource.setPoolPreparedStatements(false);
        datasource.setMaxPoolPreparedStatementPerConnectionSize(20);
       /*
        // 通过connectProperties属性来打开mergeSql功能；慢SQL记录
        Properties properties = new Properties();
        properties.setProperty("druid.stat.mergeSql", "true");
        properties.setProperty("druid.stat.slowSqlMillis", "5000");
        datasource.setConnectProperties(properties);*/
        /*try {
            // 配置监控统计拦截的filters，去掉后监控界面sql无法统计，'wall'用于防火墙
            // https://blog.csdn.net/blueheart20/article/details/52384032
            // https://www.cnblogs.com/wenbronk/p/6553994.html
            // https://blog.csdn.net/moshowgame/article/details/80304198
            datasource.setFilters("stat,wall,log4j");
        } catch (SQLException e) {
            logger.error("druid configuration initialization filter", e);
        }*/
        return datasource;
    }

}
