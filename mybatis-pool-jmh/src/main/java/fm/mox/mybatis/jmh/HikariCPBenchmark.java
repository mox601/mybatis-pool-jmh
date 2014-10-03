package fm.mox.mybatis.jmh;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class HikariCPBenchmark extends BaseConnectionPoolBenchmark {

    private static final Logger LOGGER = LoggerFactory.getLogger(HikariCPBenchmark.class);

    public HikariCPBenchmark() {

        super();

    }

    @Override
    public DataSource buildDataSource() {

        HikariConfig config = new HikariConfig();
        config.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
        config.addDataSourceProperty("serverName", "localhost");
        config.addDataSourceProperty("port", "3306");
        config.addDataSourceProperty("databaseName", "mybatis_test");
        config.addDataSourceProperty("user", "mybatis_test");
        config.addDataSourceProperty("password", "password");

        config.setMaximumPoolSize(this.maximumPoolSize);
//        config.setMinimumIdle(this.maximumPoolSize);
        config.setMinimumIdle(1);
        config.setConnectionTimeout(1000L);

        return new HikariDataSource(config);
    }

    @Override
    public void tearDownDataSource() {

        final HikariDataSource hds = (HikariDataSource) dataSource;
        hds.close();
    }

}
