import { render, screen } from '@testing-library/react';
import App from './App';

test('renders Navbar component', () => {
  render(<App />);
  const navbarElement = screen.getByText(/Finance/i);
  expect(navbarElement).toBeInTheDocument();
});

test('renders Graph component', () => {
  render(<App />);
  const graphElement = screen.getByRole('img'); // Assuming the Graph component renders an SVG or image
  expect(graphElement).toBeInTheDocument();
});
