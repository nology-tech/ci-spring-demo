import { useNavigate } from 'react-router-dom';
import BlogPostForm from '../../components/BlogPostForm/BlogPostForm';
import { BlogPostFormData } from '../../components/BlogPostForm/schema';
import { createBlogPost } from '../../services/blog-post-services';

const CreateBlogPostPage = () => {
  const navigate = useNavigate();
  const onSubmit = async (data: BlogPostFormData) => {
    createBlogPost(data)
      .then(() => navigate('/'))
      .catch((e) => console.log(e));
  };
  return (
    <>
      <h1>Create a Blog Post</h1>
      <BlogPostForm onSubmit={onSubmit} />
    </>
  );
};

export default CreateBlogPostPage;
