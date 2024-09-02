import { json } from 'react-router-dom';
import { BlogPostFormData } from '../components/BlogPostForm/schema';

const baseURL = import.meta.env.VITE_APP_API_BASE_URL;
// {
//     "id": 5,
//     "title": "new title",
//     "content": "Working with spring",
//     "createdAt": "2024-08-08T01:51:25.818+00:00",
//     "updatedAt": "2024-08-08T02:11:29.251+00:00",
//     "category": "code"
// }

export interface BlogPostReponse {
  id: number;
  title: string;
  content: string;
  createdAt: string;
  updatedAt: string;
  category: string;
}

export const getAllBlogPosts = async () => {
  const response = await fetch(baseURL + '/posts');
  if (!response.ok) {
    throw new Error('Failed to fetch');
  }
  return (await response.json()) as BlogPostReponse[];
};

export const getBlogPostById = async (id: number) => {
  const response = await fetch(`${baseURL}/posts/${id}`);
  if (!response.ok) {
    if (response.status === 404) {
      throw new Error(await response.text());
    }
    throw new Error('Something went wrong');
  }
  return (await response.json()) as BlogPostReponse;
};

export const updateBlogPostById = async (
  id: number,
  data: BlogPostFormData
) => {
  const response = await fetch(`${baseURL}/posts/${id}`, {
    method: 'PATCH',
    body: JSON.stringify(data),
    headers: {
      'Content-Type': 'application/json',
    },
  });
  if (!response.ok) {
    throw new Error('Something went wrong');
  }
  return (await response.json()) as BlogPostReponse;
};

export const deleteBlogPostById = async (id: number) => {
  const response = await fetch(baseURL + `/posts/${id}`, {
    method: 'DELETE',
  });
  if (!response.ok) {
    throw new Error('Failed to delete');
  }
  return true;
};

export const createBlogPost = async (data: BlogPostFormData) => {
  const response = await fetch(baseURL + '/posts', {
    method: 'POST',
    body: JSON.stringify(data),
    headers: {
      'Content-Type': 'application/json',
    },
  });
  if (!response.ok) {
    throw new Error('Failed to post');
  }
  return (await response.json()) as BlogPostReponse;
};
