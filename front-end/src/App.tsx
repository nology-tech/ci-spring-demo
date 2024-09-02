import { BrowserRouter, Route, Routes } from 'react-router-dom';
import BlogPostsPage from './pages/BlogPostsPage/BlogPostsPage';
import CreateBlogPostPage from './pages/CreateBlogPostPage/CreateBlogPostPage';
import EditBlogPostPage from './pages/EditBlogPostPage/EditBlogPostPage';

function App() {
  return (
    <>
      <BrowserRouter>
        <h1>My Blog App</h1>
        <Routes>
          <Route path="/" element={<BlogPostsPage />} />
          <Route path="/posts/new" element={<CreateBlogPostPage />} />
          <Route path="/posts/:id/edit" element={<EditBlogPostPage />} />
        </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;
