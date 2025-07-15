import { User } from './user.js';

export const USERS_TOKEN = "USERS_TOKEN"

export interface Users {
  findByIdentity(): Promise<User>;
}
