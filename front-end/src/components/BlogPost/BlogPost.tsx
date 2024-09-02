import { Link } from 'react-router-dom';
import { BlogPostReponse } from '../../services/blog-post-services';
import dayjs from 'dayjs';
import relativeTime from 'dayjs/plugin/relativeTime';
dayjs.extend(relativeTime);

interface BlogPostProps {
  post: BlogPostReponse;
  onDelete: (id: number) => Promise<unknown>;
}

const BlogPost = ({ post, onDelete }: BlogPostProps) => {
  return (
    <div key={post.id}>
      <h2>{post.title}</h2>
      <h3>{post.category}</h3>
      <h4>Last Edited: {dayjs(post.updatedAt).fromNow()}</h4>
      <p>{post.content}</p>
      <button onClick={() => onDelete(post.id)}>Delete</button>
      <Link to={`/posts/${post.id}/edit`}>Edit</Link>
    </div>
  );
};

export default BlogPost;
