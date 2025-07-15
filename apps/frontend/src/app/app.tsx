import { User } from '@platform/domain';
import { useSignals } from '@preact/signals-react/runtime';
import { useState } from 'react';

export function App() {
  useSignals();
  const [user] = useState(() => new User('1', { name: 'aaa', email: 'bbb' }));
  const description = user.getDescription();
  return (
    <div>
      {description.email}
      {description.name}
      <button onClick={() => user.changeName()}>changeName</button>
    </div>
  );
}

export default App;
