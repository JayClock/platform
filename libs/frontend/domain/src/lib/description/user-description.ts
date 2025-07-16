export type UserDescription = Readonly<{
  name: string;
  email: string;
  _links: UserLinks;
}>;

export type UserLinks = {
  self: {
    href: string;
  };
  purchaser: {
    href: string;
  };
};
