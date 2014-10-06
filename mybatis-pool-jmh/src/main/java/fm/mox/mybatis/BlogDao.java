package fm.mox.mybatis;

import fm.mox.mybatis.dto.Blog;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class BlogDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(BlogDao.class);

    private final SqlSessionFactory sessionFactory;

    public BlogDao(final SqlSessionFactory sessionFactory) {

        this.sessionFactory = sessionFactory;
    }

    public Blog getById(int anId) {

        Blog blog = Blog.NULL;

        try (SqlSession session = this.sessionFactory.openSession()) {
            BlogMapper mapper = session.getMapper(BlogMapper.class);
            blog = mapper.selectBlog(anId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return blog;
    }
}
