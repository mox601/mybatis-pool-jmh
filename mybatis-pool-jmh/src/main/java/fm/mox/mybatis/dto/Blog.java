package fm.mox.mybatis.dto;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class Blog {

    public static final Blog NULL = new Blog();

    private final int id;

    private final String content;

    public Blog() {

        this(-1, "");

    }

    public Blog(int id, String content) {

        this.id = id;
        this.content = content;
    }

    public int getId() {

        return id;
    }

    public String getContent() {

        return content;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Blog blog = (Blog) o;

        if (id != blog.id) {
            return false;
        }
        if (content != null ? !content.equals(blog.content) : blog.content != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {

        int result = id;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {

        return "Blog{" +
               "id=" + id +
               ", content='" + content + '\'' +
               '}';
    }
}
