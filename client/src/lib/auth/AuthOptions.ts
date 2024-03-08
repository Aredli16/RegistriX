import { getKeycloakAuthOptions } from "common";

export const getAuthOptions = () =>
  getKeycloakAuthOptions(
    process.env.KEYCLOAK_CLIENT_ID!,
    process.env.KEYCLOAK_CLIENT_SECRET!,
    process.env.KEYCLOAK_ISSUER!,
  );
