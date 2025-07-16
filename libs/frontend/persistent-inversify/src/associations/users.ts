import { injectable } from 'inversify';
import { User, UserLinks, Users as IUsers } from '@platform/domain';
import { api } from '../api.js';

interface UserBackend {
  id: string;
  name: string;
  email: string;
  _links: UserLinks;
}

@injectable()
export class Users implements IUsers {
  async findByIdentity(): Promise<User> {
    const res = await api.get<UserBackend>('/users/1');
    return new User(res.data.id, {
      name: res.data.name,
      email: res.data.email,
      _links: res.data._links,
    });
  }
}
