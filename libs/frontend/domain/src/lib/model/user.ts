import { Entity } from '../arthtype/entity.js';
import { UserDescription } from '../description/user-description.js';

export class User implements Entity<string, UserDescription> {
  constructor(private identity: string, private description: UserDescription) {}

  getIdentity(): string {
    return this.identity;
  }

  getDescription(): UserDescription {
    return this.description;
  }
}
