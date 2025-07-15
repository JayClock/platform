import { injectable } from 'inversify';
import { User, Users as IUsers } from '@platform/domain';

@injectable()
export class Users implements IUsers {
  async findByIdentity(): Promise<User> {
    await new Promise(resolve => setTimeout(resolve, 1000));
    return new User('1', { name: 'aaa', email: '@email' });
  }
}
