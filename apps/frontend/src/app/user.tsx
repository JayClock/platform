import { useQuery } from '@tanstack/react-query';
import { Users, USERS_TOKEN } from '@platform/domain';
import inversify from '@platform/persistent-inversify';

const users: Users = inversify.get(USERS_TOKEN);

export const User = () => {
  const { data, isPending } = useQuery({
    queryKey: ['key'],
    queryFn: () => users.findByIdentity(),
  });

  if (isPending) {
    return 'loading';
  }

  const description = data?.getDescription();
  return (
    <div>
      {description?.email}
      {description?.name}
      <button onClick={() => data?.changeName()}>changeName</button>
    </div>
  );
};
