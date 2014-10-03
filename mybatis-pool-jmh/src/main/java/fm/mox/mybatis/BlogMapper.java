package fm.mox.mybatis;

import fm.mox.mybatis.dto.Blog;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public interface BlogMapper {

    Blog selectBlog(int i);

}
