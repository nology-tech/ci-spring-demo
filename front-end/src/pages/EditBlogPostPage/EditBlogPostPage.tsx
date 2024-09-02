import { useEffect, useState } from 'react';
import {
  BlogPostReponse,
  getBlogPostById,
  updateBlogPostById,
} from '../../services/blog-post-services';
import { useNavigate, useParams } from 'react-router-dom';
import BlogPostForm from '../../components/BlogPostForm/BlogPostForm';
import { BlogPostFormData } from '../../components/BlogPostForm/schema';

// enum FetchStatus {
//     IDLE,
//     LOADING,
//     SUCCESS,
//     FAILURE
// }
type FetchStatus = 'IDLE' | 'LOADING' | 'SUCCESS' | 'FAILURE';
const EditBlogPostPage = () => {
  const [fetchStatus, setFetchStatus] = useState<FetchStatus>('IDLE');
  const [error, setError] = useState<Error | null>(null);
  const [blogPost, setBlogPost] = useState<BlogPostReponse | null>(null);
  const { id } = useParams() as { id: string };
  const idNumber = parseInt(id);
  const navigate = useNavigate();
  useEffect(() => {
    setFetchStatus('LOADING');
    getBlogPostById(idNumber)
      .then((post) => {
        setFetchStatus('SUCCESS');
        setBlogPost(post);
      })
      .catch((e: Error) => {
        setFetchStatus('FAILURE');
        setError(e);
      });
  }, []);

  const formSubmit = (data: BlogPostFormData) => {
    updateBlogPostById(idNumber, data)
      .then(() => navigate('/'))
      .catch(() => alert('Failed to update post'));
  };
  return (
    <>
      {fetchStatus === 'LOADING' && <p>Loading</p>}
      {fetchStatus === 'FAILURE' && (
        <p style={{ color: 'red' }}>{error?.message}</p>
      )}
      {fetchStatus === 'SUCCESS' && blogPost && (
        <BlogPostForm
          onSubmit={formSubmit}
          formType="EDIT"
          defaultValues={blogPost}
        />
      )}
    </>
  );
};

export default EditBlogPostPage;
