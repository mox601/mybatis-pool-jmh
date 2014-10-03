package fm.mox.mybatis.jmh;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class C3P0CPBenchmark extends BaseConnectionPoolBenchmark {

    private static final Logger LOGGER = LoggerFactory.getLogger(C3P0CPBenchmark.class);

    @Override
    public DataSource buildDataSource() {

        ComboPooledDataSource cpds = new ComboPooledDataSource();
        try {
            cpds.setDriverClass("com.mysql.jdbc.Driver");
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        }
        cpds.setJdbcUrl("jdbc:mysql://localhost:3306/mybatis_test");
        cpds.setUser("mybatis_test");
        cpds.setPassword("password");

//        cpds.setInitialPoolSize(this.maximumPoolSize);
        cpds.setInitialPoolSize(1);
//        cpds.setMinPoolSize(this.maximumPoolSize);
        cpds.setMinPoolSize(1);

        cpds.setAcquireIncrement(1);
        cpds.setMaxPoolSize(this.maximumPoolSize);

        return cpds;
    }

    @Override
    public void tearDownDataSource() {

        final ComboPooledDataSource cpds = (ComboPooledDataSource) this.dataSource;
        cpds.close();

    }
}
