import { User } from './user.js';

export interface Users {
  findByIdentity(): Promise<User>;
}
