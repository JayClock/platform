import { Container } from 'inversify';
import { Users } from './associations/users.js';
import { USERS_TOKEN } from '@platform/domain';

const inversify = new Container();

inversify.bind(USERS_TOKEN).to(Users).inSingletonScope();

export default inversify;
