import {
  QueryClient,
  QueryClientProvider,
} from '@tanstack/react-query';
import { User } from './user';

const queryClient = new QueryClient();

export function App() {
  return (
    <QueryClientProvider client={queryClient}>
     <User/>
    </QueryClientProvider>
  );
}

export default App;
