import { useEffect, useState } from 'react';
import {
  BlogPostReponse,
  deleteBlogPostById,
  getAllBlogPosts,
} from '../../services/blog-post-services';
import BlogPost from '../../components/BlogPost/BlogPost';

const BlogPostsPage = () => {
  const [posts, setPosts] = useState<BlogPostReponse[]>([]);
  useEffect(() => {
    getAllBlogPosts()
      .then((data) => setPosts(data))
      .catch((e) => console.log(e));
  }, []);

  const onDelete = async (id: number) => {
    const confirmed = confirm('Are you sure?');
    if (!confirmed) {
      return;
    }
    const isDeleted = await deleteBlogPostById(id).catch((e) => {
      console.log(e);
      return false;
    });
    if (isDeleted) {
      const updatedPosts = posts.filter((post) => post.id !== id);
      setPosts(updatedPosts);
    }
  };
  return (
    <>
      {posts.map((post) => (
        <BlogPost key={post.id} post={post} onDelete={onDelete} />
      ))}
    </>
  );
};
export default BlogPostsPage;
