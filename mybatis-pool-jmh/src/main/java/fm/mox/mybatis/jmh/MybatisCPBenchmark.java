package fm.mox.mybatis.jmh;

import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class MybatisCPBenchmark extends BaseConnectionPoolBenchmark {

    private static final Logger LOGGER = LoggerFactory.getLogger(MybatisCPBenchmark.class);

    public MybatisCPBenchmark() {

        super();

    }

    @Override
    public DataSource buildDataSource() {

        final PooledDataSource pds = new PooledDataSource("com.mysql.jdbc.Driver",
                "jdbc:mysql://localhost:3306/mybatis_test", "mybatis_test", "password");

        int poolMaximumIdleConnections = this.maximumPoolSize;

        if ((this.maximumPoolSize / 2) != 0) {
            poolMaximumIdleConnections = this.maximumPoolSize / 2;
        }

        pds.setPoolMaximumIdleConnections(poolMaximumIdleConnections);
        pds.setPoolMaximumActiveConnections(this.maximumPoolSize);
        return pds;
    }

    @Override
    public void tearDownDataSource() {

        final PooledDataSource pds = (PooledDataSource) dataSource;
        pds.forceCloseAll();
    }

}
