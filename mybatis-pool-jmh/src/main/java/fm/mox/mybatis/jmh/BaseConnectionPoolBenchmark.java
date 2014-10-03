package fm.mox.mybatis.jmh;

import fm.mox.mybatis.BlogDao;
import fm.mox.mybatis.BlogMapper;
import fm.mox.mybatis.dto.Blog;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.util.concurrent.TimeUnit;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
@State(value = Scope.Benchmark)
public abstract class BaseConnectionPoolBenchmark {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseConnectionPoolBenchmark.class);

    protected DataSource dataSource;

    protected BlogDao blogDao;

    @Param(value = { "1", "5", "100" })
    protected int maximumPoolSize;

    public BaseConnectionPoolBenchmark() {

    }

    @Setup(value = Level.Iteration)
    public void setupBlogDao() {

        this.dataSource = buildDataSource();

        TransactionFactory transactionFactory = new JdbcTransactionFactory();

        Environment environment = new Environment("development", transactionFactory,
                this.dataSource);

        Configuration configuration = new Configuration(environment);
        configuration.getTypeAliasRegistry().registerAlias(Blog.class);
        configuration.addMapper(BlogMapper.class);

        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = builder.build(configuration);

        this.blogDao = new BlogDao(sqlSessionFactory);
    }

    public abstract DataSource buildDataSource();

    @TearDown(value = Level.Iteration)
    public abstract void tearDownDataSource();

    @Benchmark
    @BenchmarkMode(Mode.SampleTime)
    @Fork(value = 1)
    @Threads(value = 2)
    @Warmup(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
    @Measurement(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public Blog testGetById() {

        return blogDao.getById(1);

    }
}
