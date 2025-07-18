import { Entity } from '../arthtype/entity.js';
import { UserDescription, UserLinks } from '../description/index.js';
import { Signal, signal } from '@preact/signals-core';

export class User implements Entity<string, UserDescription> {
  private readonly description: Signal<UserDescription>;

  constructor(private identity: string, description: UserDescription) {
    this.description = signal(description);
  }

  getIdentity(): string {
    return this.identity;
  }

  getDescription(): UserDescription {
    return this.description.value;
  }

  getLinks(): UserLinks {
    return this.getLinks();
  }

  changeName() {
    this.description.value = {
      ...this.description.value,
      name: 'JayClock',
    };
  }
}
